package com.skillswap.model;
import jakarta.persistence.*;
@Entity @Table(name="reviews")
public class Review { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(optional=false) private User reviewer; @ManyToOne(optional=false) private User reviewedUser; private int rating; @Column(length=1000) private String comment; public Review(){} public Long getId(){return id;} public User getReviewer(){return reviewer;} public User getReviewedUser(){return reviewedUser;} public int getRating(){return rating;} public String getComment(){return comment;} public void setReviewer(User x){reviewer=x;} public void setReviewedUser(User x){reviewedUser=x;} public void setRating(int x){rating=x;} public void setComment(String x){comment=x;} }
