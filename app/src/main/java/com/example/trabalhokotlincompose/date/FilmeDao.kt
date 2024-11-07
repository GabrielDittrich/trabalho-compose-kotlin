package com.example.trabalhokotlincompose.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FilmeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFilme(filme: Filme)

    @Update
    fun atualizarFilme(filme: Filme)

    @Delete
    fun deletarFilme(filme: Filme)

    @Query("SELECT * FROM filme_table")
    fun listarFilmes(): LiveData<List<Filme>>

    @Query("SELECT * FROM filme_table ORDER BY ano ASC")
    fun listarFilmesEmOrdem() : List<Filme>

    @Query("SELECT * FROM filme_table WHERE id = :id")
    fun getFilmeById(id: Int): LiveData<Filme>

}

