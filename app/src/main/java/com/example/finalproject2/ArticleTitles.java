package com.example.finalproject2;

/**
 * Article titles.
 */
public class ArticleTitles {

    public Long id;
    public String title;
    public String section;
    public String url;

    /**
     * Constructor.
     *
     * @param id
     * @param title
     * @param section
     * @param url
     */
    public ArticleTitles(Long id, String title, String section, String url) {

        this.id = id;
        this.title = title;
        this.section = section;
        this.url = url;
    }
}