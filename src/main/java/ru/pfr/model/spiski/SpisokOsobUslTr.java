package ru.pfr.model.spiski;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "spisok_osob_usl_tr")
public class SpisokOsobUslTr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "osob_usl_tr")
    private String osob_usl_tr;

    public SpisokOsobUslTr(String osob_usl_tr) {
        this.osob_usl_tr = osob_usl_tr;
    }

    public String getVal() {
        return osob_usl_tr;
    }

}
