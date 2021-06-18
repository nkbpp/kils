package ru.pfr.model.xlsx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.everything.DateFormat;

import javax.persistence.*;

@Data // генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "deregisteredEmployers ")
public class DeregisteredEmployers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String regnum;

    private String name;

    private String inn;

    private String kpp;

    public DeregisteredEmployers(String regnum, String name, String inn, String kpp) {
        this.regnum = regnum;
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
    }
}
