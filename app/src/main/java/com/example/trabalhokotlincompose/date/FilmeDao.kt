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
    suspend fun addFilme(filme: Filme)

    @Update
    suspend fun atualizarFilme(filme: Filme)

    @Delete
    suspend fun deletarFilme(filme: Filme)

    @Query("SELECT * FROM filme_table")
    fun listarFilmes() : LiveData<Filme>

    @Query("SELECT * FROM filme_table ORDER BY ano ASC")
    suspend fun listarFilmesEmOrdem() : List<Filme>

    @Query("SELECT * FROM filme_table WHERE id = :id")
    suspend fun getFilmeById(id: Int): LiveData<Filme>

}

