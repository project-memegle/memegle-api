package com.krince.memegle.global;

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
