package com.krince.memegle.global.constant;

public enum Criteria {

    CREATED_AT("createdAt"),
    MODIFIED_AT("modifiedAt"),
    POPULARITY("popularity");

    final String criteria;

    Criteria(String criteria) {
        this.criteria = criteria;
    }

    public String getCriteria() {
        return criteria;
    }
}
