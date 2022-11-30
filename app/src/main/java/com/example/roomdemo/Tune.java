package com.example.roomdemo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Locale;

// Сущность данных - domain

// Мапим сущность на таблицу БД
@Entity(tableName = "tunes")
public class Tune {
    @PrimaryKey
    @NonNull
    int _id;  // _ для совместимости с адаптерами
    String artist, title;
    int year;

    // ignore annotation here
    @Ignore // для избежания кофликта с библиотекой room
    public Tune() {
        this._id = 11;
        this.year = 1999;
        this.artist = "";
        this.title = "";
    }

    public Tune(int _id, String artist, String title, int year) {
        this._id = _id;
        this.artist = artist;
        this.title = title;
        this.year = year;

    }

    @Override // переопределение метода toString для строкового представление Объекта
    public String toString() { return String.format(Locale.getDefault(), "%s: %s (%d)", artist, title , year); }


}
