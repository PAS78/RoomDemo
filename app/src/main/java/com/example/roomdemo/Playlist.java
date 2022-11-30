package com.example.roomdemo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Список песен (Плей лист)

@Dao // Объект доступа к данным
    // Указываем как образом осуществляем выборку, вставку и адуление данных
interface Playlist {
    @Query("SELECT * FROM tunes ORDER BY title")
    List<Tune> selectAll();

    @Query("SELECT * FROM tunes WHERE year = 2021 ORDER BY title")
    List<Tune> selectThisYearTunes();

    @Query("SELECT * FROM tunes WHERE _id=:id")
    Tune findById(int id);

    @Insert // доделать
    void insert(Tune... tunes);

    @Delete // доделать
    void delete(Tune... tunes);

    @Update // доделать
    void update(Tune... tunes);
}
