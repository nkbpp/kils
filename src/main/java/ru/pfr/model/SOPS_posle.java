package ru.pfr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data //генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "sops_posle")
public class SOPS_posle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sops_posle")
    private Long id_sops_posle;

    @Column(name = "strahovatel")
    private String strahovatel;

    @Column(name = "name_org")
    private String name_org;

    @Column(name = "reg_num_pfr")
    private String reg_num_pfr;

    //так вроде сам создаст
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SOPS_table> sops_tables;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SvedZar_posle> svedZar_posles;

    public SOPS_posle(SOPS_posle sops_posle) {
        this.strahovatel = sops_posle.getStrahovatel();
        this.name_org = sops_posle.getName_org();
        this.reg_num_pfr = sops_posle.getReg_num_pfr();

        List<SOPS_table> sops_tables = new ArrayList<>();
        for (SOPS_table sops_table:
                sops_posle.getSops_tables()) {
            sops_tables.add(new SOPS_table(sops_table));
        }
        this.sops_tables = sops_tables;

        List<SvedZar_posle> svedZar_posles = new ArrayList<>();
        for (SvedZar_posle svedZar_posle:
                sops_posle.getSvedZar_posles()) {
            svedZar_posles.add(new SvedZar_posle(svedZar_posle));
        }
        this.svedZar_posles = svedZar_posles;
    }

    public SOPS_posle(String strahovatel, String name_org, String reg_num_pfr) {
        this.strahovatel = strahovatel;
        this.name_org = name_org;
        this.reg_num_pfr = reg_num_pfr;
    }

    public void setStrahovatel(String strahovatel) {
        this.strahovatel = strahovatel;
    }

    public void setName_org(String name_org) {
        this.name_org = name_org;
    }

    public void setReg_num_pfr(String reg_num_pfr) {
        this.reg_num_pfr = reg_num_pfr;
    }

    public void setSops_tables(List<SOPS_table> sops_tables) {
        this.sops_tables = sops_tables;
    }

    public void setSvedZar_posles(List<SvedZar_posle> svedZar_posles) {
        this.svedZar_posles = svedZar_posles;
    }

    public List<SOPS_table> getSops_tables() {
        return sops_tables;
    }

    public List<SvedZar_posle> getSvedZar_posles() {
        return svedZar_posles;
    }

    public List<SvedZar_posle> getSvedZar_posles(String d_start,String d_end) {

        List<SvedZar_posle> sz = new ArrayList<>();
        for (SvedZar_posle sp:
        svedZar_posles) {
            if((int) Integer.valueOf(d_start.substring(0,4))>= (int) Integer.valueOf(sp.getGod()) &&
                    (int) Integer.valueOf(d_end.substring(0,4))<= (int) Integer.valueOf(sp.getGod())){

                boolean b = true;

                if((int) Integer.valueOf(sp.getGod()) == (int) Integer.valueOf(d_start.substring(0,4))){ //минимальный год
                    int mounth = Integer.valueOf(d_start.substring(5,7));
                    for (SvedZarMounth svedZarMounth:
                    sp.getSvedZarMounths()) {
                        if((int) Integer.valueOf(svedZarMounth.getMounth())<mounth){b=false;}
                    }
                }

                if((int) Integer.valueOf(sp.getGod()) == (int) Integer.valueOf(d_end.substring(0,4))){ //максимальный год
                    int mounth = Integer.valueOf(d_end.substring(5,7));
                    for (SvedZarMounth svedZarMounth:
                            sp.getSvedZarMounths()) {
                        if((int) Integer.valueOf(svedZarMounth.getMounth())>mounth){b=false;}
                    }
                }

                if(b)sz.add(sp);

            }
        }
        return sz;
    }

    public String getStrahovatel() {
        return strahovatel;
    }

    public String getName_org() {
        return name_org;
    }

    public String getReg_num_pfr() {
        return reg_num_pfr;
    }

    public Long getId_sops_posle() {
        return id_sops_posle;
    }

    public boolean isSopsPosleKorR(){
        return isSopsTablePosleKorR() || isSopsPosleZarKorR();
    }

    public boolean isSopsPosleKorZ(){
        return isSopsTablePosleKorZ() || isSopsPosleZarKorZ();
    }

    public boolean isSopsTablePosleKorR(){
        for (SOPS_table st:
                sops_tables) {
            if(st.getIsreshkor()==1)return true;
        }
        return false;
    }
    public boolean isSopsTablePosleKorZ(){
        for (SOPS_table st:
                sops_tables) {
            if(st.getIszapkor()==1)return true;
        }
        return false;
    }

    public boolean isSopsPosleZarKorR(){
        for (SvedZar_posle st:
                svedZar_posles) {
            if(st.getIsreshkor()==1)return true;
        }
        return false;
    }
    public boolean isSopsPosleZarKorZ(){
        for (SvedZar_posle st:
                svedZar_posles) {
            if(st.getIszapkor()==1)return true;
        }
        return false;
    }

    public String toStringResh(){
        String s = "";
        s += strahovatel + " " + name_org + " " + reg_num_pfr + "\n";
        for (SOPS_table st:
                sops_tables) {
            if(st.isReshkor()) s += (st.toStringResh() + "\n");
        }
        for (SvedZar_posle szp:
                svedZar_posles) {
            if(szp.isReshkor()) s += (szp.toStringResh() + "\n");
        }
        return  s.trim().replaceAll("null","").replaceAll(" +"," ");
    }


}
