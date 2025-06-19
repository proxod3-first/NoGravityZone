package com.proxod3.nogravityzone.ui.theme.screens.feed.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.proxod3.nogravityzone.animation.LocalShimmerTheme
import com.proxod3.nogravityzone.animation.shimmerPlaceholder
import com.proxod3.nogravityzone.ui.models.post.FeedPost
import com.proxod3.nogravityzone.ui.models.post.PostCreator
import com.proxod3.nogravityzone.ui.models.post.PostMetrics
import com.proxod3.nogravityzone.ui.screens.feed.composables.ImageGrid
import com.proxod3.nogravityzone.ui.screens.feed.composables.UserInfoRow
import com.proxod3.nogravityzone.ui.screens.post_details.FeedPostWithLikesAndComments
import com.proxod3.nogravityzone.ui.shared_components.HashtagText
import com.proxod3.nogravityzone.ui.shared_components.PostInteractionRow
import com.proxod3.nogravityzone.utils.Utils.formatRelativeTimeFromFireStoreTimeStamp
import com.google.firebase.Timestamp


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeedPostItem(
    feedPostWithLikesAndComments: FeedPostWithLikesAndComments,
    onLikeIconClick: () -> Unit,
    onProfileClick: () -> Unit,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onPostClick() }
        ) {
            UserInfoRow(
                feedPostWithLikesAndComments.post.postCreator.displayName,
                feedPostWithLikesAndComments.post.postCreator.profilePictureUrl,
                formatRelativeTimeFromFireStoreTimeStamp(feedPostWithLikesAndComments.post.createdAt),
                onProfileClick = onProfileClick
            )

            HashtagText(
                text = feedPostWithLikesAndComments.post.content,
                onHashtagClick = {}, // TODO: Add hashtag click handler
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            )

            if (feedPostWithLikesAndComments.post.imageUrlList.isNotEmpty()) {
                val imageUrls = feedPostWithLikesAndComments.post.imageUrlList.take(4)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    when (imageUrls.size) {
                        1 -> {
                            // Single image takes full width
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imageUrls[0])
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Post Image 1",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1.5f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .shimmerPlaceholder(
                                        visible = true,
                                        shimmerTheme = LocalShimmerTheme.current,
                                    )
                            )
                        }

                        else -> {
                            ImageGrid(
                                imageUrls = imageUrls,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                if (feedPostWithLikesAndComments.post.imageUrlList.size > 4) {
                    Text(
                        text = "+${feedPostWithLikesAndComments.post.imageUrlList.size - 4} more",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            PostInteractionRow(
                likeAmount = feedPostWithLikesAndComments.post.postMetrics.likes,
                onLikeClick = onLikeIconClick,
                isLiked = feedPostWithLikesAndComments.isLiked,
                feedPostWithLikesAndComments.post.postMetrics.comments,
                onCommentClick = onPostClick, //when comment icon is clicked show post details
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedPostItem() {
    FeedPostItem(
        feedPostWithLikesAndComments = FeedPostWithLikesAndComments(
            post = FeedPost(
                content = "This is a post content",
                postCreator = PostCreator(
                    displayName = "John Doe",
                    profilePictureUrl = "https://www.example.com/profile.jpg"
                ),
                createdAt = Timestamp.now(),
                imageUrlList = listOf(
                    "https://www.example.com/image1.jpg",
                    "https://www.example.com/image2.jpg",
                    "https://www.example.com/image3.jpg",
                    "https://www.example.com/image4.jpg",
                    "https://www.example.com/image5.jpg",
                    "https://www.example.com/image6.jpg",
                    "https://www.example.com/image7.jpg",
                    "https://www.example.com/image8.jpg",
                    "https://www.example.com/image9.jpg",
                    "https://www.example.com/image10.jpg",
                ),
                postMetrics = PostMetrics(
                    likes = 100,
                    comments = 10
                )
            ),
        ),
        onLikeIconClick = {},
        onProfileClick = { },
        onPostClick = { },
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedPostItem2() {
    FeedPostItem(
        feedPostWithLikesAndComments = FeedPostWithLikesAndComments(
            post = FeedPost(
                content = "This is a post content",
                postCreator = PostCreator(
                    displayName = "John Doe",
                    profilePictureUrl = "https://www.example.com/profile.jpg"
                ),
                createdAt = Timestamp.now(),
                imageUrlList = listOf(
                    "https://www.example.com/image8.jpg",
                    "https://www.example.com/image9.jpg",
                    "https://www.example.com/image10.jpg",
                ),
                postMetrics = PostMetrics(
                    likes = 100,
                    comments = 10
                )
            ),
        ),
        onLikeIconClick = {},
        onProfileClick = { },
        onPostClick = { },
    )
}

