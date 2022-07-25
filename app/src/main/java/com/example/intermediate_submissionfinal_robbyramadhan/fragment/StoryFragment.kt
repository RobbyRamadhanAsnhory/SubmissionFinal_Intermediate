package com.example.intermediate_submissionfinal_robbyramadhan.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intermediate_submissionfinal_robbyramadhan.R
import com.example.intermediate_submissionfinal_robbyramadhan.adapter.LoadingStateAdapter
import com.example.intermediate_submissionfinal_robbyramadhan.adapter.StoryAdapter
import com.example.intermediate_submissionfinal_robbyramadhan.databinding.FragmentStoryBinding
import com.example.intermediate_submissionfinal_robbyramadhan.model.UserModel
import com.example.intermediate_submissionfinal_robbyramadhan.model.ViewModelFactory
import com.example.intermediate_submissionfinal_robbyramadhan.notice.ToastError
import com.example.intermediate_submissionfinal_robbyramadhan.viewmodel.StoryViewModel
import com.example.intermediate_submissionfinal_robbyramadhan.wrapEspressoIdlingResource
import kotlinx.coroutines.launch

class StoryFragment : Fragment() {

    private var _bindingStory: FragmentStoryBinding? = null
    private val binding get() = _bindingStory!!
    private lateinit var user: UserModel

    private lateinit var adapter: StoryAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var isFromOtherScreen = false

    private val viewModel: StoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingStory = FragmentStoryBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setupViewModel()
        playAnimation()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_option, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_maps -> {
                findNavController().navigate(
                    StoryFragmentDirections.actionStoryFragmentToStoryWithMapsFragment(
                        StoryWithMapsFragment.ACTION_STORIES
                    )
                )
            }
            R.id.menu_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.menu_logout -> {
                viewModel.logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupAction() {
        adapter = StoryAdapter().apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    if (positionStart == 0 && isFromOtherScreen.not()) {
                        binding.rvPhotos.smoothScrollToPosition(0)
                    }
                }
            })
        }

        val adapterWithLoading =
            adapter.withLoadStateFooter(footer = LoadingStateAdapter { adapter.retry() })
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvPhotos.layoutManager = layoutManager
        binding.rvPhotos.adapter = adapterWithLoading
        adapter.refresh()

        binding.swipeLayout.setOnRefreshListener {
            adapter.refresh()
            binding.swipeLayout.isRefreshing = false
        }

        binding.fbAddstory.setOnClickListener {
            goToPostStory()
        }

        wrapEspressoIdlingResource {
            lifecycleScope.launch {
                adapter.loadStateFlow.collect {
                    binding.pbMain.isVisible = (it.refresh is LoadState.Loading)
                    binding.tvNullData.isVisible =
                        it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && adapter.itemCount < 1
                    if (it.refresh is LoadState.Error) {
                        ToastError.showToast(
                            requireContext(),
                            (it.refresh as LoadState.Error).error.localizedMessage?.toString()
                                ?: getString(R.string.error_load)
                        )
                    }
                }
            }
        }

        setFragmentResultListener(StoryWithMapsFragment.KEY_RESULT) { _, bundle ->
            isFromOtherScreen = bundle.getBoolean(KEY_FROM_OTHER_SCREEN, false)
        }
    }

    private fun setupViewModel() {
        viewModel.userItems.observe(viewLifecycleOwner) { userItems ->
            if (userItems?.isLoggedIn == false) {
                findNavController().navigateUp()
            }
            this.user = userItems
        }

        viewModel.stories.observe(viewLifecycleOwner) { stories ->
            adapter.submitData(lifecycle, stories)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            ToastError.showToast(requireContext(), message)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { state ->
            showLoading(state)
        }
    }

    private fun goToPostStory() {
        val navigateAction = StoryFragmentDirections.actionStoryFragmentToPostStoryFragment()
        navigateAction.token

        findNavController().navigate(navigateAction)
    }

    private fun playAnimation() {
        val story = ObjectAnimator.ofFloat(binding.rvPhotos, View.ALPHA, 1f).setDuration(500)
        AnimatorSet().apply {
            playSequentially(story)
            startDelay = 500
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbMain.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingStory = null
    }

    companion object {
        const val KEY_FROM_OTHER_SCREEN = "other_screen"
    }

}