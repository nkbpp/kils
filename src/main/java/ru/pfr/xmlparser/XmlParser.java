package ru.pfr.xmlparser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.pfr.model.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class XmlParser {

    private Document document;

    public XmlParser(File file) throws FileNotFoundException {
        start(new FileInputStream(file));
    }

    public XmlParser(InputStream inputStream) {
        start(inputStream);
    }

    private void start(InputStream fis) {
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            document = documentBuilder.parse(fis);
        } catch (Exception e) {
            //Поймать ошибки
        }
    }

    public People getPeople(User user) {
        People people = new People();

        // Получение списка всех элементов ns2:ИнойПериод внутри корневого элемента (getDocumentElement возвращает ROOT элемент XML файла).
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("ns2:ЗЛ");
        NodeList ZL = nodeList.item(0).getChildNodes();

        for (int i = 0; i < ZL.getLength(); i++) {
            Node ZLnode = ZL.item(i);
            switch (ZLnode.getNodeName()) {
                case "Пол":{
                    people.setPol(ZLnode.getTextContent().equals("")?"":
                        (ZLnode.getTextContent().contains("М") || ZLnode.getTextContent().contains("м"))?"Мужской":
                            (ZLnode.getTextContent().contains("ж") || ZLnode.getTextContent().contains("Ж") ||
                                    ZLnode.getTextContent().contains("Н") || ZLnode.getTextContent().contains("н"))? "Женский" : "");
                }   break;
                case "ДатаРождения":
                    people.setDate_birthday(ZLnode.getTextContent());
                    break;
                case "СНИЛС":
                    people.setSnils(ZLnode.getTextContent());
                    break;
                case "ns2:ДатаРегистрации":
                    people.setDate_reg(ZLnode.getTextContent());
                    break;
                case "ФИО": {
                    NodeList fio = ZLnode.getChildNodes();
                    for (int j = 0; j < fio.getLength(); j++) {
                        Node fionode = fio.item(j);
                        switch (fionode.getNodeName()) {
                            case "Фамилия":
                                people.setFam(fionode.getTextContent());
                                break;
                            case "Имя":
                                people.setName(fionode.getTextContent());
                                break;
                            case "Отчество":
                                people.setOtch(fionode.getTextContent());
                                break;
                        }
                    }
                } break;
            }
        }

        //-------------------
        NodeList pravovajaOcenka = document.getDocumentElement().getElementsByTagName("ns2:ПравоваяОценка");
        NodeList PO = pravovajaOcenka.item(0).getChildNodes();
        for (int i = 0; i < PO.getLength(); i++) {
            Node employee = PO.item(i);
            switch (employee.getNodeName()) {
                case "ns2:НаименованиеТОПФР":
                    people.setNameTOPFR(employee.getTextContent().trim());
                    break;

                case "ns2:Акт": {
                    NodeList act = employee.getChildNodes();
                    for (int j = 0; j < act.getLength(); j++) {
                        Node actnode = act.item(j);
                        switch (actnode.getNodeName()) {
                            case "Дата":
                                people.setDataAct(actnode.getTextContent().trim());
                                break;
                            case "Номер":
                                people.setNomAct(actnode.getTextContent().trim());
                                break;
                        }
                    }
                } break;
            }
        }


        people.setInoes(getInoes());
        people.setSops_dos(getSOPS_dos());
        people.setSops_posles(getSOPS_posles());
        people.setStajs(getStajs());
        people.setUser(user);//добавить пользователя
        people.setRayon_kor(user.getRayon());//район по умолчанию
        people.setData_create(new Date());
        return people;
    }

    public List<Inoe> getInoes() {
        List<Inoe> inoes = new ArrayList<>();

        // Получение списка всех элементов ns2:ИнойПериод внутри корневого элемента (getDocumentElement возвращает ROOT элемент XML файла).
        NodeList inoeElements = document.getDocumentElement().getElementsByTagName("ns2:ИнойПериод");

        // Перебор всех элементов employee
        for (int i = 0; i < inoeElements.getLength(); i++) {
            Inoe inoe = new Inoe();
            Node employee = inoeElements.item(i);
            NodeList books = employee.getChildNodes();
            for (int j = 0; j < books.getLength(); j++) {
                Node inojPeriod = books.item(j);
                switch (inojPeriod.getNodeName()) {
                    case "ns2:Код":
                        inoe.setInoe_period(inojPeriod.getTextContent());
                        break;
                    case "ns2:Период": {
                        NodeList period = inojPeriod.getChildNodes();
                        for (int k = 0; k < period.getLength(); k++) {
                            Node node = period.item(k);
                            switch (node.getNodeName()) {
                                case "С":
                                    inoe.setPeriod_start(node.getTextContent());
                                    break;
                                case "По":
                                    inoe.setPeriod_end(node.getTextContent());
                                    break;
                            }
                        }
                    } break;
                    case "ns2:ТО-ПФР": {
                        NodeList topfr = inojPeriod.getChildNodes();
                        for (int k = 0; k < topfr.getLength(); k++) {
                            Node node = topfr.item(k);
                            switch (node.getNodeName()) {
                                case "ns2:НаименованиеКраткое":
                                    inoe.setName(node.getTextContent());
                                    break;
                                case "ns2:РегНомер":
                                    inoe.setReg_num(node.getTextContent());
                                    break;
                            }
                        }
                    }
                    break;
                    case "ns2:Уход": {
                        NodeList uhod = inojPeriod.getChildNodes();
                        for (int k = 0; k < uhod.getLength(); k++) {
                            Node node = uhod.item(k);
                            switch (node.getNodeName()) {
                                case "СНИЛС":
                                    inoe.setSnils(node.getTextContent());
                                    break;
                                case "ДатаРождения":
                                    inoe.setData_roj(node.getTextContent());
                                    break;
                            }
                        }
                    }
                    break;
                }
            }
            inoes.add(inoe);
        }
        return inoes;
    }

    public List<Staj> getStajs() {

        List<Staj> stajs = new ArrayList<>();

        // Получение списка всех элементов ns2:ИнойПериод внутри корневого элемента
        // (getDocumentElement возвращает ROOT элемент XML файла).
        //данные из таблиц сверху чтобы взять ДатаСнятияСУчета по ИдентификаторДокумента
        NodeList svs = document.getDocumentElement().getElementsByTagName("ns2:СВ");
        Map<String,String> mapsv = new HashMap<>();
        for (int i = 0; i < svs.getLength(); i++) {
            Node sv = svs.item(i);
            NodeList zapiss = sv.getChildNodes();
            for (int j = 0; j < zapiss.getLength(); j++) { //проход по всем записям
                Node zapis = zapiss.item(j);
                switch (zapis.getNodeName()) {
                    case "ns2:Запись": {
                        NodeList zapischildren = zapis.getChildNodes();
                        for (int k = 0; k < zapischildren.getLength(); k++) {
                            Node zapischild = zapischildren.item(k);
                            ArrayList<String> keys = new  ArrayList<String>();
                            //String key = "";
                            String value = "";
                            switch (zapischild.getNodeName()) {
                                case "ns2:ИдентификаторДокумента": {
                                     keys.addAll(Arrays.asList(zapischild.getTextContent().split(",")));
                                } break;
                                case "ns2:Страхователь": {
                                    NodeList strah = zapischild.getChildNodes();
                                    for (int l = 0; l < strah.getLength(); l++) {
                                        Node strahchild = zapischildren.item(l);
                                        switch (strahchild.getNodeName()) {
                                            case "ns2:ДатаСнятияСУчета": {
                                                value = strahchild.getTextContent();
                                            } break;
                                        }
                                    }
                                } break;
                            }
                            for (String key:
                                 keys) {
                                mapsv.put(key,value);
                            }

                        }
                    } break;
                }
            }
        }

        // Получение списка всех элементов ns2:ИнойПериод внутри корневого элемента (getDocumentElement возвращает ROOT элемент XML файла).
        NodeList nodeListStajs = document.getDocumentElement().getElementsByTagName("ns2:Стаж");

        for (int i = 0; i < nodeListStajs.getLength(); i++) {
            Staj staj = new Staj();
            List<Staj_table> staj_tables = new ArrayList<>();
            Node nodeListStaj = nodeListStajs.item(i);
            NodeList nodeListStajChildren = nodeListStaj.getChildNodes();
            for (int j = 0; j < nodeListStajChildren.getLength(); j++) {
                Node nodeListStajChild =  nodeListStajChildren.item(j);
                switch (nodeListStajChild.getNodeName()) {
                    case "ns2:Страхователь": {
                        NodeList nodeList = nodeListStajChild.getChildNodes();
                        for (int k = 0; k < nodeList.getLength(); k++) {
                            Node node =  nodeList.item(k);
                            switch (node.getNodeName()) {
                                case "ns2:Наименование": {
                                    staj.setName(node.getTextContent());
                                } break;
                                case "ns2:РегНомер": {
                                    staj.setReg_nom(node.getTextContent());
                                } break;
                            }
                        }
                    } break;
                    case "ns2:СтажевыйПериод": {
                        NodeList stalPeriods = nodeListStajChild.getChildNodes();
                        Staj_table staj_table = new Staj_table();
                        for (int k = 0; k < stalPeriods.getLength(); k++) {
                            Node stalPeriod =  stalPeriods.item(k);
                            switch (stalPeriod.getNodeName()) {
                                case "ns2:Период": {
                                    NodeList period = stalPeriod.getChildNodes();
                                    for (int l = 0; l < period.getLength(); l++) {
                                        Node periodchild = period.item(l);
                                        switch (periodchild.getNodeName()) {
                                            case "С":
                                                staj_table.setPeriod_s(periodchild.getTextContent());
                                                break;
                                            case "По":
                                                staj_table.setPeriod_po(periodchild.getTextContent());
                                                break;
                                        }
                                    }
                                } break;
                                case "ns2:ЛьготныйСтаж": {
                                    NodeList nodeList = stalPeriod.getChildNodes();
                                    for (int l = 0; l < nodeList.getLength(); l++) {
                                        Node node = nodeList.item(l);
                                        switch (node.getNodeName()) {
                                            case "ns2:ДопСведенияИС": //ЛьготныйСтаж ДопСведенияИС
                                                staj_table.setStrah_staj_dop_sved(node.getTextContent());
                                                break;
                                            case "ns2:ТУ": { //Территориальные условия (код)
                                                NodeList tys = node.getChildNodes();
                                                for (int m = 0; m < tys.getLength(); m++) {
                                                    Node ty = tys.item(m);
                                                    switch (ty.getNodeName()) {
                                                        case "ns2:Основание":
                                                            staj_table.setTer_usl(ty.getTextContent());
                                                            break;
                                                        case "ns2:Коэффициент":
                                                            staj_table.setTer_usl_koeff(ty.getTextContent());
                                                            break;
                                                    }
                                                }
                                            } break;
                                            case "ns2:ОУТ": { //Особые условия труда (код)
                                                NodeList tys = node.getChildNodes();
                                                for (int m = 0; m < tys.getLength(); m++) {
                                                    Node ty = tys.item(m);
                                                    switch (ty.getNodeName()) {
                                                        case "ns2:Код":
                                                            staj_table.setOsob_usl(ty.getTextContent());
                                                            break;
                                                        case "ns2:ПозицияСписка":
                                                            staj_table.setPoz_spis(ty.getTextContent());
                                                            break;
                                                    }
                                                }
                                            } break;
                                            case "ns2:ВЛ": { //Условия для досрочного назначения страховой пенсии
                                                NodeList tys = node.getChildNodes();
                                                for (int m = 0; m < tys.getLength(); m++) {
                                                    Node ty = tys.item(m);
                                                    switch (ty.getNodeName()) {
                                                        case "ns2:Основание": //Основание
                                                            staj_table.setDosr_naz_strah_pens_osn(ty.getTextContent());
                                                            break;
                                                        case "ns2:ДоляСтавки": // Дополнительные сведения
                                                            staj_table.setDosr_naz_strah_pens_dop_sved(ty.getTextContent());
                                                            break;
                                                    }
                                                }
                                            } break;
                                        }
                                    }
                                } break;
                                case "ns2:ИдентификаторДокумента": {
                                    String key = stalPeriod.getTextContent();
                                    staj_table.setDataSniatia(mapsv.get(key));
                                } break;
                            }
                        }
                        staj_tables.add(staj_table);
                    } break;
                }
            }
            staj.setStaj_tables(staj_tables);
            stajs.add(staj);
        }
        return stajs;
    }

    public List<SOPS_posle> getSOPS_posles() {

        List<SOPS_posle> sops_posles = new ArrayList<>();

        // Получение списка всех элементов ns2:ИнойПериод внутри корневого элемента (getDocumentElement возвращает ROOT элемент XML файла).
        NodeList posleregs = document.getDocumentElement().getElementsByTagName("ns2:СведенияПослеРегистрации");

        // Перебор всех элементов employee
        for (int i = 0; i < posleregs.getLength(); i++) {
            List<SvedZar_posle> svedZar_posles = new ArrayList<>();
            List<SOPS_table> sops_tables = new ArrayList<>();
            SOPS_posle sops_posle = new SOPS_posle();
            Node poslereg = posleregs.item(i);
            NodeList posleregchilds = poslereg.getChildNodes();
            for (int j = 0; j < posleregchilds.getLength(); j++) {
                Node posleregchild = posleregchilds.item(j);
                switch (posleregchild.getNodeName()) {
                    case "ns2:СтраховательПредставившийСведения": sops_posle.setStrahovatel(posleregchild.getTextContent()); break;
                    case "ns2:Страхователь": {
                        NodeList nodeList = posleregchild.getChildNodes();
                        for (int k = 0; k < nodeList.getLength(); k++) {
                            Node periodchild = nodeList.item(k);
                            switch (periodchild.getNodeName()) {
                                case "ns2:Наименование":
                                    sops_posle.setName_org(periodchild.getTextContent());
                                    break;
                                case "ns2:РегНомер":
                                    sops_posle.setReg_num_pfr(periodchild.getTextContent());
                                    break;
                            }
                        }
                    } break;
                    case "ns2:СтажевыйПериод": {
                        sops_tables.add(sops_table(posleregchild.getChildNodes()));
                    } break;
                    case "ns2:СведенияОЗаработке": {
                        svedZar_posles.add(svedZar_posle(posleregchild.getChildNodes()));
                    } break;
                }
            }
            sops_posle.setSvedZar_posles(svedZar_posles);
            sops_posle.setSops_tables(sops_tables);
            sops_posles.add(sops_posle);
        }

        return sops_posles;
    }

    public List<SOPS_do> getSOPS_dos() {

        List<SOPS_do> sops_dos = new ArrayList<>();

        NodeList doregs = document.getDocumentElement().getElementsByTagName("ns2:СведенияДоРегистрации");

        // Перебор всех элементов employee
        for (int i = 0; i < doregs.getLength(); i++) {
            List<SvedZar_do> svedZar_dos = new ArrayList<>();
            //List<SOPS_table> sops_tables = new ArrayList<>();
            List<SOPS_do_doljnost> sops_do_doljnosts = new ArrayList<>();
            SOPS_do sops_do = new SOPS_do();
            Node doreg = doregs.item(i);
            NodeList doregshilds = doreg.getChildNodes();
            for (int j = 0; j < doregshilds.getLength(); j++) {
                Node doregshild = doregshilds.item(j);
                switch (doregshild.getNodeName()) {
                    case "ns2:Организация": sops_do.setName_org(doregshild.getTextContent()); break;
                    case "ns2:ВидДеятельности": sops_do.setVid_d(doregshild.getTextContent()); break;
                    case "ns2:СтажевыйПериод": {
                        sops_do_doljnosts.add(sops_do_doljnost(doregshild.getChildNodes()));
                    } break;
                    case "ns2:СведенияОЗаработке": {
                        svedZar_dos.add(svedZar_do(doregshild.getChildNodes()));
                    } break;
                }
            }
            sops_do.setSvedZar_dos(svedZar_dos);
            sops_do.setSops_do_doljnosts(sops_do_doljnosts);
            sops_dos.add(sops_do);
        }

        return sops_dos;
    }


    public SOPS_do_doljnost sops_do_doljnost(NodeList nodes) {

        SOPS_do_doljnost sops_do_doljnost = new SOPS_do_doljnost();

        List<SOPS_table> sops_tables = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            switch (node.getNodeName()) {
                case "ns2:ПрофессияДолжность": sops_do_doljnost.setDoljnost(node.getTextContent()); break;
            }
        }

        sops_tables.add(sops_table(nodes));
        sops_do_doljnost.setSops_tables(sops_tables);

        return sops_do_doljnost;
    }


    public SOPS_table sops_table(NodeList nodes) {
        SOPS_table sops_table = new SOPS_table();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            switch (node.getNodeName()) {
                case "ns2:Период": {
                    NodeList period = node.getChildNodes();
                    for (int j = 0; j < period.getLength(); j++) {
                        Node periodchild = period.item(j);
                        switch (periodchild.getNodeName()) {
                            case "С": //начало периода
                                sops_table.setPeriod_start(periodchild.getTextContent());
                                break;
                            case "По": // Конец периода
                                sops_table.setPeriod_end(periodchild.getTextContent());
                                break;
                        }
                    }
                } break;
                case "ns2:ЛьготныйСтаж": {
                    NodeList period = node.getChildNodes();
                    for (int j = 0; j < period.getLength(); j++) {
                        Node periodchild = period.item(j);
                        switch (periodchild.getNodeName()) {
                            case "ns2:ДопСведенияИС": {
                                sops_table.setTrud_staj_dopsved(periodchild.getTextContent());
                            } break;
                            case "ns2:ТУ": { // Территориальные условия (код)
                                NodeList osnows = periodchild.getChildNodes();
                                for (int k = 0; k < osnows.getLength(); k++) {
                                    Node osnow = osnows.item(k);
                                    switch (osnow.getNodeName()) {
                                        case "ns2:Основание": {
                                            sops_table.setTer_uslovija(osnow.getTextContent());
                                        } break;
                                        case "ns2:Коэффициент": {
                                            sops_table.setTer_uslovija_koef(osnow.getTextContent());
                                        } break;
                                    }
                                }
                            } break;
                            case "ns2:ВЛ": { //Выслуга лет
                                NodeList osnows = periodchild.getChildNodes();
                                for (int k = 0; k < osnows.getLength(); k++) {
                                    Node osnow = osnows.item(k);
                                    switch (osnow.getNodeName()) {
                                        case "ns2:Основание": { // основание (код)
                                            sops_table.setVis_let_osn(osnow.getTextContent());
                                        } break;
                                        case "ns2:ДоляСтавки": { //дополнительные сведения
                                            sops_table.setVis_let_dopsved(osnow.getTextContent());
                                        } break;
                                    }
                                }
                            } break;
                            case "ns2:ОУТ": { // Особые условия труда (код)
                                NodeList osnows = periodchild.getChildNodes();
                                for (int k = 0; k < osnows.getLength(); k++) {
                                    Node osnow = osnows.item(k);
                                    switch (osnow.getNodeName()) {
                                        case "ns2:Код": {
                                            sops_table.setOsob_uslovija(osnow.getTextContent());
                                        } break;
                                        case "ns2:ПозицияСписка": {
                                            String d = osnow.getTextContent();
                                            sops_table.setPoz_spis(osnow.getTextContent());
                                        } break;
                                    }
                                }
                            } break;
                        }
                    }
                } break;
            }
        }
        return sops_table;
    }

    public String sops_doljnost(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            switch (node.getNodeName()) {
                case "ns2:ПрофессияДолжность": return node.getTextContent();
            }
        }
        return "";
    }

    public SvedZar_posle svedZar_posle(NodeList nodes) {

        SvedZar_posle svedZar_posle = new SvedZar_posle();

        List<SvedZarMounth> svedZarMounths = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            switch (node.getNodeName()) {
                case "ns2:КатегорияЗЛ": {
                    svedZar_posle.setKatZL(node.getTextContent());
                } break;
                case "ns2:Итого": {
                    svedZar_posle.setItogo(node.getTextContent());
                } break;
                case "ns2:ОтчетныйПериод": {
                    NodeList period = node.getChildNodes();
                    for (int j = 0; j < period.getLength(); j++) {
                        Node periodchild = period.item(j);
                        switch (periodchild.getNodeName()) {
                            case "ns2:Код":
                                svedZar_posle.setGod(periodchild.getTextContent());
                                break;
                            case "ns2:Год":
                                svedZar_posle.setGod(periodchild.getTextContent());
                                break;
                        }
                    }
                } break;
                case "ns2:ЗаМесяц": {
                    svedZarMounths.add(svedZarMounth(node.getChildNodes()));
                } break;
            }
            svedZar_posle.setSvedZarMounths(svedZarMounths);
        }
        return svedZar_posle;
    }

    public SvedZar_do svedZar_do(NodeList nodes) {

        SvedZar_do svedZar_do = new SvedZar_do();

        List<SvedZarGod> svedZarGods = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            switch (node.getNodeName()) {
                case "ns2:РеквизитыСправки": {
                    NodeList recvizit = node.getChildNodes();
                    for (int j = 0; j < recvizit.getLength(); j++) {
                        Node recvizitchild = recvizit.item(j);
                        switch (recvizitchild.getNodeName()) {
                            case "ns2:Дата":
                                svedZar_do.setData_sprav(recvizitchild.getTextContent());
                                break;
                            case "ns2:Номер":
                                svedZar_do.setNom_sprav(recvizitchild.getTextContent());
                                break;
                        }
                    }
                } break;
                case "ns2:Период": {
                    NodeList period = node.getChildNodes();
                    for (int j = 0; j < period.getLength(); j++) {
                        Node periodchild = period.item(j);
                        switch (periodchild.getNodeName()) {
                            case "С":
                                svedZar_do.setPeriod_s(periodchild.getTextContent());
                                break;
                            case "По":
                                svedZar_do.setPeriod_po(periodchild.getTextContent());
                                break;
                        }
                    }
                } break;
                case "ns2:ЗаГод": {
                    svedZarGods.add(svedZarGod(node.getChildNodes()));
                } break;
            }

        }
        svedZar_do.setZarGods(svedZarGods);
        return svedZar_do;
    }

    public SvedZarGod svedZarGod(NodeList nodes) {

        SvedZarGod svedZarGod = new SvedZarGod();

        List<SvedZarMounth> svedZarMounths = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);

            switch (node.getNodeName()) {
                case "ns2:Год": svedZarGod.setGod(node.getTextContent()); break;
                case "ns2:Итого": svedZarGod.setItogo(node.getTextContent()); break;
                case "ns2:ЗаМесяц": {
                    svedZarMounths.add(svedZarMounth(node.getChildNodes()));
                } break;
            }
            svedZarGod.setSvedZarMounths(svedZarMounths);
        }

        return svedZarGod;
    }

    public SvedZarMounth svedZarMounth(NodeList nodes) {

        SvedZarMounth svedZarMounth = new SvedZarMounth();

        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);

            switch (node.getNodeName()) {
                case "ns2:Месяц": svedZarMounth.setMounth(node.getTextContent()); break;
                case "ns2:Сумма": svedZarMounth.setSum(node.getTextContent()); break;
            }
        }

        return svedZarMounth;
    }

}
