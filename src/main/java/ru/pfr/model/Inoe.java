package ru.pfr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.everything.DateFormat;

import javax.persistence.*;

@Data // генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "inoe")
public class Inoe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inoe")
    private Long id_inoe;

    private String name;

    private String reg_num;

    private String inoe_period;

    private String period_start;

    private String period_end;

    private String snils;

    private String data_roj;

    private int isreshkor;

    public Inoe(String name, String reg_num, String inoe_period, String period_start, String period_end, String snils, String data_roj) {
        this.name = name;
        this.reg_num = reg_num;
        this.inoe_period = inoe_period;
        this.period_start = period_start;
        this.period_end = period_end;
        this.snils = snils;
        this.data_roj = data_roj;
    }

    public Inoe(Inoe inoe) {
        this.name = inoe.getName();
        this.reg_num = inoe.getReg_num();
        this.inoe_period = inoe.getInoe_period();
        this.period_start = inoe.getPeriod_start();
        this.period_end = inoe.getPeriod_end();
        this.snils = inoe.getSnils();
        this.data_roj = inoe.getData_roj();
        this.isreshkor = inoe.getIsreshkor();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReg_num(String reg_num) {
        this.reg_num = reg_num;
    }

    public void setInoe_period(String inoe_period) {
        this.inoe_period = inoe_period;
    }

    public void setPeriod_start(String period_start) {
        this.period_start = period_start;
    }

    public void setPeriod_end(String period_end) {
        this.period_end = period_end;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public void setData_roj(String data_roj) {
        this.data_roj = data_roj;
    }

    public String getName() {
        return name;
    }

    public String getReg_num() {
        return reg_num;
    }

    public String getInoe_period() {
        return inoe_period;
    }

    public String getPeriod_startD() {
        return DateFormat.DataEngToRus(period_start);
    }

    public String getPeriod_endD() {
        return DateFormat.DataEngToRus(period_end);
    }

    public String getSnils() {
        return snils;
    }

    public String getData_rojD() {
        return DateFormat.DataEngToRus(data_roj);
    }

    public boolean isReshkor() {
        return isreshkor==1;
    }


    public String toStringResh(){
        return  (name + " " + reg_num + " " + inoe_period + " " + period_start + " " +
                period_end + " " + snils + " " + data_roj).trim().replaceAll("null","").replaceAll(" +"," ");
    }
}
