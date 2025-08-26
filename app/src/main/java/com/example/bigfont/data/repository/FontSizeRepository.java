package com.example.bigfont.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bigfont.data.dao.FontSizeDao;
import com.example.bigfont.data.database.AppDatabase;
import com.example.bigfont.data.entity.FontSize;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class FontSizeRepository {
    private final FontSizeDao fontSizeDao;

    public FontSizeRepository(FontSizeDao fontSizeDao) {
        this.fontSizeDao = fontSizeDao;
    }

    public List<FontSize> getAllFontSizes() {
        return fontSizeDao.getAllFontSizes();
    }

    public void insert(FontSize fontSize) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            fontSizeDao.insert(fontSize);
        });
    }

    public void delete(FontSize fontSize) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            fontSizeDao.delete(fontSize);
        });
    }

    public void update(FontSize fontSize) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            fontSizeDao.update(fontSize);
        });
    }
}