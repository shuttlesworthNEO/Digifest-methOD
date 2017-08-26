package com.example.digifestmethod.models;

/**
 * Created by piyush0 on 18/08/17.
 */

public class Query {
    String id;
    String text;

    public Query(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Query{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
