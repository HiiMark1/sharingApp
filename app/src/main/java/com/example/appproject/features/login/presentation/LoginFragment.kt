package com.example.appproject.features.login.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.appproject.MainActivity
import com.example.appproject.R
import com.example.appproject.databinding.FragmentLoginBinding
import com.example.appproject.utils.AppViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        factory
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        if (auth.currentUser != null) {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_profileFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.INVISIBLE

        binding = FragmentLoginBinding.bind(view)
        initObservers()

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

    private fun initObservers() {
        viewModel.isCompleted.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                if (it) {
                    view?.findNavController()?.apply {
                        navigate(R.id.action_loginFragment_to_profileFragment)
                    }
                }
            }, onFailure = {
                showMessage(R.string.error_login)
                Log.e("e", it.message.toString())
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }
    }

    private fun signIn(email: String, password: String) {
        viewModel.signIn(email, password)
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