package com.example.roomdemo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roomdemo.db.Playlist;
import com.example.roomdemo.db.TunesDB;
import com.example.roomdemo.domain.Tune;

public class MainActivity extends AppCompatActivity {
    TunesDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = TunesDB.get(this); // открывает БД, если её нет - создать

        // отобразим данные в БД на ListView
        // делаем выборку всех композиций
        Cursor c = db.query("SELECT * FROM tunes", null);
        //setCursorInUIThread(c);
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(this, R.layout.tune_item, c, c.getColumnNames(), new int[]{R.id._id, R.id.artist, R.id.title, R.id.year}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        ListView lv = findViewById(R.id.listview);
        lv.setAdapter(adapter);

        // Выбор в ListView
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ColorDrawable d = (ColorDrawable) view.getBackground();
                if (d != null && d.getColor() == Color.YELLOW) {
                    view.setBackgroundColor(Color.TRANSPARENT);
                    // TODO задание: удалить запись, которая выделена пользователем (position - номер в списке)
                    deleteTune(id);
                } else view.setBackgroundColor(Color.YELLOW);

            }
        };
        lv.setOnItemClickListener(listener);

        // задание: создать метод для генерации случайных продуктов и
        // вставки этих данных в таблицу

    }

    // Упрощает использование и обновление Курсора (данных полученных из таблицы)
    public void setCursorInUIThread(Cursor c) {
        Context ctx = getApplicationContext();
        // Для Асинхронности и обращения в ней к элементам интерфейса
        runOnUiThread(new Runnable() {
            // Получения данных из таблицы и передача в Адаптер
            @Override
            public void run() {
                SimpleCursorAdapter adapter =
                        new SimpleCursorAdapter(ctx, R.layout.tune_item, c, c.getColumnNames(), new int[]{R.id._id, R.id.artist, R.id.title, R.id.year}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                Log.d("PAS-LOG", "Records in adapter: " + adapter.getCount());
                ListView lv = findViewById(R.id.listview);
                lv.setAdapter(adapter);
            }
        });

    }

    // Полное удаление через отдельный Асинхронный поток
    public void onClearClick(View v) {
        new Thread() {
            @Override
            public void run() {
                db.clearAllTables();
                Cursor c = db.query("SELECT * FROM tunes", null);
                setCursorInUIThread(c);

            }
        }.start();

    }

    // Добаваление записи также в отдельном потоке
    public void onAddTuneClick(View v) {
        new Thread() {
            @Override
            public void run() {

                // Получаем Интерфейс обращения к таблице
                Playlist playlist = db.playlist();

                Tune tune = new Tune(playlist.findMaxId() + 1, "The Prodigy", "Matrix theme", 2000);
                playlist.insert(tune);

                Cursor cursor = db.query("SELECT * FROM tunes", null);
                Log.d("PAS-LOG", "Records after insert: " + cursor.getCount());
                setCursorInUIThread(cursor);
            }
        }.start(); // запустит созданный поток
    }

    // Удаление записи также в отдельном потоке
    public void deleteTune(long id) {
        new Thread() {
            @Override
            public void run() {

                // Получаем Интерфейс обращения к таблице
                Playlist playlist = db.playlist();

                Tune tune = playlist.findById(id);
                playlist.delete(tune);

                Cursor cursor = db.query("SELECT * FROM tunes", null);
                Log.d("PAS-LOG", "Records after delete: " + cursor.getCount());
                setCursorInUIThread(cursor);
            }
        }.start(); // запустит созданный поток
    }

}
