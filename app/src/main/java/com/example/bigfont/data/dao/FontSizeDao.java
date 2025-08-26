package com.example.bigfont.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bigfont.data.entity.FontSize;

import java.util.List;

@Dao
public interface FontSizeDao {
    @Insert
    void insert(FontSize fontSize);

    @Query("SELECT * FROM font_sizes")
    List<FontSize> getAllFontSizes();

    @Query("SELECT * FROM font_sizes WHERE isDefault = 1 LIMIT 1")
    FontSize getDefaultFontSize();

    @Query("DELETE FROM font_sizes")
    void deleteAll();
}
