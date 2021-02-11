package study.realWorld;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.entity.Articles;
import study.realWorld.repository.ArticlesRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticlesTestingUtil {
    @Autowired
    protected ArticlesRepository articlesRepository;

    protected String title = "제목";
    protected String description = "개요";
    protected String body = "내용";
    protected Articles articles;

    protected ArticleCreateDto createDto = ArticleCreateDto
            .builder()
            .title(title)
            .description(description)
            .body(body)
            .build();

    protected void assertArticlesEqualToDto(Articles articles, ArticleCreateDto testDto) {
        assertThat(articles.getTitle()).isEqualTo(testDto.getTitle());
        assertThat(articles.getDescription()).isEqualTo(testDto.getDescription());
        assertThat(articles.getBody()).isEqualTo(testDto.getBody());
    }

    protected void assertArticlesResponseEqualToDto(ArticleDto articleDto, ArticleCreateDto testDto) {
        assertThat(articleDto.getTitle()).isEqualTo(testDto.getTitle());
        assertThat(articleDto.getDescription()).isEqualTo(testDto.getDescription());
        assertThat(articleDto.getBody()).isEqualTo(testDto.getBody());
    }

    protected void createArticleInit() {
        articlesRepository.save(createDto.toEntity());
        articles = createDto.toEntity();
    }

    protected void assertDtoIsEqualTo(ArticleDto dto, ArticleCreateDto expected) {
        Assertions.assertThat(dto.getSlug()).isEqualTo(expected.getSlug());
        Assertions.assertThat(dto.getTitle()).isEqualTo(expected.getTitle());
        Assertions.assertThat(dto.getDescription()).isEqualTo(expected.getDescription());
        Assertions.assertThat(dto.getBody()).isEqualTo(expected.getBody());
    }


    @AfterEach
    protected void tearDown() {
        articlesRepository.deleteAll();
    }
}
