package com.hackaprende.dogedex.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.hackaprende.dogedex.main.MainActivity
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.databinding.ActivityLoginBinding
import com.hackaprende.dogedex.model.User

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions , SignUpFragment.SignUpFragmentActions{

    private val viewModel  : AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingWeel = binding.loadingWheel

        viewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Loading -> {
                    loadingWeel.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Error -> {
                    loadingWeel.visibility = View.GONE
                    showErrorDialog(status.message)
                }

                is ApiResponseStatus.Success -> {
                    loadingWeel.visibility = View.GONE
                }
            }
        }

        viewModel.user.observe(this) {
            user ->
            if (user != null) {
                User.setLoggedInUser(this, user)
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showErrorDialog(message : String) {
        AlertDialog.Builder(this)
            .setTitle(com.google.android.material.R.string.error_a11y_label)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) {_, _ -> }
            .create()
            .show()
    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onSignUpFieldsValidated(
        email: String,
        password: String,
        confirmationPassword: String
    ) {
       viewModel.signUp(email, password, confirmationPassword)
    }

    override fun onSingInValidated(email: String, password: String) {
        viewModel.singIn(email, password)
    }
}