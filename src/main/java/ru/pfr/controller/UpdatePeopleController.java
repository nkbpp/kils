package ru.pfr.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pfr.everything.DateFormat;
import ru.pfr.model.*;
import ru.pfr.model.shablon.Shablon;
import ru.pfr.model.spiski.*;
import ru.pfr.service.Inoe.InoeService;
import ru.pfr.service.LogiService;
import ru.pfr.service.PeopleOldService;
import ru.pfr.service.PeopleService;
import ru.pfr.service.SOPS.*;
import ru.pfr.service.Staj.StajService;
import ru.pfr.service.Staj.Staj_tableService;
import ru.pfr.service.User.RayonService;
import ru.pfr.service.shablon.ShablonService;
import ru.pfr.service.spiski.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = {"/", "/kils/", "/KILS/"})
public class UpdatePeopleController {

    @Autowired
    PeopleService peopleService;

    @Autowired
    PeopleOldService peopleOldService;

    @Autowired
    SOPS_doService sops_doService;

    @Autowired
    SOPS_posleService sops_posleService;

    @Autowired
    StajService stajService;

    @Autowired
    SOPS_tableService sops_tableService;

    @Autowired
    InoeService inoeService;

    @Autowired
    Staj_tableService staj_tableService;

    @Autowired
    SvedZar_posleService svedZar_posleService;

    @Autowired
    SvedZarMounthService svedZarMounthService;

    @Autowired
    SvedZar_doService svedZar_doService;

    @Autowired
    SvedZarGodService svedZarGodService;

    @Autowired
    SOPS_doDoljnostService sops_doDoljnostService;

    //СПИСКИ
    @Autowired
    SpisokDecretDetiService spisokDecretDetiService;

    @Autowired
    SpisokDejatelService spisokDejatelService;

    @Autowired
    SpisokIschisStajService spisokIschisStajService;

    @Autowired
    SpisokOsobUslTrService spisokOsobUslTrService;

    @Autowired
    SpisokRaiKoefService spisokRaiKoefService;

    @Autowired
    SpisokTerUslService spisokTerUslService;

    @Autowired
    SpisokVisLetService spisokVisLetService;

    @Autowired
    SpisokDejatelService getSpisokDejatelService;

    @Autowired
    RayonService rayonService;

    @Autowired
    ShablonService shablonService;

    @Autowired
    LogiService logiService;

    //ДОБАВЛЕНИЕ
    @PostMapping("/kart_graj/sved_zar_do")
    public @ResponseBody
    String sved_zar_do(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "SvedZarDoNom_spravAdd", defaultValue = "") String SvedZarDoNom_spravAdd,
            @RequestParam(value = "SvedZarDoData_spravAdd", defaultValue = "") String SvedZarDoData_spravAdd,
            @RequestParam(value = "SvedZarDoPeriod_sAdd", defaultValue = "") String SvedZarDoPeriod_sAdd,
            @RequestParam(value = "SvedZarDoPeriod_poAdd", defaultValue = "") String SvedZarDoPeriod_poAdd,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Сведения о заработке добавление  UpdatePeopleController sved_zar_do()"));

