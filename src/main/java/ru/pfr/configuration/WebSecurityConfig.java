package ru.pfr.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.pfr.service.User.UserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;
    @Autowired
    private AuthProvider authProvider;
    /*@Autowired
    CustomAuthFailureHandler customAuthFailureHandler;*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//включить авторизацию
                .antMatchers("**/*.html").permitAll()
                .antMatchers("**/*.css").permitAll()
                .antMatchers("**/*.js").permitAll()
                //.antMatchers("/", "/pkv").permitAll()//полный доступ
                .anyRequest().authenticated()//для стальных авторизация
                .and()
                .formLogin()
                    .loginPage("/kils/login")//маппинг логина
                    .permitAll()//разрешаем пользоваться всем
                    //.failureHandler(customAuthFailureHandler)
                    .and()
                .logout()//включаем логаут
                    .permitAll();//разрешаем пользоваться всем
    }

/*
    private final Environment environment;

    @Autowired
    public WebSecurityConfig(Environment environment) {
        this.environment = environment;
    }
*/


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) a;
/*        String username = null;
        String password = null;
        try {
            username = environment.getRequiredProperty("username");
            password = environment.getRequiredProperty("password");
        }catch (Exception e){
            username="";
            password="";
        }*/
        //String password = environment.getRequiredProperty("user.password");
        auth.authenticationProvider(authProvider);
        // auth.userDetailsService(userService).
        //       passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
