package com.qdocs.ssre241123.model;

public class FeeCategory {
    private String feeCategory;

    public FeeCategory(String feeCategory) {
        this.feeCategory = feeCategory;
    }

    public String getFeeCategory() {
        return feeCategory;
    }

    public void setFeeCategory(String feeCategory) {
        this.feeCategory = feeCategory;
    }

    @Override
    public String toString() {
        return "FeeCategory{" +
                "feeCategory='" + feeCategory + '\'' +
                '}';
    }
}