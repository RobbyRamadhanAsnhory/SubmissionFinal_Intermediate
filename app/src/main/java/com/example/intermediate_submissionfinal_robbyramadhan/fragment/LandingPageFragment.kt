package com.example.intermediate_submissionfinal_robbyramadhan.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.intermediate_submissionfinal_robbyramadhan.R
import com.example.intermediate_submissionfinal_robbyramadhan.databinding.FragmentLandingPageBinding
import com.example.intermediate_submissionfinal_robbyramadhan.model.ViewModelFactory
import com.example.intermediate_submissionfinal_robbyramadhan.viewmodel.LandingPageViewModel

class LandingPageFragment : Fragment() {

    private var _bindingLanding: FragmentLandingPageBinding? = null
    private val binding get() = _bindingLanding!!

    private val viewModel: LandingPageViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingLanding = FragmentLandingPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setupViewModel()
        playAnimation()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_loginFragment)
        }
        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_registerFragment)
        }
    }

    private fun setupViewModel() {
        viewModel.userItem.observe(viewLifecycleOwner) { userModel ->
            if (userModel?.isLoggedIn == true) {
                findNavController().navigate(R.id.action_landingFragment_to_loginFragment)
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.descTextView, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }

        AnimatorSet().apply {
            playSequentially(title, desc, together)
            start()
        }
    }
}