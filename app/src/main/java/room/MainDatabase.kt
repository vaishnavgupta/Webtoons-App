package room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import modelClass.Webtoons


@Database(entities = [Webtoons::class], version = 1)
abstract class MainDatabase: RoomDatabase() {
    abstract fun webtoonsDao():WebtoonsDAO


    companion object{
        @Volatile
        private var INSTANCE:MainDatabase?=null


        //declaring public method for private field
        fun getDatabase(context: Context):MainDatabase{
            if(INSTANCE==null){
                synchronized(this){    //multiple threads mein bhi ek hi instance banegi
                    //creating instance of database
                    INSTANCE= Room.databaseBuilder(context.applicationContext,MainDatabase::class.java,"webtoonstestDb")
                        .createFromAsset("webtoonstest.db")
                        .build()
                }


            }
            return INSTANCE!!


        }
    }
}
