package com.example.bigfont.data.repository;

import com.example.bigfont.data.dao.FontWeightDao;
import com.example.bigfont.data.database.AppDatabase;
import com.example.bigfont.data.entity.FontWeight;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FontWeightRepository {
    private final FontWeightDao fontWeightDao;

    public FontWeightRepository(FontWeightDao fontWeightDao) {
        this.fontWeightDao = fontWeightDao;
    }

    public List<FontWeight> getAllFontWeights() {
        try {
            return AppDatabase.databaseWriteExecutor.submit(fontWeightDao::getAllFontWeights).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FontWeight getDefaultFontWeight() {
        try {
            return AppDatabase.databaseWriteExecutor.submit(fontWeightDao::getDefaultFontWeight).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FontWeight getFontWeightByName(String name) {
        try {
            return AppDatabase.databaseWriteExecutor.submit(() -> fontWeightDao.getFontWeightByName(name)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(FontWeight fontWeight) {
        AppDatabase.databaseWriteExecutor.execute(() -> fontWeightDao.insert(fontWeight));
    }

    public void delete(FontWeight fontWeight) {
        AppDatabase.databaseWriteExecutor.execute(() -> fontWeightDao.delete(fontWeight));
    }

    public void update(FontWeight fontWeight) {
        AppDatabase.databaseWriteExecutor.execute(() -> fontWeightDao.update(fontWeight));
    }

}
