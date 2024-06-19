package com.mufid.istory.ui.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufid.istory.source.remote.response.story.ListStory
import com.mufid.istory.source.remote.response.story.StoryResponse
import com.mufid.istory.ui.main.MainAdapter
import com.mufid.istory.ui.main.MainViewModel
import com.mufid.istory.viewmodel.ViewModelFactory
import com.mufid.istory.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var factory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { factory }
    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireActivity())
        setUpView()
    }

    private fun setUpView() {
        mainAdapter = MainAdapter()
        viewModel.getUserToken().observe(viewLifecycleOwner) {
            val token = "Bearer $it"
            viewModel.setToken(token)
            viewModel.getAllStory.observe(viewLifecycleOwner, observer)
        }
        binding?.rvStories?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }

    private val observer = Observer<StoryResponse> {
        if (it != null) {
            val listStory = it.listStory
            Log.d("HOME_TOKEN_LIST", "setUpView: $it")
            if (listStory != null) {
                Log.d("HOME_TOKEN_LIST_DATA", "setUpView: ${it.listStory}")
                mainAdapter.setData(listStory as List<ListStory>)
                binding?.progressBar?.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}