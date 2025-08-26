package com.example.bigfont.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "font_sizes")
public class FontSize {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int sizeInPercent;
    private boolean isDefault;

    public FontSize(int sizeInPercent, boolean isDefault) {
        this.sizeInPercent = sizeInPercent;
        this.isDefault = isDefault;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSizeInPercent() {
        return sizeInPercent;
    }

    public void setSizeInPercent(int sizeInPercent) {
        this.sizeInPercent = sizeInPercent;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}