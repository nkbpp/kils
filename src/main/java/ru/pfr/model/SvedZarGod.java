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
@Table(name = "svedzargod")
public class SvedZarGod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_svedzargod")
    private Long id_svedzargod;

    private String god;

    private String itogo;

    private int isreshkor;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SvedZarMounth> svedZarMounths;

    public SvedZarGod(String god, String itogo, int isreshkor, List<SvedZarMounth> svedZarMounths) {
        this.god = god;
        this.itogo = itogo;
        this.isreshkor = isreshkor;
        this.svedZarMounths = svedZarMounths;
    }

    public SvedZarGod(SvedZarGod svedZarGod) {
        this.god = svedZarGod.god;
        this.itogo = svedZarGod.itogo;
        this.isreshkor = svedZarGod.isreshkor;
        List<SvedZarMounth> svedZarMounths = new ArrayList<>();
        for (SvedZarMounth svedZarMounth:
            svedZarGod.getSvedZarMounths()) {
            svedZarMounths.add(new SvedZarMounth(svedZarMounth));
        }
        this.svedZarMounths = svedZarMounths;
    }

    public void setGod(String god) {
        this.god = god;
    }

    public void setItogo(String itogo) {
        this.itogo = String.format("%.2f",Double.valueOf(itogo)).replace(',','.');
    }

    public String getItogo() {
        return String.format("%.2f",Double.valueOf(itogo)).replace(',','.');
    }

    public void setSvedZarMounths(List<SvedZarMounth> svedZarMounths) {
        this.svedZarMounths = svedZarMounths;
    }

    public List<SvedZarMounth> getSvedZarMounths() {
        return svedZarMounths;
    }

    public SvedZarGod(String god, String itogo, List<SvedZarMounth> svedZarMounths) {
        this.god = god;
        this.itogo = itogo;
        this.svedZarMounths = svedZarMounths;
    }

    public String getGod() {
        return god;
    }

    public Long getId_svedzargod() {
        return id_svedzargod;
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

    public boolean isReshkor() {
        return isreshkor==1;
    }


    public String toStringResh(){
        String s = "";
        s += god + " " + itogo + "\n";
        for (SvedZarMounth szm:
                svedZarMounths) {
            s += (szm.toStringResh() + "\n");
        }
        return  s.trim().replaceAll("null","").replaceAll(" +"," ");
    }
}
