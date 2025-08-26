package com.example.bigfont.data.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bigfont.data.entity.Tip;

import java.util.List;

@Dao
public interface TipDao {
    @Insert
    void insert(Tip tip);

    @Query("SELECT * FROM tips")
    List<Tip> getAllTips();

    @Query("DELETE FROM tips")
    void deleteAll();
}