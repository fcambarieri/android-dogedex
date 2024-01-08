package com.hackaprende.dogedex.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.databinding.FragmentSignUpBinding
import com.hackaprende.dogedex.util.isEmpty
import com.hackaprende.dogedex.util.isValidEmail

class SignUpFragment : Fragment() {

    interface SignUpFragmentActions {
        fun onSignUpFieldsValidated(email: String, password: String, confirmationPassword: String)
    }

    private lateinit var signUpFragmentActions: SignUpFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        } catch (e: Exception) {
            throw java.lang.ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    private lateinit var binding: FragmentSignUpBinding
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
        signUpFragmentActions.onSignUpFieldsValidated(email, password, passwordConfirmation)
    }

    private fun clearErrors() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""
        binding.confirmPasswordInput.error = ""
    }

}