package com.flowz.paybackjobapp.ui.list


import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout.VERTICAL
//import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowz.agromailjobtask.adapter.ImagesAdapter
import com.flowz.agromailjobtask.utils.Constants
import com.flowz.byteworksjobtask.util.getConnectionType
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.paybackjobapp.R
import com.flowz.paybackjobapp.databinding.FragmentImageListBinding
import com.flowz.paybackjobapp.models.Hit
import com.flowz.paybackjobapp.models.ImageResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_image_list) {

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

        imagesAdapter = ImagesAdapter{
            transitionToDetailView(it)
        }
        observeState()

        if (getConnectionType(requireContext())){
            viewModel.searchImageTypeFromNetwork(Constants.DEFAULTSEARCHVALUE)
        }else{
            AlertDialog.Builder(requireContext()).setTitle(getString(R.string.no_internet_connection))
                .setMessage(getString(R.string.internet_connection_error_message))
                .setPositiveButton(getString(R.string.ok)) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
            loadDataFromRoomDb()
        }


        binding.apply {

            fetchImages.setOnClickListener {

                if (TextUtils.isEmpty(enteredImageType.text.toString())) {
                    enteredImageType.setError(getString(R.string.enter_valid_input))
                    return@setOnClickListener
                }else{

                imageType = enteredImageType.text.toString().trim()

                    if (getConnectionType(requireContext())){
                        viewModel.searchImageTypeFromNetwork(imageType)
                    }else{
                        AlertDialog.Builder(requireContext()).setTitle(getString(R.string.no_internet_connection))
                            .setMessage(getString(R.string.internet_connection_error_message2))
                            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()

                    }


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
                        }

                        ImagesApiStatus.DONE-> {
                            viewModel.imagesFromNetwork.observe(viewLifecycleOwner, Observer {
                                loadRecyclerView(it.hits)
                            })

                        }
                    }

                }
            })
        }

    }

    fun loadRecyclerView(images: List<Hit>) {

        binding.apply {
            errorImage.isVisible = false
            imagesAdapter.submitList(images)
            rvList.layoutManager = LinearLayoutManager(requireContext())
            rvList.adapter = imagesAdapter
            val decoration = DividerItemDecoration(requireContext(), VERTICAL)
            rvList.addItemDecoration(decoration)

            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
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

    fun loadDataFromRoomDb() {
        lifecycleScope.launch {

            viewModel.imagesFromLocalDb.collect {
                    loadRecyclerView(it)
            }
        }

    }


    fun transitionToDetailView(hit: Hit){
        AlertDialog.Builder(this.requireContext()).setTitle(getString(R.string.open_details))
            .setMessage(getString(R.string.sure_to_open))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                val action = ImageListFragmentDirections.actionImageListFragmentToImageDetailFragment()
                action.hit = hit
                Navigation.findNavController(requireView()).navigate(action)
            }
            .setNegativeButton(getString(R.string.no)){ _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()

    }


}