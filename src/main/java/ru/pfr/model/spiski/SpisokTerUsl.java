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
@Table(name = "spisok_ter_usl")
public class SpisokTerUsl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ter_usl")
    private String ter_usl;

    public SpisokTerUsl(String ter_usl) {
        this.ter_usl = ter_usl;
    }

    public String getVal() {
        return ter_usl;
    }

}
