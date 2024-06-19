package com.mufid.istory.ui.main.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mufid.istory.databinding.FragmentSettingBinding
import com.mufid.istory.ui.authentication.AuthenticationActivity
import com.mufid.istory.ui.authentication.AuthenticationViewModel
import com.mufid.istory.viewmodel.ViewModelFactory

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding
    private lateinit var factory: ViewModelFactory
    private val authViewModel: AuthenticationViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireActivity())
        setUpView()
    }

    @SuppressLint("StringFormatInvalid")
    private fun setUpView() {
        binding?.apply {
            textBahasa.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            tvLogout.setOnClickListener {
                authViewModel.logOut()
                startActivity(Intent(activity, AuthenticationActivity::class.java))
                activity?.finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}