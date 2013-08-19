package com.nils.becker.fhplaner.model;

/**
 * Created by nils on 8/15/13.
 */
public class RowSection implements RowItem {

    public RowSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
}
