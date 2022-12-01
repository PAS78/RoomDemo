package com.example.roomdemo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdemo.domain.Tune;

import java.util.List;

// Список песен (Плей лист)

@Dao // Объект доступа к данным
// Указываем каким образом осуществляем выборку, вставку и адуление данных
public interface Playlist {
    @Query("SELECT * FROM tunes ORDER BY title")
    List<Tune> selectAll();

    @Query("SELECT * FROM tunes WHERE year = 2021 ORDER BY title")
    List<Tune> selectThisYearTunes();

    @Query("SELECT * FROM tunes WHERE _id=:id")
    Tune findById(long id);

    // Получаем максимальный id для инкримента следующего
    @Query("SELECT MAX(_id) FROM tunes")
    int findMaxId();

    @Insert
        // доделать
    void insert(Tune... tunes);

    @Delete ()
        // доделать
    void delete(Tune... tunes);

    @Update
        // доделать
    void update(Tune... tunes);
}
