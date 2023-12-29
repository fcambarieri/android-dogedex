package com.hackaprende.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    interface SignUpFragmentActions {
        fun onSignUpFieldsValidated(email : String, password : String, confirmationPassword : String)
    }

    private lateinit var signUpFragmentActions: SignUpFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        }catch (e : Exception) {
            throw java.lang.ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    private lateinit var binding : FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        setupSingUpButton()
        return binding.root
    }

    private fun setupSingUpButton() {
        binding.signUpButton.setOnClickListener {
            clearErrors()
            validateFields()
        }
    }

    private fun validateFields() {

        val email = binding.emailEdit.text.toString()
        if (!isValidEmail(email)) {
            binding.emailInput.error = getString(R.string.emailNotValid)
            return
        }
        val password = binding.passwordEdit.text.toString()
        if (password.isEmpty()) {
            binding.passwordInput.error = getString(R.string.password_is_empty)
            return
        }
        val passwordConfirmation = binding.confirmPasswordEdit.text.toString()
        if (passwordConfirmation.isEmpty()) {
            binding.confirmPasswordInput.error = getString(R.string.password_confirmation_is_empty)
            return
        }
        if (password != passwordConfirmation) {
            binding.confirmPasswordInput.error = getString(R.string.passwords_not_match)
            return
        }
    }

    private fun clearErrors() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""
        binding.confirmPasswordInput.error = ""
    }

    private fun isValidEmail(email : String) : Boolean {
        return !email.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun String.isEmpty() : Boolean {
        return this.length == 0
    }
}