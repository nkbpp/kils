package ru.pfr.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "logi")
public class Logi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "datelog")
    private Date date;

    @Column(name = "user")
    private String user;

    @Column(name = "type")
    private Long type;

    @Column(name = "text")
    private String text;

    public Logi() {
    }

    public Logi(Date date, String user, Long type, String text) {
        this.date = date;
        this.user = user;
        this.type = type;
        this.text = text;
    }

    public Logi(Date date, String user, String text) {
        this.date = date;
        this.user = user;
        this.type = 0l;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
