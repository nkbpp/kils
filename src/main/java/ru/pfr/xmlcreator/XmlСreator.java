package ru.pfr.xmlcreator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ru.pfr.CreateFile.CreateFile;
import ru.pfr.everything.DateFormat;
import ru.pfr.model.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.pfr.everything.DateFormat.DataEngToRus;

public class XmlСreator {

    public XmlСreator() {
    }

    public void createXml(CreateFile createFile, People people) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            // создаем пустой объект Document, в котором будем
            // создавать наш xml-файл
            Document doc = builder.newDocument();
            doc.setXmlStandalone(true);
            // создаем корневой элемент
            Element filePFR =
                    doc.createElementNS("http://schema.pfr.ru", "ФайлПФР");
            // добавляем корневой элемент в объект Document
            doc.appendChild(filePFR);

            // добавляем дочерний элемент к корневому
            filePFR.appendChild(getElement(doc, "ИмяФайла", createFile.getXmlName()));

            filePFR.appendChild(getZagolovokFile(doc, "07.00", "ВНЕШНИЙ", "КОНВЕРТАЦИЯ", "4.9", "ПФР"));

            Element pachkaVhodDocumentov = doc.createElement("ПачкаВходящихДокументов");
            pachkaVhodDocumentov.setAttribute("Окружение", "В составе файла");
            pachkaVhodDocumentov.setAttribute("Стадия", "До обработки");

            //ВХОДЯЩАЯ_ОПИСЬ
            Element vhodOpic = doc.createElement("ВХОДЯЩАЯ_ОПИСЬ");
            vhodOpic.appendChild(getElement(doc, "НомерВпачке", "1"));//верно
            vhodOpic.appendChild(getElement(doc, "ТипВходящейОписи", "ОПИСЬ ПАЧКИ"));
            Element sostavitelPachki = doc.createElement("СоставительПачки");
            Element nalogNom = doc.createElement("НалоговыйНомер");
            nalogNom.appendChild(getElement(doc, "ИНН", people.getUser().getRayon().getInn()));
            nalogNom.appendChild(getElement(doc, "КПП", people.getUser().getRayon().getKpp()));
            sostavitelPachki.appendChild(nalogNom);
            sostavitelPachki.appendChild(getElement(doc, "НаименованиеОрганизации", people.getUser().getRayon().getNamerayon().toUpperCase()));
            sostavitelPachki.appendChild(getElement(doc, "РегистрационныйНомер", people.getUser().getRayon().getRegnummru()));
            vhodOpic.appendChild(sostavitelPachki);
            Element nomPachki = doc.createElement("НомерПачки");
            nomPachki.appendChild(getElement(doc, "Основной", createFile.getXmlName().substring(38, 43)));
            vhodOpic.appendChild(nomPachki);

            Element sostavDoc = doc.createElement("СоставДокументов");
            sostavDoc.appendChild(getElement(doc, "Количество", "1"));//верно
            Element nalDoc = doc.createElement("НаличиеДокументов");
            nalDoc.appendChild(getElement(doc, "ТипДокумента", "КОНВЕРТАЦИЯ"));
            nalDoc.appendChild(getElement(doc, "Количество", "1"));//верно
            sostavDoc.appendChild(nalDoc);
            vhodOpic.appendChild(sostavDoc);

            vhodOpic.appendChild(getElement(doc, "ДатаСоставления", DateFormat.tekDateRus()));
            pachkaVhodDocumentov.appendChild(vhodOpic);


