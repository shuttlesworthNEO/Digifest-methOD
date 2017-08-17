package com.example.digifestmethod.models;

/**
 * Created by piyush0 on 18/08/17.
 */

public class Feedback {
    private Integer resolved;
    private String queryId;
    private String username;

    public Feedback(Integer resolved, String queryId, String username) {
        this.resolved = resolved;
        this.queryId = queryId;
        this.username = username;
    }
}
