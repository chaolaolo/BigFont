package com.example.bigfont.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bigfont.data.dao.FontSizeDao;
import com.example.bigfont.data.database.AppDatabase;
import com.example.bigfont.data.entity.FontSize;
import com.example.bigfont.data.repository.FontSizeRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private final FontSizeRepository repository;
    private final MutableLiveData<List<FontSize>> _fontSizes = new MutableLiveData<>();
    public LiveData<List<FontSize>> fontSizes = _fontSizes;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        FontSizeDao fontSizeDao = AppDatabase.getDatabase(application).fontSizeDao();
        repository = new FontSizeRepository(fontSizeDao);
        loadFontSizes();
    }

    private void loadFontSizes() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<FontSize> fontSizes = repository.getAllFontSizes();
            _fontSizes.postValue(fontSizes);
        });
    }

    public void insert(FontSize fontSize) {
        repository.insert(fontSize);
    }

}