            Element konvertacija = doc.createElement("КОНВЕРТАЦИЯ");
            konvertacija.appendChild(getElement(doc, "НомерВпачке", "2"));//верно
            konvertacija.appendChild(getElement(doc, "ВидФормы", "СЗВ-К+"));
            konvertacija.appendChild(getElement(doc, "ТипСведений", "КОРР"));//"КОРРЕКТИРУЮЩАЯ"));// //ИСХОДНАЯ, КОРРЕКТИРУЮЩАЯ, ОТМЕНЯЮЩАЯ
            konvertacija.appendChild(getElement(doc, "СтраховойНомер", people.getSnils()));
            //ФИО
            konvertacija.appendChild(getFIO(doc, people.getFam().toUpperCase(), people.getName().toUpperCase(), people.getOtch().toUpperCase()));
            konvertacija.appendChild(getElement(doc, "ДатаРождения", DataEngToRus(people.getDate_birthday())));
            //ОрганизацияПредставившаяСЗВ //уточнить точно эти данные?!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            konvertacija.appendChild(getOrganizacijaPredostavSvedenija(doc, people.getUser().getRayon().getNamerayon().toUpperCase(), people.getUser().getRayon().getRegnummru()));
            konvertacija.appendChild(getElement(doc, "ДатаЗаполнения", DateFormat.tekDateRus()));
            Element stajDoReg = doc.createElement("СтажДоРегистрации");
            List<SOPS_do> sops_dos = people.getSops_dos();
            stajDoReg.appendChild(getElement(doc, "Количество", String.valueOf(people.getKolSops_do_doljnost_tables()))); // от количества организаций
            for (SOPS_do sd :
                    sops_dos) {

                for (SOPS_do_doljnost sops_do_doljnost :
                        sd.getSops_do_doljnosts()) {
                    Element periodDejatelnosti = doc.createElement("ПериодДеятельности");
                        periodDejatelnosti.appendChild(getElement(doc, "ВидДеятельности", sd.getVid_d().toUpperCase().trim()));
                    periodDejatelnosti.appendChild(getElement(doc, "НаименованиеОрганизации", sd.getName_org().toUpperCase()));


                    boolean b = true;
                    for (SvedZar_do svedZar_do :
                            sd.getSvedZar_dos()) {
                        if (svedZar_do.getPeriod_s().equals(sops_do_doljnost.getSops_tables().get(0).getPeriod_start()) &&
                                svedZar_do.getPeriod_po().equals(sops_do_doljnost.getSops_tables().get(0).getPeriod_end())) {
                            b = false;
                            Element zarabotokZaPeriod = doc.createElement("ЗаработокЗаПериод");
                            zarabotokZaPeriod.appendChild(getElement(doc, "Количество", "1"));

                            Element spravkaOZarabotke = doc.createElement("СправкаОзаработке");
                            spravkaOZarabotke.appendChild(getElement(doc, "ДатаВыдачиСправки", DataEngToRus(svedZar_do.getData_sprav())));
                            spravkaOZarabotke.appendChild(getElement(doc, "НомерСправкиОзаработке", svedZar_do.getNom_sprav()));
                            spravkaOZarabotke.appendChild(getElement(doc, "ДатаНачалаПериода", DataEngToRus(svedZar_do.getPeriod_s())));
                            spravkaOZarabotke.appendChild(getElement(doc, "ДатаКонцаПериода", DataEngToRus(svedZar_do.getPeriod_po())));

                            int kol = 0;
                            for (SvedZarGod svedZarGod :
                                    svedZar_do.getZarGods()) {
                                for (SvedZarMounth svedZarMounth :
                                        svedZarGod.getSvedZarMounths()) {
                                    if (svedZarMounth.getSum().equals("0") || svedZarMounth.getSum().equals("0.00")) {
                                    } else {
                                        kol++;
                                    }
                                }
                                kol++;
                            }
                            spravkaOZarabotke.appendChild(getElement(doc, "Количество", String.valueOf(kol)));

                            for (SvedZarGod svedZarGod :
                                    svedZar_do.getZarGods()) {
                                for (SvedZarMounth svedZarMounth :
                                        svedZarGod.getSvedZarMounths()) {
                                    if (svedZarMounth.getSum().equals("0") || svedZarMounth.getSum().equals("0.00")) {
                                    } else {
                                        Element zarabotoc = doc.createElement("Заработок");
                                        zarabotoc.appendChild(getElement(doc, "ТипСтроки", "МЕСЦ"));
                                        zarabotoc.appendChild(getElement(doc, "Год", svedZarGod.getGod()));
                                        zarabotoc.appendChild(getElement(doc, "Месяц", svedZarMounth.getMounth()));
                                        zarabotoc.appendChild(getElement(doc, "НачисленоВсего", svedZarMounth.getSum()));
                                        spravkaOZarabotke.appendChild(zarabotoc);
                                    }
                                }
                                Element zarabotoc = doc.createElement("Заработок");
                                zarabotoc.appendChild(getElement(doc, "ТипСтроки", "ИТОГ"));
                                zarabotoc.appendChild(getElement(doc, "Год", svedZarGod.getGod()));
                                zarabotoc.appendChild(getElement(doc, "НачисленоВсего", svedZarGod.getItogo()));
                                spravkaOZarabotke.appendChild(zarabotoc);
                            }

                            zarabotokZaPeriod.appendChild(spravkaOZarabotke);
                            periodDejatelnosti.appendChild(zarabotokZaPeriod);
                            periodDejatelnosti.appendChild(getVsePeriodyRaboty(doc, sops_do_doljnost,sd.getVid_d().toUpperCase().trim()));

                            stajDoReg.appendChild(periodDejatelnosti);
                        }
                    }
                    if (b) {
                        periodDejatelnosti.appendChild(getVsePeriodyRaboty(doc, sops_do_doljnost,sd.getVid_d().toUpperCase().trim()));
                        stajDoReg.appendChild(periodDejatelnosti);
                    }

                }

            }

