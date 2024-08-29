package com.krince.memegle.global;

public enum Criteria {

    CREATED_AT("createdAt"),
    MODIFIED_AT("modifiedAt");

    final String criteria;

    Criteria(String criteria) {
        this.criteria = criteria;
    }

    public String getCriteria() {
        return criteria;
    }
}