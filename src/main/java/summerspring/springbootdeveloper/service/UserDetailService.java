package summerspring.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import summerspring.springbootdeveloper.domain.User;
import summerspring.springbootdeveloper.repository.UserRepository;

@RequiredArgsConstructor
@Service


public class UserDetailService implements UserDetailsService {
//스프링 시큐리티에서 로그인 시 사용자 정보를 가져오는 인터페이스
    private final UserRepository userRepository;

    //사용자이름(email)으로 사용자 정보를 가져오는 메서드
    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException((email)));
    }
}
