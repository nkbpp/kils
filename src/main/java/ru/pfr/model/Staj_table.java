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
@Table(name = "staj_table")
public class Staj_table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_staj_table")
    private Long id_staj_table;

    private String period_s; //начало периода с

    private String period_po; //конец периода по

    private String uvol; //сведения об увольнении

    private String ter_usl; //территориальные условия (код)

    private String ter_usl_koeff; //территориальные условия коэфф

    private String osob_usl; //Особые условия труда (код)

    private String poz_spis; //Особые условия труда (код)

    private String strah_staj_osn; //Исчисление страхового стажа Основание (код)

    private String strah_staj_dop_sved; //Исчисление страхового стажа Дополнительные сведения

    private String dosr_naz_strah_pens_osn; //Условия для досрочного назначения страховой пенсии Основание (код)

    private String dosr_naz_strah_pens_dop_sved; //Условия для досрочного назначения страховой пенсии Дополнительные сведения

    private String strah_vzn; //Сведения о начислении (уплате) страховых взносов за указанный период

    private String kod_zastr_lica; //Код категории застрахованного лица

    private String dataSniatia; //поле из таблицы сверху

    private int isreshkor;

    private int iszapkor;

    public Staj_table(String period_s, String period_po, String uvol, String ter_usl, String ter_usl_koef, String osob_usl, String poz_spis, String strah_staj_osn, String strah_staj_dop_sved, String dosr_naz_strah_pens_osn, String dosr_naz_strah_pens_dop_sved, String strah_vzn, String kod_zastr_lica, String dataSniatia) {
        this.period_s = period_s;
        this.period_po = period_po;
        this.uvol = uvol;
        this.ter_usl = ter_usl;
        this.ter_usl_koeff = ter_usl_koef;
        this.osob_usl = osob_usl;
        this.poz_spis = poz_spis;
        this.strah_staj_osn = strah_staj_osn;
        this.strah_staj_dop_sved = strah_staj_dop_sved;
        this.dosr_naz_strah_pens_osn = dosr_naz_strah_pens_osn;
        this.dosr_naz_strah_pens_dop_sved = dosr_naz_strah_pens_dop_sved;
        this.strah_vzn = strah_vzn;
        this.kod_zastr_lica = kod_zastr_lica;
        this.dataSniatia = dataSniatia;
    }

    public Staj_table(Staj_table staj_table) {
        this.period_s = staj_table.getPeriod_s();
        this.period_po = staj_table.getPeriod_po();
        this.uvol = staj_table.getUvol();
        this.ter_usl = staj_table.getTer_usl();
        this.ter_usl_koeff = staj_table.getTer_usl_koeff();
        this.osob_usl = staj_table.getOsob_usl();
        this.poz_spis = staj_table.getPoz_spis();
        this.strah_staj_osn = staj_table.getStrah_staj_osn();
        this.strah_staj_dop_sved = staj_table.getStrah_staj_dop_sved();
        this.dosr_naz_strah_pens_osn = staj_table.getDosr_naz_strah_pens_osn();
        this.dosr_naz_strah_pens_dop_sved = staj_table.getDosr_naz_strah_pens_dop_sved();
        this.strah_vzn = staj_table.getStrah_vzn();
        this.kod_zastr_lica = staj_table.getKod_zastr_lica();
        this.dataSniatia = staj_table.getDataSniatia();
        this.isreshkor = staj_table.getIsreshkor();
        this.iszapkor = staj_table.getIszapkor();
    }

    public void setPeriod_s(String period_s) {
        this.period_s = period_s;
    }

    public void setPeriod_po(String period_po) {
        this.period_po = period_po;
    }

    public void setUvol(String uvol) {
        this.uvol = uvol;
    }

    public void setTer_usl(String ter_usl) {
        this.ter_usl = ter_usl;
    }

    public void setOsob_usl(String osob_usl) {
        this.osob_usl = osob_usl;
    }

    public void setStrah_staj_osn(String strah_staj_osn) {
        this.strah_staj_osn = strah_staj_osn;
    }

    public void setStrah_staj_dop_sved(String strah_staj_dop_sved) {
        this.strah_staj_dop_sved = strah_staj_dop_sved;
    }

    public void setDosr_naz_strah_pens_osn(String dosr_naz_strah_pens_osn) {
        this.dosr_naz_strah_pens_osn = dosr_naz_strah_pens_osn;
    }

    public void setDosr_naz_strah_pens_dop_sved(String dosr_naz_strah_pens_dop_sved) {
        this.dosr_naz_strah_pens_dop_sved = dosr_naz_strah_pens_dop_sved;
    }

    public void setStrah_vzn(String strah_vzn) {
        this.strah_vzn = strah_vzn;
    }

    public void setKod_zastr_lica(String kod_zastr_lica) {
        this.kod_zastr_lica = kod_zastr_lica;
    }

    public void setDataSniatia(String dataSniatia) {
        this.dataSniatia = dataSniatia;
    }

    public void setTer_usl_koeff(String ter_usl_koeff) {
        this.ter_usl_koeff = ter_usl_koeff;
    }


    public String getTer_uslNULL() {
        return ter_usl;
    }

    public String getTer_usl_koeffNULL() {
        return ter_usl_koeff;
    }

    public String getOsob_uslNULL() {
        return osob_usl;
    }

    public String getPoz_spisNULL() {
        return poz_spis;
    }

    public String getPeriod_sD() {
        return DateFormat.DataEngToRus(period_s);
    }

    public String getPeriod_poD() {
        return DateFormat.DataEngToRus(period_po);
    }

    public String getDataSniatiaD() {
        return DateFormat.DataEngToRus(dataSniatia);
    }

    public boolean isReshkor() {
        return isreshkor==1;
    }

    public boolean isZapkor() {
        return iszapkor==1;
    }

    public String toStringResh(){
        return (this.getPeriod_sD() + " " + this.getPeriod_poD() + " " + uvol + " " + ter_usl + " " +
                    ter_usl_koeff + " " + osob_usl + " " + poz_spis + " " +
                    strah_staj_osn + " " + strah_staj_dop_sved + " " + dosr_naz_strah_pens_osn + " " +
                    dosr_naz_strah_pens_dop_sved + " " + strah_vzn + " " + kod_zastr_lica + " " +
                this.getDataSniatiaD()).trim().replaceAll("null","").replaceAll(" +"," ");
    }

    public String getKod(){
        int godStart = Integer.parseInt(period_s.substring(0,4));
        int mounthStart = Integer.parseInt(period_s.substring(5,7));

        int godEnd = Integer.parseInt(period_po.substring(0,4));
        int mounthEnd = Integer.parseInt(period_po.substring(5,7));
        switch (godStart){
            case 1997:{}
            case 1998:{}
            case 1999:{}
            case 2000:{
                if(mounthStart>=1 && mounthEnd<=6) return "2"; // 01.01.гггг по 30.06.2001
                if(mounthStart>=7 && mounthEnd<=12) return "4"; //с01.07.гггг по 31.12.2001

                if(mounthStart>=1 && mounthEnd<=12) return "0"; //с 01.01.гггг по 31.12.гггг
            } break;
            case 2001:{
                if(mounthStart>=1 && mounthEnd<=3) return "5";//с 01.01.гггг по 31.03.2001
                if(mounthStart>=4 && mounthEnd<=6) return "6";//с 01.04.гггг по 30.06.2001
                if(mounthStart>=7 && mounthEnd<=9) return "7";//с 01.07.гггг по 30.09.2001
                if(mounthStart>=10 && mounthEnd<=12) return "8";//с 01.10.гггг по 31.12.2001

                if(mounthStart>=1 && mounthEnd<=6) return "2"; // 01.01.гггг по 30.06.2001
                if(mounthStart>=1 && mounthEnd<=9) return "1"; // 01.01.гггг по 30.09.2001

                if(mounthStart>=4 && mounthEnd<=9) return "3"; // с 01.04.гггг по 30.09.2001
                if(mounthStart>=4 && mounthEnd<=12) return "9"; // с 01.04.гггг по 31.12.2001

                if(mounthStart>=7 && mounthEnd<=12) return "4"; //с01.07.гггг по 31.12.2001

                if(mounthStart>=1 && mounthEnd<=12) return "0"; //с 01.01.гггг по 31.12.гггг
            } break;
            case 2010:{
                if(mounthStart>=1 && mounthEnd<=6) return "1"; // 01.01.гггг по 30.06.2001
                if(mounthStart>=7 && mounthEnd<=12) return "2"; //с01.07.гггг по 31.12.2001

                if(mounthStart>=1 && mounthEnd<=12) return "0"; //с 01.01.гггг по 31.12.гггг
            } break;
            case 2011:{}
            case 2012:{}
            case 2013:{
                if(mounthStart>=1 && mounthEnd<=3) return "1"; // с 01.01.гггг по 31.03.гггг
                if(mounthStart>=4 && mounthEnd<=6) return "2"; //с 01.04.гггг по 30.06.гггг
                if(mounthStart>=7 && mounthEnd<=9) return "3"; //с 01.07.гггг по 30.09.гггг
                if(mounthStart>=10 && mounthEnd<=12) return "4"; //с 01.10.гггг по 31.12.гггг

                if(mounthStart>=1 && mounthEnd<=12) return "0"; //с 01.01.гггг по 31.12.гггг
            } break;
            case 2014:{
                if(mounthStart>=1 && mounthEnd<=3) return "3"; // с 01.01.гггг по 31.03.гггг
                if(mounthStart>=4 && mounthEnd<=6) return "6"; //с 01.04.гггг по 30.06.гггг
                if(mounthStart>=7 && mounthEnd<=9) return "9"; //с 01.07.гггг по 30.09.гггг
                if(mounthStart>=10 && mounthEnd<=12) return "0"; //с 01.10.гггг по 31.12.гггг
                if(mounthStart>=1 && mounthEnd<=12) return "4"; //с 01.01.гггг по 31.12.гггг
            } break;
        }
        return "0";
    }

    public String getGodStart(){
        return period_s.substring(0,4);
    }

}
