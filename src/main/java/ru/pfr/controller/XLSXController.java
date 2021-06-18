package ru.pfr.controller;


import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.pfr.model.Logi;
import ru.pfr.model.User;
import ru.pfr.model.xlsx.DeregisteredEmployers;
import ru.pfr.service.LogiService;
import ru.pfr.service.PeopleService;
import ru.pfr.service.count.KolfileService;
import ru.pfr.service.xlsx.DeregisteredEmployersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping(value = {"/kils/"})
public class XLSXController {

    @Autowired
    PeopleService peopleService;

    @Autowired
    DeregisteredEmployersService deregisteredEmployersService;

    @Autowired
    KolfileService kolfileService;

    @Autowired
    LogiService logiService;

    //ЗАГРУЗКА
    @GetMapping("download_xlsx")
    public String download_xlsx(
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(new Date(),user.getLogin(),"Страница загрузки xlsx XLSXController download_xlsx()"));

        model.addAttribute("user", user);
        List<String> roleList = roleList();
        model.addAttribute("roleList", roleList);
        return "download_xlsx";
    }

    //ЗАГРУЗКА добавить
    @PostMapping(value = "download_xlsx/addxlsx", produces = "text/plain")
    public @ResponseBody
    String addxlsx(
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            HttpServletRequest req,
            Model model) throws IOException {

        logiService.save(new Logi(new Date(),user.getLogin(),"Загрузка xlsx XLSXController addxlsx()"));

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

        for (Part fp :
                filePart) {
            try {
                FileInputStream fileContent = (FileInputStream) fp.getInputStream();

                XSSFWorkbook workbook = new XSSFWorkbook(fileContent);
                XSSFSheet sheet = workbook.getSheetAt(0);

                // получаем Iterator по всем строкам в листе
                Iterator<Row> it = sheet.iterator();
                if(it.hasNext())it.next(); // игнор заголовка
                //проходим по всему листу
                while (it.hasNext()) {
                    String regnum = "";
                    String name = "";
                    String inn = "";
                    String kpp = "";
                    Row row = it.next();

                    regnum = row.getCell(0).getStringCellValue();
                    name = row.getCell(1).getStringCellValue();
                    inn = row.getCell(2).getStringCellValue();
                    kpp = row.getCell(3).getStringCellValue();

                    DeregisteredEmployers d = new DeregisteredEmployers(regnum,name,inn,kpp);
                    deregisteredEmployersService.save(d);
                }
            } catch (Exception e) {
            }
        }
        return "";
    }

    private List<String> roleList(){
        List<String> rl = new ArrayList<>();
        UsernamePasswordAuthenticationToken a = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        a.getAuthorities().forEach( g ->
                rl.add(g.toString()));
        return rl;
    }

}
