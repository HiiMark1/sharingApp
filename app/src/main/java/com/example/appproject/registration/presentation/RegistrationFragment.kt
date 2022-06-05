package com.example.appproject.registration.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.appproject.R
import com.example.appproject.databinding.FragmentRegistrationBinding
import com.example.appproject.di.DIContainer
import com.example.appproject.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility =
            View.INVISIBLE

        initObjects()
        initObservers()

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
        createUser(email, password)
        val user = auth.currentUser
        if (user != null) {
            view?.findNavController()?.navigate(R.id.action_registrationFragment_to_loginFragment)
        } else {
            showMessage(R.string.registration)
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

    private fun addDefaultInfo(email: String) {
        val user = Firebase.auth.currentUser

        if (user != null) {
            viewModel.addUserInfoInDb(user.uid, email)
        }
    }

    private fun createUser(email: String, password: String) {
        viewModel.createUser(email, password)
        addDefaultInfo(email)
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            requireView(),
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun initObjects() {
        val factory = ViewModelFactory(DIContainer)
        viewModel = ViewModelProvider(
            this,
            factory
        )[RegistrationViewModel::class.java]
    }

    private fun initObservers() {
        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }
    }
}