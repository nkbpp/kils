package ru.pfr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.everything.DateFormat;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data // 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
//@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "people")
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_people")
    private Long id_people;

    @Column(name = "fam")
    private String fam;

    @Column(name = "name")
    private String name;

    @Column(name = "otch")
    private String otch;

    @Column(name = "pol")
    private String pol;

    @Column(name = "date_birthday")
    private String date_birthday;

    @Column(name = "snils")
    private String snils;

    @Column(name = "date_reg")
    private String date_reg;

    @Column(name = "data_create")
    private Date data_create;

    @Column(name = "nameTOPFR")
    private String nameTOPFR;

    @Column(name = "dataAct")
    private String dataAct;

    @Column(name = "nomAct")
    private String nomAct;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SOPS_do> sops_dos;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SOPS_posle> sops_posles;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Staj> stajs;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Inoe> inoes;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    private int status;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "rayon_kor")
    private Rayon rayon_kor;

    public People(String fam, String name, String otch, String pol, String date_birthday, String snils, String date_reg, Date data_create, List<SOPS_do> sops_dos, List<SOPS_posle> sops_posles, List<Staj> stajs, List<Inoe> inoes, User user) {
        this.fam = fam;
        this.name = name;
        this.otch = otch;
        this.pol = pol;
        this.date_birthday = date_birthday;
        this.snils = snils;
        this.date_reg = date_reg;
        this.data_create = data_create;
        this.sops_dos = sops_dos;
        this.sops_posles = sops_posles;
        this.stajs = stajs;
        this.inoes = inoes;
        this.user = user;
        status = 0;
        rayon_kor = user.getRayon();
    }

    public boolean isRayonKor(Rayon r) {
        if(rayon_kor == null)return false;
        if(rayon_kor.getId().equals(r.getId())) return true;
        return false;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOtch(String otch) {
        this.otch = otch;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public void setDate_birthday(String date_birthday) {
        this.date_birthday = date_birthday;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public void setDate_reg(String date_reg) {
        this.date_reg = date_reg;
    }

    public void setSops_dos(List<SOPS_do> sops_dos) {
        this.sops_dos = sops_dos;
    }

    public void setSops_posles(List<SOPS_posle> sops_posles) {
        this.sops_posles = sops_posles;
    }

    public void setInoes(List<Inoe> inoes) {
        this.inoes = inoes;
    }

    public void setStajs(List<Staj> stajs) {
        this.stajs = stajs;
    }



    public SOPS_do getSops_doById(Long id) {
        for (SOPS_do s:
             sops_dos) {
            if(id.equals(s.getId_sops_do()))
            return s;
        }
        return null;
    }

    public SOPS_posle getSops_posleById(Long id) {
        for (SOPS_posle s:
                sops_posles) {
            if(id.equals(s.getId_sops_posle()))
                return s;
        }
        return null;
    }

    public Staj getStajById(Long id) {
        for (Staj s:
                stajs) {
            //long v = s.getId_staj();
            if(id.equals(s.getId_staj())){
                return s;
            }
        }
        return null;
    }

    public People() {
        data_create = new Date();
    }

    public Long getId_people() {
        return id_people;
    }

    public String getFam() {
        return fam;
    }

    public String getName() {
        return name;
    }

    public String getOtch() {
        return otch;
    }

    public String getFIO() {
        return fam + " " + name + " " + otch;
    }

    public String getDate_birthdayD() {
        return DateFormat.DataEngToRus(date_birthday);
    }

    public String getSnils() {
        return snils;
    }

    public int getKolSops_do_doljnost_tables() {
        int i = 0;
        for (SOPS_do sops_do:
             sops_dos) {
            for (SOPS_do_doljnost sops_do_doljnost:
                 sops_do.getSops_do_doljnosts()) {
                i+=sops_do_doljnost.getSops_tables().size();
            }
        }
        return i;
    }

    public List<SOPS_do> getSops_dos() {
        //return sops_dos;
        return sops_dos==null?null:sort_sops_dos(sops_dos);
    }

    public List<SOPS_posle> getSops_posles() {
        return sops_posles==null?null:sort_sops_posle(sops_posles);
    }

    public List<Staj> getStajs() {
        return stajs==null?null:sort_staj(stajs);
    }

    public List<Inoe> getInoes() {
        return inoes==null?null:sort_inoe(inoes);
    }

    //сортировка
    private List<SOPS_do> sort_sops_dos(List<SOPS_do> s) {
        Comparator<SOPS_do> comparator = Comparator.comparing(obj -> {
            try {
                return obj.getSops_do_doljnosts().get(0).getSops_tables().get(0).getPeriod_start();
            }catch (Exception e){
                return "01.01.1800";
            }
        });
        s.sort(comparator);
        return s;
    }

    private List<SOPS_posle> sort_sops_posle(List<SOPS_posle> s) {
        Comparator<SOPS_posle> comparator = Comparator.comparing(obj -> {
            try {
                return obj.getSops_tables().get(0).getPeriod_start();
            }catch (Exception e){
                return "01.01.1800";
            }
        });
        s.sort(comparator);
        return s;
    }

    private List<Staj> sort_staj(List<Staj> s) {
        Comparator<Staj> comparator = Comparator.comparing(obj -> {
            try {
                return obj.getStaj_tables().get(0).getPeriod_s();
            }catch (Exception e){
                return "01.01.1800";
            }
        });
        s.sort(comparator);
        return s;
    }

    private List<Inoe> sort_inoe(List<Inoe> s) {
        Comparator<Inoe> comparator = Comparator.comparing(obj -> {
            try {
                return obj.getPeriod_start();
            }catch (Exception e){
                return "01.01.1800";
            }
        });
        s.sort(comparator);
        return s;
    }

    //isKor
    public boolean isKorAllR(){
        return isInoeKorR() || isStajKorR() || isSopsTablePosleKorR() || isSopsPosleZarKorR() ||
                isSopsTableDoKorR() || isSopsDoGodKorR();
    }
    public boolean isKorAllZ(){
        return  isStajKorZ() || isSopsTablePosleKorZ() || isSopsPosleZarKorZ() || isSopsTableDoKorZ();
    }

    //inoe
    public boolean isInoeKorR(){
        for (Inoe i:
                inoes) {
            if(i.getIsreshkor()==1)return true;
        }
        return false;
    }

    //staj
    public boolean isStajKorR(){
        for (Staj s:
                stajs) {
            if(s.isKorR()==true)return true;
        }
        return false;
    }
    public boolean isStajKorZ(){
        for (Staj s:
                stajs) {
            if(s.isKorZ()==true)return true;
        }
        return false;
    }

    //sopsPosleTable
    public boolean isSopsTablePosleKorR(){
        for (SOPS_posle sp:
                sops_posles) {
            if(sp.isSopsTablePosleKorR())return true;
        }
        return false;
    }
    public boolean isSopsTablePosleKorZ(){
        for (SOPS_posle sp:
                sops_posles) {
            if(sp.isSopsTablePosleKorZ())return true;
        }
        return false;
    }

    public boolean isSopsPosleZarKorR(){
        for (SOPS_posle sp:
                sops_posles) {
            if(sp.isSopsPosleZarKorR())return true;
        }
        return false;
    }
    public boolean isSopsPosleZarKorZ(){
        for (SOPS_posle sp:
                sops_posles) {
            if(sp.isSopsPosleZarKorZ())return true;
        }
        return false;
    }

    //sopsDoTable
    public boolean isSopsTableDoKorR(){
        for (SOPS_do sd:
                sops_dos) {
            if(sd.isSopsTableDoKorR())return true;
        }
        return false;
    }
    public boolean isSopsTableDoKorZ(){
        for (SOPS_do sd:
                sops_dos) {
            if(sd.isSopsTableDoKorZ())return true;
        }
        return false;
    }

    public boolean isSopsDoGodKorR(){
        for (SOPS_do sd:
                sops_dos) {
            if(sd.isSopsDoGodKorR())return true;
        }
        return false;
    }

    public String getShablonRech(){
        String s = "";

        for (SOPS_do sops_do:
                sops_dos) {
            if(sops_do.isSopsTableDoKorR() || sops_do.isSopsDoGodKorR()) s += (sops_do.toStringResh() + "; \n");
        }

        for (SOPS_posle sops_posle:
                sops_posles) {
            if(sops_posle.isSopsTablePosleKorZ() || sops_posle.isSopsPosleZarKorR()) s += (sops_posle.toStringResh() + "; \n");
        }

        for (Staj staj:
                stajs) {
            if(staj.isKorR()) s += (staj.toStringResh() + "; \n");
        }

        for (Inoe i:
             inoes) {
            if(i.isReshkor()) s += (i.toStringResh() + "; \r\n");
        }

        return s;
    }

    public String getDataActRus() {
        return DateFormat.DataEngToRus(dataAct);
    }
}
