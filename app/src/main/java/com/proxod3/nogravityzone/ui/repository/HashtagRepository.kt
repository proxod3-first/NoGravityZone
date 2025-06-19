package com.proxod3.nogravityzone.ui.repository

import android.util.Log
import com.proxod3.nogravityzone.ui.models.Hashtag
import com.proxod3.nogravityzone.ui.models.Hashtag.Companion.HASHTAGS_COLLECTION
import com.proxod3.nogravityzone.ui.models.Hashtag.Companion.HASHTAG_LAST_USED
import com.proxod3.nogravityzone.ui.models.Hashtag.Companion.HASHTAG_USE_COUNT
import com.proxod3.nogravityzone.ui.models.toHashtag
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface IHashtagRepository {
    /**
     * Adds operations to update/create hashtags to the provided WriteBatch.
     * Increments counts for existing tags, creates new ones.
     *
     * @param batch The Firestore WriteBatch to add operations to.
     * @param tags The list of tags being used.
     */
    suspend fun addHashtagUpdatesToBatch(batch: WriteBatch, tags: List<String>)

    /**
     * Adds operations to decrement hashtag counts to the provided WriteBatch.
     *
     * @param batch The Firestore WriteBatch to add operations to.
     * @param tags The list of tags whose usage is being removed/decremented.
     */
    suspend fun addHashtagDecrementToBatch(batch: WriteBatch, tags: List<String>)
}

class HashtagRepository @Inject constructor(
    firestore: FirebaseFirestore
) : IHashtagRepository {

    private val hashtagsCollection = firestore.collection(HASHTAGS_COLLECTION)

    // Helper to load existing hashtags using Firestore
    private suspend fun loadHashTagData(tags: List<String>): List<Hashtag> {
        if (tags.isEmpty()) return emptyList()
        return try {
            // Use 'whereIn' query - limited to 30 tags per query
            val querySnapshot = hashtagsCollection.whereIn(Hashtag.TAG, tags.take(30)).get().await()
            querySnapshot.documents.mapNotNull { it.toObject(Hashtag::class.java) }
        } catch (e: Exception) {
            Log.e("HashtagRepository", "Error fetching hashtags: ${tags.joinToString()}", e)
            emptyList()
        }
    }

    override suspend fun addHashtagUpdatesToBatch(batch: WriteBatch, tags: List<String>) {
        if (tags.isEmpty()) return

        try {
            val uniqueTags =
                tags.map { it.lowercase().trim() }.filter { it.isNotEmpty() }.distinct()

            if (uniqueTags.isEmpty()) return

            // Fetch existing data for these tags
            val existingHashTags = loadHashTagData(uniqueTags)
            val existingTagMap =
                existingHashTags.associateBy { it.tag } // Map tag name to Hashtag object

            uniqueTags.forEach { tag ->
                val docRef = hashtagsCollection.document(tag) // Use tag name as document ID

                if (existingTagMap.containsKey(tag)) {
                    // Update existing hashtag count and timestamp
                    batch.update(docRef, HASHTAG_USE_COUNT, FieldValue.increment(1))
                    batch.update(
                        docRef,
                        HASHTAG_LAST_USED,
                        System.currentTimeMillis()
                    )
                } else {
                    // Create new hashtag document
                    val newHashtag = tag.toHashtag() // Assumes this sets count to 1 and timestamp
                    batch.set(docRef, newHashtag)
                }
            }
        } catch (e: Exception) {
            Log.e("HashtagRepository", "Error preparing hashtag updates for batch", e)
        }
    }

    override suspend fun addHashtagDecrementToBatch(batch: WriteBatch, tags: List<String>) {
        if (tags.isEmpty()) return

        try {
            val uniqueTags =
                tags.map { it.lowercase().trim() }.filter { it.isNotEmpty() }.distinct()
            if (uniqueTags.isEmpty()) return

            uniqueTags.forEach { tag ->
                val docRef = hashtagsCollection.document(tag)
                // Decrement the usage count. If doc/field doesn't exist, this might set it to -1.
                batch.update(docRef, HASHTAG_USE_COUNT, FieldValue.increment(-1))
            }
        } catch (e: Exception) {
            Log.e("HashtagRepository", "Error preparing hashtag decrements for batch", e)
        }
    }
}