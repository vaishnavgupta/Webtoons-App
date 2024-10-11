package viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modelClass.Webtoons
import repository.WebtoonsRepository

class MainViewModel(private val wTRepository: WebtoonsRepository):ViewModel() {
    fun getWt():LiveData<List<Webtoons>>{
        return wTRepository.getWT()
    }
    fun insertQuote(wT:Webtoons){
        viewModelScope.launch(Dispatchers.IO){  //a couroutine fun to call suspend fun
            wTRepository.insertWT(wT)
        }
    }
    fun updateFav(f:Int,id:Int){
        viewModelScope.launch(Dispatchers.IO){
            wTRepository.updateWTFav(f,id)
        }
    }
    fun updateRat(r:Int,id:Int){
        viewModelScope.launch(Dispatchers.IO){
            wTRepository.updateWTRat(r,id)
        }
    }
    fun getWtWId(id:Int):LiveData<Webtoons>{
        return wTRepository.getWTWithId(id)
    }



}