            konvertacija.appendChild(stajDoReg);
            Element tabNom = doc.createElement("ТабельныйНомерОценщика");
            {
                tabNom.appendChild(getElement(doc, "КодПодразделения", "041"+people.getUser().getRayon().getKod()));
            }
            konvertacija.appendChild(tabNom);

            Element fioOcen = doc.createElement("ФИОоценщика");
            {
                fioOcen.appendChild(getElement(doc, "Фамилия", people.getFam()));
                fioOcen.appendChild(getElement(doc, "Имя", people.getName()));
                fioOcen.appendChild(getElement(doc, "Отчество", people.getOtch()));
            }
            konvertacija.appendChild(fioOcen);

            Element svedObAct = doc.createElement("СведенияОбАкте");
            {
                svedObAct.appendChild(getElement(doc, "НаименованиеТО",  people.getUser().getRayon().getNamerayon().toUpperCase()));//people.getNameTOPFR()));
                svedObAct.appendChild(getElement(doc, "РегистрационныйНомерТО", people.getUser().getRayon().getRegnummru()));//"041" + people.getUser().getRayon().getKod()));
                svedObAct.appendChild(getElement(doc, "ДатаАкта", DataEngToRus(people.getDataAct())));
                svedObAct.appendChild(getElement(doc, "НомерАкта", people.getNomAct()));
                svedObAct.appendChild(getElement(doc, "КодОснованияЗавершенияПравовойОбработки", "ПОЛНЫЙ ОБЪЕМ"));
                svedObAct.appendChild(getElement(doc, "НаименованиеОснованияЗавершенияПравовойОбработки", "ПРАВОВАЯ ОБРАБОТКА ЗАВЕРШЕНА В ПОЛНОМ ОБЪЁМЕ"));
            }
            konvertacija.appendChild(svedObAct);

            //добавить данные в конвертацию
            pachkaVhodDocumentov.appendChild(konvertacija);

            filePFR.appendChild(pachkaVhodDocumentov);


            doc.getDocumentElement().normalize();

