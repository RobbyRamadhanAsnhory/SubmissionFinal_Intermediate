package com.example.intermediate_submissionfinal_robbyramadhan.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.intermediate_submissionfinal_robbyramadhan.Complement.withDateFormat
import com.example.intermediate_submissionfinal_robbyramadhan.R
import com.example.intermediate_submissionfinal_robbyramadhan.databinding.FragmentStoryDetailBinding

class StoryDetailFragment : Fragment() {

    private var _bindingStoryDetail: FragmentStoryDetailBinding? = null
    private val binding get() = _bindingStoryDetail!!
    private val args: StoryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingStoryDetail = FragmentStoryDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val story = args.story

        binding.apply {
            nameTextView.text = story.name
            dateTextView.text = getString(R.string.date, story.createdAt?.withDateFormat())
            descTextView.text = story.description
        }


        Glide.with(requireContext())
            .load(story.imageUrl)
            .into(binding.previewImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingStoryDetail = null
    }

    override fun onDestroy() {
        super.onDestroy()
        setFragmentResult(
            StoryWithMapsFragment.KEY_RESULT,
            Bundle().apply { putBoolean(StoryFragment.KEY_FROM_OTHER_SCREEN, true) })
    }


}