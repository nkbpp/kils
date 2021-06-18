package ru.pfr.model.shablon;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
// генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "shablon")
public class Shablon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "dokument", columnDefinition = "LONGBLOB")
    private byte[] dokument;
    @Column(name = "name_dokument", nullable = true, length = 400)
    private String nameDokument;
    @Column(name = "name_file", nullable = true, length = 400)
    private String nameFile;

    private int number;

    public Shablon() {
    }

    public Shablon(byte[] dokument, String nameDokument, String nameFile) {
        this.dokument = dokument;
        this.nameDokument = nameDokument;
        this.nameFile = nameFile;
    }

}
