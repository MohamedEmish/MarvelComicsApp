package com.amosh.feature.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.amosh.base.BaseFragment
import com.amosh.common.isOffline
import com.amosh.feature.databinding.FragmentMainBinding
import com.amosh.feature.ui.MainViewModel
import com.amosh.feature.ui.contract.MainContract
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by activityViewModels()

    private val adapter: ComicsAdapter by lazy {
        ComicsAdapter({ comics ->
            viewModel.setEvent(
                MainContract.Event.OnFetchComicDetails(
                    comics?.id ?: return@ComicsAdapter
                )
            )
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(comics)
            findNavController().navigate(action)
        }, {
            getComicsList()
        })
    }

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        binding.rvComics.adapter = adapter

        initObservers()
    }

    private fun getComicsList() =
        viewModel.setEvent(MainContract.Event.OnFetchComicsList)

    /**
     * Initialize Observers
     */
    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (val state = it.viewState) {
                        is MainContract.ComicsState.Idle -> {
                            binding.pbLoading.isVisible = false
                        }
                        is MainContract.ComicsState.Loading -> {
                            binding.pbLoading.isVisible = true
                        }
                        is MainContract.ComicsState.Success -> {
                            val data = state.comicsList
                            adapter.submitList(data)
                            binding.emptyState.isVisible = data.isEmpty()
                            binding.pbLoading.isVisible = false
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it) {
                        is MainContract.Effect.ShowError -> {
                            binding.emptyState.isVisible = adapter.currentList.isEmpty()
                            binding.pbLoading.isVisible = false
                            if (requireContext().isOffline()) {
                                Toast.makeText(
                                    requireContext(),
                                    "No internet connection, please try again later",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                }
            }
        }
    }
}