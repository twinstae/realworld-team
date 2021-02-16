package study.realWorld.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import study.realWorld.jwt.JwtAccessDeniedHandler;
import study.realWorld.jwt.JwtAuthenticationEntryPoint;
import study.realWorld.jwt.JwtSecurityConfig;
import study.realWorld.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring()
            .antMatchers(
                "/h2-console/**"
                ,"/favicon.ico"
                ,"/error"
            );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .csrf().disable()
                    .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)

                    .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin()

                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/hello").permitAll()
                    .antMatchers("/api/users").permitAll()
                    .antMatchers("/api/users/signin").permitAll()
                    .antMatchers("/api/users/signup").permitAll()
                    .antMatchers("/api/articles").permitAll()
                    // .antMatchers("/api/articles").permitAll() 테스트를 위한 임시 허가
                    // .antMatchers("/api/articles/제목").permitAll() 테스트를 위한 임시 허가

                    .anyRequest().authenticated()

                    .and()
                    .apply(new JwtSecurityConfig(tokenProvider));
    }
}
