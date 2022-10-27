package io.swagger.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SpringSecurityConfiguration  extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService authenticateUserDetailService;

    private String ADMIN = "ADMIN";
    private String BRAND_ADMIN_WRITE = "BRAND_ADMIN_WRITE";
    private String BRAND_ADMIN_READ = "BRAND_ADMIN_READ";
    private String WAREHOUSE_STAFF_WRITE = "WAREHOUSE_STAFF_WRITE";
    private String WAREHOUSE_STAFF_READ = "WAREHOUSE_STAFF_READ";
    private String STORE_STAFF_READ = "STORE_STAFF_READ";
    private String STORE_STAFF_WRITE = "STORE_STAFF_WRITE";
    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v3 (OpenAPI)
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**"
            // other public endpoints of your API may be appended to this array
    };

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST,"/user").permitAll()
                .antMatchers(HttpMethod.GET,"/user/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                // Disable session creation on spring security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(authenticateUserDetailService).passwordEncoder(passwordEncoder);
    }
}

