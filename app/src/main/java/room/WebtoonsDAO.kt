package room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import modelClass.Webtoons


@Dao
interface WebtoonsDAO {
    @Query("UPDATE data SET favourite=:f WHERE id LIKE :idpass")
    suspend fun updateWebtoonsFav(f:Int,idpass:Int)
    @Query("UPDATE data SET avg_rating=:r WHERE id LIKE :idpass")
    suspend fun updateWebtoonsRating(r:Int,idpass:Int)
    @Insert
    suspend fun insertWebtoons(toons:Webtoons)
    @Delete
    suspend fun deleteWebtoons(toons:Webtoons)
    @Query("SELECT * FROM data")
    fun getWebtoons():LiveData<List<Webtoons>>
    @Query("SELECT * FROM data WHERE id LIKE :id")
    fun getWebtoonsWithId(id:Int):LiveData<Webtoons>

}
