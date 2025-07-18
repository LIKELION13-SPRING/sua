package summerspring.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summerspring.springbootdeveloper.domain.Article;
import summerspring.springbootdeveloper.dto.AddArticleRequest;
import summerspring.springbootdeveloper.repository.BlogRepository;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }
}
