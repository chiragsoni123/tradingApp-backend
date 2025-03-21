package com.chirag.config;

import com.chirag.modal.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;

@Configuration
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
                .oauth2Login(oauth->{
                    oauth.loginPage("/login/google");
                    oauth.authorizationEndpoint(authorization->
                            authorization.baseUri("/login/oauth2/authorization"));
                    oauth.successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                            if (authentication.getPrincipal() instanceof DefaultOAuth2User){
                                DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
                                String email = userDetails.getAttribute("email");
                                String fullName = userDetails.getAttribute("name");
                                String phone = userDetails.getAttribute("phone");
                                String picture = userDetails.getAttribute("picture");
                                boolean email_verified = Boolean.TRUE.equals(userDetails.getAttribute("email_verified"));

                                User user = new User();
                                user.setFullName(fullName);
                                user.setEmail(email);
                                user.setVerified(email_verified);
                                user.setMobile(phone);
                                user.setPicture(picture);

                                System.out.println("---------------" + email+
                                        "-------------"+
                                        "============"
                                + "--------"+ user);
                            }
                        }
                    });
                })
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return null;
    }

}
