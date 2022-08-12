package com.example.codeexample.presentation.posts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codeexample.R
import com.example.codeexample.data.Resource
import com.example.codeexample.databinding.FragmentPostsBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PostsFragment : Fragment() {

    @Inject
    lateinit var vm: PostsVM
    private lateinit var binding: FragmentPostsBinding
    private lateinit var adapter: Adapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_posts,
                container,
                false
            )
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.postsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        if (it.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Local database is empty, please wait for network call",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            adapter.addAll(it)
                            binding.invisible = true
                        }
                    }
                }
                is Resource.Loading -> {
                    binding.invisible = false
                }
                is Resource.Error -> {
                    binding.invisible = true
                    Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        vm.getPosts()
    }

    private fun initViews() {
        val rv = binding.rvPosts
        adapter = Adapter { id ->
            val action = PostsFragmentDirections.actionPostsToPostDetails(id)
            findNavController().navigate(action)
        }
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
    }
}