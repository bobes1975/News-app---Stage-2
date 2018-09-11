package com.example.android.newsappstage2_rebuild;

public class News {
    // News title
    private final String newsTitle;

    // News section
    private final String newsCategory;

    // Author name
    private final String newsAuthor;

    // Date of publishing
    private final String newsDate;

    // News URL
    private final String newsUrl;

    // News thumb url
    private final String newsThumb;

    /*
     * Constructs a new EducationNews object.
     *
     * @param title    is news title
     * @param category is the section of the news
     * @param author   is author name
     * @param date     is news date publishing
     * @param url      is news URL
    //* @param thumb   is news thumbnail url
     */

    public News(String title, String category, String author, String date, String url, String thumb) {
        newsTitle = title;
        newsCategory = category;
        newsAuthor = author;
        newsDate = date;
        newsUrl = url;
        newsThumb = thumb;
    }

    /**
     * Returns the news title.
     */
    public String getNewsTitle() {
        return newsTitle;
    }

    /**
     * Returns the news category.
     */
    public String getNewsCategory() {
        return newsCategory;
    }

    /**
     * Returns the name of author.
     */
    public String getNewsAuthor() {
        return newsAuthor;
    }

    /**
     * Returns publishing date.
     */
    public String getNewsDate() {
        return newsDate;
    }

    /**
     * Returns the news URL.
     */
    public String getUrl() {
        return newsUrl;
    }


    //Returns the thumbnail URL.

    public String getNewsThumb() {
        return newsThumb;
    }

}