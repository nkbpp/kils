package ru.pfr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.everything.DateFormat;

import javax.persistence.*;
import java.util.List;

@Data //генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Table(name = "SOPS_table")
public class SOPS_table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sops_do_table")
    private Long id_sops_do_table;

    @Column(name = "period_start")
    private String period_start;

    @Column(name = "period_end")
    private String period_end;

    @Column(name = "ter_uslovija")
    private String ter_uslovija;

    @Column(name = "ter_uslovija_koef")
    private String ter_uslovija_koef;

    @Column(name = "osob_uslovija")
    private String osob_uslovija;

    @Column(name = "poz_spis")
    private String poz_spis;

    @Column(name = "trud_staj_osn")
    private String trud_staj_osn;

    @Column(name = "trud_staj_dopsved")
    private String trud_staj_dopsved;

    @Column(name = "vis_let_osn")
    private String vis_let_osn;

    @Column(name = "vis_let_dopsved")
    private String vis_let_dopsved;

    private int isreshkor;

    private int iszapkor;

    public SOPS_table(String period_start, String period_end, String ter_uslovija, String ter_uslovija_koef, String osob_uslovija, String poz_spis, String trud_staj_osn, String trud_staj_dopsved, String vis_let_osn, String vis_let_dopsved) {
        this.period_start = period_start;
        this.period_end = period_end;
        this.ter_uslovija = ter_uslovija;
        this.ter_uslovija_koef = ter_uslovija_koef;
        this.osob_uslovija = osob_uslovija;
        this.poz_spis = poz_spis;
        this.trud_staj_osn = trud_staj_osn;
        this.trud_staj_dopsved = trud_staj_dopsved;
        this.vis_let_osn = vis_let_osn;
        this.vis_let_dopsved = vis_let_dopsved;
    }

    public SOPS_table(SOPS_table sops_table) {
        this.period_start = sops_table.getPeriod_start();
        this.period_end = sops_table.getPeriod_end();
        this.ter_uslovija = sops_table.getTer_uslovija();
        this.ter_uslovija_koef = sops_table.getTer_uslovija_koef();
        this.osob_uslovija = sops_table.getOsob_uslovija();
        this.poz_spis = sops_table.getPoz_spis();
        this.trud_staj_osn = sops_table.getTrud_staj_osn();
        this.trud_staj_dopsved = sops_table.getTrud_staj_dopsved();
        this.vis_let_osn = sops_table.getVis_let_osn();
        this.vis_let_dopsved = sops_table.getVis_let_dopsved();
        this.isreshkor = sops_table.getIsreshkor();
        this.iszapkor = sops_table.getIszapkor();
    }

    public void setTer_uslovija_koef(String ter_uslovija_koef) {
        this.ter_uslovija_koef = ter_uslovija_koef;
    }

    public void setPeriod_start(String period_start) {
        this.period_start = period_start;
    }

    public void setPeriod_end(String period_end) {
        this.period_end = period_end;
    }

    public void setTer_uslovija(String ter_uslovija) {
        this.ter_uslovija = ter_uslovija;
    }

    public void setOsob_uslovija(String osob_uslovija) {
        this.osob_uslovija = osob_uslovija;
    }

    public void setTrud_staj_osn(String trud_staj_osn) {
        this.trud_staj_osn = trud_staj_osn;
    }

    public void setTrud_staj_dopsved(String trud_staj_dopsved) {
        this.trud_staj_dopsved = trud_staj_dopsved;
    }

    public void setVis_let_osn(String vis_let_osn) {
        this.vis_let_osn = vis_let_osn;
    }

    public void setVis_let_dopsved(String vis_let_dopsved) {
        this.vis_let_dopsved = vis_let_dopsved;
    }

    public Long getId_sops_do_table() {
        return id_sops_do_table;
    }

    public void setPoz_spis(String poz_spis) {
        this.poz_spis = poz_spis;
    }

    public String getTer_uslovija() {
        return ter_uslovija==null?"":ter_uslovija;
    }

    public String getTer_uslovija_koef() {
        return ter_uslovija_koef==null?"":ter_uslovija_koef;
    }

    public String getOsob_uslovija() {
        return osob_uslovija==null?"":osob_uslovija;
    }

    public String getPoz_spis() {
        return poz_spis==null?"":poz_spis;
    }

    public String getTrud_staj_osn() {
        return trud_staj_osn==null?"":trud_staj_osn;
    }

    public String getTrud_staj_dopsved() {
        return trud_staj_dopsved==null?"":trud_staj_dopsved;
    }

    public String getVis_let_osn() {
        return vis_let_osn==null?"":vis_let_osn;
    }

    public String getVis_let_dopsved() {
        return vis_let_dopsved==null?"":vis_let_dopsved;
    }

    public String getPeriod_startD() {
        return DateFormat.DataEngToRus(period_start);
    }

    public String getPeriod_endD() {
        return DateFormat.DataEngToRus(period_end);
    }

    public boolean isReshkor() {
        return isreshkor==1;
    }

    public boolean isZapkor() {
        return iszapkor==1;
    }

    public String toStringResh(){
        return (this.getPeriod_startD() + " " + this.getPeriod_endD() + " " + getTer_uslovija() + " " +
                getTer_uslovija_koef() + " " +
                getOsob_uslovija() + " " + getPoz_spis() + " " +
                getTrud_staj_osn() + " " + getTrud_staj_dopsved() + " " + getVis_let_osn() + " " +
                getVis_let_dopsved()).trim().replaceAll("null","").replaceAll(" +"," ");
    }

    public String toStringZap(){
        return (getTer_uslovija() + " " +
                getTer_uslovija_koef() + " " +
                getOsob_uslovija() + " " + getPoz_spis() + " " +
                getTrud_staj_osn() + " " + getTrud_staj_dopsved() + " " + getVis_let_osn() + " " +
                getVis_let_dopsved()).trim().replaceAll("null","").replaceAll(" +"," ");
    }

    public String getKod(){
        int godStart = Integer.parseInt(period_start.substring(0,4));
        int mounthStart = Integer.parseInt(period_start.substring(5,7));

        int godEnd = Integer.parseInt(period_end.substring(0,4));
        int mounthEnd = Integer.parseInt(period_end.substring(5,7));
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

}
