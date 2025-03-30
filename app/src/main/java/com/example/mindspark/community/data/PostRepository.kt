package com.example.mindspark.community.data

import com.example.mindspark.community.model.Comment
import com.example.mindspark.community.model.Post
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PostRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val postsCollection = firestore.collection("posts")

    // Add a new post to Firestore
    fun addPost(post: Post, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        postsCollection.add(post)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    // Add a comment to a post
    fun addComment(postId: String, comment: Comment, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val postRef = postsCollection.document(postId)

        // First, get the current post to check if comments exists
        postRef.get()
            .addOnSuccessListener { document ->
                val post = document.toObject(Post::class.java)
                if (post != null) {
                    // Add the new comment to the comments list
                    val commentWithId = comment.copy(commentId = firestore.collection("posts").document().id)
                    val updatedComments = (post.comments ?: listOf()) + commentWithId

                    // Update the post with the new comments list
                    postRef.update("comments", updatedComments)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { exception -> onFailure(exception) }
                } else {
                    onFailure(Exception("Post not found"))
                }
            }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    // Retrieve posts once (ordered by timestamp descending)
    fun getPosts(onSuccess: (List<Post>) -> Unit, onFailure: (Exception) -> Unit) {
        postsCollection.orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val posts = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Post::class.java)?.copy(postId = document.id)
                }
                onSuccess(posts)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Observe posts in real time
    fun observePosts(onUpdate: (List<Post>) -> Unit) {
        postsCollection.orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    // Optionally handle error
                    return@addSnapshotListener
                }
                val posts = querySnapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Post::class.java)?.copy(postId = doc.id)
                } ?: emptyList()
                onUpdate(posts)
            }
    }
}