package ru.pfr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.everything.DateFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data // генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "svedzar_do")
public class SvedZar_do {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_svedzar")
    private Long id_svedzar;

    //реквизиты справки
    private String data_sprav;

    private String nom_sprav;

    //период
    private String period_s;

    private String period_po;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SvedZarGod> zarGods;

    public void setData_sprav(String data_sprav) {
        this.data_sprav = data_sprav;
    }

    public String getData_spravD() {
        return DateFormat.DataEngToRus(data_sprav);
    }

    public String getPeriod_sD() {
        return DateFormat.DataEngToRus(period_s);
    }

    public String getPeriod_poD() {
        return DateFormat.DataEngToRus(data_sprav);
    }



    public void setNom_sprav(String nom_sprav) {
        this.nom_sprav = nom_sprav;
    }

    public void setPeriod_s(String period_s) {
        this.period_s = period_s;
    }

    public void setPeriod_po(String period_po) {
        this.period_po = period_po;
    }

    public void setZarGods(List<SvedZarGod> zarGods) {
        this.zarGods = zarGods;
    }

    public List<SvedZarGod> getZarGods() {
        return zarGods;
    }

    public SvedZar_do(String nom_sprav, String data_sprav, String period_s, String period_po) {
        this.data_sprav = data_sprav;
        this.nom_sprav = nom_sprav;
        this.period_s = period_s;
        this.period_po = period_po;
    }

    public SvedZar_do(SvedZar_do svedZar_do) {
        this.data_sprav = svedZar_do.getData_sprav();
        this.nom_sprav = svedZar_do.getNom_sprav();
        this.period_s = svedZar_do.getPeriod_s();
        this.period_po = svedZar_do.getPeriod_po();

        List<SvedZarGod> svedZarGods = new ArrayList<>();
        for (SvedZarGod svedZarGod:
                svedZar_do.getZarGods()) {
            svedZarGods.add(new SvedZarGod(svedZarGod));
        }

        this.zarGods = svedZarGods;
    }

    public boolean isSopsDoGodKorR(){
        for (SvedZarGod sz:
                zarGods) {
            if(sz.getIsreshkor()==1)return true;
        }
        return false;
    }


    public String toStringResh(){
        String s = "";
        s += getData_spravD() + " " + nom_sprav + " " + getPeriod_sD() + " " + getPeriod_poD() + "\n";
        for (SvedZarGod szg:
                zarGods) {
            if(szg.isReshkor()) s += (szg.toStringResh() + "\n");
        }
        return  s.trim().replaceAll("null","").replaceAll(" +"," ");
    }


}
