package ru.pfr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pfr.model.Logi;
import ru.pfr.model.People;
import ru.pfr.model.Rayon;
import ru.pfr.model.User;
import ru.pfr.model.spiski.SpisokDejatel;
import ru.pfr.service.LogiService;
import ru.pfr.service.PeopleService;
import ru.pfr.service.User.RayonService;
import ru.pfr.service.spiski.SpisokDejatelService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = {"/", "/kils/", "/KILS/"})
public class MainController {

    @Autowired
    PeopleService peopleService;

    @Autowired
    SpisokDejatelService spisokDejatelService;

    @Autowired
    RayonService rayonService;

    @Autowired
    LogiService logiService;

    @RequestMapping
    public String mains(
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"Авторизация прошла успешно MainController mains()"));
        //logger.info("User = " + user.getLogin() + " Авторизация прошла успешно MainController mains()");
        if (user.getRayon().getKod().equals("000"))
            return "redirect:/kils/upfr_menu";
        else if (user.getRayon().getKod().equals("999"))
            return "redirect:/kils/admin";
        else if (user.getRayon().getKod().equals("001") ||
                user.getRayon().getKod().equals("002") ||
                user.getRayon().getKod().equals("003") ||
                user.getRayon().getKod().equals("004") ||
                user.getRayon().getKod().equals("005") ||
                user.getRayon().getKod().equals("006") ||
                user.getRayon().getKod().equals("007") ||
                user.getRayon().getKod().equals("009") ||
                user.getRayon().getKod().equals("013") ||
                user.getRayon().getKod().equals("014") ||
                user.getRayon().getKod().equals("015") ||
                user.getRayon().getKod().equals("016") ||
                user.getRayon().getKod().equals("017") ||
                user.getRayon().getKod().equals("018") ||
                user.getRayon().getKod().equals("019") ||
                user.getRayon().getKod().equals("020") ||
                user.getRayon().getKod().equals("021") ||
                user.getRayon().getKod().equals("022") ||
                user.getRayon().getKod().equals("023") ||
                user.getRayon().getKod().equals("024") ||
                user.getRayon().getKod().equals("025") ||
                user.getRayon().getKod().equals("026") ||
                user.getRayon().getKod().equals("027"))
            return "redirect:/kils/upfr_menu";
        return "/kils";
    }

    @GetMapping("upfr_menu")
    public String upfr_menu(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Вывод меню MainController upfr_menu()"));

        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);

        model.addAttribute("user", user);
        return "upfr_menu";
    }

    @GetMapping("find_kart_graj")
    public String find_kart_graj(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Вывод всех граждан MainController find_kart_graj()"));

        List<People> people = peopleService.findByNameAndSnils(user.getRayon().getKod()); //все люди из этого района
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);

        //people = peopleService.peopleRoleDownload(people,roleList); // только статус 1 если роль Download

