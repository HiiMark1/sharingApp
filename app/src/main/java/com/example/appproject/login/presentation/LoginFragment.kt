package com.example.appproject.login.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.appproject.R
import com.example.appproject.databinding.FragmentLoginBinding
import com.example.appproject.di.DIContainer
import com.example.appproject.registration.presentation.RegistrationViewModel
import com.example.appproject.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: LoginViewModel

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
        viewModel.signIn(email, password)
        view?.findNavController()?.navigate(R.id.action_loginFragment_to_profileFragment)
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

    private fun initObjects() {
        val factory = ViewModelFactory(DIContainer)
        viewModel = ViewModelProvider(
            this,
            factory
        )[LoginViewModel::class.java]
    }

    private fun initObservers() {
        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }
    }
}