package com.mufid.istory.ui.authentication.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mufid.istory.viewmodel.ViewModelFactory
import com.mufid.istory.R
import com.mufid.istory.databinding.FragmentRegisterBinding
import com.mufid.istory.ui.authentication.AuthenticationViewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    private lateinit var factory: ViewModelFactory
    private val viewModel: AuthenticationViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireActivity())
        setUpView()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding?.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val textRegister = ObjectAnimator.ofFloat(binding?.textRegister, View.ALPHA, 1f).setDuration(500)
        val etName = ObjectAnimator.ofFloat(binding?.etName, View.ALPHA, 1f).setDuration(500)
        val etEmail = ObjectAnimator.ofFloat(binding?.etEmail, View.ALPHA, 1f).setDuration(500)
        val etPass = ObjectAnimator.ofFloat(binding?.etPassword, View.ALPHA, 1f).setDuration(500)
        val containerLogin = ObjectAnimator.ofFloat(binding?.container, View.ALPHA, 1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding?.btnRegister, View.ALPHA, 1f).setDuration(500)
        AnimatorSet().apply {
            playSequentially(textRegister, etName, etEmail, etPass, containerLogin,btnRegister)
            start()
        }
    }

    private fun setUpView() {
        binding?.apply {
            tvLogin.setOnClickListener {
                activity?.onBackPressed()
            }

            btnRegister.setOnClickListener {
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
                progressBar.visibility = View.VISIBLE
                viewModel.register(etName.text.trim().toString(), etEmail.text.trim().toString(),
                    etPassword.text?.trim().toString()).observe(viewLifecycleOwner){
                    progressBar.visibility = View.GONE
                    if (it != null){
                        if (it.message != null){
                            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }else{
                            Toast.makeText(requireActivity(), resources.getString(R.string.text_error_register), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}