package com.smartspace;

public class ColorItem {
    private String colorName;
    private int imageResourceId;

    public ColorItem(String colorName, int imageResourceId) {
        this.colorName = colorName;
        this.imageResourceId = imageResourceId;
    }

    public String getColorName() {
        return colorName;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}

