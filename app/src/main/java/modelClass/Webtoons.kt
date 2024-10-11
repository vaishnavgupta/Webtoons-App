package modelClass

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "data")
data class Webtoons(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val heading:String,
    val image:String,
    val creator:String,
    val episode:Int,
    val content:String,
    val favourite:Int,
    val avg_rating:Int

)
