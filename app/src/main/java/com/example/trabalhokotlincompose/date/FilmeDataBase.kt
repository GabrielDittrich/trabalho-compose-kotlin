package com.example.trabalhokotlincompose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Filme::class], version = 1, exportSchema = false)
abstract class FilmeDataBase: RoomDatabase() {

    abstract fun filmeDao(): FilmeDao

    companion object{

        @Volatile
        private var INSTANCE: FilmeDataBase? = null

        fun getDatabase(context: Context): FilmeDataBase {

            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }else{

                synchronized(this){

                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FilmeDataBase::class.java,
                        "filme_table"
                    ).build()

                    INSTANCE = instance
                    return instance

                }

            }

        }

    }
}

