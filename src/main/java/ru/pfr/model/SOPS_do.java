package ru.pfr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data //генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "sops_do")
public class SOPS_do {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sops_do")
    private Long id_sops_do;

    @Column(name = "name_org")
    private String name_org;

    @Column(name = "vid_d")
    private String vid_d;


    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SvedZar_do> svedZar_dos;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SOPS_do_doljnost> sops_do_doljnosts;

    public SOPS_do(String name_org, String vid_d) {
        this.name_org = name_org;
        this.vid_d = vid_d;
    }

    public SOPS_do(SOPS_do sops_do) {
        this.name_org = sops_do.getName_org();
        this.vid_d = sops_do.getVid_d();

        List<SvedZar_do> svedZar_dos = new ArrayList<>();
        for (SvedZar_do svedZar_do:
                sops_do.getSvedZar_dos()) {
            svedZar_dos.add(new SvedZar_do(svedZar_do));
        }
        this.svedZar_dos = svedZar_dos;

        List<SOPS_do_doljnost> sops_do_doljnosts = new ArrayList<>();
        for (SOPS_do_doljnost sops_do_doljnost:
                sops_do.getSops_do_doljnosts()) {
            sops_do_doljnosts.add(new SOPS_do_doljnost(sops_do_doljnost));
        }
        this.sops_do_doljnosts = sops_do_doljnosts;
    }

    public void setName_org(String name_org) {
        this.name_org = name_org;
    }

    public void setVid_d(String vid_d) {
        this.vid_d = vid_d;
    }

    public void setSvedZar_dos(List<SvedZar_do> svedZar_dos) {
        this.svedZar_dos = svedZar_dos;
    }



    public Long getId_sops_do() {
        return id_sops_do;
    }

    public void setSops_do_doljnosts(List<SOPS_do_doljnost> sops_do_doljnosts) {
        this.sops_do_doljnosts = sops_do_doljnosts;
    }

    public List<SOPS_do_doljnost> getSops_do_doljnosts() {
        return sort_sops_do_doljnosts(sops_do_doljnosts);
    }

    public List<SvedZar_do> getSvedZar_dos() {
        return sort_svedZar_dos(svedZar_dos);
    }

    //сортировка
    private List<SOPS_do_doljnost> sort_sops_do_doljnosts(List<SOPS_do_doljnost> st) {
        Comparator<SOPS_do_doljnost> comparator = Comparator.comparing(obj -> {
            try {
                return obj.getSops_tables().get(0).getPeriod_start();
            }catch (Exception e){
                return "01.01.1800";
            }
        });
        st.sort(comparator);
        return st;
    }

    private List<SvedZar_do> sort_svedZar_dos(List<SvedZar_do> s) {
        Comparator<SvedZar_do> comparator = Comparator.comparing(obj -> {
            try {
                return obj.getPeriod_s();
            }catch (Exception e){
                return "01.01.1800";
            }
        });
        s.sort(comparator);
        return s;
    }

    public boolean isSopsTableDoKorR(){
        for (SOPS_do_doljnost sd:
                sops_do_doljnosts) {
            if(sd.isSopsTableDoKorR())return true;
        }
        return false;
    }
    public boolean isSopsTableDoKorZ(){
        for (SOPS_do_doljnost sd:
                sops_do_doljnosts) {
            if(sd.isSopsTableDoKorZ())return true;
        }
        return false;
    }

    public boolean isSopsDoGodKorR(){
        for (SvedZar_do sd:
                svedZar_dos) {
            if(sd.isSopsDoGodKorR())return true;
        }
        return false;
    }

    public String toStringResh(){
        String s = "";
        s += name_org + " " + vid_d + "\n";
        for (SOPS_do_doljnost sdd:
                sops_do_doljnosts) {
            if(sdd.isSopsTableDoKorR()) s += (sdd.toStringResh() + "\n");
        }
        for (SvedZar_do szd:
                svedZar_dos) {
            if(szd.isSopsDoGodKorR()) s += (szd.toStringResh() + "\n");
        }
        return  s.trim().replaceAll("null","").replaceAll(" +"," ");
    }



}
