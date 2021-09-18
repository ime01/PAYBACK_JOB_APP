package com.flowz.paybackjobapp.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.paybackjobapp.R
import com.flowz.paybackjobapp.databinding.FragmentImageListBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_image_list) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ImagesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentImageListBinding.bind(view)


        binding.apply {

            textView1.setOnClickListener {

                viewModel.searchImageTypeFromNetwork("fruits")
                viewModel.imagesFromNetworkStaus.observe(viewLifecycleOwner, Observer { state->

                    state?.also {
                        when(it){
                            ImagesApiStatus.ERROR-> {
                                showSnackbar(textView1, "Error fetching data from Api")
                            }
                            ImagesApiStatus.LOADING-> {
                                showSnackbar(textView1, "Loading data from Api")
                            }
                            ImagesApiStatus.DONE-> {
                                showSnackbar(textView1, "Images fetched from Api")
                                viewModel.imagesFromNetwork.observe(viewLifecycleOwner, Observer {

                                    textView1.text = it.toString()

//                                    it.hits.forEach {
//                                         textView1.text = it.user
//                                    }
                                })

                            }
                        }

                    }
                })

            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImageListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}