            //создаем объект TransformerFactory для печати в консоль
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // для красивого вывода в консоль
            transformer.setOutputProperty(OutputKeys.ENCODING, "cp1251");
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
    private Node getVsePeriodyRaboty(Document doc, SOPS_do_doljnost sops_do_doljnost,String vid_d) {
        Element vsePeriodRaboty = doc.createElement("ВсеПериодыРаботы");

        vsePeriodRaboty.appendChild(getElement(doc, "Количество", "1")); // если один период в одной организации!!!
        Element periodRaboty = doc.createElement("ПериодРаботы");

        Element osnovnajaStroka = doc.createElement("ОсновнаяСтрока");
        osnovnajaStroka.appendChild(getElement(doc, "НомерСтроки", "1")); // если один период в одной организации!!!
        osnovnajaStroka.appendChild(getElement(doc, "ДатаНачалаПериода", DataEngToRus(sops_do_doljnost.getSops_tables().get(0).getPeriod_start())));
        osnovnajaStroka.appendChild(getElement(doc, "ДатаКонцаПериода", DataEngToRus(sops_do_doljnost.getSops_tables().get(0).getPeriod_end())));

/*            boolean b = Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getTer_uslovija()).orElse("").trim().equals("");
            boolean b2 = Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getVis_let_osn()).orElse("").trim().equals("");
            boolean b3 = Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getVis_let_dopsved()).orElse("").trim().equals("");
            boolean b4 = Optional.ofNullable(sops_do_doljnost.getDoljnost()).orElse("").trim().equals("");*/

        if (!Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getTer_uslovija()).orElse("").trim().equals("") ||
                !Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getVis_let_osn()).orElse("").trim().equals("") ||
                !Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getVis_let_dopsved()).orElse("").trim().equals("") ||
                !Optional.ofNullable(sops_do_doljnost.getDoljnost()).orElse("").trim().equals("")
        ) {

            String s = "РАБОТА, СЛУЖБА, ДВСТО, ТДКРЫМ, ДОГКРЫМ, ИПКРЫМ, РАБЗАГР";
            //System.out.println("vd= " + vid_d + " str = " + s + " b = " + (s.indexOf(vid_d.toUpperCase())!=-1));
            if (s.indexOf(vid_d.toUpperCase())!=-1) {
                Element osobennostiUcheta = doc.createElement("ОсобенностиУчета");

                if (!Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getTer_uslovija()).orElse("").trim().equals("")) {
                    Element ter_uslovija = doc.createElement("ТерриториальныеУсловия");
                    ter_uslovija.appendChild(getElement(doc, "ОснованиеТУ", sops_do_doljnost.getSops_tables().get(0).getTer_uslovija().toUpperCase()));
                    osobennostiUcheta.appendChild(ter_uslovija);
                }
                if (!Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getVis_let_osn()).orElse("").trim().equals("") ||
                        !Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getVis_let_dopsved()).orElse("").trim().equals("")
                ) {
                    Element visluga_let = doc.createElement("ВыслугаЛет");
                    if (!Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getVis_let_osn()).orElse("").trim().equals("")) {
                        visluga_let.appendChild(getElement(doc, "ОснованиеВЛ", sops_do_doljnost.getSops_tables().get(0).getVis_let_osn().toUpperCase()));
                    }
                    if (!Optional.ofNullable(sops_do_doljnost.getSops_tables().get(0).getVis_let_dopsved()).orElse("").trim().equals("")) {
                        visluga_let.appendChild(getElement(doc, "ДоляСтавки", sops_do_doljnost.getSops_tables().get(0).getVis_let_dopsved().toUpperCase()));
                    }
                    osobennostiUcheta.appendChild(visluga_let);
                }
                if (!Optional.ofNullable(sops_do_doljnost.getDoljnost()).orElse("").trim().equals("")) {
                    osobennostiUcheta.appendChild(getElement(doc, "ПрофессияДолжность", sops_do_doljnost.getDoljnost().toUpperCase()));
                }
                osobennostiUcheta.appendChild(getElement(doc,"ОтметкаОценщика", "ПРНТ"));
                osnovnajaStroka.appendChild(osobennostiUcheta);
            }
        }

        periodRaboty.appendChild(osnovnajaStroka);

        vsePeriodRaboty.appendChild(periodRaboty);
        return vsePeriodRaboty;
    }

    // ОрганизацияПредставившаяСЗВ
    private Node getOrganizacijaPredostavSvedenija(Document doc, String nameOrg, String regNom) {
        Element fio = doc.createElement("ОрганизацияПредставившаяСЗВ-К");
        fio.appendChild(getElement(doc, "НаименованиеОрганизации", nameOrg.toUpperCase()));
        fio.appendChild(getElement(doc, "РегистрационныйНомер", regNom.toUpperCase()));
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
    private Node getZagolovokFile(Document doc, String versiaFormata, String typeFile, String nameProgramm, String version, String istochnikFile) {
        Element zagolovokFile = doc.createElement("ЗаголовокФайла");

        zagolovokFile.appendChild(getElement(doc, "ВерсияФормата", versiaFormata));

        zagolovokFile.appendChild(getElement(doc, "ТипФайла", typeFile));

        Element programmPodgotovkiDannih = doc.createElement("ПрограммаПодготовкиДанных");
        programmPodgotovkiDannih.appendChild(getElement(doc, "НазваниеПрограммы", nameProgramm));
        programmPodgotovkiDannih.appendChild(getElement(doc, "Версия", version));
        zagolovokFile.appendChild(programmPodgotovkiDannih);

        zagolovokFile.appendChild(getElement(doc, "ИсточникДанных", istochnikFile));

        return zagolovokFile;
    }

    // утилитный метод для создание нового узла XML-файла
    private Node getElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}
