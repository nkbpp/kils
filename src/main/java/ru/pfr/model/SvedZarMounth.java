package ru.pfr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data // генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "svedzarmounth")
public class SvedZarMounth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_svedzarmounth")
    private Long id_svedzarmounth;

    private String mounth;

    private String sum;


    public void setMounth(String mounth) {
        this.mounth = mounth;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getMounthName() {
        String s = "";
        switch (mounth){
            case "1" : s = "январь"; break;
            case "2" : s = "февраль"; break;
            case "3" : s = "март"; break;
            case "4" : s = "апрель"; break;
            case "5" : s = "май"; break;
            case "6" : s = "июнь"; break;
            case "7" : s = "июль"; break;
            case "8" : s = "август"; break;
            case "9" : s = "сентябрь"; break;
            case "10" : s = "октябрь"; break;
            case "11" : s = "ноябрь"; break;
            case "12" : s = "декабрь"; break;
        }
        return s;
    }

    public SvedZarMounth(String mounth, String sum) {
        this.mounth = mounth;
        this.sum = sum;
    }

    public SvedZarMounth(SvedZarMounth svedZarMounth) {
        this.mounth = svedZarMounth.getMounth();
        this.sum = svedZarMounth.getSum();
    }

    public String getSum() {
        return sum;
    }

    public String getMounth() {
        return mounth;
    }

    public String toStringResh(){
        return (this.getMounthName() + " " + sum).trim().replaceAll("null","").replaceAll(" +"," ");
    }


}
