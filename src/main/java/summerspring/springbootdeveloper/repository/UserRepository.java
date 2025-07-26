package summerspring.springbootdeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import summerspring.springbootdeveloper.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    //이메일로 사용자를 식별함.
}
