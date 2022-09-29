package com.example.appproject.features.registration.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.appproject.MainActivity
import com.example.appproject.R
import com.example.appproject.databinding.FragmentRegistrationBinding
import com.example.appproject.utils.AppViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModels {
        factory
    }
    private lateinit var email: String


    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility =
            View.INVISIBLE

        binding = FragmentRegistrationBinding.bind(view)
        initObservers()

        with(binding) {
            btnBackToLogin.setOnClickListener {
                view.findNavController().navigateUp()
            }
            btnSignUp.setOnClickListener {
                if (checkEditTexts()) {
                    email = etMail.text.toString()
                    signUp(email, etPassword.text.toString())
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.isCompleted.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                val user = Firebase.auth.currentUser
                if (it && user != null) {
                    viewModel.addUserInfoInDb(user.uid)
                    view?.findNavController()
                        ?.navigate(R.id.action_registrationFragment_to_loginFragment)
                    showMessage(R.string.success_registration)
                }
            }, onFailure = {
                showMessage(R.string.error_registration)
                Log.e("e", it.message.toString())
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }
    }

    private fun signUp(email: String, password: String) {
        viewModel.createUser(email, password)
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

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            requireView(),
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}