/*        if (roleList.indexOf(ROLE_ENUM.ROLE_DOWNLOAD.getString()) != -1 &&
                roleList.indexOf(ROLE_ENUM.ROLE_UPDATE.getString()) == -1) {*/
            //people = peopleService.peopleUserKodEqualsRayonKod(people, user); //код района совподает с кодом пользователя
        //}

        model.addAttribute("people", people);
        model.addAttribute("user", user);
        List<Rayon> rayon = rayonService.findAllMRU();
        model.addAttribute("rayon", rayon);

        return "find_kart_graj";
    }

    @GetMapping("find_kart_graj_another_ray")
    public String find_kart_graj_another_ray(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Вывод всех граждан из другиз районов MainController find_kart_graj()"));

        List<People> people = peopleService.findByNameAndSnils(user.getRayon().getKod()); //все люди из этого района

        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);

        people = peopleService.peopleRoleDownload(people, roleList); // только статус 1 если роль Download

        people = peopleService.peopleUserKodNotEqualsRayonKod(people, user);

        model.addAttribute("people", people);
        model.addAttribute("user", user);
        List<Rayon> rayon = rayonService.findAllMRU();
        model.addAttribute("rayon", rayon);

        return "find_kart_graj";
    }


    @GetMapping("find_kart_graj_fragment")
    public String find_kart_graj_fragment(
            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "fam_rod", defaultValue = "") String fam_rod,
            @RequestParam(value = "snils_rod", defaultValue = "") String snils_rod,
            @RequestParam(value = "hreff", defaultValue = "") String hreff,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Поиск граждан MainController find_kart_graj_fragment()"));

        List<People> people = peopleService.findByNameAndSnils(fam_rod, snils_rod, list, user.getRayon().getKod()); //все люди из этого района

        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);

        if (hreff.indexOf("find_kart_graj_another_ray") == -1) { // не отсюда
/*            if (roleList.indexOf(ROLE_ENUM.ROLE_DOWNLOAD.getString()) != -1 &&
                    roleList.indexOf(ROLE_ENUM.ROLE_UPDATE.getString()) == -1)*/
                //people = peopleService.peopleUserKodEqualsRayonKod(people, user);//код района совподает с кодом пользователя
        } else {

            people = peopleService.peopleRoleDownload(people, roleList); // только статус 1 если роль Download

/*            if (roleList.indexOf(ROLE_ENUM.ROLE_DOWNLOAD.getString()) != -1 &&
                    roleList.indexOf(ROLE_ENUM.ROLE_UPDATE.getString()) == -1)*/
                people = peopleService.peopleUserKodNotEqualsRayonKod(people, user); //код района не совподает с кодом пользователя
        }


        model.addAttribute("people", people);
        model.addAttribute("user", user);
        List<Rayon> rayon = rayonService.findAllMRU();
        model.addAttribute("rayon", rayon);

        return "fragment/find_kart_graj_fragment";
    }

    @GetMapping("get_kart_graj")
    public String get_kart_graj(
            @RequestParam(value = "id") Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Получить гражданина по id="+id+" MainController get_kart_graj()"));

        People people;
        if (id == -1) {
            people = new People();
        } else {
            people = peopleService.findById(id);
        }

        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);

        List<SpisokDejatel> spisokDejatels = new ArrayList<>();
        spisokDejatels.add(new SpisokDejatel(0l, ""));
        spisokDejatels.addAll(spisokDejatelService.findAll());

        model.addAttribute("spisokDejatels", spisokDejatels);
        model.addAttribute("people", people);
        model.addAttribute("user", user);

        List<Rayon> rayon = rayonService.findAllMRU();
        model.addAttribute("rayon", rayon);


        return "kart_graj/kart_graj";
    }


    @PostMapping("/kart_graj/peopleupdatestatus")
    public @ResponseBody
    String peopleupdatestatus(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "idray", defaultValue = "") Long idray,
            @RequestParam(value = "hreff", defaultValue = "") String hreff,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"peopleupdatestatus()"));

        String message = "Что то пошло не так!";
        try {
            People people = peopleService.findById(id);

            if (hreff.indexOf("find_kart_graj_another_ray") != -1)  {
                people.setStatus(2); //может и 3 надо
            } else {
                Rayon rayon = rayonService.findById(idray);
                people.setRayon_kor(rayon);
                people.setStatus(people.getStatus()+1);
            }

            peopleService.save(people);

            message = String.valueOf(people.getId_people());

            List<Rayon> rayon = rayonService.findAllMRU();
            model.addAttribute("rayon", rayon);
            List<String> roleList = roleList();
            model.addAttribute("roleList", roleList);
        } catch (Exception e) {
            message = "Что то пошло не так!";
        }
        return message;
    }

    @PostMapping("/kart_graj/peoplebackstatus")
    public @ResponseBody
    String peoplebackstatus(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "idray", defaultValue = "") Long idray,
            @RequestParam(value = "hreff", defaultValue = "") String hreff,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"peoplebackstatus()"));

        String message = "Что то пошло не так!";
        try {
            People people = peopleService.findById(id);
            people.setStatus(people.getStatus()-1);
            peopleService.save(people);

            message = String.valueOf(people.getId_people());

            List<Rayon> rayon = rayonService.findAllMRU();
            model.addAttribute("rayon", rayon);
            List<String> roleList = roleList();
            model.addAttribute("roleList", roleList);
        } catch (Exception e) {
            message = "Что то пошло не так!";
        }
        return message;
    }


    private List<String> roleList() {
        List<String> rl = new ArrayList<>();
        UsernamePasswordAuthenticationToken a = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        a.getAuthorities().forEach(g ->
                rl.add(g.toString()));
        return rl;
    }

}
