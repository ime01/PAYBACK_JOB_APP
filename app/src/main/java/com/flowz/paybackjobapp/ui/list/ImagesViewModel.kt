package com.flowz.paybackjobapp.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowz.paybackjobapp.models.ImageResponse
import com.flowz.paybackjobapp.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


enum class  ImagesApiStatus {LOADING, ERROR, DONE}

@HiltViewModel
class ImagesViewModel @Inject constructor(private val imagesRepository: ImageRepository) : ViewModel(){

    val imagesFromNetwork = MutableLiveData<ImageResponse>()
    val imagesFromNetworkStaus = MutableLiveData<ImagesApiStatus>()


    fun searchImageTypeFromNetwork(imageType: String){

//        EspressoIdlingResource.increment()

        viewModelScope.launch(Dispatchers.IO){
            try {
                withContext(Dispatchers.Main){
                    imagesFromNetworkStaus.value = ImagesApiStatus.LOADING
                }

                imagesFromNetwork.postValue(imagesRepository.fetchAllImages(imageType))

                withContext(Dispatchers.Main){
                    imagesFromNetworkStaus.value = ImagesApiStatus.DONE
                }
//            Sending Drinks from Network into Room Database
//                val alldrinks = imagesRepository.fetchAllDrinks(drinkType)
//
//                imagesRepository.insertListOfDrinksIntoDb(alldrinks.drinks)

            }catch (e:Exception){
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    imagesFromNetworkStaus.value = ImagesApiStatus.ERROR
                }
            }

        }
    }




}