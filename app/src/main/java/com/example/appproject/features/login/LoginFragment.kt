package com.example.appproject.features.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.appproject.R
import com.example.appproject.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        if (auth.currentUser != null) {
            println("current user is not null")
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_profileFragment)
        } else {
            println("current user is null")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.INVISIBLE

        binding = FragmentLoginBinding.bind(view)

        with(binding) {
            btnSignIn.setOnClickListener {
                if (checkEditTexts()) {
                    signIn(etMail.text.toString(), etPassword.text.toString())
                }
            }
            btnSignUp.setOnClickListener {
                signUp()
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view?.findNavController()?.navigate(R.id.action_loginFragment_to_profileFragment)
                    showMessage(R.string.success_login)
                } else {
                    showMessage(R.string.something_wrong)
                }
            }
    }

    private fun signUp() {
        view?.findNavController()?.navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            requireView(),
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
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
            return true
        }
    }
}