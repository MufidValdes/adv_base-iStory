package com.mufid.istory.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mufid.istory.R
import com.mufid.istory.databinding.FragmentDetailBinding
import com.mufid.istory.utils.DateUtil.toAnotherDate

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding?= null
    private val binding get() = _binding
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        bottomNav = requireActivity().findViewById(R.id.bottom_nav)
        bottomNav.visibility = View.GONE

        val listStory = DetailFragmentArgs.fromBundle(arguments as Bundle).listStory
        (activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        setHasOptionsMenu(true)

        binding?.apply {
            toolbar.title = listStory?.name
            tvDate.text = listStory?.createdAt?.toAnotherDate()
            tvDesc.text = listStory?.description
            Glide.with(this@DetailFragment)
                .load(listStory?.photoUrl)
                .into(imgStory)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bottomNav.visibility = View.VISIBLE
    }
}