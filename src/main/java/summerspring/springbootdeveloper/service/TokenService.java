package summerspring.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summerspring.springbootdeveloper.config.jwt.TokenProvider;
import summerspring.springbootdeveloper.domain.User;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    
    public String createNewAccessToken(String refreshToken) {
        //토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        //유효한 토큰일때 리프레시 토큰으로 사용자id 찾고 새로운 액세스 토큰 생성
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
