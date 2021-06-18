package ru.pfr.xmlcreator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ru.pfr.CreateFile.CreateFile;
import ru.pfr.everything.DateFormat;
import ru.pfr.model.*;
import ru.pfr.model.xlsx.DeregisteredEmployers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.List;
import java.util.Optional;

public class XmlСreator2 {

    public XmlСreator2() {
    }

    private Element createEDPFR(Document doc){
        Element EDPFR =
                doc.createElementNS("http://пф.рф/СЗВ-КОРР/2020-08-10", "ЭДПФР");
        EDPFR.setAttribute("xmlns:АФ5", "http://пф.рф/АФ/2018-12-07");
        EDPFR.setAttribute("xmlns:ИС5", "http://пф.рф/ВС/ИС/2020-08-10");
        EDPFR.setAttribute("xmlns:УТ2", "http://пф.рф/УТ/2017-08-21");
        EDPFR.setAttribute("xmlns:ВС2", "http://пф.рф/ВС/типы/2017-10-23");
        EDPFR.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
        return EDPFR;
    }

    private Element createStrahovatel(Document doc, String rn, String inn, String kpp, String nameorg){
        Element strahovatel = doc.createElement("Страхователь");
        strahovatel.appendChild(getElement(doc, "УТ2:РегНомер", rn));
        strahovatel.appendChild(getElement(doc, "УТ2:ИНН", inn));
        strahovatel.appendChild(getElement(doc, "УТ2:КПП", kpp));
        strahovatel.appendChild(getElement(doc, "ИС5:Наименование", nameorg.toUpperCase()));
        return strahovatel;
    }

    public void createXml(CreateFile createFile, People people, Staj staj, DeregisteredEmployers de) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            // создаем пустой объект Document, в котором будем
            // создавать наш xml-файл
            Document doc = builder.newDocument();
            doc.setXmlStandalone(true);
            // создаем корневой элемент
            Element EDPFR = createEDPFR(doc);
            // добавляем корневой элемент в объект Document
            doc.appendChild(EDPFR);

            // добавляем дочерний элемент к корневому
            EDPFR.appendChild(getSlujInform(doc, createFile.getXmlName().substring(44, 80), "SPU_ORB 2.108"));

            Element odv1 = doc.createElement("ОДВ-1");
            {
                odv1.appendChild(getElement(doc, "Тип", "0")); // всегда
                {
                    Element strahovatel = createStrahovatel(doc,staj.getReg_nom(),de.getInn(),de.getKpp(),staj.getName());
/*                    Element strahovatel = doc.createElement("Страхователь");
                    strahovatel.appendChild(getElement(doc, "УТ2:РегНомер", sp.getReg_num_pfr()));
                    strahovatel.appendChild(getElement(doc, "УТ2:ИНН", de.getInn()));
                    strahovatel.appendChild(getElement(doc, "УТ2:КПП", de.getKpp()));
                    strahovatel.appendChild(getElement(doc, "ИС5:Наименование", sp.getName_org().toUpperCase()));*/
                    odv1.appendChild(strahovatel);
                }
                {
                    Element otper = doc.createElement("ОтчетныйПериод");
                    otper.appendChild(getElement(doc, "Код", "0")); //всегда
                    otper.appendChild(getElement(doc, "Год", String.valueOf(DateFormat.tekGod()))); //текущий год????????
                    odv1.appendChild(otper);
                }
                odv1.appendChild(getElement(doc, "КоличествоЗЛ", "1")); //непонятно!!!!!!!!!!!
                {
                    Element rucovoditel = doc.createElement("Руководитель"); //непонятно!!!!!!!!!!!
                    rucovoditel.appendChild(getElement(doc, "Должность", "-"));
                    {
                        Element fio = doc.createElement("ФИО");
                        fio.appendChild(getElement(doc, "УТ2:Фамилия", "-"));
                        rucovoditel.appendChild(fio);
                    }
                    odv1.appendChild(rucovoditel);
                }

                odv1.appendChild(getElement(doc, "ДатаЗаполнения", DateFormat.tekDateEng())); // возможно другой формат даты!!!
            }
            EDPFR.appendChild(odv1);
/*            List<SOPS_posle> sops_posles = people.getSops_posles();
            for (SOPS_posle sp:
                    sops_posles) {*/


