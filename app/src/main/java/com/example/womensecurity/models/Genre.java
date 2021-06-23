package com.example.womensecurity.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.Collections;

public class Genre extends ExpandableGroup {

    public Genre(String title, String items) {
        super(title, Collections.singletonList(items));
    }
}
