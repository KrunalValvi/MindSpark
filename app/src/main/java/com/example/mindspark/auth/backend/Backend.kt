package com.example.mindspark.auth.backend

import android.app.DatePickerDialog
import android.content.Context
import android.util.Patterns
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

// Data class that holds the profile form data along with error messages.
data class ProfileData(
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val nickname: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val fullNameError: String = "",
    val dobError: String = "",
    val emailError: String = "",
    val phoneError: String = "",
    val genderError: String = ""
)

// Retrieve Firebase user info and populate a ProfileData instance.
fun getFirebaseProfileData(): ProfileData {
    val firebaseUser = Firebase.auth.currentUser
    return ProfileData(
        fullName = firebaseUser?.displayName ?: "",
        email = firebaseUser?.email ?: "",
        phoneNumber = firebaseUser?.phoneNumber ?: ""
    )
}

// Validate the profile data and return an updated instance with error messages.
fun validateProfile(data: ProfileData): ProfileData {
    var updatedData = data
    updatedData = if (data.fullName.isBlank())
        updatedData.copy(fullNameError = "Full Name is required")
    else updatedData.copy(fullNameError = "")

    updatedData = if (data.dateOfBirth.isBlank())
        updatedData.copy(dobError = "Date of Birth is required")
    else updatedData.copy(dobError = "")

    updatedData = if (data.email.isBlank())
        updatedData.copy(emailError = "Email is required")
    else if (!Patterns.EMAIL_ADDRESS.matcher(data.email).matches())
        updatedData.copy(emailError = "Invalid email address")
    else updatedData.copy(emailError = "")

    updatedData = if (data.phoneNumber.isBlank())
        updatedData.copy(phoneError = "Phone number is required")
    else if (data.phoneNumber.length != 10)
        updatedData.copy(phoneError = "Phone number must be 10 digits")
    else updatedData.copy(phoneError = "")

    updatedData = if (data.gender.isBlank())
        updatedData.copy(genderError = "Gender is required")
    else updatedData.copy(genderError = "")

    return updatedData
}

// Show a DatePickerDialog and pass the chosen date back via the callback.
fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    DatePickerDialog(
        context,
        { _, year, month, day ->
            val dateString = "$day/${month + 1}/$year"
            onDateSelected(dateString)
        },
        currentYear,
        currentMonth,
        currentDay
    ).show()
}

// Function to store profile data in Firestore.
fun storeProfileData(
    profileData: ProfileData,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val currentUser = Firebase.auth.currentUser
    if (currentUser == null) {
        onFailure("User not logged in")
        return
    }

    val db = FirebaseFirestore.getInstance()
    val userDoc = hashMapOf(
        "fullName" to profileData.fullName,
        "nickname" to profileData.nickname,
        "dateOfBirth" to profileData.dateOfBirth,
        "email" to profileData.email,
        "phoneNumber" to profileData.phoneNumber,
        "gender" to profileData.gender
    )

    db.collection("users").document(currentUser.uid)
        .set(userDoc)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception ->
            onFailure(exception.message ?: "An error occurred")
        }
}

fun updateUserPin(
    pin: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val currentUser = Firebase.auth.currentUser
    if (currentUser == null) {
        onFailure("User not logged in")
        return
    }
    val db = FirebaseFirestore.getInstance()
    db.collection("users").document(currentUser.uid)
        .update("pin", pin)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception ->
            onFailure(exception.message ?: "An error occurred")
        }
}

fun updateUserFingerprint(
    fingerprintData: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val currentUser = Firebase.auth.currentUser
    if (currentUser == null) {
        onFailure("User not logged in")
        return
    }
    val db = FirebaseFirestore.getInstance()
    // Update the "fingerprint" field in the user's document.
    db.collection("users").document(currentUser.uid)
        .update("fingerprint", fingerprintData)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception ->
            onFailure(exception.message ?: "An error occurred")
        }
}