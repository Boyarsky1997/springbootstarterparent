package com.github.boyarsky1997.springboot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @Column(name = "title")
    private String title;

    @Column(name = "anons")
    private String anons;

    @Column(name = "fullText")
    private String fullText;

    @Column(name = "views")
    private int views;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "clientId")
    @JsonIgnoreProperties("posts")
    private Client client;

    public Post(String title, String anons, String fullText, Client client) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
        this.client = client;
    }


    public Post(String title, String anons, String fullText, int views, Client client) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
        this.views = views;
        this.client = client;
    }

    public Post() {
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
