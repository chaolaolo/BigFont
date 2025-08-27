package com.example.bigfont.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bigfont.data.entity.FontWeight;

import java.util.List;

@Dao
public interface FontWeightDao {

    @Insert
    void insert(FontWeight fontWeight);

    @Query("SELECT * FROM font_weights ORDER BY style ASC")
    List<FontWeight> getAllFontWeights();

    @Query("SELECT * FROM font_weights WHERE isDefault = 1 LIMIT 1")
    FontWeight getDefaultFontWeight();

    @Query("SELECT * FROM font_weights WHERE name = :name LIMIT 1")
    FontWeight getFontWeightByName(String name);

    @Delete
    void delete(FontWeight fontWeight);

    @Update
    void update(FontWeight fontWeight);

}
