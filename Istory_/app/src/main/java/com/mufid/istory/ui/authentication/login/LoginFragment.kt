package com.mufid.istory.ui.authentication.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mufid.istory.ui.authentication.AuthenticationViewModel
import com.mufid.istory.viewmodel.ViewModelFactory
import com.mufid.istory.R
import com.mufid.istory.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private lateinit var factory: ViewModelFactory
    private val viewModel: AuthenticationViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireActivity())
        setUpView()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding?.imageLogo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val textLogin = ObjectAnimator.ofFloat(binding?.textLogin, View.ALPHA, 1f).setDuration(500)
        val etEmail = ObjectAnimator.ofFloat(binding?.etEmail, View.ALPHA, 1f).setDuration(500)
        val etPass = ObjectAnimator.ofFloat(binding?.etPassword, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding?.btnLogin, View.ALPHA, 1f).setDuration(500)
        val containerDaftar = ObjectAnimator.ofFloat(binding?.container, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(textLogin, etEmail, etPass, btnLogin, containerDaftar)
            start()
        }
    }

    private fun setUpView() {
        binding?.apply {
            tvDaftar.setOnClickListener {
                it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
                progressBar.setVisible()
                viewModel.login(etEmail.text.trim().toString(), etPassword.text?.trim().toString()).observe(viewLifecycleOwner) {
                    if (it != null) {
                        if (it.message != null) {
                            progressBar.setGone()
                            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                            it.loginResult?.token?.let { token -> viewModel.saveUserToken(token) }
                            viewModel.setIsFirstTime(false)
                            activity?.finish()
                            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                        } else {
                            progressBar.setGone()
                            Toast.makeText(requireActivity(), resources.getString(R.string.text_error_login), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun View.setVisible() {
        this.visibility = View.VISIBLE
    }

    private fun View.setGone() {
        this.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}