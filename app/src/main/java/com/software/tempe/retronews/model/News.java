package com.software.tempe.retronews.model;

import java.util.List;

public class News {
    private String status;
    private int totalResult;
    private List<Articles> articles;

    public String getStatus() {
        return status;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public List<Articles> getArticles() {
        return articles;
    }
}
