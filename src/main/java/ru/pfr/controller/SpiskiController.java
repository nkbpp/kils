package ru.pfr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.model.Logi;
import ru.pfr.model.User;
import ru.pfr.model.spiski.*;
import ru.pfr.service.LogiService;
import ru.pfr.service.spiski.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = {"/kils/spisok/"})
public class SpiskiController {

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
    LogiService logiService;

    //Открытие
    @GetMapping("ter_usl")
    public String ter_usl(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Страница территориальные условия  SpiskiController ter_usl()"));

        model.addAttribute("user", user);
        List<SpisokTerUsl> spisok = spisokTerUslService.findAll();
        model.addAttribute("spisok", spisok);
        model.addAttribute("type", "ter_usl");
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);
        return "spisok/spisok";
    }

    @GetMapping("rai_koef")
    public String rai_koef(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Страница Районный коэффициент  SpiskiController rai_koef()"));

        model.addAttribute("user", user);
        List<SpisokRaiKoef> spisok = spisokRaiKoefService.findAll();
        model.addAttribute("spisok", spisok);
        model.addAttribute("type", "rai_koef");
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);
        return "spisok/spisok";
    }

    @GetMapping("osob_usl_tr")
    public String osob_usl_tr(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Страница Особые условия труда  SpiskiController osob_usl_tr()"));

        model.addAttribute("user", user);
        List<SpisokOsobUslTr> spisok = spisokOsobUslTrService.findAll();
        model.addAttribute("spisok", spisok);
        model.addAttribute("type", "osob_usl_tr");
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);
        return "spisok/spisok";
    }

    @GetMapping("ischis_staj")
    public String ischis_staj(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Страница ИСЧИСЛЕНИЕ СТАЖА  SpiskiController ischis_staj()"));

        model.addAttribute("user", user);
        List<SpisokIschisStaj> spisok = spisokIschisStajService.findAll();
        model.addAttribute("spisok", spisok);
        model.addAttribute("type", "ischis_staj");
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);
        return "spisok/spisok";
    }

    @GetMapping("vis_let")
    public String vis_let(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Страница ВЫСЛУГА ЛЕТ  SpiskiController vis_let()"));

        model.addAttribute("user", user);
        List<SpisokVisLet> spisok = spisokVisLetService.findAll();
        model.addAttribute("spisok", spisok);
        model.addAttribute("type", "vis_let");
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);
        return "spisok/spisok";
    }

    @GetMapping("dejatel")
    public String dejatel(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Страница ДЕЯТЕЛЬНОСТЬ SpiskiController dejatel()"));

        model.addAttribute("user", user);
        List<SpisokDejatel> spisok = spisokDejatelService.findAll();
        model.addAttribute("spisok", spisok);
        model.addAttribute("type", "dejatel");
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);
        return "spisok/spisok";
    }

    @GetMapping("decret_deti")
    public String decret_deti(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Страница ДЕКРЕТ-ДЕТИ SpiskiController decret_deti()"));

        model.addAttribute("user", user);
        List<SpisokDecretDeti> spisok = spisokDecretDetiService.findAll();
        model.addAttribute("spisok", spisok);
        model.addAttribute("type", "decret_deti");
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);
        return "spisok/spisok";
    }

    @GetMapping("/add")
    public String spisokadd(
            @RequestParam String type,
            @RequestParam String value,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Добавление списка SpiskiController spisokadd()"));

        try {
            switch (type) {
                case "ter_usl": {
                    SpisokTerUsl spisokTerUsl = new SpisokTerUsl(value);
                    spisokTerUslService.save(spisokTerUsl);
                    List<SpisokTerUsl> spisok = spisokTerUslService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "ter_usl");
                }break;
                case "rai_koef": {
                    SpisokRaiKoef spisokRaiKoef = new SpisokRaiKoef(value);
                    spisokRaiKoefService.save(spisokRaiKoef);
                    List<SpisokRaiKoef> spisok = spisokRaiKoefService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "rai_koef");
                }break;
                case "osob_usl_tr": {
                    SpisokOsobUslTr spisokOsobUslTr = new SpisokOsobUslTr(value);
                    spisokOsobUslTrService.save(spisokOsobUslTr);
                    List<SpisokOsobUslTr> spisok = spisokOsobUslTrService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "osob_usl_tr");
                }break;
                case "ischis_staj": {
                    SpisokIschisStaj spisokIschisStaj = new SpisokIschisStaj(value);
                    spisokIschisStajService.save(spisokIschisStaj);
                    List<SpisokIschisStaj> spisok = spisokIschisStajService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "ischis_staj");
                }break;
                case "vis_let": {
                    SpisokVisLet spisokVisLet = new SpisokVisLet(value);
                    spisokVisLetService.save(spisokVisLet);
                    List<SpisokVisLet> spisok = spisokVisLetService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "vis_let");
                }break;
                case "dejatel": {
                    SpisokDejatel spisokDejatel = new SpisokDejatel(value);
                    spisokDejatelService.save(spisokDejatel);
                    List<SpisokDejatel> spisok = spisokDejatelService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "dejatel");
                }break;
                case "decret_deti": {
                    SpisokDecretDeti spisokDecretDeti = new SpisokDecretDeti(value);
                    spisokDecretDetiService.save(spisokDecretDeti);
                    List<SpisokDecretDeti> spisok = spisokDecretDetiService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "decret_deti");
                }break;
            }
        } catch (Exception e) {
        }

        model.addAttribute("user", user);
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);
        return "spisok/spisok";
    }

    @GetMapping("/del")
    public String spisokdel(
            @RequestParam Long id,
            @RequestParam String type,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Удаление списка SpiskiController spisokdel()"));

        try {
            switch (type) {
                case "ter_usl": {
                    spisokTerUslService.delete(id);
                    List<SpisokTerUsl> spisok = spisokTerUslService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "ter_usl");
                }
                break;
                case "rai_koef": {
                    spisokRaiKoefService.delete(id);
                    List<SpisokRaiKoef> spisok = spisokRaiKoefService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "rai_koef");
                }
                break;
                case "osob_usl_tr": {
                    spisokOsobUslTrService.delete(id);
                    List<SpisokOsobUslTr> spisok = spisokOsobUslTrService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "osob_usl_tr");
                }
                break;
                case "ischis_staj": {
                    spisokIschisStajService.delete(id);
                    List<SpisokIschisStaj> spisok = spisokIschisStajService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "ischis_staj");
                }
                break;
                case "vis_let": {
                    spisokVisLetService.delete(id);
                    List<SpisokVisLet> spisok = spisokVisLetService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "vis_let");
                }
                break;
                case "dejatel": {
                    spisokDejatelService.delete(id);
                    List<SpisokDejatel> spisok = spisokDejatelService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "dejatel");
                }
                break;
                case "decret_deti": {
                    spisokDecretDetiService.delete(id);
                    List<SpisokDecretDeti> spisok = spisokDecretDetiService.findAll();
                    model.addAttribute("spisok", spisok);
                    model.addAttribute("type", "decret_deti");
                }
                break;
            }
        } catch (Exception e) {
        }

        model.addAttribute("user", user);
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);
        return "spisok/spisok";
    }


    private List<String> roleList(){
        List<String> rl = new ArrayList<>();
        UsernamePasswordAuthenticationToken a = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        a.getAuthorities().forEach( g ->
                rl.add(g.toString()));
        return rl;
    }

}
