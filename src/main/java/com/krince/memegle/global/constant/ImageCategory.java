package com.krince.memegle.global.constant;

public enum ImageCategory {

    MUDO("MUDO"),
    ETC("ETC");

    String category;

    ImageCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
