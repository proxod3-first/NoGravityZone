import com.google.firebase.Timestamp

data class Comment(
    val id: String = "",
    val postId: String = "",
    val userId: String = "",
    val content: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val likesCount: Int = 0,
    val userDisplayInfo: UserDisplayInfo = UserDisplayInfo(),
    val isPending: Boolean = false,
) {
    companion object {
        const val TIMESTAMP_FIELD = "timestamp"
        const val POST_ID_FIELD = "postId"
        const val COMMENTS_COLLECTION = "comments"
        const val COMMENTS_LIKES_COUNT = "likesCount"
    }
}

data class UserDisplayInfo(
    val displayName: String = "",
    val profileImageUrl: String? = null,
)
