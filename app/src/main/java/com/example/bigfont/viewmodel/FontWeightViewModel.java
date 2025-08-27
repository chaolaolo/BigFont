package com.example.bigfont.viewmodel;

import android.app.Application;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bigfont.data.database.AppDatabase;
import com.example.bigfont.data.entity.FontWeight;
import com.example.bigfont.data.repository.FontWeightRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FontWeightViewModel extends AndroidViewModel {
    private final FontWeightRepository repository;

    private final MutableLiveData<List<FontWeight>> _fontWeights = new MutableLiveData<>();
    public LiveData<List<FontWeight>> fontWeights = _fontWeights;

    private final MutableLiveData<String> _snackbarMessage = new MutableLiveData<>();
    public LiveData<String> snackbarMessage = _snackbarMessage;


    private static final ExecutorService INIT_EXECUTOR = Executors.newSingleThreadExecutor();

    public FontWeightViewModel(@NonNull Application application) {
        super(application);
        repository = new FontWeightRepository(AppDatabase.getDatabase(application).fontWeightDao());

        INIT_EXECUTOR.execute(() -> {
            if (repository.getAllFontWeights().isEmpty()) {
                initializeData();
            }
            loadFontWeights();
        });

    }

    private void initializeData() {
        repository.insert(new FontWeight("Normal", Typeface.NORMAL, true));
        repository.insert(new FontWeight("Bold", Typeface.BOLD, true));
        repository.insert(new FontWeight("Italic", Typeface.ITALIC, true));
        repository.insert(new FontWeight("Bold Italic", Typeface.BOLD_ITALIC, true));
    }

    private void loadFontWeights() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<FontWeight> weights = repository.getAllFontWeights();
            _fontWeights.postValue(weights);
        });
    }

    public void applyFontWeight(FontWeight newWeight) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            FontWeight currentDefault = repository.getDefaultFontWeight();
            if (currentDefault != null) {
                currentDefault.setDefault(false);
                repository.update(currentDefault);
            }
            newWeight.setDefault(true);
            repository.update(newWeight);
            loadFontWeights();
            _snackbarMessage.postValue("applied_font_weight:" + newWeight.getName());
        });
    }

    public void resetToDefault() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            FontWeight defaultWeight = repository.getFontWeightByName("Normal");
            if (defaultWeight != null) {
                FontWeight currentDefault = repository.getDefaultFontWeight();
                if (currentDefault != null) {
                    currentDefault.setDefault(false);
                    repository.update(currentDefault);
                }
                defaultWeight.setDefault(true);
                repository.update(defaultWeight);
            }
            loadFontWeights();
            _snackbarMessage.postValue("reset_to_default:Normal");
        });
    }

    public FontWeight getDefaultFontWeight() {
        // Thực hiện trên luồng chính vì chỉ để đọc và không làm tắc nghẽn
        try {
            return AppDatabase.databaseWriteExecutor.submit(() -> repository.getDefaultFontWeight()).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
