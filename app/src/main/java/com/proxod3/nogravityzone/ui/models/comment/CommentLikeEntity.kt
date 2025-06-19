package com.proxod3.nogravityzone.ui.models.comment


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

/**
 * Data class representing a local comment like.
 *
 * @property id The unique identifier for the comment like.
 * @property userId The unique identifier of the user who liked the comment.
 * @property commentId The unique identifier of the comment that was liked.
 * @property postId The unique identifier of the post that the comment belongs to.
 * @property timestamp The timestamp when the post was liked.
 */
@Entity(tableName = "comment_likes")
data class CommentLikeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String = "", // Will be "$userId_$postId"
    @ColumnInfo(name = "user_id")
    val userId: String = "",
    @ColumnInfo(name = "comment_id")
    val commentId: String = "",
    @ColumnInfo(name = "post_id")
    val postId: String = "",
    @ColumnInfo(name = "timestamp")
    val timestamp: Timestamp = Timestamp.now()
)