package com.flowz.paybackjobapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.flowz.paybackjobapp.R
import com.flowz.paybackjobapp.databinding.FragmentImageDetailBinding
import com.flowz.paybackjobapp.databinding.FragmentImageListBinding
import com.flowz.paybackjobapp.models.Hit


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class ImageDetailFragment : Fragment(R.layout.fragment_image_detail) {

    private var _binding: FragmentImageDetailBinding? = null
    private val binding get() = _binding!!

    private var hit: Hit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            hit = ImageDetailFragmentArgs.fromBundle(it).hit
        }
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_image_detail, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentImageDetailBinding.bind(view)


        binding.apply {

            hit?.apply {

                numberOfLikes.text = this.likes.toString()
                numberOfComments.text = this.comments.toString()
                numberOfDownloads.text = this.downloads.toString()
                fUserName.text = this.user
                fImageTags.text = this.tags
            }



            Glide.with(imageDetailLarge)
                .load(hit?.largeImageURL)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_search_24)
                .error(R.drawable.ic_baseline_error_24)
                .fallback(R.drawable.ic_baseline_search_24)
                .into(imageDetailLarge)

        }



    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImageDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}