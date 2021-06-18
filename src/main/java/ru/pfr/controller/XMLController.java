package ru.pfr.controller;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pfr.CreateFile.CreateFile;
import ru.pfr.model.*;
import ru.pfr.model.xlsx.DeregisteredEmployers;
import ru.pfr.repositories.PeopleRepository;
import ru.pfr.service.LogiService;
import ru.pfr.service.PeopleService;
import ru.pfr.service.SOPS.SOPS_posleService;
import ru.pfr.service.Staj.StajService;
import ru.pfr.service.count.KolfileService;
import ru.pfr.service.xlsx.DeregisteredEmployersService;
import ru.pfr.xmlcreator.XmlСreator;
import ru.pfr.xmlcreator.XmlСreator2;
import ru.pfr.xmlparser.XmlParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping(value = {"/kils/"})
public class XMLController {

    @Autowired
    PeopleService peopleService;

    @Autowired
    KolfileService kolfileService;

    @Autowired
    SOPS_posleService sops_posleService;

    @Autowired
    StajService stajService;

    @Autowired
    LogiService logiService;

    @Autowired
    DeregisteredEmployersService deregisteredEmployersService;

    //ЗАГРУЗКА
    @GetMapping("download_xml")
    public String download_xml(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Страница загрузки xml XMLController download_xml()"));

        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);

        model.addAttribute("user", user);
        return "download_xml";
    }

    //ЗАГРУЗКА добавить
    @PostMapping(value = "download_xml/addxml", produces = "text/plain")
    public @ResponseBody
    String addxml(
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            HttpServletRequest req,
            Model model) throws IOException {

        logiService.save(new Logi(new Date(),user.getLogin(),"Загрузка xml XMLController addxml()"));

        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Collection<Part> filePart = null;
        try {
            filePart = req.getParts();
        } catch (Exception e) {
        }

        int s = filePart.size();

        Map<String, Map<String, String>> mapFiles2 = new LinkedHashMap<>(); //для отправки ошибки

        for (Part fp :
                filePart) {

            boolean b = true;
            List<String> err;
            Map<String, String> map3 = new LinkedHashMap<>();
            try {
                FileInputStream fileContent =
                        fileContent = (FileInputStream) fp.getInputStream();
                XmlParser xmlParser = new XmlParser(fileContent);
                People people = xmlParser.getPeople(user);

                peopleService.saveall(people);

                //People people2 = peopleService.findById(people.getId_people()); //это для теста потом можно убрать

                System.out.println("asd");

            } catch (Exception e) {
                return "ERROR: Ошибка при загрузке файла!";
            }
        }

        return mapFiles2.toString();
    }

    @GetMapping("/find_kart_graj/downloadxml")
    public @ResponseBody
    byte[] downloadxml(
            @RequestParam(value = "idpeople", defaultValue = "") Long idpeople,
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            Model model) throws IOException {

        logiService.save(new Logi(new Date(),user.getLogin(),"Получить xml после регистрации XMLController downloadxml()"));

        InputStream in = null;
        People people = peopleService.findById(idpeople);

        CreateFile createFile = new CreateFile(1, user, kolfileService, null);
        new XmlСreator().createXml(createFile, people);

        //createFile.getResp(resp);

        return IOUtils.toByteArray(createFile.getInputStream(resp));
    }

    @GetMapping("/find_kart_graj/downloadxmlStaj")
    public @ResponseBody
    byte[] downloadxmlStaj(
            @RequestParam(value = "idpeople", defaultValue = "") Long idpeople,
            @RequestParam(value = "idst", defaultValue = "") Long idst,
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            Model model) throws IOException {

        logiService.save(new Logi(new Date(),user.getLogin(),"Получить xml после регистрации стаж XMLController downloadxmlStaj()"));

        InputStream in = null;

        People people = peopleService.findById(idpeople);

        Staj staj = stajService.findById(idst);
        //SOPS_posle sops_posle = sops_posleService.findById(idsp);

        CreateFile createFile = new CreateFile(2, user, kolfileService, staj.getReg_nom());
        //CreateFile createFile = new CreateFile(2, user, kolfileService, sops_posle.getReg_num_pfr());

        DeregisteredEmployers deregisteredEmployers = deregisteredEmployersService.findByRegNum(staj.getReg_nom());
        //DeregisteredEmployers deregisteredEmployers = deregisteredEmployersService.findByRegNum(sops_posle.getReg_num_pfr());
        if(deregisteredEmployers==null)deregisteredEmployers = new DeregisteredEmployers(staj.getReg_nom(), staj.getName(), "0000000000", "000000000");
        //if(deregisteredEmployers==null)deregisteredEmployers = new DeregisteredEmployers(sops_posle.getReg_num_pfr(), sops_posle.getName_org(), "0000000000", "000000000");

        new XmlСreator2().createXml(createFile, people, staj, deregisteredEmployers);

        return IOUtils.toByteArray(createFile.getInputStream(resp));
    }

    @GetMapping("/find_kart_graj/downloadxml2")
    public @ResponseBody
    byte[] downloadxml2(
            @RequestParam(value = "idpeople", defaultValue = "") Long idpeople,
            @RequestParam(value = "idsp", defaultValue = "") Long idsp,
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            Model model) throws IOException {

        logiService.save(new Logi(new Date(),user.getLogin(),"XMLController downloadxml2()"));

        InputStream in = null;

        People people = peopleService.findById(idpeople);
        SOPS_posle sops_posle = sops_posleService.findById(idsp);

        CreateFile createFile = new CreateFile(2, user, kolfileService, sops_posle.getReg_num_pfr());

        DeregisteredEmployers deregisteredEmployers = deregisteredEmployersService.findByRegNum(sops_posle.getReg_num_pfr());
        if(deregisteredEmployers==null)deregisteredEmployers = new DeregisteredEmployers(sops_posle.getReg_num_pfr(), sops_posle.getName_org(), "0000000000", "000000000");

        new XmlСreator2().createXml(createFile, people, sops_posle, deregisteredEmployers);

        return IOUtils.toByteArray(createFile.getInputStream(resp));
    }

    @GetMapping("/find_kart_graj/downloadxml2/getFilename")
    public @ResponseBody
    String getFilename(
            @RequestParam(value = "idpeople", defaultValue = "") Long idpeople,
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            Model model) throws IOException {

        logiService.save(new Logi(new Date(),user.getLogin(),"Вернуть имя файла XMLController getFilename()"));

        String path = "./xml/";

        // определяем объект для каталога
        File dir = new File(path);
        // если объект представляет каталог
        if(dir.isDirectory())
        {
            // получаем все вложенные объекты в каталоге
            for(File item : dir.listFiles()){
                if(item.isDirectory()){
                    //System.out.println(item.getName() + "  \t folder");
                }
                else{
                    if(item.getName().substring(0,3).equals("ПФР")){
                        return item.getName();
                    }
                    //System.out.println(item.getName() + "\t file");
                }
            }
        }

        return "nofile";
    }


    private List<String> roleList(){
        List<String> rl = new ArrayList<>();
        UsernamePasswordAuthenticationToken a = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        a.getAuthorities().forEach( g ->
                rl.add(g.toString()));
        return rl;
    }

}
