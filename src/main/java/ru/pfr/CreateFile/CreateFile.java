package ru.pfr.CreateFile;

import ru.pfr.model.User;
import ru.pfr.service.count.KolfileService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class CreateFile {

    String path;
    String xmlName;

    FileOutputStream fos;
    OutputStreamWriter outputfile;


    File dir;
    File xml;


    public CreateFile(int type, User user,
            KolfileService kolfileService, String regnum) {
        switch (type){
            case 1:{
                path = "./xml/";
                //xmlName = "convxmlfile_" + tekDate() + ".xml";
                //xmlName = "PFR-700-Y-" + tekDate().substring(0,4) + "-ORG-" + user.getRayon().getRegnummru() + "-DCK-" + "00001" + "-DPT-000000-DCK-00000.XML";
                xmlName = "PFR-700-Y-" + tekDate().substring(0,4) +
                        "-ORG-" + user.getRayon().getRegnummru() +
                        "-DCK-" + kolfileService.kolfile(1l,5) + "-DPT-000000-DCK-00000.XML";
            }break;
            case 2:{
                path = "./xml/";
                String uuid = UUID.randomUUID().toString();
                //xmlName = "spuxmlfile_" + tekDate() + ".xml";
                //xmlName = "PFR-700-Y-" + tekDate().substring(0,4) + "-ORG-" + user.getRayon().getRegnummru() + "-DCK-" + kolfileService.kolfile(2l) + "-DPT-000000-DCK-00000.XML";
                xmlName = "ПФР_" + regnum + //.substring(0,7) + "-" + "______" +
                        "_041" + user.getRayon().getKod() + //kolfileService.kolfile(2l,6) + //user.getRayon().getRegnummru().substring(0,3) + user.getRayon().getRegnummru().substring(4,7) +
                        "_СЗВ-КОРР_" + tekDate() + "_"
                        + uuid + ".XML";
            }break;
        }

        dir = new File(path);
        dir.mkdir();

        for (File myFile :dir.listFiles() ) //удалить старые файлы
            if (myFile.isFile())
                myFile.delete();

        xml = new File(dir,xmlName);

        //создаем файл
        try {
            fos = new FileOutputStream(xml);
            switch (type){
                case 1:{
                    outputfile = new OutputStreamWriter(fos,
                            "Windows-1251");
                }break;
                case 2:{
                    outputfile = new OutputStreamWriter(fos,
                            "UTF-8");
                }break;
            }
            fos.close();
            //outputfile = new OutputStreamWriter(fos, "Windows-1251");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public OutputStreamWriter getOutputfile(){
        return this.outputfile;
    }


    private String tekDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    public InputStream getInputStream(HttpServletResponse resp){
        FileInputStream fis;
        try {
            fis = new FileInputStream(xml);

            //ститать файл в массив байт
            byte[] buffer = new byte[fis.available()];
            // чтение файла в буфер
            fis.read(buffer, 0, fis.available());
            fis.close();
            //resp.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", xmlName);
            resp.setContentType("text/xml; charset=UTF-8");
            resp.setHeader("Cache-Control", "no-cache");
            resp.setHeader(headerKey, headerValue);

            return new ByteArrayInputStream(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File getXml() {
        return xml;
    }

    public String getXmlName() {
        return xmlName;
    }
}
