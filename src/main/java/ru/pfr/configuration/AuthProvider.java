package ru.pfr.configuration;

import jodd.http.HttpUtil;
import jodd.http.HttpValuesMap;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.pfr.Application;
import ru.pfr.model.Adminparam;
import ru.pfr.model.Logi;
import ru.pfr.model.Rayon;
import ru.pfr.model.User;
import ru.pfr.role_enum.ROLE_ENUM;
import ru.pfr.service.LogiService;
import ru.pfr.service.User.AdminparamService;
import ru.pfr.service.User.RayonService;
import ru.pfr.service.User.UserService;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private RayonService rayonService;

    @Autowired
    private AdminparamService adminparamService;

    @Autowired
    UserService userService;

    @Autowired
    LogiService logiService;

    private static final Logger logger = LogManager.getLogger(Application.class);

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        logger.info("AuthProvider authenticate()");
        SecurityContext context = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) a;
        String username = String.valueOf(auth.getPrincipal());
        String password = String.valueOf(auth.getCredentials());
        Map<String, String> parameterList = new HashMap<>();
        parameterList.put("adr", "http://127.0.0.0/Autent");
        parameterList.put("kod", "170"); //код приложения?
        parameterList.put("login", username);
        logiService.save(new Logi(
                new Date(),
                username,
                "Попытка авторизации по логину " + username + " AuthProvider authenticate()"));
        logger.info("Попытка авторизации по логину " + username + " AuthProvider authenticate()");
        parameterList.put("pass", password);
        CloseableHttpResponse httpResponse = getHTTPResponse("http://10.41.0.247:322/ACS/AutentAll",
                parameterList);
        Header[] headers = httpResponse.getHeaders("Location");

        //****************************************************
