package ru.pfr.model.count;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.everything.DateFormat;

import javax.persistence.*;

@Data // генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "colfile")
public class Kolfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colfile")
    private Long id_colfile;

    private int kol;

    private int god;

    public Kolfile(int kol, int god) {
        this.kol = kol;
        this.god = god;
    }

}
