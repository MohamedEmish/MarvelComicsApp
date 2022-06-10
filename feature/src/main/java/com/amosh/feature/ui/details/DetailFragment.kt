package com.amosh.feature.ui.details

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.amosh.base.BaseFragment
import com.amosh.common.toHttps
import com.amosh.feature.databinding.FragmentDetailsBinding
import com.amosh.feature.model.ComicsUiModel
import com.amosh.feature.ui.MainViewModel
import com.amosh.feature.ui.contract.MainContract
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailsBinding>() {

    private val viewModel: MainViewModel by activityViewModels()
    private val mViewPagerAdapter by lazy {
        ImageSliderViewPagerAdapter()
    }

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding
        get() = FragmentDetailsBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setData(it.selectedComics)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it) {
                        is MainContract.Effect.ShowError -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setData(selectedComics: ComicsUiModel?) {
        if (selectedComics == null) requireActivity().onBackPressed()
        else
            with(binding) {
                tvTitle.apply {
                    text = selectedComics.title
                    setOnClickListener { requireActivity().onBackPressed() }
                }
                viewPager.adapter = mViewPagerAdapter
                mViewPagerAdapter.submitList(
                    when {
                        selectedComics.images.isNullOrEmpty() -> listOf(selectedComics.thumbnail ?: "")
                        else -> selectedComics.images
                    }
                )

                tvDescription.text = "Description: ${selectedComics.description}"
                tvPageCount.text = "Page Count: ${selectedComics.pageCount}"
                tvUrl.apply {
                    text = getUrlSpan(selectedComics.url)
                    setOnClickListener { openLink(selectedComics.url ?: "") }
                }
                tvSeriesName.text = "Series Name: ${selectedComics.seriesName}"
                tvPrice.text = "Price ${selectedComics.price}"

            }
    }

    private fun getUrlSpan(url: String?): CharSequence {
        val spannableString: Spannable = SpannableString("Url: $url").apply {
            setSpan(ForegroundColorSpan(Color.BLUE), 4, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(UnderlineSpan(), 4, this.length, 0)
        }
        return spannableString
    }

    private fun openLink(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url.toHttps())))
    }
}