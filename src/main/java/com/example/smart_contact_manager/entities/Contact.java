package com.example.smart_contact_manager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;
    private String name;
    private String nick_name;
    private String phone;
    @Column(unique = true)
    private String email;
    @Column(length = 1000)
    private String description;
    private String work;

    @ManyToOne()
    private User user;

    public Contact() {
        super();
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // @Override
    // public String toString() {
    //     return "Contact [cid=" + cid + ", name=" + name + ", nick_name=" + nick_name + ", phone=" + phone + ", email="
    //             + email + ", description=" + description +  ", work=" + work + ", user="
    //             + user + "]";
    // }

    
}