        String message = "Данные успешно сохранены!";
        SvedZar_do svedZar_do;
        try {
            SOPS_do sops_do = sops_doService.findById(id);
            svedZar_do = new SvedZar_do(SvedZarDoNom_spravAdd,SvedZarDoData_spravAdd,SvedZarDoPeriod_sAdd,SvedZarDoPeriod_poAdd);
            svedZar_doService.save(svedZar_do);
            sops_do.getSvedZar_dos().add(svedZar_do);
            sops_doService.save(sops_do);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/addstaj")
    public @ResponseBody
    String addstaj(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "addstajreg_nom", defaultValue = "") String addstajreg_nom,
            @RequestParam(value = "addstajname", defaultValue = "") String addstajname,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Стаж добавление  UpdatePeopleController addstaj()"));

        String message = "Данные успешно сохранены!";
        Staj staj;
        try {
            People people = peopleService.findById(id);
            staj = new Staj(addstajreg_nom,addstajname);
            stajService.save(staj);
            people.getStajs().add(staj);
            peopleService.save(people);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/addsopsposle")
    public @ResponseBody
    String addsopsposle(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "addsopsposlestrahovatel", defaultValue = "") String addsopsposlestrahovatel,
            @RequestParam(value = "addsopsposlename_org", defaultValue = "") String addsopsposlename_org,
            @RequestParam(value = "addsopsposlereg_num_pfr", defaultValue = "") String addsopsposlereg_num_pfr,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Сведения после регистрации добавление  UpdatePeopleController addstaj()"));

        String message = "Данные успешно сохранены!";
        SOPS_posle sops_posle;
        try {
            People people = peopleService.findById(id);
            sops_posle = new SOPS_posle(addsopsposlestrahovatel,addsopsposlename_org,addsopsposlereg_num_pfr);
            sops_posleService.save(sops_posle);
            people.getSops_posles().add(sops_posle);
            peopleService.save(people);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/addsopsdo")
    public @ResponseBody
    String addsopsdo(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "addsopsdoname_org", defaultValue = "") String addsopsdoname_org,
            @RequestParam(value = "addsopsdovid_d", defaultValue = "") String addsopsdovid_d,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Сведения до регистрации добавление  UpdatePeopleController addstaj()"));

        String message = "Данные успешно сохранены!";
        SOPS_do sops_do;
        try {
            People people = peopleService.findById(id);
            sops_do = new SOPS_do(addsopsdoname_org,addsopsdovid_d);
            sops_doService.save(sops_do);
            List<SOPS_do> v = people.getSops_dos();
            v.add(sops_do);
            peopleService.save(people);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sved_zar_god_add")
    public @ResponseBody
    String sved_zar_god_add(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "swedzargodGodAdd", defaultValue = "") String swedzargodGodAdd,
            @RequestParam(value = "swedzargodJanuaryAdd", defaultValue = "") String swedzargodJanuaryAdd,
            @RequestParam(value = "swedzargodFebruaryAdd", defaultValue = "") String swedzargodFebruaryAdd,
            @RequestParam(value = "swedzargodMarchAdd", defaultValue = "") String swedzargodMarchAdd,
            @RequestParam(value = "swedzargodAprilAdd", defaultValue = "") String swedzargodAprilAdd,
            @RequestParam(value = "swedzargodMayAdd", defaultValue = "") String swedzargodMayAdd,
            @RequestParam(value = "swedzargodJuneAdd", defaultValue = "") String swedzargodJuneAdd,
            @RequestParam(value = "swedzargodJulyAdd", defaultValue = "") String swedzargodJulyAdd,
            @RequestParam(value = "swedzargodAugustAdd", defaultValue = "") String swedzargodAugustAdd,
            @RequestParam(value = "swedzargodSeptemberAdd", defaultValue = "") String swedzargodSeptemberAdd,
            @RequestParam(value = "swedzargodOctoberAdd", defaultValue = "") String swedzargodOctoberAdd,
            @RequestParam(value = "swedzargodNovemberAdd", defaultValue = "") String swedzargodNovemberAdd,
            @RequestParam(value = "swedzargodDecemberAdd", defaultValue = "") String swedzargodDecemberAdd,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Сведения о заработке за год добавление  UpdatePeopleController sved_zar_god_add()"));

        String message = "Данные успешно сохранены!";

        try {
            SvedZar_do svedZar_do = svedZar_doService.findById(id);
            //People people = peopleService.findById(38l);
            List<SvedZarMounth> svedZarMounths = new ArrayList<>();
            if(!swedzargodJanuaryAdd.equals(""))svedZarMounths.add(new SvedZarMounth("1",swedzargodJanuaryAdd));
            if(!swedzargodFebruaryAdd.equals(""))svedZarMounths.add(new SvedZarMounth("2",swedzargodFebruaryAdd));
            if(!swedzargodMarchAdd.equals(""))svedZarMounths.add(new SvedZarMounth("3",swedzargodMarchAdd));
            if(!swedzargodAprilAdd.equals(""))svedZarMounths.add(new SvedZarMounth("4",swedzargodAprilAdd));
            if(!swedzargodMayAdd.equals(""))svedZarMounths.add(new SvedZarMounth("5",swedzargodMayAdd));
            if(!swedzargodJuneAdd.equals(""))svedZarMounths.add(new SvedZarMounth("6",swedzargodJuneAdd));
            if(!swedzargodJulyAdd.equals(""))svedZarMounths.add(new SvedZarMounth("7",swedzargodJulyAdd));
            if(!swedzargodAugustAdd.equals(""))svedZarMounths.add(new SvedZarMounth("8",swedzargodAugustAdd));
            if(!swedzargodSeptemberAdd.equals(""))svedZarMounths.add(new SvedZarMounth("9",swedzargodSeptemberAdd));
            if(!swedzargodOctoberAdd.equals(""))svedZarMounths.add(new SvedZarMounth("10",swedzargodOctoberAdd));
            if(!swedzargodNovemberAdd.equals(""))svedZarMounths.add(new SvedZarMounth("11",swedzargodNovemberAdd));
            if(!swedzargodDecemberAdd.equals(""))svedZarMounths.add(new SvedZarMounth("12",swedzargodDecemberAdd));
            svedZarMounthService.saveall(svedZarMounths);
            double itogo = 0;
            for (SvedZarMounth s:
                    svedZarMounths) {
                itogo += Double.valueOf(s.getSum());
            }
            SvedZarGod svedZarGod = new SvedZarGod(swedzargodGodAdd,String.valueOf(itogo),svedZarMounths);
            svedZarGod.setIsreshkor(1);
            svedZarGodService.save(svedZarGod);

            svedZar_do.getZarGods().add(svedZarGod);
            svedZar_doService.save(svedZar_do);
            System.out.println("asdasdasd");
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sved_zar_posle_add")
    public @ResponseBody
    String sved_zar_posle_add(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "swedzarposleKatZLAdd", defaultValue = "") String swedzarposleKatZLAdd,
            @RequestParam(value = "swedzarposleKodAdd", defaultValue = "") String swedzarposleKodAdd,
            @RequestParam(value = "swedzarposleGodAdd", defaultValue = "") String swedzarposleGodAdd,
            @RequestParam(value = "swedzarposleJanuaryAdd", defaultValue = "") String swedzarposleJanuaryAdd,
            @RequestParam(value = "swedzarposleFebruaryAdd", defaultValue = "") String swedzarposleFebruaryAdd,
            @RequestParam(value = "swedzarposleMarchAdd", defaultValue = "") String swedzarposleMarchAdd,
            @RequestParam(value = "swedzarposleAprilAdd", defaultValue = "") String swedzarposleAprilAdd,
            @RequestParam(value = "swedzarposleMayAdd", defaultValue = "") String swedzarposleMayAdd,
            @RequestParam(value = "swedzarposleJuneAdd", defaultValue = "") String swedzarposleJuneAdd,
            @RequestParam(value = "swedzarposleJulyAdd", defaultValue = "") String swedzarposleJulyAdd,
            @RequestParam(value = "swedzarposleAugustAdd", defaultValue = "") String swedzarposleAugustAdd,
            @RequestParam(value = "swedzarposleSeptemberAdd", defaultValue = "") String swedzarposleSeptemberAdd,
            @RequestParam(value = "swedzarposleOctoberAdd", defaultValue = "") String swedzarposleOctoberAdd,
            @RequestParam(value = "swedzarposleNovemberAdd", defaultValue = "") String swedzarposleNovemberAdd,
            @RequestParam(value = "swedzarposleDecemberAdd", defaultValue = "") String swedzarposleDecemberAdd,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Сведения о заработке после добавление  UpdatePeopleController sved_zar_posle_add()"));

        String message = "Данные успешно сохранены!";
        try {

            List<SvedZarMounth> svedZarMounths = new ArrayList<>();
            if(!swedzarposleJanuaryAdd.equals(""))svedZarMounths.add(new SvedZarMounth("1",swedzarposleJanuaryAdd));
            if(!swedzarposleFebruaryAdd.equals(""))svedZarMounths.add(new SvedZarMounth("2",swedzarposleFebruaryAdd));
            if(!swedzarposleMarchAdd.equals(""))svedZarMounths.add(new SvedZarMounth("3",swedzarposleMarchAdd));
            if(!swedzarposleAprilAdd.equals(""))svedZarMounths.add(new SvedZarMounth("4",swedzarposleAprilAdd));
            if(!swedzarposleMayAdd.equals(""))svedZarMounths.add(new SvedZarMounth("5",swedzarposleMayAdd));
            if(!swedzarposleJuneAdd.equals(""))svedZarMounths.add(new SvedZarMounth("6",swedzarposleJuneAdd));
            if(!swedzarposleJulyAdd.equals(""))svedZarMounths.add(new SvedZarMounth("7",swedzarposleJulyAdd));
            if(!swedzarposleAugustAdd.equals(""))svedZarMounths.add(new SvedZarMounth("8",swedzarposleAugustAdd));
            if(!swedzarposleSeptemberAdd.equals(""))svedZarMounths.add(new SvedZarMounth("9",swedzarposleSeptemberAdd));
            if(!swedzarposleOctoberAdd.equals(""))svedZarMounths.add(new SvedZarMounth("10",swedzarposleOctoberAdd));
            if(!swedzarposleNovemberAdd.equals(""))svedZarMounths.add(new SvedZarMounth("11",swedzarposleNovemberAdd));
            if(!swedzarposleDecemberAdd.equals(""))svedZarMounths.add(new SvedZarMounth("12",swedzarposleDecemberAdd));

            svedZarMounthService.saveall(svedZarMounths);

            double itogo = 0;
            for (SvedZarMounth s:
                 svedZarMounths) {
                itogo += Double.valueOf(s.getSum());
            }

            SvedZar_posle svedZar_posle = new SvedZar_posle(swedzarposleKatZLAdd,swedzarposleKodAdd,swedzarposleGodAdd,String.valueOf(itogo),svedZarMounths);
            svedZar_posle.setIsreshkor(1);
            svedZar_posleService.save(svedZar_posle);

            SOPS_posle sops_posle = sops_posleService.findById(id);
            sops_posle.getSvedZar_posles().add(svedZar_posle);

            sops_posleService.save(sops_posle);

        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/stajtableadd")
    public @ResponseBody
    String stajtableadd(
            @RequestParam(value = "idperson", defaultValue = "") Long idperson,
            @RequestParam(value = "idstaj", defaultValue = "") Long idstaj,
            @RequestParam(value = "stajtableaddPeriod_s", defaultValue = "") String stajtableaddPeriod_s,
            @RequestParam(value = "stajtableaddPeriod_po", defaultValue = "") String stajtableaddPeriod_po,
            @RequestParam(value = "stajtableaddUvol", defaultValue = "") String stajtableaddUvol,
            @RequestParam(value = "stajtableaddTer_usl", defaultValue = "") String stajtableaddTer_usl,
            @RequestParam(value = "stajtableaddTer_usl_koef", defaultValue = "") String stajtableaddTer_usl_koef,
            @RequestParam(value = "stajtableaddOsob_usl", defaultValue = "") String stajtableaddOsob_usl,
            @RequestParam(value = "stajtableaddPoz_spis", defaultValue = "") String poz_spis,
            @RequestParam(value = "stajtableaddStrah_staj_osn", defaultValue = "") String stajtableaddStrah_staj_osn,
            @RequestParam(value = "stajtableaddStrah_staj_dop_sved", defaultValue = "") String stajtableaddStrah_staj_dop_sved,
            @RequestParam(value = "stajtableaddDosr_naz_strah_pens_osn", defaultValue = "") String stajtableaddDosr_naz_strah_pens_osn,
            @RequestParam(value = "stajtableaddDosr_naz_strah_pens_dop_sved", defaultValue = "") String stajtableaddDosr_naz_strah_pens_dop_sved,
            @RequestParam(value = "stajtableaddStrah_vzn", defaultValue = "") String stajtableaddStrah_vzn,
            @RequestParam(value = "stajtableaddKod_zastr_lica", defaultValue = "") String stajtableaddKod_zastr_lica,
            @RequestParam(value = "stajtableaddDataSniatia", defaultValue = "") String stajtableaddDataSniatia,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Таблица стажа добавление  UpdatePeopleController stajtableadd()"));

        String message = "Данные успешно сохранены!";
        try {
            Staj_table staj_table = new Staj_table(
                    stajtableaddPeriod_s, stajtableaddPeriod_po,
                    stajtableaddUvol, stajtableaddTer_usl, stajtableaddTer_usl_koef,
                    stajtableaddOsob_usl, poz_spis, stajtableaddStrah_staj_osn,
                    stajtableaddStrah_staj_dop_sved, stajtableaddDosr_naz_strah_pens_osn,
                    stajtableaddDosr_naz_strah_pens_dop_sved, stajtableaddStrah_vzn,
                    stajtableaddKod_zastr_lica, stajtableaddDataSniatia);
            staj_table.setIsreshkor(1);
            People people = peopleService.findById(idperson);

            people.getStajById(idstaj).getStaj_tables().add(staj_table);

            staj_tableService.save(staj_table);
            peopleService.save(people);

        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sopstableposleadd")
    public @ResponseBody
    String sopstableposleadd(
            @RequestParam(value = "idperson", defaultValue = "") Long idperson,
            @RequestParam(value = "idsopsposle", defaultValue = "") Long idsopsposle,
            @RequestParam(value = "sopstableposlePeriod_start", defaultValue = "") String sopstableposlePeriod_start,
            @RequestParam(value = "sopstableposlePeriod_end", defaultValue = "") String sopstableposlePeriod_end,
            @RequestParam(value = "sopstableposleTer_uslovija", defaultValue = "") String sopstableposleTer_uslovija,
            @RequestParam(value = "sopstableposleTer_uslovija_koef", defaultValue = "") String sopstableposleTer_uslovija_koef,
            @RequestParam(value = "sopstableposleOsob_uslovija", defaultValue = "") String sopstableposleOsob_uslovija,
            @RequestParam(value = "poz_spis", defaultValue = "") String poz_spis,
            @RequestParam(value = "sopstableposleTrud_staj_osn", defaultValue = "") String sopstableposleTrud_staj_osn,
            @RequestParam(value = "sopstableposleTrud_staj_dopsved", defaultValue = "") String sopstableposleTrud_staj_dopsved,
            @RequestParam(value = "sopstableposleVis_let_osn", defaultValue = "") String sopstableposleVis_let_osn,
            @RequestParam(value = "sopstableposleVis_let_dopsved", defaultValue = "") String sopstableposleVis_let_dopsved,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"До регистрации таблица добавление  UpdatePeopleController sopstableposleadd()"));

        String message = "Данные успешно сохранены!";
        try {
            SOPS_table sops_table = new SOPS_table(sopstableposlePeriod_start, sopstableposlePeriod_end,
                    sopstableposleTer_uslovija, sopstableposleTer_uslovija_koef,
                    sopstableposleOsob_uslovija, poz_spis,
                    sopstableposleTrud_staj_osn, sopstableposleTrud_staj_dopsved,
                    sopstableposleVis_let_osn, sopstableposleVis_let_dopsved);
            sops_table.setIsreshkor(1);
            People people = peopleService.findById(idperson);

            SOPS_posle sops_posle = people.getSops_posleById(idsopsposle);
            List<SOPS_table> sops_table1 = sops_posle.getSops_tables();
            sops_table1.add(sops_table);

            sops_tableService.save(sops_table);
            peopleService.save(people);

        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sopsdoljnostadd")
    public @ResponseBody
    String sopsdoljnostadd(
            @RequestParam(value = "idperson", defaultValue = "") Long idperson,
            @RequestParam(value = "idsopsdo", defaultValue = "") Long idsopsdo,
            @RequestParam(value = "sopstabledoPeriod_start", defaultValue = "") String sopstabledoPeriod_start,
            @RequestParam(value = "sopstabledoPeriod_end", defaultValue = "") String sopstabledoPeriod_end,
            @RequestParam(value = "sopstabledoTer_uslovija", defaultValue = "") String sopstabledoTer_uslovija,
            @RequestParam(value = "sopstabledoTer_uslovija_koef", defaultValue = "") String sopstabledoTer_uslovija_koef,
            @RequestParam(value = "sopstabledoOsob_uslovija", defaultValue = "") String sopstabledoOsob_uslovija,
            @RequestParam(value = "sopstabledoTrud_staj_osn", defaultValue = "") String sopstabledoTrud_staj_osn,
            @RequestParam(value = "sopstabledoTrud_staj_dopsved", defaultValue = "") String sopstabledoTrud_staj_dopsved,
            @RequestParam(value = "sopstabledoVis_let_osn", defaultValue = "") String sopstabledoVis_let_osn,
            @RequestParam(value = "sopstabledoVis_let_dopsved", defaultValue = "") String sopstabledoVis_let_dopsved,
            @RequestParam(value = "poz_spis", defaultValue = "") String poz_spis,
            @RequestParam(value = "sdoljnostadd", defaultValue = "") String sdoljnostadd,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Должность добавление  UpdatePeopleController sopsdoljnostadd()"));

        String message = "Данные успешно сохранены!";
        try {

            SOPS_do sops_do = sops_doService.findById(idsopsdo);

            SOPS_table sops_table = new SOPS_table();
            sops_table.setPeriod_start(sopstabledoPeriod_start);
            sops_table.setPeriod_end(sopstabledoPeriod_end);
            sops_table.setTer_uslovija(sopstabledoTer_uslovija);
            sops_table.setTer_uslovija_koef(sopstabledoTer_uslovija_koef);
            sops_table.setOsob_uslovija(sopstabledoOsob_uslovija);
            sops_table.setTrud_staj_osn(sopstabledoTrud_staj_osn);
            sops_table.setTrud_staj_dopsved(sopstabledoTrud_staj_dopsved);
            sops_table.setVis_let_osn(sopstabledoVis_let_osn);
            sops_table.setVis_let_dopsved(sopstabledoVis_let_dopsved);
            sops_table.setPoz_spis(poz_spis);

            sops_table.setIsreshkor(1);

            sops_tableService.save(sops_table);

            ArrayList<SOPS_table> sops_tables = new ArrayList<>();
            sops_tables.add(sops_table);
            SOPS_do_doljnost sops_do_doljnosts = new SOPS_do_doljnost(sdoljnostadd,sops_tables);

            sops_doDoljnostService.save(sops_do_doljnosts);
            sops_do.getSops_do_doljnosts().add(sops_do_doljnosts);
            sops_doService.save(sops_do);



        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/inoeadd")
    public @ResponseBody
    String inoeadd(
            @RequestParam(value = "idperson", defaultValue = "") Long idperson,
            @RequestParam(value = "inoeName", defaultValue = "") String inoeName,
            @RequestParam(value = "inoeReg_num", defaultValue = "") String inoeReg_num,
            @RequestParam(value = "inoeInoe_period", defaultValue = "") String inoeInoe_period,
            @RequestParam(value = "inoePeriod_start", defaultValue = "") String inoePeriod_start,
            @RequestParam(value = "inoePeriod_end", defaultValue = "") String inoePeriod_end,
            @RequestParam(value = "inoeSnils", defaultValue = "") String inoeSnils,
            @RequestParam(value = "inoeData_roj", defaultValue = "") String inoeData_roj,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Иное добавление  UpdatePeopleController inoeadd()"));

        String message = "Данные успешно сохранены!";
        try {
            Inoe inoe = new Inoe(inoeName, inoeReg_num, inoeInoe_period, inoePeriod_start,
                    inoePeriod_end, inoeSnils, inoeData_roj);
            inoe.setIsreshkor(1);
            People people = peopleService.findById(idperson);
            people.getInoes().add(inoe);
            inoeService.save(inoe);
            peopleService.save(people);
        }catch (Exception e){
            message = "Что то пошло не так! " + e;
        }
        return message;
    }

    //УДАЛЕНИЕ

    @PostMapping("/kart_graj/peopledelette")
    public @ResponseBody
    String peopledelette(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление записи о человеке  UpdatePeopleController peopledelette()"));

        String message = "Данные успешно удалены!";
        try {
            peopleService.delete(id);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/deleteSvedZarDo")
    public @ResponseBody
    String deleteSvedZarDo(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление сведения о заработке до  UpdatePeopleController deleteSvedZarDo()"));

        String message = "Данные успешно удалены!";
        try {
            svedZar_doService.delete(id);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/deletteSvedGod")
    public @ResponseBody
    String deletteSvedGod(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление сведения о заработке год до  UpdatePeopleController deletteSvedGod()"));

        String message = "Данные успешно удалены!";
        try {
            svedZarGodService.delete(id);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/deletteSvedZarPosle")
    public @ResponseBody
    String deletteSvedZarPosle(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление сведения о заработке после  UpdatePeopleController deletteSvedZarPosle()"));

        String message = "Данные успешно удалены!";
        try {
            svedZar_posleService.delete(id);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/inoedelette")
    public @ResponseBody
    String inoedelette(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление иное  UpdatePeopleController inoedelette()"));

        String message = "Данные успешно удалены!";
        try {
            inoeService.delete(id);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/stajtabledelette")
    public @ResponseBody
    String stajtabledelette(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление таблица стажа  UpdatePeopleController stajtabledelette()"));

        String message = "Данные успешно удалены!";
        try {
            staj_tableService.delete(id);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sopsposledelette")
    public @ResponseBody
    String sopsposledelette(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление после  UpdatePeopleController sopsposledelette()"));

        String message = "Данные успешно удалены!";
        try {
            sops_posleService.delete(id);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/stajdelette")
    public @ResponseBody
    String stajdelette(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление стажа  UpdatePeopleController stajdelette()"));

        String message = "Данные успешно удалены!";
        try {
            stajService.delete(id);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sopsdodelette")
    public @ResponseBody
    String sopsdodelette(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление до  UpdatePeopleController sopsdodelette()"));

        String message = "Данные успешно удалены!";
        try {
            sops_doService.delete(id);
        } catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sopsposletabledelette")
    public @ResponseBody
    String sopsposletabledelette(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление таблицы после UpdatePeopleController sopsposletabledelette()"));

        String message = "Данные успешно удалены!";
        try {
            sops_tableService.delete(id);
            //если это последний удалить все?
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sopsdoljnostdelette")
    public @ResponseBody
    String sopsdoljnostdelette(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление должность UpdatePeopleController sopsdoljnostdelette()"));

        String message = "Данные успешно удалены!";
        try {
            sops_doDoljnostService.delete(id);
            //если это последний удалить все?
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    //ИЗМЕНЕНИЕ

    @PostMapping("/kart_graj/updateSvedZarDo")
    public @ResponseBody
    String updateSvedZarDo(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "SvedZarDoNom_sprav", defaultValue = "") String SvedZarDoNom_sprav,
            @RequestParam(value = "SvedZarDoData_sprav", defaultValue = "") String SvedZarDoData_sprav,
            @RequestParam(value = "SvedZarDoPeriod_s", defaultValue = "") String SvedZarDoPeriod_s,
            @RequestParam(value = "SvedZarDoPeriod_po", defaultValue = "") String SvedZarDoPeriod_po,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение сведений о заработке до UpdatePeopleController updateSvedZarDo()"));

        String message = "Данные успешно сохранены!";
        try {
            SvedZar_do svedZar_do = svedZar_doService.findById(id);
            svedZar_do.setNom_sprav(SvedZarDoNom_sprav);
            svedZar_do.setData_sprav(SvedZarDoData_sprav);
            svedZar_do.setPeriod_s(SvedZarDoPeriod_s);
            svedZar_do.setPeriod_po(SvedZarDoPeriod_po);
            svedZar_doService.save(svedZar_do);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sved_zar_god_update")
    public @ResponseBody
    String sved_zar_god_update(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "swedzargodGodUpd", defaultValue = "") String swedzargodGodUpd,
            @RequestParam(value = "swedzargodJanuaryUpd", defaultValue = "") String swedzargodJanuaryUpd,
            @RequestParam(value = "swedzargodFebruaryUpd", defaultValue = "") String swedzargodFebruaryUpd,
            @RequestParam(value = "swedzargodMarchUpd", defaultValue = "") String swedzargodMarchUpd,
            @RequestParam(value = "swedzargodAprilUpd", defaultValue = "") String swedzargodAprilUpd,
            @RequestParam(value = "swedzargodMayUpd", defaultValue = "") String swedzargodMayUpd,
            @RequestParam(value = "swedzargodJuneUpd", defaultValue = "") String swedzargodJuneUpd,
            @RequestParam(value = "swedzargodJulyUpd", defaultValue = "") String swedzargodJulyUpd,
            @RequestParam(value = "swedzargodAugustUpd", defaultValue = "") String swedzargodAugustUpd,
            @RequestParam(value = "swedzargodSeptemberUpd", defaultValue = "") String swedzargodSeptemberUpd,
            @RequestParam(value = "swedzargodOctoberUpd", defaultValue = "") String swedzargodOctoberUpd,
            @RequestParam(value = "swedzargodNovemberUpd", defaultValue = "") String swedzargodNovemberUpd,
            @RequestParam(value = "swedzargodDecemberUpd", defaultValue = "") String swedzargodDecemberUpd,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение сведений о заработке до год UpdatePeopleController sved_zar_god_update()"));

        String message = "Данные успешно сохранены!";
        try {

            People people = peopleService.findById(38l);
            SvedZarGod svedZarGod = svedZarGodService.findById(id);
            List<SvedZarMounth> svedZarMounths = svedZarGod.getSvedZarMounths();
            svedZarMounthService.deleteall(svedZarMounths);

            svedZarMounths.clear();

            if(!swedzargodJanuaryUpd.equals(""))svedZarMounths.add(new SvedZarMounth("1",swedzargodJanuaryUpd));
            if(!swedzargodFebruaryUpd.equals(""))svedZarMounths.add(new SvedZarMounth("2",swedzargodFebruaryUpd));
            if(!swedzargodMarchUpd.equals(""))svedZarMounths.add(new SvedZarMounth("3",swedzargodMarchUpd));
            if(!swedzargodAprilUpd.equals(""))svedZarMounths.add(new SvedZarMounth("4",swedzargodAprilUpd));
            if(!swedzargodMayUpd.equals(""))svedZarMounths.add(new SvedZarMounth("5",swedzargodMayUpd));
            if(!swedzargodJuneUpd.equals(""))svedZarMounths.add(new SvedZarMounth("6",swedzargodJuneUpd));
            if(!swedzargodJulyUpd.equals(""))svedZarMounths.add(new SvedZarMounth("7",swedzargodJulyUpd));
            if(!swedzargodAugustUpd.equals(""))svedZarMounths.add(new SvedZarMounth("8",swedzargodAugustUpd));
            if(!swedzargodSeptemberUpd.equals(""))svedZarMounths.add(new SvedZarMounth("9",swedzargodSeptemberUpd));
            if(!swedzargodOctoberUpd.equals(""))svedZarMounths.add(new SvedZarMounth("10",swedzargodOctoberUpd));
            if(!swedzargodNovemberUpd.equals(""))svedZarMounths.add(new SvedZarMounth("11",swedzargodNovemberUpd));
            if(!swedzargodDecemberUpd.equals(""))svedZarMounths.add(new SvedZarMounth("12",swedzargodDecemberUpd));

            svedZarMounthService.saveall(svedZarMounths);

            double itogo = 0;
            for (SvedZarMounth s:
                    svedZarMounths) {
                itogo += Double.valueOf(s.getSum());
            }

            svedZarGod.setGod(swedzargodGodUpd);
            svedZarGod.setItogo(String.valueOf(itogo));
            svedZarGod.setSvedZarMounths(svedZarMounths);
            svedZarGod.setIsreshkor(1);
            svedZarGodService.save(svedZarGod);

        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sved_zar_posle_update")
    public @ResponseBody
    String sved_zar_posle_update(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "swedzarposleKatZLUpd", defaultValue = "") String swedzarposleKatZLUpd,
            @RequestParam(value = "swedzarposleKodUpd", defaultValue = "") String swedzarposleKodUpd,
            @RequestParam(value = "swedzarposleGodUpd", defaultValue = "") String swedzarposleGodUpd,
            @RequestParam(value = "swedzarposleJanuaryUpd", defaultValue = "") String swedzarposleJanuaryUpd,
            @RequestParam(value = "swedzarposleFebruaryUpd", defaultValue = "") String swedzarposleFebruaryUpd,
            @RequestParam(value = "swedzarposleMarchUpd", defaultValue = "") String swedzarposleMarchUpd,
            @RequestParam(value = "swedzarposleAprilUpd", defaultValue = "") String swedzarposleAprilUpd,
            @RequestParam(value = "swedzarposleMayUpd", defaultValue = "") String swedzarposleMayUpd,
            @RequestParam(value = "swedzarposleJuneUpd", defaultValue = "") String swedzarposleJuneUpd,
            @RequestParam(value = "swedzarposleJulyUpd", defaultValue = "") String swedzarposleJulyUpd,
            @RequestParam(value = "swedzarposleAugustUpd", defaultValue = "") String swedzarposleAugustUpd,
            @RequestParam(value = "swedzarposleSeptemberUpd", defaultValue = "") String swedzarposleSeptemberUpd,
            @RequestParam(value = "swedzarposleOctoberUpd", defaultValue = "") String swedzarposleOctoberUpd,
            @RequestParam(value = "swedzarposleNovemberUpd", defaultValue = "") String swedzarposleNovemberUpd,
            @RequestParam(value = "swedzarposleDecemberUpd", defaultValue = "") String swedzarposleDecemberUpd,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение сведений о заработке после UpdatePeopleController sved_zar_posle_update()"));

        String message = "Данные успешно сохранены!";
        try {
            SvedZar_posle svedZar_posle = svedZar_posleService.findById(id);
            List<SvedZarMounth> svedZarMounths = svedZar_posle.getSvedZarMounths();
            svedZarMounthService.deleteall(svedZarMounths);

            svedZarMounths.clear();

            if(!swedzarposleJanuaryUpd.equals(""))svedZarMounths.add(new SvedZarMounth("1",swedzarposleJanuaryUpd));
            if(!swedzarposleFebruaryUpd.equals(""))svedZarMounths.add(new SvedZarMounth("2",swedzarposleFebruaryUpd));
            if(!swedzarposleMarchUpd.equals(""))svedZarMounths.add(new SvedZarMounth("3",swedzarposleMarchUpd));
            if(!swedzarposleAprilUpd.equals(""))svedZarMounths.add(new SvedZarMounth("4",swedzarposleAprilUpd));
            if(!swedzarposleMayUpd.equals(""))svedZarMounths.add(new SvedZarMounth("5",swedzarposleMayUpd));
            if(!swedzarposleJuneUpd.equals(""))svedZarMounths.add(new SvedZarMounth("6",swedzarposleJuneUpd));
            if(!swedzarposleJulyUpd.equals(""))svedZarMounths.add(new SvedZarMounth("7",swedzarposleJulyUpd));
            if(!swedzarposleAugustUpd.equals(""))svedZarMounths.add(new SvedZarMounth("8",swedzarposleAugustUpd));
            if(!swedzarposleSeptemberUpd.equals(""))svedZarMounths.add(new SvedZarMounth("9",swedzarposleSeptemberUpd));
            if(!swedzarposleOctoberUpd.equals(""))svedZarMounths.add(new SvedZarMounth("10",swedzarposleOctoberUpd));
            if(!swedzarposleNovemberUpd.equals(""))svedZarMounths.add(new SvedZarMounth("11",swedzarposleNovemberUpd));
            if(!swedzarposleDecemberUpd.equals(""))svedZarMounths.add(new SvedZarMounth("12",swedzarposleDecemberUpd));

            svedZarMounthService.saveall(svedZarMounths);

            double itogo = 0;
            for (SvedZarMounth s:
                    svedZarMounths) {
                itogo += Double.valueOf(s.getSum());
            }

            svedZar_posle.setItogo(String.valueOf(itogo));
            svedZar_posle.setKatZL(swedzarposleKatZLUpd);
            svedZar_posle.setKod(swedzarposleKodUpd);
            svedZar_posle.setGod(swedzarposleGodUpd);
            svedZar_posle.setSvedZarMounths(svedZarMounths);
            svedZar_posle.setIsreshkor(1);

            svedZar_posleService.save(svedZar_posle);

        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sopstableupdate")
    public @ResponseBody
    String sopstableupdate(
            @RequestParam(value = "idsopstable", defaultValue = "") Long idsopstable,
            @RequestParam(value = "sopstablePeriod_startupd", defaultValue = "") String sopstablePeriod_start,
            @RequestParam(value = "sopstablePeriod_endupd", defaultValue = "") String sopstablePeriod_end,
            @RequestParam(value = "sopstableTer_uslovijaupd", defaultValue = "") String sopstableTer_uslovija,
            @RequestParam(value = "sopstableTer_uslovijaupd_koef", defaultValue = "") String sopstableTer_uslovijaupd_koef,
            @RequestParam(value = "sopstableOsob_uslovijaupd", defaultValue = "") String sopstableOsob_uslovija,
            @RequestParam(value = "poz_spis") String poz_spis,
            @RequestParam(value = "sopstableTrud_staj_osnupd", defaultValue = "") String sopstableTrud_staj_osn,
            @RequestParam(value = "sopstableTrud_staj_dopsvedupd", defaultValue = "") String sopstableTrud_staj_dopsved,
            @RequestParam(value = "sopstableVis_let_osnupd", defaultValue = "") String sopstableVis_let_osn,
            @RequestParam(value = "sopstableVis_let_dopsvedupd", defaultValue = "") String sopstableVis_let_dopsved,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение таблицы до/после UpdatePeopleController sopstableupdate()"));

        String message = "Данные успешно сохранены!";
        try {
            SOPS_table sops_table = sops_tableService.findById(idsopstable);
            sops_table.setPeriod_start(sopstablePeriod_start);
            sops_table.setPeriod_end(sopstablePeriod_end);
            sops_table.setTer_uslovija(sopstableTer_uslovija);
            sops_table.setTer_uslovija_koef(sopstableTer_uslovijaupd_koef);
            sops_table.setOsob_uslovija(sopstableOsob_uslovija);
            sops_table.setPoz_spis(poz_spis);
            sops_table.setTrud_staj_osn(sopstableTrud_staj_osn);
            sops_table.setTrud_staj_dopsved(sopstableTrud_staj_dopsved);
            sops_table.setVis_let_osn(sopstableVis_let_osn);
            sops_table.setVis_let_dopsved(sopstableVis_let_dopsved);
            sops_table.setIsreshkor(1);

            sops_tableService.save(sops_table);

        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sopsdoljnostupdate")
    public @ResponseBody
    String sopsdoljnostupdate(
            @RequestParam(value = "iddoljnost", defaultValue = "") Long iddoljnost,
            @RequestParam(value = "sopstablePeriod_startupd", defaultValue = "") String sopstablePeriod_start,
            @RequestParam(value = "sopstablePeriod_endupd", defaultValue = "") String sopstablePeriod_end,
            @RequestParam(value = "sopstableTer_uslovijaupd", defaultValue = "") String sopstableTer_uslovija,
            @RequestParam(value = "sopstableTer_uslovijaupd_koef", defaultValue = "") String sopstableTer_uslovijaupd_koef,
            @RequestParam(value = "sopstableOsob_uslovijaupd", defaultValue = "") String sopstableOsob_uslovija,
            @RequestParam(value = "sopstableTrud_staj_osnupd", defaultValue = "") String sopstableTrud_staj_osn,
            @RequestParam(value = "sopstableTrud_staj_dopsvedupd", defaultValue = "") String sopstableTrud_staj_dopsved,
            @RequestParam(value = "sopstableVis_let_osnupd", defaultValue = "") String sopstableVis_let_osn,
            @RequestParam(value = "sopstableVis_let_dopsvedupd", defaultValue = "") String sopstableVis_let_dopsved,
            @RequestParam(value = "poz_spis", defaultValue = "") String poz_spis,
            @RequestParam(value = "sopsdoljnostupd", defaultValue = "") String sopsdoljnostupd,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение должность UpdatePeopleController sopsdoljnostupdate()"));

        String message = "Данные успешно сохранены!";
        try {
            SOPS_do_doljnost sops_do_doljnost = sops_doDoljnostService.findById(iddoljnost);

            SOPS_table sops_table = sops_do_doljnost.getSops_tables().get(0);
            sops_table.setPeriod_start(sopstablePeriod_start);
            sops_table.setPeriod_end(sopstablePeriod_end);
            sops_table.setTer_uslovija(sopstableTer_uslovija);
            sops_table.setTer_uslovija_koef(sopstableTer_uslovijaupd_koef);
            sops_table.setOsob_uslovija(sopstableOsob_uslovija);
            sops_table.setTrud_staj_osn(sopstableTrud_staj_osn);
            sops_table.setTrud_staj_dopsved(sopstableTrud_staj_dopsved);
            sops_table.setVis_let_osn(sopstableVis_let_osn);
            sops_table.setVis_let_dopsved(sopstableVis_let_dopsved);
            sops_table.setPoz_spis(poz_spis);
            sops_table.setIsreshkor(1);
            //sops_tableService.save(sops_table);

            sops_do_doljnost.setDoljnost(sopsdoljnostupd);
            //sops_do_doljnost.getSops_tables().add(sops_table);

            sops_doDoljnostService.save(sops_do_doljnost);

        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/stajtableupdate")
    public @ResponseBody
    String stajtableupdate(
            @RequestParam(value = "idstajtable", defaultValue = "") Long idstajtable,
            @RequestParam(value = "stajtableupdatePeriod_s", defaultValue = "") String stajtableupdatePeriod_s,
            @RequestParam(value = "stajtableupdatePeriod_po", defaultValue = "") String stajtableupdatePeriod_po,
            @RequestParam(value = "stajtableupdateUvol", defaultValue = "") String stajtableupdateUvol,
            @RequestParam(value = "stajtableupdateTer_usl", defaultValue = "") String stajtableupdateTer_usl,
            @RequestParam(value = "stajtableupdateTer_usl_koef", defaultValue = "") String stajtableupdateTer_usl_koef,
            @RequestParam(value = "stajtableupdateOsob_usl", defaultValue = "") String stajtableupdateOsob_usl,
            @RequestParam(value = "stajtableupdatePoz_spis", defaultValue = "") String stajtableupdatePoz_spis,
            @RequestParam(value = "stajtableupdateStrah_staj_osn", defaultValue = "") String stajtableupdateStrah_staj_osn,
            @RequestParam(value = "stajtableupdateStrah_staj_dop_sved", defaultValue = "") String stajtableupdateStrah_staj_dop_sved,
            @RequestParam(value = "stajtableupdateDosr_naz_strah_pens_osn", defaultValue = "") String stajtableupdateDosr_naz_strah_pens_osn,
            @RequestParam(value = "stajtableupdateDosr_naz_strah_pens_dop_sved", defaultValue = "") String stajtableupdateDosr_naz_strah_pens_dop_sved,
            @RequestParam(value = "stajtableupdateStrah_vzn", defaultValue = "") String stajtableupdateStrah_vzn,
            @RequestParam(value = "stajtableupdateKod_zastr_lica", defaultValue = "") String stajtableupdateKod_zastr_lica,
            @RequestParam(value = "stajtableupdateDataSniatia", defaultValue = "") String stajtableupdateDataSniatia,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение таблица стажа UpdatePeopleController stajtableupdate()"));

        String message = "Данные успешно сохранены!";
        try {
            Staj_table staj_table = staj_tableService.findById(idstajtable);
            staj_table.setPeriod_s(stajtableupdatePeriod_s);
            staj_table.setPeriod_po(stajtableupdatePeriod_po);
            staj_table.setUvol(stajtableupdateUvol);
            staj_table.setTer_usl(stajtableupdateTer_usl);
            staj_table.setTer_usl_koeff(stajtableupdateTer_usl_koef);
            staj_table.setOsob_usl(stajtableupdateOsob_usl);
            staj_table.setPoz_spis(stajtableupdatePoz_spis);
            staj_table.setStrah_staj_osn(stajtableupdateStrah_staj_osn);
            staj_table.setStrah_staj_dop_sved(stajtableupdateStrah_staj_dop_sved);
            staj_table.setOsob_usl(stajtableupdateOsob_usl);
            staj_table.setDosr_naz_strah_pens_osn(stajtableupdateDosr_naz_strah_pens_osn);
            staj_table.setDosr_naz_strah_pens_dop_sved(stajtableupdateDosr_naz_strah_pens_dop_sved);
            staj_table.setStrah_vzn(stajtableupdateStrah_vzn);
            staj_table.setKod_zastr_lica(stajtableupdateKod_zastr_lica);
            staj_table.setDataSniatia(stajtableupdateDataSniatia);
            staj_table.setIsreshkor(1);
            staj_tableService.save(staj_table);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/inoetableupdate")
    public @ResponseBody
    String inoetableupdate(
            @RequestParam(value = "idinoe", defaultValue = "") Long idinoe,
            @RequestParam(value = "inoeNameupdate", defaultValue = "") String inoeNameupdate,
            @RequestParam(value = "inoeReg_numupdate", defaultValue = "") String inoeReg_numupdate,
            @RequestParam(value = "inoeInoe_periodupdate", defaultValue = "") String inoeInoe_periodupdate,
            @RequestParam(value = "inoePeriod_startupdate", defaultValue = "") String inoePeriod_startupdate,
            @RequestParam(value = "inoePeriod_endupdate", defaultValue = "") String inoePeriod_endupdate,
            @RequestParam(value = "inoeSnilsupdate", defaultValue = "") String inoeSnilsupdate,
            @RequestParam(value = "inoeData_rojupdate", defaultValue = "") String inoeData_rojupdate,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение иное UpdatePeopleController inoetableupdate()"));

        String message = "Данные успешно сохранены!";
        try {
            Inoe inoe = inoeService.findById(idinoe);
            inoe.setName(inoeNameupdate);
            inoe.setReg_num(inoeReg_numupdate);
            inoe.setInoe_period(inoeInoe_periodupdate);
            inoe.setPeriod_start(inoePeriod_startupdate);
            inoe.setPeriod_end(inoePeriod_endupdate);
            inoe.setSnils(inoeSnilsupdate);
            inoe.setData_roj(inoeData_rojupdate);
            inoe.setIsreshkor(1);
            inoeService.save(inoe);
        }catch (Exception e){
            message = "Что то пошло не так! " + e;
        }
        return message;
    }

    @PostMapping("/kart_graj/stajupdatename")
    public @ResponseBody
    String stajupdatename(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "stajgetReg_nom", defaultValue = "") String stajgetReg_nom,
            @RequestParam(value = "stajgetName", defaultValue = "") String stajgetName,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение стаж UpdatePeopleController stajupdatename()"));

        String message = "Данные успешно сохранены!";
        try {
            Staj staj = stajService.findById(id);
            staj.setReg_nom(stajgetReg_nom);
            staj.setName(stajgetName);
            stajService.save(staj);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/sopsposleupdatename")
    public @ResponseBody
    String sopsposleupdatename(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "sopsposlegetStrahovatel", defaultValue = "") String sopsposlegetStrahovatel,
            @RequestParam(value = "sopsposlegetName_org", defaultValue = "") String sopsposlegetName_org,
            @RequestParam(value = "sopsposlegetReg_num_pfr", defaultValue = "") String sopsposlegetReg_num_pfr,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение после UpdatePeopleController sopsposleupdatename()"));

        String message = "Данные успешно сохранены!";
        try {
            SOPS_posle sops_posle = sops_posleService.findById(id);
            sops_posle.setStrahovatel(sopsposlegetStrahovatel);
            sops_posle.setName_org(sopsposlegetName_org);
            sops_posle.setReg_num_pfr(sopsposlegetReg_num_pfr);
            sops_posleService.save(sops_posle);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

/*    @PostMapping("/kart_graj/sopsdoupdateDoljnost")//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public @ResponseBody
    String sopsdoupdateDoljnost(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "sopsdogetDoljnost", defaultValue = "") String sopsdogetDoljnost,
            @AuthenticationPrincipal User user,
            Model model) {

        String message = "Данные успешно сохранены!";
*//*        try {
            SOPS_do sops_do = sops_doService.findById(id);
            sops_do.setDoljnost(sopsdogetDoljnost);
            sops_doService.save(sops_do);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }*//*
        return message;
    }*/

    @PostMapping("/kart_graj/sopsdoupdatename")
    public @ResponseBody
    String sopsdoupdatename(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "sopsdogetName_org", defaultValue = "") String sopsdogetName_org,
            @RequestParam(value = "sopsdogetVid_d", defaultValue = "") String sopsdogetVid_d,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение до UpdatePeopleController sopsdoupdatename()"));

        String message = "Данные успешно сохранены!";
        try {
            SOPS_do sops_do = sops_doService.findById(id);
            sops_do.setName_org(sopsdogetName_org);
            sops_do.setVid_d(sopsdogetVid_d);
            sops_doService.save(sops_do);
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/peopleUpdate")
    public @ResponseBody
    String upfr_menu(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "peoplegetFam", defaultValue = "") String peoplegetFam,
            @RequestParam(value = "peoplegetName", defaultValue = "") String peoplegetName,
            @RequestParam(value = "peoplegetOtch", defaultValue = "") String peoplegetOtch,
            @RequestParam(value = "peoplegetPol", defaultValue = "") String peoplegetPol,
            @RequestParam(value = "peoplegetSnils", defaultValue = "") String peoplegetSnils,
            @RequestParam(value = "peoplegetDatebirthday", defaultValue = "") String peoplegetDatebirthday,
            @RequestParam(value = "nameTO", defaultValue = "") String nameTO,
            @RequestParam(value = "dataAct", defaultValue = "") String dataAct,
            @RequestParam(value = "nomAct", defaultValue = "") String nomAct,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Изменение основных сведений UpdatePeopleController sopsdoupdatename()"));

        String message = "Что то пошло не так!";
        try {
            People people;
            if(id==null){
                people = new People();
            }else{
                people = peopleService.findById(id);


                //PeopleOld peopleOld = peopleOldService.findById(id);

/*                if(peopleOldService.findById(id)){
                    PeopleOld peopleOld = new PeopleOld(people);
                    peopleOldService.save(peopleOld);
                }*/

            }
            people.setFam(peoplegetFam);
            people.setName(peoplegetName);
            people.setOtch(peoplegetOtch);
            people.setPol(peoplegetPol);
            people.setDate_birthday(peoplegetDatebirthday);
            people.setSnils(peoplegetSnils);

            people.setNameTOPFR(nameTO);
            people.setDataAct(dataAct);
            people.setNomAct(nomAct);

            people.setUser(user);
            people.setRayon_kor(user.getRayon());
            peopleService.save(people);
            message = String.valueOf(people.getId_people());
        }catch (Exception e){
            message = "Что то пошло не так!";
        }
        return message;
    }



    //открытие модальных окон
    @GetMapping("/kart_graj/inoetableupdate_popup")
    public String inoetableupdate_popup(
            @RequestParam(value = "idinoe", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна таблица иное изменение UpdatePeopleController inoetableupdate_popup()"));

        Inoe inoe = inoeService.findById(id);
        model.addAttribute("inoe", inoe);
        return "popup/inoe_popup :: inoe_popup_update";
    }
    @GetMapping("/kart_graj/inoetableadd_popup")
    public String inoetableadd_popup(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна таблица иное добавление UpdatePeopleController inoetableadd_popup()"));

        return "popup/inoe_popup :: inoe_popup_add";
    }

    @GetMapping("/kart_graj/stajtableupdate_popup")
    public String stajtableupdate_popup(
            @RequestParam(value = "idstajtable", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна таблица стаж изменение UpdatePeopleController stajtableupdate_popup()"));

        Staj_table staj_table = staj_tableService.findById(id);
        model.addAttribute("staj_table", staj_table);

        List<SpisokTerUsl> terUsl = new ArrayList<>();
        terUsl.add(new SpisokTerUsl(-1l,staj_table.getTer_usl()));
        terUsl.add(new SpisokTerUsl(0l,""));
        terUsl.addAll(spisokTerUslService.findAll());
        List<SpisokOsobUslTr> osobUslTr = new ArrayList<>();
        osobUslTr.add(new SpisokOsobUslTr(-1l,staj_table.getOsob_usl()));
        osobUslTr.add(new SpisokOsobUslTr(0l,""));
        osobUslTr.addAll(spisokOsobUslTrService.findAll());
        List<SpisokIschisStaj> ischisStaj = new ArrayList<>();
        ischisStaj.add(new SpisokIschisStaj(-1l,staj_table.getStrah_staj_osn()));
        ischisStaj.add(new SpisokIschisStaj(0l,""));
        ischisStaj.addAll(spisokIschisStajService.findAll());

        List<SpisokDejatel> ischisStajDop = new ArrayList<>();
        ischisStajDop.add(new SpisokDejatel(-1l,staj_table.getStrah_staj_dop_sved()));
        ischisStajDop.add(new SpisokDejatel(0l,""));
        ischisStajDop.addAll(spisokDejatelService.findAll());

        model.addAttribute("ischisStajDop", ischisStajDop);

        model.addAttribute("terUsl", terUsl);
        model.addAttribute("osobUslTr", osobUslTr);
        model.addAttribute("ischisStaj", ischisStaj);

        return "popup/staj_popup :: staj_popup_update";
    }
    @GetMapping("/kart_graj/stajtableadd_popup")
    public String stajtableadd_popup(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна таблица стаж добавление UpdatePeopleController stajtableadd_popup()"));

        Staj staj = stajService.findById(id);
        model.addAttribute("staj", staj);

        List<SpisokTerUsl> terUsl = new ArrayList<>();
        terUsl.add(new SpisokTerUsl(0l,""));
        terUsl.addAll(spisokTerUslService.findAll());
        List<SpisokOsobUslTr> osobUslTr = new ArrayList<>();
        osobUslTr.add(new SpisokOsobUslTr(0l,""));
        osobUslTr.addAll(spisokOsobUslTrService.findAll());
        List<SpisokIschisStaj> ischisStaj = new ArrayList<>();
        ischisStaj.add(new SpisokIschisStaj(0l,""));
        ischisStaj.addAll(spisokIschisStajService.findAll());
        List<SpisokVisLet> visLet = new ArrayList<>();
        visLet.add(new SpisokVisLet(0l,""));
        visLet.addAll(spisokVisLetService.findAll());
        List<SpisokDejatel> ischisStajDop = new ArrayList<>();
        ischisStajDop.add(new SpisokDejatel(0l,""));
        ischisStajDop.addAll(spisokDejatelService.findAll());

        model.addAttribute("ischisStajDop", ischisStajDop);
        model.addAttribute("terUsl", terUsl);
        model.addAttribute("osobUslTr", osobUslTr);
        model.addAttribute("ischisStaj", ischisStaj);
        model.addAttribute("visLet", visLet);

        return "popup/staj_popup :: staj_popup_add";
    }

    @GetMapping("/kart_graj/sopsdoljnostupdate_popup")
    public String sopsdoljnostupdate_popup(
            @RequestParam(value = "iddoljnost", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна должность изменение UpdatePeopleController sopsdoljnostupdate_popup()"));

        SOPS_do_doljnost sops_do_doljnost = sops_doDoljnostService.findById(id);
        model.addAttribute("sops_do_doljnost", sops_do_doljnost);

        List<SpisokTerUsl> terUsl = new ArrayList<>();
        terUsl.add(new SpisokTerUsl(-1l,sops_do_doljnost.getSops_tables().get(0).getTer_uslovija()));
        terUsl.add(new SpisokTerUsl(0l,""));
        terUsl.addAll(spisokTerUslService.findAll());
        List<SpisokOsobUslTr> osobUslTr = new ArrayList<>();
        osobUslTr.add(new SpisokOsobUslTr(-1l,sops_do_doljnost.getSops_tables().get(0).getOsob_uslovija()));
        osobUslTr.add(new SpisokOsobUslTr(0l,""));
        osobUslTr.addAll(spisokOsobUslTrService.findAll());
        List<SpisokIschisStaj> ischisStaj = new ArrayList<>();
        ischisStaj.add(new SpisokIschisStaj(-1l,sops_do_doljnost.getSops_tables().get(0).getTrud_staj_osn()));
        ischisStaj.add(new SpisokIschisStaj(0l,""));
        ischisStaj.addAll(spisokIschisStajService.findAll());
        List<SpisokVisLet> visLet = new ArrayList<>();
        visLet.add(new SpisokVisLet(-1l,sops_do_doljnost.getSops_tables().get(0).getVis_let_osn()));
        visLet.add(new SpisokVisLet(0l,""));
        visLet.addAll(spisokVisLetService.findAll());

        List<SpisokDejatel> ischisStajDop = new ArrayList<>();
        ischisStajDop.add(new SpisokDejatel(-1l,sops_do_doljnost.getSops_tables().get(0).getVis_let_dopsved()));
        ischisStajDop.add(new SpisokDejatel(0l,""));
        ischisStajDop.addAll(spisokDejatelService.findAll());


        model.addAttribute("terUsl", terUsl);
        model.addAttribute("osobUslTr", osobUslTr);
        model.addAttribute("ischisStaj", ischisStaj);
        model.addAttribute("ischisStajDop", ischisStajDop);
        model.addAttribute("visLet", visLet);

        return "popup/sops_table_popup :: sops_doljnost_popup_update";
    }

    @GetMapping("/kart_graj/sopstableupdate_popup")
    public String sopstableupdate_popup(
            @RequestParam(value = "idsopstable", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна таблица до/после изменение UpdatePeopleController sopstableupdate_popup()"));

        SOPS_table sops_table = sops_tableService.findById(id);
        model.addAttribute("sops_table", sops_table);

        List<SpisokTerUsl> terUsl = new ArrayList<>();
        terUsl.add(new SpisokTerUsl(-1l,sops_table.getTer_uslovija()));
        terUsl.add(new SpisokTerUsl(0l,""));
        terUsl.addAll(spisokTerUslService.findAll());
        List<SpisokOsobUslTr> osobUslTr = new ArrayList<>();
        osobUslTr.add(new SpisokOsobUslTr(-1l,sops_table.getOsob_uslovija()));
        osobUslTr.add(new SpisokOsobUslTr(0l,""));
        osobUslTr.addAll(spisokOsobUslTrService.findAll());
        List<SpisokIschisStaj> ischisStaj = new ArrayList<>();
        ischisStaj.add(new SpisokIschisStaj(-1l,sops_table.getTrud_staj_osn()));
        ischisStaj.add(new SpisokIschisStaj(0l,""));
        ischisStaj.addAll(spisokIschisStajService.findAll());
        List<SpisokVisLet> visLet = new ArrayList<>();
        visLet.add(new SpisokVisLet(-1l,sops_table.getVis_let_osn()));
        visLet.add(new SpisokVisLet(0l,""));
        visLet.addAll(spisokVisLetService.findAll());
        List<SpisokDejatel> ischisStajDop = new ArrayList<>();
        ischisStajDop.add(new SpisokDejatel(-1l,sops_table.getVis_let_dopsved()));
        ischisStajDop.add(new SpisokDejatel(0l,""));
        ischisStajDop.addAll(spisokDejatelService.findAll());

        model.addAttribute("ischisStajDop", ischisStajDop);
        model.addAttribute("terUsl", terUsl);
        model.addAttribute("osobUslTr", osobUslTr);
        model.addAttribute("ischisStaj", ischisStaj);
        model.addAttribute("visLet", visLet);

        return "popup/sops_table_popup :: sops_table_popup_update";
    }


    @GetMapping("/kart_graj/sopstabledoadd_popup")
    public String sopstabledoadd_popup(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна таблица до/после добавление UpdatePeopleController sopstabledoadd_popup()"));

        model.addAttribute("id", id);
        return "popup/sops_table_popup :: sopstabledoadd";
    }
    @GetMapping("/kart_graj/sopsdoljnostadd_popup")
    public String sopsdoljnostadd_popup(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна должность добавление UpdatePeopleController sopsdoljnostadd_popup()"));

        model.addAttribute("id", id);

        List<SpisokTerUsl> terUsl = new ArrayList<>();
        terUsl.add(new SpisokTerUsl(0l,""));
        terUsl.addAll(spisokTerUslService.findAll());
        List<SpisokOsobUslTr> osobUslTr = new ArrayList<>();
        osobUslTr.add(new SpisokOsobUslTr(0l,""));
        osobUslTr.addAll(spisokOsobUslTrService.findAll());
        List<SpisokIschisStaj> ischisStaj = new ArrayList<>();
        ischisStaj.add(new SpisokIschisStaj(0l,""));
        ischisStaj.addAll(spisokIschisStajService.findAll());
        List<SpisokVisLet> visLet = new ArrayList<>();
        visLet.add(new SpisokVisLet(0l,""));
        visLet.addAll(spisokVisLetService.findAll());

        List<SpisokDejatel> ischisStajDop = new ArrayList<>();
        ischisStajDop.add(new SpisokDejatel(0l,""));
        ischisStajDop.addAll(spisokDejatelService.findAll());

        model.addAttribute("ischisStajDop", ischisStajDop);
        model.addAttribute("terUsl", terUsl);
        model.addAttribute("osobUslTr", osobUslTr);
        model.addAttribute("ischisStaj", ischisStaj);
        model.addAttribute("visLet", visLet);

        return "popup/sops_table_popup :: sopsdoljnostadd";
    }

    @GetMapping("/kart_graj/sopstableposleadd_popup")
    public String sopstableposleadd_popup(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна таблица после добавление UpdatePeopleController sopsdoljnostadd_popup()"));

        model.addAttribute("id", id);

        List<SpisokTerUsl> terUsl = new ArrayList<>();
        terUsl.add(new SpisokTerUsl(0l,""));
        terUsl.addAll(spisokTerUslService.findAll());
        List<SpisokOsobUslTr> osobUslTr = new ArrayList<>();
        osobUslTr.add(new SpisokOsobUslTr(0l,""));
        osobUslTr.addAll(spisokOsobUslTrService.findAll());
        List<SpisokIschisStaj> ischisStaj = new ArrayList<>();
        ischisStaj.add(new SpisokIschisStaj(0l,""));
        ischisStaj.addAll(spisokIschisStajService.findAll());

        List<SpisokDejatel> ischisStajDop = new ArrayList<>();
        ischisStajDop.add(new SpisokDejatel(0l,""));
        ischisStajDop.addAll(getSpisokDejatelService.findAll());

        List<SpisokVisLet> visLet = new ArrayList<>();
        visLet.add(new SpisokVisLet(0l,""));
        visLet.addAll(spisokVisLetService.findAll());

/*        List<SpisokDecretDeti> decretDeti = spisokDecretDetiService.findAll();
        List<SpisokDejatel> dejatel = spisokDejatelService.findAll();
        List<SpisokRaiKoef> raiKoef = spisokRaiKoefService.findAll();*/

        model.addAttribute("terUsl", terUsl);
        model.addAttribute("osobUslTr", osobUslTr);
        model.addAttribute("ischisStaj", ischisStaj);
        model.addAttribute("ischisStajDop", ischisStajDop);
        model.addAttribute("visLet", visLet);

        return "popup/sops_table_popup :: sops_posle_table_popup_add";
    }

    //svedzarposle
    @GetMapping("/kart_graj/addSvedZarPosle_popup")
    public String addSvedZarPosle(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна сведения о заработке добавление UpdatePeopleController addSvedZarPosle_popup()"));

        model.addAttribute("id", id);
        return "popup/sved_zar_posle_popup :: sved_zar_posle_popup_add";
    }
    @GetMapping("/kart_graj/updateSvedZarPosle_popup")
    public String updateSvedZarPosle(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна сведения о заработке изменение UpdatePeopleController updateSvedZarPosle()"));

        SvedZar_posle svedZar_posle = svedZar_posleService.findById(id);
        model.addAttribute("svedZar_posle", svedZar_posle);
        return "popup/sved_zar_posle_popup :: sved_zar_posle_popup_update";
    }

    //svedzargod
    @GetMapping("/kart_graj/addSvedZarGod_popup")
    public String addSvedZarGod_popup(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна сведения о заработке год добавление UpdatePeopleController addSvedZarGod_popup()"));

        model.addAttribute("id", id);
        return "popup/sved_zar_god_popup :: sved_zar_god_popup_add";
    }
    @GetMapping("/kart_graj/updateSvedZarGod_popup")
    public String updateSvedZarGod_popup(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна сведения о заработке год изменение UpdatePeopleController updateSvedZarGod_popup()"));

        SvedZarGod svedZarGod = svedZarGodService.findById(id);
        model.addAttribute("svedZar_god", svedZarGod);
        return "popup/sved_zar_god_popup :: sved_zar_god_popup_update";
    }

    //
    @GetMapping("/kart_graj/addSvedZarGodFromFile_popup")
    public String addSvedZarGodFromFile_popup(
            @RequestParam(value = "idsvedzar", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна сведения о заработке год из файла добавление UpdatePeopleController addSvedZarGodFromFile_popup()"));

        model.addAttribute("id", id);
        return "popup/sops_table_popup :: addSvedZarGodFromFile_popup";
    }

    //вместе
    @GetMapping("/kart_graj/addsopsdo_popup")
    public String addsopsdo_popup(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна до добавление UpdatePeopleController addsopsdo_popup()"));

        People people = peopleService.findById(id);

        List<SpisokDejatel> ischisStajDop = new ArrayList<>();
        ischisStajDop.add(new SpisokDejatel(0l,""));
        ischisStajDop.addAll(spisokDejatelService.findAll());

        model.addAttribute("ischisStajDop", ischisStajDop);

        model.addAttribute("id", people.getId_people());
        return "popup/sopsdo_popup :: addsopsdo_popup";
    }
    @GetMapping("/kart_graj/addsopsposle_popup")
    public String addsopsposle_popup(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна после добавление UpdatePeopleController addsopsposle_popup()"));

        People people = peopleService.findById(id);
        model.addAttribute("id", people.getId_people());
        return "popup/sopsposle_popup :: addsopsposle_popup";
    }
    @GetMapping("/kart_graj/addstaj_popup")
    public String addstaj_popup(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна стаж добавление UpdatePeopleController addstaj_popup()"));

        People people = peopleService.findById(id);
        model.addAttribute("id", people.getId_people());
        return "popup/staj_popup :: addstaj_popup";
    }
    @GetMapping("/kart_graj/sved_zar_do_popup")
    public String sved_zar_do_popup(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"открытие модального окна сведения о заработке до UpdatePeopleController sved_zar_do_popup()"));

        SOPS_do sops_do = sops_doService.findById(id);
        model.addAttribute("id", sops_do.getId_sops_do());
        return "popup/sved_zar_do_popup :: sved_zar_do_popup";
    }


    //PLASE
    @GetMapping("/kart_graj/sops_do_place")
    public String sops_do_place(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController sops_do_place()"));

        People people = peopleService.findById(id);

        List<SpisokDejatel> spisokDejatels = new ArrayList<>();
        spisokDejatels.add(new SpisokDejatel(0l,""));
        spisokDejatels.addAll(spisokDejatelService.findAll());

        model.addAttribute("spisokDejatels", spisokDejatels);
        model.addAttribute("sops_do", people.getSops_dos());
        return "kart_graj/sops_do_fragment :: sops_do";
    }

    @GetMapping("/kart_graj/sops_posle_place")
    public String sops_posle_place(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController sops_posle_place()"));

        People people = peopleService.findById(id);

        model.addAttribute("sops_posle", people.getSops_posles());
        model.addAttribute("people", people);
        return "kart_graj/sops_posle_fragment :: sops_posle";
    }

    @GetMapping("/kart_graj/staj_place")
    public String staj_place(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController staj_place()"));

        People people = peopleService.findById(id);
        model.addAttribute("staj", people.getStajs());
        return "kart_graj/staj_fragment :: staj";
    }

    @GetMapping("/kart_graj/inoe_place")
    public String inoe_place(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController inoe_place()"));

        People people = peopleService.findById(id);
        model.addAttribute("inoe", people.getInoes());
        return "kart_graj/inoe_fragment :: inoe";
    }


    //загрузка из word
    @PostMapping(value = "/kart_graj/addSvedZarGodFromFile", produces = "text/plain")
    public @ResponseBody
    String addSvedZarGodFromFile(
            HttpServletResponse resp,
            HttpServletRequest req,
            @AuthenticationPrincipal User user,
            Model model) throws IOException {

        logiService.save(new Logi(new Date(),user.getLogin(),"загрузка из word UpdatePeopleController addSvedZarGodFromFile()"));

        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Long id = -1l;
        try {
            id = Long.valueOf(req.getParameter("id"));
        } catch (Exception e) {
        }
        Part filePart = null;
        try {
            filePart = req.getPart("file"); // Retrieves <input type="file" name="file">
        } catch (Exception e) {
        }

        FileInputStream fileContent = (FileInputStream) filePart.getInputStream();

        // открываем файл и считываем его содержимое в объект XWPFDocument
        XWPFDocument docxFile = null;
        try {
            docxFile = new XWPFDocument(OPCPackage.open(fileContent));
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        List<XWPFTable> xwpfTables = docxFile.getTables();
        int k = 0;
        for (int i = 0, max = -1; i < xwpfTables.size(); i++) {
            if(max<=xwpfTables.get(i).getRows().size()){
                max = xwpfTables.get(i).getRows().size();
                k=i;
            }
        }
        XWPFTable table = docxFile.getTables().get(k);; //получаю таблицу с ниибольшим количеством строк
//        XWPFTable table = null; //получаю первую и единственную таблицу
//        try{
//            table = docxFile.getTables().get(1);
//        }
//        catch (Exception e){
//            table = docxFile.getTables().get(0);//получаю первую и единственную таблицу
//        }


        int row = table.getRows().size();
        int col = table.getRow(0).getTableCells().size();

        SvedZar_do svedZar_do = svedZar_doService.findById(id);

        for (int i = 1; i < col; i++) {

            String god = table.getRow(0).getTableCells().get(i).getText();
/*            Pattern pattern = Pattern.compile("(.*)(\\d{4})(.*)");
            Matcher matcher = pattern.matcher(god);
            if  (matcher.find()) {
                god = matcher.group(2);
            }*/

            List<SvedZarMounth> svedZarMounths = new ArrayList<>();
            for (int j = 1, mounth = 1; j < row; j++, mounth++) {
                String sum = table.getRow(j).getTableCells().get(i).getText().replace(',','.').replace('-','.').trim();
                //Pattern pattern = Pattern.compile("^\\d+\\.?\\d*$");
                if(Pattern.matches("^\\d+\\.?\\d*$",sum)){
                    svedZarMounths.add(new SvedZarMounth(
                            String.valueOf(j-1),
                            sum
                    ));
                }
            }
            svedZarMounthService.saveall(svedZarMounths);

            double itogo = 0;
            try{
                for (SvedZarMounth s:
                        svedZarMounths) {
                    itogo += Double.valueOf(s.getSum());
                }
            }catch (Exception e){
                itogo = -1;
            }

            SvedZarGod svedZarGod = new SvedZarGod(god,String.valueOf(itogo),svedZarMounths);
            svedZarGodService.save(svedZarGod);
            svedZar_do.getZarGods().add(svedZarGod);
            svedZar_doService.save(svedZar_do);

        }

/*        for (int i = 0; i < table.getRows().size(); i++) {
            XWPFTableRow xwpfTableRow = table.getRow(i);
            XWPFTableCell cell = xwpfTableRow.getTableCells().size();

        }

        int s2 = table.getRows().size();
        int s1 = table.();*/

        return "OK";
    }

    @PostMapping("/kart_graj/sopstablekor")
    public @ResponseBody
    String sopstablekor(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "сl", defaultValue = "") String сl,
            @RequestParam(value = "ischeked", defaultValue = "") String ischekeds,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController sopstablekor()"));

        int ischeked = Integer.valueOf(ischekeds);

        try {
            SOPS_table sops_table = sops_tableService.findById(id);

            if(сl.equals("sopstablekorresh"))sops_table.setIsreshkor(ischeked);
            if(сl.equals("sopstablekorrzap"))sops_table.setIszapkor(ischeked);

            sops_tableService.save(sops_table);
        }catch (Exception e){
            return  "Что то пошло не так!";
        }
        return "success";
    }

    @PostMapping("/kart_graj/stajtablekor")
    public @ResponseBody
    String stajtablekor(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "сl", defaultValue = "") String сl,
            @RequestParam(value = "ischeked", defaultValue = "") String ischekeds,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController stajtablekor()"));

        int ischeked = Integer.valueOf(ischekeds);

        try {
            Staj_table staj_table = staj_tableService.findById(id);

            if(сl.equals("stajtablekorresh"))staj_table.setIsreshkor(ischeked);
            if(сl.equals("stajtablekorrzap"))staj_table.setIszapkor(ischeked);

            staj_tableService.save(staj_table);
        }catch (Exception e){
            return  "Что то пошло не так!";
        }
        return "success";
    }

    @PostMapping("/kart_graj/inoekor")
    public @ResponseBody
    String inoekor(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "сl", defaultValue = "") String сl,
            @RequestParam(value = "ischeked", defaultValue = "") String ischekeds,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController inoekor()"));

        int ischeked = Integer.valueOf(ischekeds);

        try {
            Inoe inoe = inoeService.findById(id);
            inoe.setIsreshkor(ischeked);
            inoeService.save(inoe);
        }catch (Exception e){
            return  "Что то пошло не так!";
        }
        return "success";
    }

    @PostMapping("/kart_graj/svedzargodkorresh")
    public @ResponseBody
    String svedzargodkorresh(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "сl", defaultValue = "") String сl,
            @RequestParam(value = "ischeked", defaultValue = "") String ischekeds,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController svedzargodkorresh()"));

        int ischeked = Integer.valueOf(ischekeds);

        try {
            SvedZarGod svedZarGod = svedZarGodService.findById(id);
            svedZarGod.setIsreshkor(ischeked);
            svedZarGodService.save(svedZarGod);
        }catch (Exception e){
            return  "Что то пошло не так!";
        }
        return "success";
    }

    @PostMapping("/kart_graj/svedzarposlekor")
    public @ResponseBody
    String svedzarposlekor(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "сl", defaultValue = "") String сl,
            @RequestParam(value = "ischeked", defaultValue = "") String ischekeds,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController svedzarposlekor()"));

        int ischeked = Integer.valueOf(ischekeds);

        try {
            SvedZar_posle svedZar_posle = svedZar_posleService.findById(id);

            if(сl.equals("svedzarposlekorresh"))svedZar_posle.setIsreshkor(ischeked);
            if(сl.equals("svedzarposlekorrzap"))svedZar_posle.setIszapkor(ischeked);

            svedZar_posleService.save(svedZar_posle);
        }catch (Exception e){
            return  "Что то пошло не так!";
        }
        return "success";
    }

    @GetMapping(
            value = "/kart_graj/shabresh",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] shabresh(
            @RequestParam(value = "id") Long idpeople,
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            Model model) throws IOException {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController shabresh()"));

        InputStream in = null;
        try {
            People people = peopleService.findById(idpeople);

            Shablon shablon = shablonService.findById(1l);

            InputStream inputStream = new ByteArrayInputStream(shablon.getDokument());

            XWPFDocument docxFile = null;
            docxFile = new XWPFDocument(inputStream);
            // открываем файл и считываем его содержимое в объект XWPFDocument

            reshReplace(docxFile.getParagraphs(), people, shablon);

            for (XWPFTable tbl : docxFile.getTables()) {
                tbl.getRows().forEach(pars -> {
                    reshReplace(pars.getCell(0).getParagraphs(), people, shablon);
                });
            }

            shablon.setNumber(shablon.getNumber()+1);
            shablonService.save(shablon);

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            docxFile.write(b);

            resp.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "resh.docx");
            resp.setHeader(headerKey, headerValue);
            resp.setContentLength(b.size());
            resp.getOutputStream().write(b.toByteArray());

            in = new ByteArrayInputStream(b.toByteArray());
        } catch (Exception e) {

        }
        return IOUtils.toByteArray(in);
    }

    private void reshReplace(List<XWPFParagraph> paragraphs,People people, Shablon shablon){
        for (XWPFParagraph p : paragraphs) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains("$")) {
                        text = text.replace("$1", DateFormat.tekDateRus());
                        text = text.replace("$2", String.valueOf(shablon.getNumber()));
                        text = text.replace("$3", people.getFIO());
                        text = text.replace("$4", people.getSnils());
                        text = text.replace("$5", people.getShablonRech());
                        r.setText(text, 0);
                    }
                    //System.out.println(text);
                }
            }
        }
    }

    private void zapReplace(List<XWPFParagraph> paragraphs,People people){
        for (XWPFParagraph p : paragraphs) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    System.out.println(text);
                    if (text != null && text.contains("$")) {
                        text = text.replace("$1", people.getUser().getRayon().getNamerayon());
                        text = text.replace("$2", people.getUser().getRayon().getRegnummru());
                        text = text.replace("$3", people.getRayon_kor().getNamerayon());
                        text = text.replace("$4", people.getRayon_kor().getRegnummru());
                        r.setText(text, 0);
                    }
                }
            }
        }
    }

    private void styleParagraph(XWPFParagraph paragraph, String s){
        XWPFRun run = paragraph.createRun();
        run.setFontSize(13);
        run.setBold(false);
        run.setText(s);
    }

    private void setBorders(XWPFTableCell cell){
        cell.getCTTc().addNewTcPr().addNewTcBorders().addNewRight().setVal(STBorder.SINGLE);
        cell.getCTTc().addNewTcPr().addNewTcBorders().addNewLeft().setVal(STBorder.SINGLE);
        cell.getCTTc().addNewTcPr().addNewTcBorders().addNewBottom().setVal(STBorder.SINGLE);
    }

    @GetMapping(
            value = "/kart_graj/shabzap",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] shabzap(
            @RequestParam(value = "id") Long idpeople,
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            Model model) throws IOException {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController shabzap()"));

        InputStream in = null;
        try {
            People people = peopleService.findById(idpeople);
            PeopleOld peopleOld = peopleOldService.findById(idpeople);

            Shablon shablon = shablonService.findById(2l);

            InputStream inputStream = new ByteArrayInputStream(shablon.getDokument());

            XWPFDocument docxFile = null;
            docxFile = new XWPFDocument(inputStream);
            // открываем файл и считываем его содержимое в объект XWPFDocument

            zapReplace(docxFile.getParagraphs(), people);


            XWPFTable zl_table = null;
            XWPFTable strah_table = null;
            int k = 0;
            for (XWPFTable tbl : docxFile.getTables()) {
                tbl.getRows().forEach(pars -> {
                    pars.getTableCells().forEach(c ->
                            zapReplace(c.getParagraphs(), people)
                            );
//                    zapReplace(pars.getCell(0).getParagraphs(), people);
//                    zapReplace(pars.getCell(2).getParagraphs(), people);
                });
                if(k==1){
                    zl_table = tbl;
                }
                if(k==2){
                    strah_table = tbl;
                }
                k++;
            }

            //пришлось создать новый документ для параграфов
            XWPFDocument document = new XWPFDocument();

            XWPFTableRow tableRowTwo = zl_table.createRow();
            XWPFTableCell cell = null;

            XWPFParagraph paragraph = document.createParagraph();
            styleParagraph(paragraph, people.getFam());
            cell = tableRowTwo.getCell(0);
            cell.setParagraph(paragraph);
            setBorders(cell);


            paragraph = document.createParagraph();
            styleParagraph(paragraph, people.getName());
            cell = tableRowTwo.getCell(1);
            cell.setParagraph(paragraph);
            setBorders(cell);

            paragraph = document.createParagraph();
            styleParagraph(paragraph, people.getOtch());
            cell = tableRowTwo.getCell(2);
            cell.setParagraph(paragraph);
            setBorders(cell);

            paragraph = document.createParagraph();
            styleParagraph(paragraph, people.getSnils());
            cell = tableRowTwo.getCell(3);
            cell.setParagraph(paragraph);
            setBorders(cell);

            paragraph = document.createParagraph();
            styleParagraph(paragraph, people.getDate_birthdayD());
            cell = tableRowTwo.getCell(4);
            cell.setParagraph(paragraph);
            setBorders(cell);


            //пришлось создать новый документ для параграфов
            XWPFDocument document2 = new XWPFDocument();
            XWPFParagraph paragraph2 = document2.createParagraph();

            for (SOPS_posle sp:
                 people.getSops_posles()) {

                if(sp.isSopsTablePosleKorZ()){
                    for (SOPS_table st:
                         sp.getSops_tables()) {
                        if(st.isZapkor()){

                            String vidZaprosa = ""; //Д – дополнение лицевого счета; У – уточнение индивидуальных сведений
                            String utochnijaemyeDannye = "";//ЗП – заработная плата, ОС – общий стаж, СС – специальный стаж; БС - без стажа


                            String s1 = "";
                            String s2 = "";

                            String sprom = (" " + peopleOld.zaprosUtochnenie(st, sp.getReg_num_pfr()));
                            if(sprom.trim().equals("")){
                                //sprom = (" " + peopleOld.zaprosCorrectirovka(st, sp.getReg_num_pfr()));
                                sprom = (" " + st.toStringZap());
                            }

                            s1 += (st.getPeriod_startD() + sprom);
                            s2 += (st.getPeriod_endD() + sprom);

                            XWPFTableRow tableRow = strah_table.createRow();
                            tableRow.addNewTableCell();
                            tableRow.addNewTableCell();
                            tableRow.addNewTableCell();

                            XWPFTableCell cell2 = null;

                            paragraph2 = document2.createParagraph();
                            styleParagraph(paragraph2, s1);
                            cell2 = tableRow.getCell(0);
                            cell2.setParagraph(paragraph2);
                            setBorders(cell2);

                            paragraph2 = document2.createParagraph();
                            styleParagraph(paragraph2, s2);
                            cell2 = tableRow.getCell(1);
                            cell2.setParagraph(paragraph2);
                            setBorders(cell2);

                            paragraph2 = document2.createParagraph();
                            styleParagraph(paragraph2, vidZaprosa);
                            cell2 = tableRow.getCell(2);
                            cell2.setParagraph(paragraph2);
                            setBorders(cell2);

                            paragraph2 = document2.createParagraph();
                            styleParagraph(paragraph2, utochnijaemyeDannye);
                            cell2 = tableRow.getCell(3);
                            cell2.setParagraph(paragraph2);
                            setBorders(cell2);

                            paragraph2 = document2.createParagraph();
                            styleParagraph(paragraph2, "");
                            cell2 = tableRow.getCell(4);
                            cell2.setParagraph(paragraph2);
                            setBorders(cell2);

                            paragraph2 = document2.createParagraph();
                            styleParagraph(paragraph2, sp.getName_org());
                            cell2 = tableRow.getCell(4);
                            cell2.setParagraph(paragraph2);
                            setBorders(cell2);

                            paragraph2 = document2.createParagraph();
                            styleParagraph(paragraph2, sp.getReg_num_pfr());
                            cell2 = tableRow.getCell(5);
                            cell2.setParagraph(paragraph2);
                            setBorders(cell2);

                            paragraph2 = document2.createParagraph();
                            styleParagraph(paragraph2, "");
                            cell2 = tableRow.getCell(6);
                            cell2.setParagraph(paragraph2);
                            setBorders(cell2);

                        }
                    }
                }
            }




            ByteArrayOutputStream b = new ByteArrayOutputStream();
            docxFile.write(b);

            resp.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "zap.docx");
            resp.setHeader(headerKey, headerValue);
            resp.setContentLength(b.size());
            resp.getOutputStream().write(b.toByteArray());

            in = new ByteArrayInputStream(b.toByteArray());
        } catch (Exception e) {

        }
        return IOUtils.toByteArray(in);
    }

    @PostMapping("/kart_graj/peopleold")
    public @ResponseBody //если изменения делаем копию
    String peopleold(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"UpdatePeopleController peopleold()"));

        People people = peopleService.findById(id);

            try{
                //peopleOldService.delete(id);
                PeopleOld peopleOld = peopleOldService.findById(id);
            }  catch (Exception e){
                PeopleOld peopleOld = new PeopleOld(people);
                peopleOldService.saveall(peopleOld);
            }

        return "";
    }

}
