package com.cms.model;

import java.time.LocalDateTime;

public class Content {
    private int id;
    private int authorID;
    private String title;
    private String brief;
    private String content;
    private String sort;
    private LocalDateTime createDate;
    private LocalDateTime updateTime;

    public Content(int id, int authorID, String title, String brief, String content, String sort, LocalDateTime createDate, LocalDateTime updateTime) {
        this.id = id;
        this.authorID = authorID;
        this.title = title;
        this.brief = brief;
        this.content = content;
        this.sort = sort;
        this.createDate = createDate;
        this.updateTime = updateTime;
    }

    public Content() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
