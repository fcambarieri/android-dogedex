package com.hackaprende.dogedex.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.databinding.FragmentLoginBinding
import com.hackaprende.dogedex.util.isEmpty
import com.hackaprende.dogedex.util.isValidEmail

class LoginFragment : Fragment() {

    interface LoginFragmentActions {
        fun onRegisterButtonClick()
        fun onSingInValidated(email: String, password: String)
    }

    private lateinit var loginFragmentActions: LoginFragmentActions

    lateinit var binding : FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        }catch (e : Exception) {
            throw java.lang.ClassCastException("$context must implement LoginFragmentActions")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        }
        binding.loginButton.setOnClickListener {
            loginAction()
        }
        return binding.root
    }

    private fun loginAction() {
        clearErrors()
        validateFields()
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
        loginFragmentActions.onSingInValidated(email, password)
    }

    private fun clearErrors() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""
    }
}