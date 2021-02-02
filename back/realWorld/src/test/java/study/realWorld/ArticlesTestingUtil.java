package study.realWorld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.entity.Articles;
import study.realWorld.repository.ArticlesRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ArticlesTestingUtil {

    @Autowired
    protected ArticlesRepository articlesRepository;

    protected ArticleCreateDto dto = ArticleCreateDto
            .builder()
            .title("title")
            .description("description")
            .body("body")
            .build();

    protected void assertEqualToDto(Articles articles, ArticleCreateDto testDto) {
        assertThat(articles.getTitle()).isEqualTo(testDto.getTitle());
        assertThat(articles.getDescription()).isEqualTo(testDto.getDescription());
        assertThat(articles.getBody()).isEqualTo(testDto.getBody());
    }
}
