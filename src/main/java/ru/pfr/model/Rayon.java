package ru.pfr.model;

//import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "rayon")
public class Rayon implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rayon")
    private Long id_rayon;

    @Column(name = "namerayon")
    private String namerayon;

    @Column(name = "kod")
    private String kod;

    @Column(name = "regnummru")
    private String regnummru;

    @Column(name = "inn")
    private String inn;

    @Column(name = "kpp")
    private String kpp;

    @Column(name = "kategorija")
    private String kategorija;

    //дата постановки на учет
    @Column(name = "data_p_u")
    private String data_p_u;

    //дата снятия с учета
    @Column(name = "data_s_u")
    private String data_s_u;

    public Rayon() {
    }

    public Rayon(String namerayon, String kod) {
        this.namerayon = namerayon;
        this.kod = kod;
    }

    public Rayon(Long id_rayon, String namerayon, String kod) {
        this.id_rayon = id_rayon;
        this.namerayon = namerayon;
        this.kod = kod;
    }

    public Rayon(String namerayon, String kod, String regnummru, String inn, String kpp) {
        this.namerayon = namerayon;
        this.kod = kod;
        this.regnummru = regnummru;
        this.inn = inn;
        this.kpp = kpp;
    }

    public Rayon(String namerayon, String kod, String regnummru, String inn, String kpp, String kategorija, String data_p_u, String data_s_u) {
        this.namerayon = namerayon;
        this.kod = kod;
        this.regnummru = regnummru;
        this.inn = inn;
        this.kpp = kpp;
        this.kategorija = kategorija;
        this.data_p_u = data_p_u;
        this.data_s_u = data_s_u;
    }

    public Long getId() {
        return id_rayon;
    }

    public void setId(Long id) {
        this.id_rayon = id;
    }

    public String getNamerayon() {
        return namerayon;
    }

    public void setNamerayon(String namerayon) {
        this.namerayon = namerayon;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getRegnummru() {
        return regnummru;
    }

    @Override
    public String getAuthority() {
        return getKod();
    }

    public String getInn() {
        return inn;
    }

    public String getKpp() {
        return kpp;
    }

    public String getKategorija() {
        return kategorija;
    }

    public String getData_p_u() {
        return data_p_u;
    }

    public String getData_s_u() {
        return data_s_u;
    }
}
