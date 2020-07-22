package com.example.roomdemo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface Playlist {
    @Query("SELECT * FROM tunes ORDER BY title")
    List<Tune> selectAll();

    @Insert
    void insert(Tune... tunes);

    @Delete
    void delete(Tune... tunes);

    @Update
    void update(Tune... tunes);
}
