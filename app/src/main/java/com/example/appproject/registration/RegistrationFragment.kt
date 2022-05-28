package com.example.appproject.registration

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.appproject.R
import com.example.appproject.databinding.FragmentRegistrationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = Firebase.database
        myRef = database.getReference("users")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility =
            View.INVISIBLE

        binding = FragmentRegistrationBinding.bind(view)

        with(binding) {
            btnSignUp.setOnClickListener {
                if (checkEditTexts()) {
                    signUp(etMail.text.toString(), etPassword.text.toString())
                }
            }
        }
    }

    private fun signUp(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    addPhotoAndNick(email)
                    view?.findNavController()
                        ?.navigate(R.id.action_registrationFragment_to_loginFragment)
                    showMessage(R.string.success_registration)
                } else {
                    showMessage(R.string.something_wrong)
                }
            }
    }

    private fun checkEditTexts(): Boolean {
        with(binding) {
            if (etMail.text.toString() == "") {
                showMessage(R.string.empty_login)
                return false
            }
            if (etPassword.text.toString() == "") {
                showMessage(R.string.empty_password)
                return false
            }
            if (etPassword.text.length < 8 || etPassword.text.length > 30) {
                showMessage(R.string.password_length_error)
                return false
            }
            if (etMail.text.length < 8 || !etMail.text.contains("@") || !etMail.text.contains(".")) {
                showMessage(R.string.not_correct_mail_error)
                return false
            }
            if (etPassword.text.toString() != etConfPassword.text.toString()) {
                showMessage(R.string.not_same_pass)
                return false
            }
            return true
        }
    }

    private fun addPhotoAndNick(email: String){
        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = email
            photoUri =
                Uri.parse("https://firebasestorage.googleapis.com/v0/b/sharing-b7eaf.appspot.com/o/blank-profile-picture-973460_640.png?alt=media&token=cd210b22-0396-4db2-a410-f8fde87e4e30")
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userEntity = User(
                        user.displayName,
                        user.photoUrl.toString(),
                    )
                    myRef.setValue(userEntity)
                }
            }

    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            requireView(),
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}