            {
                for (Staj_table st :
                        staj.getStaj_tables()) {
                    if(st.isZapkor()){
//                        for (SvedZar_posle svedZar_posle:
//                                sp.getSvedZar_posles(st.getPeriod_start(),st.getPeriod_end())) {

                            Element szv_korr = doc.createElement("СЗВ-КОРР");
                            {//страхователь
                                Element strahovatel = doc.createElement("Страхователь");
                                strahovatel.appendChild(getElement(doc, "УТ2:РегНомер", staj.getReg_nom()));

                                strahovatel.appendChild(getElement(doc, "УТ2:ИНН", de.getInn()));
                                strahovatel.appendChild(getElement(doc, "УТ2:КПП", de.getKpp()));

                                strahovatel.appendChild(getElement(doc, "ИС5:Наименование", staj.getName().toUpperCase()));
                                szv_korr.appendChild(strahovatel);
                            }
                            {//ОтчетныйПериод
                                Element op = doc.createElement("ОтчетныйПериод");
                                op.appendChild(getElement(doc, "Код", "0")); //НЕПОНЯТНО!!!!!!!
                                op.appendChild(getElement(doc, "Год", String.valueOf(DateFormat.tekGod()))); //текущий год????????
                                szv_korr.appendChild(op);
                            }
                            szv_korr.appendChild(getElement(doc, "Тип", "0")); //НЕПОНЯТНО!!!!!!!

                            {//КорректируемыйПериод
                                Element kp = doc.createElement("КорректируемыйПериод");
                                {
                                    Element op = doc.createElement("ОтчетныйПериод");//непонятно!!!!!!!!!!!
                                    op.appendChild(getElement(doc, "Код", st.getKod()));//непонятно!!!!!!!!!!! ТУТ ФИГАЧИМ КОДЫ
                                    op.appendChild(getElement(doc, "Год", st.getGodStart()));
                                    kp.appendChild(op);
                                }
                                {
                                    Element s = doc.createElement("Страхователь");
                                    s.appendChild(getElement(doc, "УТ2:РегНомер", staj.getReg_nom()));
                                    s.appendChild(getElement(doc, "УТ2:ИНН", de.getInn()));
                                    s.appendChild(getElement(doc, "УТ2:КПП", de.getKpp()));
                                    kp.appendChild(s);
                                }
                                szv_korr.appendChild(kp);
                            }
                            {//ЗЛ
                                Element zl = doc.createElement("ЗЛ");
                                {//ФИО
                                    Element fio = doc.createElement("ФИО");
                                    fio.appendChild(getElement(doc, "УТ2:Фамилия", people.getFam().toUpperCase()));
                                    fio.appendChild(getElement(doc, "УТ2:Имя", people.getName().toUpperCase()));
                                    fio.appendChild(getElement(doc, "УТ2:Отчество", people.getOtch().toUpperCase()));
                                    zl.appendChild(fio);
                                }
                                zl.appendChild(getElement(doc, "СНИЛС", people.getSnils()));
                                szv_korr.appendChild(zl);
                            }

                            {//ДанныеЗЛ Уточнить!!!!
//                                Element dzl = doc.createElement("ДанныеЗЛ");
//                                dzl.appendChild(getElement(doc, "Категория", svedZar_posle.getKatZL())); //категория!!!
//                                szv_korr.appendChild(dzl);
                            }
                            {
                                //СтажевыйПериод
                                Element stajPeriod = doc.createElement("СтажевыйПериод");
                                Element period = doc.createElement("ИС5:Период");
                                period.appendChild(getElement(doc, "УТ2:С", st.getPeriod_s()));
                                period.appendChild(getElement(doc, "УТ2:По", st.getPeriod_po()));
                                stajPeriod.appendChild(period);

                                if (!Optional.ofNullable(st.getOsob_usl()).orElse("").trim().equals("") ||
                                        !Optional.ofNullable(st.getPoz_spis()).orElse("").trim().equals("")//!!!!
                                ) {
                                    Element lgotStaj = doc.createElement("ИС5:ЛьготныйСтаж");
                                    if (!Optional.ofNullable(st.getOsob_usl()).orElse("").trim().equals("")) {
                                        Element out = doc.createElement("ИС5:ОУТ");
                                        //out.appendChild(getElement(doc, "ИС5:Код", st.getOsob_uslovija()));
                                        out.appendChild(getElement(doc, "ИС5:ПозицияСписка", st.getPoz_spis()));
                                        lgotStaj.appendChild(out);
                                    }
                                    if (!Optional.ofNullable(st.getStrah_staj_dop_sved()).orElse("").trim().equals("")) {
                                        lgotStaj.appendChild(getElement(doc, "ИС5:ДопСведенияИС", st.getStrah_staj_dop_sved().toUpperCase()));//??
                                    }
                                    stajPeriod.appendChild(lgotStaj);
                                }
                                szv_korr.appendChild(stajPeriod);
                            }
                            EDPFR.appendChild(szv_korr);

                        }
                    }
                }
            //}

