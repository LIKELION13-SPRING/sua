package summerspring.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import summerspring.springbootdeveloper.service.UserDetailService;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//실제 인증 처리를 하는 시큐리티 설정 파일
public class WebSecurityConfig {

    private final UserDetailService userService;

    //스프링 시큐리티 기능 비활성화: 이미지, html 파일같은 정적 resource들에 설정
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring() //밑의 경로들은 security가 완전히 무시함
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    //특정 HTTP요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                //특정 경로에 대한 엑세스 설정
                        .requestMatchers("/login", "/signup", "/user")
                        .permitAll() //누구나 접근 가능
                        .anyRequest().authenticated()) // 저 세개 외 다른 url: 인증 성공해야 접근 가능
                .formLogin(formLogin -> formLogin
                // 폼 기반 로그인 설정
                        .loginPage("/login") // 로그인 페이지 경로 설정
                        .defaultSuccessUrl("/articles") // 로그인 완료되었을 때 이동할 경로 설정
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login") //로그아웃 완료되었을 때 이동할 경로 설정
                        .invalidateHttpSession(true) //로그아웃 이후에 세션 전체 삭제 
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    //인증 관리자 관련 설정: 사용자 정보 가져올 서비스 재정의, 인증 방법 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
    throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    //패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
