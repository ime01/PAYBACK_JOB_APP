package com.flowz.paybackjobapp.ui.list


import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout.VERTICAL
//import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowz.agromailjobtask.adapter.ImagesAdapter
import com.flowz.agromailjobtask.utils.Constants
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.paybackjobapp.R
import com.flowz.paybackjobapp.databinding.FragmentImageListBinding
import com.flowz.paybackjobapp.models.Hit
import com.flowz.paybackjobapp.models.ImageResponse
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_image_list), ImagesAdapter.RowClickListener {

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!
    lateinit var imagesAdapter  : ImagesAdapter
    lateinit var imageType: String

    private val viewModel: ImagesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentImageListBinding.bind(view)

        showWelcomeMarqueeText()
        imagesAdapter = ImagesAdapter(this@ImageListFragment)
        viewModel.searchImageTypeFromNetwork(Constants.DEFAULTSEARCHVALUE)
        observeState()


        binding.apply {

            fetchImages.setOnClickListener {

                if (TextUtils.isEmpty(enteredImageType.text.toString())) {
                    enteredImageType.setError(getString(R.string.enter_valid_input))
                    return@setOnClickListener
                }else{

                imageType = enteredImageType.text.toString().trim()

                viewModel.searchImageTypeFromNetwork("$imageType")
//
                }

            }

        }

    }

    fun observeState(){

        binding.apply {
            viewModel.imagesFromNetworkStaus.observe(viewLifecycleOwner, Observer { state->

                state?.also {
                    when(it){
                        ImagesApiStatus.ERROR-> {

                            errorImage.isVisible = true
                            showSnackbar(welcomeTextMarquee, getString(R.string.error_fetching_data))
                        }
                        ImagesApiStatus.LOADING-> {
                            shimmerFrameLayout.startShimmer()
//                                showSnackbar(welcomeTextMarquee, "Loading data from Api")
                        }
                        ImagesApiStatus.DONE-> {
                            showSnackbar(welcomeTextMarquee, getString(R.string.fetched))
                            viewModel.imagesFromNetwork.observe(viewLifecycleOwner, Observer {
                                loadRecyclerView(it)
                            })

                        }
                    }

                }
            })
        }

    }

    fun loadRecyclerView(images: ImageResponse) {

        binding.apply {
            errorImage.isVisible = false
            imagesAdapter.submitList(images.hits)
            rvList.layoutManager = LinearLayoutManager(requireContext())
            rvList.adapter = imagesAdapter
            val decoration = DividerItemDecoration(requireContext(), VERTICAL)
            rvList.addItemDecoration(decoration)

            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
//            rvList.isVisible = true
        }


    }

    fun showWelcomeMarqueeText() {

        binding.apply {

            welcomeTextMarquee.apply {
                setSingleLine()
                ellipsize = TextUtils.TruncateAt.MARQUEE
                marqueeRepeatLimit = -1
                isSelected = true
            }
        }
    }

    override fun onItemClickListener(hit: Hit) {
        val action = ImageListFragmentDirections.actionImageListFragmentToImageDetailFragment()
        action.hit = hit
        Navigation.findNavController(requireView()).navigate(action)

    }


}