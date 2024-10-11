package repository

import androidx.lifecycle.LiveData
import modelClass.Webtoons
import room.WebtoonsDAO

class WebtoonsRepository(private val WebtoonsDao:WebtoonsDAO) {

    fun getWT():LiveData<List<Webtoons>>{
        return WebtoonsDao.getWebtoons()
    }
    suspend fun updateWTFav(f:Int,idPass:Int){
        WebtoonsDao.updateWebtoonsFav(f,idPass)
    }
    suspend fun updateWTRat(r:Int,idPass:Int){
        WebtoonsDao.updateWebtoonsRating(r,idPass)
    }
    suspend fun insertWT(toons:Webtoons){
        WebtoonsDao.insertWebtoons(toons)
    }
    suspend fun deleteWT(toons:Webtoons){
        WebtoonsDao.deleteWebtoons(toons)
    }
    fun getWTWithId(id:Int):LiveData<Webtoons>{
        return WebtoonsDao.getWebtoonsWithId(id)
    }
}