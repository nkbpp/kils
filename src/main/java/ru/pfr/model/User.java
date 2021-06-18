package ru.pfr.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "active")
    private Long active;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_rayon")
    private Rayon rayon;

    public User() {
    }

    public User(Long id, String login, Long active, Rayon rayon) {
        this.id = id;
        this.login = login;
        this.active = active;
        this.rayon = rayon;
        this.date = new Date();
    }

    public User(String login, Long active, Rayon rayon) {
        this.login = login;
        this.active = active;
        this.rayon = rayon;
        this.date = new Date();
    }

    public User(String login, Rayon rayon) {
        this.login = login;
        this.active = 1l;
        this.rayon = rayon;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }

    public Rayon getRayon() {
        return rayon;
    }

    public void setRayon(Rayon rayon) {
        this.rayon = rayon;
    }
}
