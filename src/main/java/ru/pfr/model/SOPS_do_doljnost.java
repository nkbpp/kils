package ru.pfr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
//генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "sops_do_doljnost")
public class SOPS_do_doljnost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sops_do_doljnost")
    private Long id_sops_do_doljnost;

    @Column(name = "doljnost") //студент
    private String doljnost;

    //так вроде сам создаст
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SOPS_table> sops_tables;

    public SOPS_do_doljnost(String doljnost, List<SOPS_table> sops_tables) {
        this.doljnost = doljnost;
        this.sops_tables = sops_tables;
    }

    public SOPS_do_doljnost(SOPS_do_doljnost sops_do_doljnost) {

        this.doljnost = sops_do_doljnost.getDoljnost();

        List<SOPS_table> sops_tables = new ArrayList<>();
        for (SOPS_table sops_table:
                sops_do_doljnost.getSops_tables()) {
            sops_tables.add(new SOPS_table(sops_table));
        }
        this.sops_tables = sops_tables;
    }

    public Long getId_sops_do_doljnost() {
        return id_sops_do_doljnost;
    }

    public List<SOPS_table> getSops_tables() {
        //return sops_tables;
        return sops_table_sortData(sops_tables);
    }

    private List<SOPS_table> sops_table_sortData(List<SOPS_table> st) {
        Comparator<SOPS_table> comparator = Comparator.comparing(obj -> {
            try {
                return obj.getPeriod_start();
            }catch (Exception e){
                return "01.01.1800";
            }
        });
        st.sort(comparator);
        return st;
    }

    public boolean isSopsTableDoKorR(){
        for (SOPS_table st:
                sops_tables) {
            if(st.getIsreshkor()==1)return true;
        }
        return false;
    }

    public boolean isSopsTableDoKorZ(){
        for (SOPS_table st:
                sops_tables) {
            if(st.getIszapkor()==1)return true;
        }
        return false;
    }

    public String toStringResh(){
        String s = "";
        s += doljnost + "\n";
        for (SOPS_table st:
                sops_tables) {
            if(st.isReshkor()) s += (st.toStringResh() + "\n");
        }
        return  s.trim().replaceAll("null","").replaceAll(" +"," ");
    }
}
