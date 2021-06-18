package ru.pfr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data // генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "svedzar_posle")
public class SvedZar_posle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_svedzar")
    private Long id_svedzar;

    //КатегорияЗЛ
    private String katZL;

    //период
    private String kod;

    private String god;

    private String itogo;

    private int isreshkor;

    private int iszapkor;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SvedZarMounth> svedZarMounths;

    public SvedZar_posle(SvedZar_posle svedZar_posle) {
        this.katZL = svedZar_posle.getKatZL();
        this.kod = svedZar_posle.getKod();
        this.god = svedZar_posle.getGod();
        this.itogo = svedZar_posle.getItogo();
        this.isreshkor = svedZar_posle.getIsreshkor();
        this.iszapkor = svedZar_posle.getIszapkor();

        List<SvedZarMounth> svedZarMounths = new ArrayList<>();
        for (SvedZarMounth svedZarMounth:
                svedZar_posle.getSvedZarMounths()) {
            svedZarMounths.add(new SvedZarMounth(svedZarMounth));
        }
        this.svedZarMounths = svedZarMounths;
    }

    public void setKatZL(String katZL) {
        this.katZL = katZL;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public void setGod(String god) {
        this.god = god;
    }

    public void setItogo(String itogo) {
        this.itogo = String.format("%.2f",Double.valueOf(itogo)).replace(',','.');
    }

    public String getItogo() {
        String s = itogo;
        return String.format("%.2f",Double.valueOf(itogo)).replace(',','.');
    }

    public void setSvedZarMounths(List<SvedZarMounth> svedZarMounths) {
        this.svedZarMounths = svedZarMounths;
    }

    public List<SvedZarMounth> getSvedZarMounths() {
        return svedZarMounths;
    }

    public SvedZarMounth getSvedZarMounthByMounth(String s) {
        for (SvedZarMounth svedZarMounth:
                svedZarMounths) {
            if(svedZarMounth.getMounth().equals(s))
                return svedZarMounth;
        }
        return null;
    }

    public boolean isEmptySvedZarMounthByMounth(String s) {
        for (SvedZarMounth svedZarMounth:
                svedZarMounths) {
            if(svedZarMounth.getMounth().equals(s))
                return true;
        }
        return false;
    }

    public Long getId_svedzar() {
        return id_svedzar;
    }

    public SvedZar_posle(String katZL, String kod, String god, String itogo, List<SvedZarMounth> svedZarMounths) {
        this.katZL = katZL;
        this.kod = kod;
        this.god = god;
        this.itogo = itogo;
        this.svedZarMounths = svedZarMounths;
    }

    public boolean isReshkor() {
        return isreshkor==1;
    }

    public boolean isZapkor() {
        return iszapkor==1;
    }


    public String toStringResh(){
        String s = "";
        s += katZL + " " + kod + " " + god + " " + itogo + "\n";
        for (SvedZarMounth szm:
                svedZarMounths) {
            s += (szm.toStringResh() + "\n");
        }
        return  s.trim().replaceAll("null","").replaceAll(" +"," ");
    }



}
