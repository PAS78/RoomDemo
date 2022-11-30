package com.example.roomdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Наша БД

// Указывается для какой модели данных создаем БД
// При измении модели данных нужно поменять версию
@Database(entities={Tune.class}, version=2)
public abstract class TunesDB extends RoomDatabase {
    // возвращает Интерфейс обращяени к данным
    abstract Playlist playlist();

    private static final String DB_NAME="tunes.db";
    // ссылка на экземпляр класса
    // volatile - переменная без кэширования
    private static volatile TunesDB INSTANCE=null;

    // метод получени БД
    synchronized static TunesDB get(Context ctxt) {

        // проверяем есть ли БД в памяти (определен ли INSTANCE)
        if (INSTANCE==null) {
            // создаем БД если нет
            INSTANCE=create(ctxt, false);
        }
        return(INSTANCE);
    }

    // метод создания БД
    // если БД нет - создается, если есть - открывается на запись
    static TunesDB create(Context ctxt, boolean memoryOnly) {
        RoomDatabase.Builder<TunesDB> b;
        if (memoryOnly) {
            b = Room.inMemoryDatabaseBuilder(ctxt.getApplicationContext(),
                    TunesDB.class);
        }
        else {
            b=Room.databaseBuilder(ctxt.getApplicationContext(), TunesDB.class,
                    DB_NAME);
        }
        return(b.build());

    }
}
