package com.qdocs.ssre241123.model;

public class MyModel {
    private String name;
    private boolean isChecked;

    public MyModel(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
