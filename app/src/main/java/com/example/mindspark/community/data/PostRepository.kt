package com.example.mindspark.community.data

import com.example.mindspark.community.model.Post
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
