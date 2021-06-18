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
@Table(name = "spisok_dejatel")
public class SpisokDejatel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dejatel")
    private String dejatel;

    public SpisokDejatel(String dejatel) {
        this.dejatel = dejatel;
    }

    public String getVal() {
        return dejatel;
    }

}