            //}

            doc.getDocumentElement().normalize();

            //создаем объект TransformerFactory для печати в консоль
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // для красивого вывода в консоль
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource domSource = new DOMSource(doc);
            //печатаем в файл
            StreamResult file = new StreamResult(createFile.getXml().getAbsoluteFile());
            //записываем данные
            transformer.transform(domSource, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createXml(CreateFile createFile, People people, SOPS_posle sp, DeregisteredEmployers de) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            // создаем пустой объект Document, в котором будем
            // создавать наш xml-файл
            Document doc = builder.newDocument();
            doc.setXmlStandalone(true);
            // создаем корневой элемент
            Element EDPFR = createEDPFR(doc);

            // добавляем корневой элемент в объект Document
            doc.appendChild(EDPFR);

            // добавляем дочерний элемент к корневому
            EDPFR.appendChild(getSlujInform(doc, createFile.getXmlName().substring(44, 80), "SPU_ORB 2.108"));

            Element odv1 = doc.createElement("ОДВ-1");
            {
                odv1.appendChild(getElement(doc, "Тип", "0")); // всегда
                {
                    Element strahovatel = createStrahovatel(doc,sp.getReg_num_pfr(),de.getInn(),de.getKpp(),sp.getName_org());
/*                    Element strahovatel = doc.createElement("Страхователь");
                    strahovatel.appendChild(getElement(doc, "УТ2:РегНомер", sp.getReg_num_pfr()));
                    strahovatel.appendChild(getElement(doc, "УТ2:ИНН", de.getInn()));
                    strahovatel.appendChild(getElement(doc, "УТ2:КПП", de.getKpp()));
                    strahovatel.appendChild(getElement(doc, "ИС5:Наименование", sp.getName_org().toUpperCase()));*/
                    odv1.appendChild(strahovatel);
                }
                {
                    Element otper = doc.createElement("ОтчетныйПериод");
                    otper.appendChild(getElement(doc, "Код", "0")); //всегда
                    otper.appendChild(getElement(doc, "Год", String.valueOf(DateFormat.tekGod()))); //текущий год????????
                    odv1.appendChild(otper);
                }
                odv1.appendChild(getElement(doc, "КоличествоЗЛ", "1")); //непонятно!!!!!!!!!!!
                {
                    Element rucovoditel = doc.createElement("Руководитель"); //непонятно!!!!!!!!!!!
                    rucovoditel.appendChild(getElement(doc, "Должность", "-"));
                    {
                        Element fio = doc.createElement("ФИО");
                        fio.appendChild(getElement(doc, "УТ2:Фамилия", "-"));
                        rucovoditel.appendChild(fio);
                    }
                    odv1.appendChild(rucovoditel);
                }

                odv1.appendChild(getElement(doc, "ДатаЗаполнения", DateFormat.tekDateEng())); // возможно другой формат даты!!!
            }
            EDPFR.appendChild(odv1);
/*            List<SOPS_posle> sops_posles = people.getSops_posles();
            for (SOPS_posle sp:
                    sops_posles) {*/


            {
                for (SOPS_table st :
                        sp.getSops_tables()) {
                    if(st.isZapkor()){ //if(st.isReshkor()){
                        for (SvedZar_posle svedZar_posle:
                        sp.getSvedZar_posles(st.getPeriod_start(),st.getPeriod_end())) {

                            Element szv_korr = doc.createElement("СЗВ-КОРР");
                            {//страхователь
                                Element strahovatel = doc.createElement("Страхователь");
                                strahovatel.appendChild(getElement(doc, "УТ2:РегНомер", sp.getReg_num_pfr()));

                                strahovatel.appendChild(getElement(doc, "УТ2:ИНН", de.getInn()));
                                strahovatel.appendChild(getElement(doc, "УТ2:КПП", de.getKpp()));

                                strahovatel.appendChild(getElement(doc, "ИС5:Наименование", sp.getName_org().toUpperCase()));
                                szv_korr.appendChild(strahovatel);
                            }
                            {//ОтчетныйПериод
                                Element op = doc.createElement("ОтчетныйПериод");
                                op.appendChild(getElement(doc, "Код", "0")); //НЕПОНЯТНО!!!!!!!
                                op.appendChild(getElement(doc, "Год", String.valueOf(DateFormat.tekGod()))); //текущий год????????
                                szv_korr.appendChild(op);
                            }
                            szv_korr.appendChild(getElement(doc, "Тип", "0")); //НЕПОНЯТНО!!!!!!!

                            {//КорректируемыйПериод
                                Element kp = doc.createElement("КорректируемыйПериод");
                                {
                                    Element op = doc.createElement("ОтчетныйПериод");//непонятно!!!!!!!!!!!
                                    op.appendChild(getElement(doc, "Код", st.getKod()));//непонятно!!!!!!!!!!! ТУТ ФИГАЧИМ КОДЫ
                                    op.appendChild(getElement(doc, "Год", svedZar_posle.getGod()));
                                    kp.appendChild(op);
                                }
                                {
                                    Element s = doc.createElement("Страхователь");
                                    s.appendChild(getElement(doc, "УТ2:РегНомер", sp.getReg_num_pfr()));
                                    s.appendChild(getElement(doc, "УТ2:ИНН", de.getInn()));
                                    s.appendChild(getElement(doc, "УТ2:КПП", de.getKpp()));
                                    kp.appendChild(s);
                                }
                                szv_korr.appendChild(kp);
                            }
                            {//ЗЛ
                                Element zl = doc.createElement("ЗЛ");
                                {//ФИО
                                    Element fio = doc.createElement("ФИО");
                                    fio.appendChild(getElement(doc, "УТ2:Фамилия", people.getFam().toUpperCase()));
                                    fio.appendChild(getElement(doc, "УТ2:Имя", people.getName().toUpperCase()));
                                    fio.appendChild(getElement(doc, "УТ2:Отчество", people.getOtch().toUpperCase()));
                                    zl.appendChild(fio);
                                }
                                zl.appendChild(getElement(doc, "СНИЛС", people.getSnils()));
                                szv_korr.appendChild(zl);
                            }

                            {//ДанныеЗЛ
                                Element dzl = doc.createElement("ДанныеЗЛ");
                                dzl.appendChild(getElement(doc, "Категория", svedZar_posle.getKatZL())); //категория!!!
                                szv_korr.appendChild(dzl);
                            }
                            {
                                //СтажевыйПериод
                                Element stajPeriod = doc.createElement("СтажевыйПериод");
                                Element period = doc.createElement("ИС5:Период");
                                period.appendChild(getElement(doc, "УТ2:С", st.getPeriod_start()));
                                period.appendChild(getElement(doc, "УТ2:По", st.getPeriod_end()));
                                stajPeriod.appendChild(period);

                                if (!Optional.ofNullable(st.getOsob_uslovija()).orElse("").trim().equals("") ||
                                        !Optional.ofNullable(st.getTrud_staj_dopsved()).orElse("").trim().equals("")
                                ) {
                                    Element lgotStaj = doc.createElement("ИС5:ЛьготныйСтаж");
                                    if (!Optional.ofNullable(st.getOsob_uslovija()).orElse("").trim().equals("")) {
                                        Element out = doc.createElement("ИС5:ОУТ");
                                        out.appendChild(getElement(doc, "ИС5:Код", st.getOsob_uslovija()));
                                        //out.appendChild(getElement(doc, "ИС5:ПозицияСписка", st.()));//???
                                        lgotStaj.appendChild(out);
                                    }
                                    if (!Optional.ofNullable(st.getTrud_staj_dopsved()).orElse("").trim().equals("")) {
                                        lgotStaj.appendChild(getElement(doc, "ИС5:ДопСведенияИС", st.getTrud_staj_dopsved().toUpperCase()));//??
                                    }
                                    stajPeriod.appendChild(lgotStaj);
                                }
                                szv_korr.appendChild(stajPeriod);
                            }
                            EDPFR.appendChild(szv_korr);

                        }
                    }
                }
            }

            //}

            doc.getDocumentElement().normalize();

            //создаем объект TransformerFactory для печати в консоль
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // для красивого вывода в консоль
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource domSource = new DOMSource(doc);
            //печатаем в файл
            StreamResult file = new StreamResult(createFile.getXml().getAbsoluteFile());
            //записываем данные
            transformer.transform(domSource, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ВсеПериодыРаботы
    private Node getVsePeriodyRaboty(Document doc, SOPS_do_doljnost sops_do_doljnost) {
        Element vsePeriodRaboty = doc.createElement("ВсеПериодыРаботы");

        vsePeriodRaboty.appendChild(getElement(doc, "Количество", String.valueOf("1")));
        Element periodRaboty = doc.createElement("ПериодРаботы");

        Element osnovnajaStroka = doc.createElement("ОсновнаяСтрока");
        osnovnajaStroka.appendChild(getElement(doc, "НомерСтроки", "1"));
        osnovnajaStroka.appendChild(getElement(doc, "ДатаНачалаПериода", DateFormat.DataEngToRus(sops_do_doljnost.getSops_tables().get(0).getPeriod_start())));
        osnovnajaStroka.appendChild(getElement(doc, "ДатаКонцаПериода", DateFormat.DataEngToRus(sops_do_doljnost.getSops_tables().get(0).getPeriod_end())));

        Element osobennostiUcheta = doc.createElement("ОсобенностиУчета");

        Element ter_uslovija = doc.createElement("ТерриториальныеУсловия");
        ter_uslovija.appendChild(getElement(doc, "ОснованиеТУ", sops_do_doljnost.getSops_tables().get(0).getTer_uslovija()));
        if (sops_do_doljnost.getSops_tables().get(0).getTer_uslovija() != null) {
            osobennostiUcheta.appendChild(ter_uslovija);
        }


        Element visluga_let = doc.createElement("ВыслугаЛет");
        visluga_let.appendChild(getElement(doc, "ОснованиеВЛ", sops_do_doljnost.getSops_tables().get(0).getVis_let_osn()));
        if (sops_do_doljnost.getSops_tables().get(0).getVis_let_dopsved() != null) {
            visluga_let.appendChild(getElement(doc, "ДоляСтавки", sops_do_doljnost.getSops_tables().get(0).getVis_let_dopsved()));
        }
        if (sops_do_doljnost.getSops_tables().get(0).getVis_let_osn() != null) {
            osobennostiUcheta.appendChild(visluga_let);
        }

        osobennostiUcheta.appendChild(getElement(doc, "ПрофессияДолжность", sops_do_doljnost.getDoljnost()));
        osobennostiUcheta.appendChild(getElement(doc, "ОтметкаОценщика", ""));
        osnovnajaStroka.appendChild(osobennostiUcheta);

        periodRaboty.appendChild(osnovnajaStroka);

        vsePeriodRaboty.appendChild(periodRaboty);
        return vsePeriodRaboty;
    }

    // ОрганизацияПредставившаяСЗВ
    private Node getOrganizacijaPredostavSvedenija(Document doc, String nameOrg, String regNom) {
        Element fio = doc.createElement("ОрганизацияПредставившаяСЗВ-К");
        fio.appendChild(getElement(doc, "НаименованиеОрганизации", nameOrg));
        fio.appendChild(getElement(doc, "РегистрационныйНомер", regNom));
        return fio;
    }

    // метод для создания нового узла XML-файла getFIO
    private Node getFIO(Document doc, String fam, String name, String otch) {
        Element fio = doc.createElement("ФИО");
        fio.appendChild(getElement(doc, "Фамилия", fam));
        fio.appendChild(getElement(doc, "Имя", name));
        fio.appendChild(getElement(doc, "Отчество", otch));
        return fio;
    }

    // метод для создания нового узла XML-файла getZagolovokFile
    private Node getSlujInform(Document doc, String GUID, String programmPodgotovki) {
        Element slujInform = doc.createElement("СлужебнаяИнформация");

        slujInform.appendChild(getElement(doc, "АФ5:GUID", GUID));

        slujInform.appendChild(getElement(doc, "АФ5:ДатаВремя", DateFormat.tekDatexml2()));

        slujInform.appendChild(getElement(doc, "АФ5:ПрограммаПодготовки", programmPodgotovki));

        return slujInform;
    }

    // утилитный метод для создание нового узла XML-файла
    private Node getElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }



}
