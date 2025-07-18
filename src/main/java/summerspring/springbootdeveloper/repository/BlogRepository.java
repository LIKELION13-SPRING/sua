package summerspring.springbootdeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import summerspring.springbootdeveloper.domain.Article;

public interface BlogRepository  extends JpaRepository<Article,Long> {
}