/*        Logerr logerr;
        try {
            logerr = logerrService.findByLoginuser(username);
        }
        catch (Exception e){
            logerr = new Logerr(username);
            logerrService.save(logerr);
        }

        //для количества попыток-----------------------------
        Adminparam adminparam = adminparamService.findByAdminparam();

        Long datenow = new Date().getTime()+10800000l; //избавление от погрешности во времени
        //long t = datenow - logerr.getDate().getTime();
        //boolean bbb = (datenow - logerr.getDate().getTime()) <= 600000l;
        if(logerr.getActive()>=adminparam.getKolpopitok() &&
                logerr.getActive()<adminparam.getBlock() &&
                (datenow - logerr.getDate().getTime()) <=
                        (600000l + ((adminparam.getKolpopitok()-2)*adminparam.getKoefpopitok()*60000l))){
            logerr.setActive(logerr.getActive()+1);
            logerr.setDate(new Date(datenow));
            logerrService.save(logerr);
            logiService.save(new Logi(
                    new Date(),
                    username,
                    "Попытка авторизации превышен лимит попыток количество попыток"
                            + logerr.getActive()+" AuthProvider authenticate()"));
            logger.info("Попытка авторизации по логину "+username+" Превышен лимит попыток " +
                    "  Количество попыток "+logerr.getActive()+" AuthProvider authenticate()");

            throw new BadCredentialsException("Превышен лимит попыток");
        }
        if(logerr.getActive()>=adminparam.getBlock()){
            logiService.save(new Logi(
                    new Date(),
                    username,
                    "Попытка авторизации пользователь заблокирован количество попыток"
                            + logerr.getActive()+" AuthProvider authenticate()"));
            logger.info("Попытка авторизации по логину "+username+" Пользователь заблокирован" +
                    " Количество попыток "+logerr.getActive()+"  AuthProvider authenticate()");
            throw new BadCredentialsException("Пользователь заблокирован");
        }
        if (headers.length == 0) {
            logerr.setActive(logerr.getActive()+1);
            logerr.setDate(new Date(datenow));
            logerrService.save(logerr);
            logiService.save(new Logi(
                    new Date(),
                    username,
                    "Попытка авторизации пароль неверен AuthProvider authenticate()"));
            logger.info("Попытка авторизации по логину "+username+" Пароль неверен AuthProvider authenticate()");
            throw new BadCredentialsException("Пароль неверен");
        }
        else{
            logerr.setActive(0l);
            logerr.setDate(new Date(datenow));
            logerrService.save(logerr);
        }*/
        //-----------------------------


        //v2****************************************************
        User logerr;
        try {
            logerr = userService.findByLoginuser(username);
        } catch (Exception e) {
            Rayon  r = rayonService.findByKod("1000").get();
            logerr = new User(username, r);
            userService.save(logerr);
        }

        //для количества попыток-----------------------------
        Adminparam adminparam = adminparamService.findByAdminparam();

        Long datenow = new Date().getTime() + 10800000l; //избавление от погрешности во времени
        //long t = datenow - logerr.getDate().getTime();
        //boolean bbb = (datenow - logerr.getDate().getTime()) <= 600000l;
        if (logerr.getActive() >= adminparam.getKolpopitok() &&
                logerr.getActive() < adminparam.getBlock() &&
                (datenow - logerr.getDate().getTime()) <=
                        (600000l + ((adminparam.getKolpopitok() - 2) * adminparam.getKoefpopitok() * 60000l))) {
            logerr.setActive(logerr.getActive() + 1);
            logerr.setDate(new Date(datenow));
            userService.save(logerr);
            logiService.save(new Logi(
                    new Date(),
                    username,
                    "Попытка авторизации превышен лимит попыток количество попыток"
                            + logerr.getActive() + " AuthProvider authenticate()"));
            logger.info("Попытка авторизации по логину " + username + " Превышен лимит попыток " +
                    "  Количество попыток " + logerr.getActive() + " AuthProvider authenticate()");

            throw new BadCredentialsException("Превышен лимит попыток");
        }
        if (logerr.getActive() >= adminparam.getBlock()) {
            logiService.save(new Logi(
                    new Date(),
                    username,
                    "Попытка авторизации пользователь заблокирован количество попыток"
                            + logerr.getActive() + " AuthProvider authenticate()"));
            logger.info("Попытка авторизации по логину " + username + " Пользователь заблокирован" +
                    " Количество попыток " + logerr.getActive() + "  AuthProvider authenticate()");
            throw new BadCredentialsException("Пользователь заблокирован");
        }
        if (headers.length == 0) {
            logerr.setActive(logerr.getActive() + 1);
            logerr.setDate(new Date(datenow));
            userService.save(logerr);
            logiService.save(new Logi(
                    new Date(),
                    username,
                    "Попытка авторизации пароль неверен AuthProvider authenticate()"));
            logger.info("Попытка авторизации по логину " + username + " Пароль неверен AuthProvider authenticate()");
            throw new BadCredentialsException("Пароль неверен");
        } else {
            logerr.setActive(0l);
            logerr.setDate(new Date(datenow));
            userService.save(logerr);
        }
        //-----------------------------


        String response = headers[0].getValue();
        Matcher authQueryString = Pattern
                .compile("^http://127\\.0\\.0\\.0/Autent\\?([^\\r\\n]++)$")
                .matcher(response);
        if (!authQueryString.find()) {
            throw new BadCredentialsException("Пароль неверен");
        }
        HttpValuesMap<Object> authData = HttpUtil.parseQuery(authQueryString.group(1), true);
        Collection<GrantedAuthority> roleList = new HashSet<>();
        Object[] rights = authData.get("right");
        String userId = (String) authData.get("id")[0];
        String upfrCode = (String) authData.get("upfr")[0];
        for (Object right : rights) {
            Integer rightCode = Integer.parseInt((String) right);
            switch (rightCode) {
                case 3011:
                    roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_UPDATE.getString()));
                    break;
                case 3012:
                    roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_DOWNLOAD.getString()));
                    //upfrCode="002";
                    break;
                case 3010://3010
                    roleList.add(new SimpleGrantedAuthority(ROLE_ENUM.ROLE_ADMIN.getString()));
                    upfrCode = "999";
                    break;
            }
        }

        while (upfrCode.length() < 3) {
            upfrCode = "0" + upfrCode;
        }
/*        User us = new User();
        us.setActive(1);
        us.setId(Long.parseLong(userId));
        us.setLogin(username);
        us.setRayon(rayonService.findByKod(upfrCode).orElse(new Rayon()));*/

        User us = userService.findByLoginuser(username);
        us.setActive(1l);
        //us.setId(Long.parseLong(userId));
        us.setRayon(rayonService.findByKod(upfrCode).get());
        userService.save(us);

        a = new UsernamePasswordAuthenticationToken(us, "", roleList);
        context.setAuthentication(a);
        logiService.save(new Logi(
                new Date(),
                us.getLogin(),
                "Пользователь " + us.getLogin() + " авторизован  AuthProvider authenticate()"));
        logger.info("Пользователь " + us.getLogin() + " авторизован  AuthProvider authenticate()end");
        return a;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }


    public CloseableHttpResponse getHTTPResponse(String addr, Map<String, String> parameterList) {
        try {
            BasicCookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpUriRequest req;
            CloseableHttpResponse response;
            RequestConfig requestConfig = RequestConfig
                    .copy(RequestConfig.DEFAULT)
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .build();
            RequestBuilder reqBuilder = RequestBuilder.post().setUri(new URI(addr));
            for (String key : parameterList.keySet()) {
                reqBuilder.addParameter(key, parameterList.get(key));
            }
            req = reqBuilder.setConfig(requestConfig).build();
            response = httpclient.execute(req);
            return response;
        } catch (URISyntaxException | IOException ex) {
            return null;
        }
    }

}
