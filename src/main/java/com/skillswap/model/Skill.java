package com.skillswap.model;
import jakarta.persistence.*;
@Entity @Table(name="skills")
public class Skill { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String name; private String category; public Skill(){} public Skill(String name,String category){this.name=name;this.category=category;} public Long getId(){return id;} public String getName(){return name;} public String getCategory(){return category;} public void setId(Long id){this.id=id;} public void setName(String name){this.name=name;} public void setCategory(String category){this.category=category;} }
