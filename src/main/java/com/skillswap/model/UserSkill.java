package com.skillswap.model;
import jakarta.persistence.*;
@Entity @Table(name="user_skills")
public class UserSkill { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(optional=false) private User user; @ManyToOne(optional=false) private Skill skill; @Enumerated(EnumType.STRING) private SkillType type; public UserSkill(){} public Long getId(){return id;} public User getUser(){return user;} public Skill getSkill(){return skill;} public SkillType getType(){return type;} public void setId(Long id){this.id=id;} public void setUser(User u){this.user=u;} public void setSkill(Skill s){this.skill=s;} public void setType(SkillType t){this.type=t;} public enum SkillType {TEACH,LEARN} }
