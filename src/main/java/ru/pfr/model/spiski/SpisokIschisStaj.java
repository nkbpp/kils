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
@Table(name = "spisok_ischis_staj")
public class SpisokIschisStaj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ischis_staj")
    private String ischis_staj;

    public SpisokIschisStaj(String ischis_staj) {
        this.ischis_staj = ischis_staj;
    }

    public String getVal() {
        return ischis_staj;
    }

}
