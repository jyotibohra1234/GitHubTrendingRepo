package com.example.githubtrendingrepo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.githubtrendingrepo.R
import com.example.githubtrendingrepo.databinding.FragmentGithubListBinding
import com.example.githubtrendingrepo.ui.base.BaseFragment
import com.example.githubtrendingrepo.util.showToastMsg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GithubListFragment : BaseFragment(R.layout.fragment_github_list){
        private val gitHubViewModel by viewModels<GitHubViewModel>()
        private lateinit var adapter: GithubPagingAdapter
        private lateinit var binding: FragmentGithubListBinding

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding = FragmentGithubListBinding.bind(view)
            initView()
            observeData()
        }

        private fun initView() {
            adapter = GithubPagingAdapter()
            binding.recyclerView.adapter =
                adapter.withLoadStateFooter(
                    footer = GitHubLoadStateAdapter {
                        adapter.retry()
                    })
            binding.swipeRefresh.setOnRefreshListener {
                adapter.refresh()
            }
        }

        private fun observeData() {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                adapter.loadStateFlow.collect { loadStates ->
                    with(binding) {
                        with(loadStates) {
                            if (swipeRefresh.isRefreshing && refresh is LoadState.Error && adapter.itemCount > 0) {
                                fragmentContext.showToastMsg(getString(R.string.str_empty_github_list))
                            }
                            swipeRefresh.isRefreshing =
                                swipeRefresh.isRefreshing && refresh is LoadState.Loading
                            contentProgressBar.contentProgressBarLayout.isVisible =
                                adapter.itemCount == 0 && (refresh is LoadState.Loading || mediator == null)
                            contentEmpty.contentEmptyLayout.isVisible =
                                adapter.itemCount == 0 && refresh is LoadState.Error
                            contentEmpty.textViewRetry.setOnClickListener {
                                adapter.retry()
                            }
                            if (refresh is LoadState.Error) {
                                binding.contentEmpty.textViewError.text =
                                    (refresh as LoadState.Error).error.localizedMessage
                            }
                        }

                    }
                }
            }
            gitHubViewModel.setQueryParameter("android").pagingDataLiveData.observe(
                viewLifecycleOwner
            ) {
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }
        }

//    override fun redirectToDetailScreen(position: Int) {
//        val action = GithubListFragmentDirections.actionGithubListFragmentToGitHubDetailFragment()
//        binding.root.findNavController().navigate(action)
//    }
}