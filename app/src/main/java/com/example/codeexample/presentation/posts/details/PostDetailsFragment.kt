package com.example.codeexample.presentation.posts.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.codeexample.R
import com.example.codeexample.data.Resource
import com.example.codeexample.databinding.FragmentPostDetailsBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PostDetailsFragment : Fragment() {

    @Inject
    lateinit var vm: PostDetailsVM
    private lateinit var binding: FragmentPostDetailsBinding
    private val args: PostDetailsFragmentArgs by navArgs()

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
                R.layout.fragment_post_details,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        vm.postLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    binding.invisible = true
                    result.data?.let { binding.item = it }
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
        vm.getPost(args.postId)
    }
}