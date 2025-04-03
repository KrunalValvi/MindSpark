package com.example.mindspark.courses.data

import android.annotation.SuppressLint
import android.util.Log
import com.example.mindspark.R
import com.example.mindspark.courses.model.MentorModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object MentorData {
    private const val TAG = "MentorData"

    @SuppressLint("StaticFieldLeak")
    private val db = FirebaseFirestore.getInstance()
    private val mentorsCollection = db.collection("mentors")

    suspend fun getAllMentors(): List<MentorModel> = withContext(Dispatchers.IO) {
        try {
            val snapshot = mentorsCollection.get().await()
            val mentors = snapshot.documents.mapNotNull { document ->
                val data = document.data
                if (data != null) {
                    // Add the document ID as userId
                    data["userId"] = document.id
                    MentorModel.fromFirestore(data, R.drawable.ic_profile_placeholder)
                } else null
            }
            Log.i(TAG, "Successfully loaded ${mentors.size} mentors")
            mentors
        } catch (e: FirebaseFirestoreException) {
            when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                    Log.e(TAG, "Permission denied when fetching mentors", e)
                FirebaseFirestoreException.Code.UNAVAILABLE ->
                    Log.e(TAG, "Firestore service is unavailable", e)
                else ->
                    Log.e(TAG, "Error fetching mentors", e)
            }
            emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error in getAllMentors", e)
            emptyList()
        }
    }

    suspend fun getMentorByUserId(userId: String): MentorModel? = withContext(Dispatchers.IO) {
        try {
            val doc = mentorsCollection.document(userId).get().await()
            val data = doc.data
            if (data != null) {
                data["userId"] = doc.id
                MentorModel.fromFirestore(data, R.drawable.ic_profile_placeholder)
            } else null
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching mentor with ID: $userId", e)
            null
        }
    }
}

