package summerspring.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import summerspring.springbootdeveloper.domain.User;
import summerspring.springbootdeveloper.dto.AddUserRequest;
import summerspring.springbootdeveloper.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입 후 정보 신규 저장 메서드
    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) //패스워드 암호화
                .build()).getId();
    }
}
