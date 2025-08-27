package com.example.bigfont.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "font_weights")
public class FontWeight {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int style;
    private boolean isDefault;

    public FontWeight(String name, int style, boolean isDefault) {
        this.name = name;
        this.style = style;
        this.isDefault = isDefault;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
