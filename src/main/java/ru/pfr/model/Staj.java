package ru.pfr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data // генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "staj")
public class Staj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_staj")
    private Long id_staj;

    private String reg_nom;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Staj_table> staj_tables;

    public Staj(String reg_nom, String name) {
        this.reg_nom = reg_nom;
        this.name = name;
    }

    public Staj(Staj staj) {
        this.reg_nom = staj.getReg_nom();
        this.name = staj.getReg_nom();

        List<Staj_table> staj_tables = new ArrayList<>();
        for (Staj_table staj_table:
                staj.getStaj_tables()) {
            staj_tables.add(new Staj_table(staj_table));
        }
        this.staj_tables = staj_tables;
    }

    public void setReg_nom(String reg_nom) {
        this.reg_nom = reg_nom;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStaj_tables(List<Staj_table> staj_tables) {
        this.staj_tables = staj_tables;
    }

    public List<Staj_table> getStaj_tables() {
        return staj_tables==null?staj_tables:sort_staj_table(staj_tables);
    }

    public Staj_table getStaj_tablesOne() {
        return staj_tables.get(0);
    }

    public Long getId_staj() {
        return id_staj;
    }

    private List<Staj_table> sort_staj_table(List<Staj_table> s) {
        Comparator<Staj_table> comparator = Comparator.comparing(obj -> {
            try {
                return obj.getPeriod_s();
            }catch (Exception e){
                return "01.01.1800";
            }
        });
        s.sort(comparator);
        return s;
    }

    public boolean isKorR(){
        for (Staj_table t:
                staj_tables) {
            if(t.getIsreshkor()==1)return true;
        }
        return false;
    }

    public boolean isKorZ(){
        for (Staj_table t:
                staj_tables) {
            if(t.getIszapkor()==1)return true;
        }
        return false;
    }

    public String toStringResh(){
        String s = "";
        s += reg_nom + " " + name + "\n";
        for (Staj_table st:
             staj_tables) {
            if(st.isReshkor()) s += (st.toStringResh() + "\n");
        }
        return  s.trim().replaceAll("null","").replaceAll(" +"," ");
    }

}
