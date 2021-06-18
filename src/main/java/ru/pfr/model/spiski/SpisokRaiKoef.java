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
@Table(name = "spisok_rai_koef")
public class SpisokRaiKoef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rai_koef")
    private String rai_koef;

    public SpisokRaiKoef(String rai_koef) {
        this.rai_koef = rai_koef;
    }

    public String getVal() {
        return rai_koef;
    }

}
