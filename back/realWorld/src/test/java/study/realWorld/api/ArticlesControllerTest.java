package study.realWorld.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.realWorld.ArticlesTestingUtil;
import study.realWorld.api.dto.ArticleListDto;
import study.realWorld.api.dto.ArticleDto;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlesControllerTest extends ArticlesTestingUtil {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl(){
        return "http://localhost:" + port + "/api/articles";
    }

    @Test
    public void articleResponseDtoTest() {
        ArticleDto responseDto = ArticleDto
                .builder()
                .title(title)
                .description(description)
                .body(body)
                .build();

        assertArticlesResponseEqualToDto(responseDto, createDto);
    }

    @Test
    public void getArticleListTest() {
        createArticleInit();

        ResponseEntity<ArticleListDto> responseEntity = restTemplate.getForEntity(
                baseUrl(), ArticleListDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);

        ArticleListDto responseBody = responseEntity.getBody();
        assertThat(responseBody.getArticlesCount()).isEqualTo(2);

        ArticleDto first = responseBody.getArticles().get(0);
        assertArticlesResponseEqualToDto(first, createDto);
    }

    private void assertStatus(ResponseEntity responseEntity, HttpStatus expectedStatus) {
        assertThat(responseEntity.getStatusCode()).isEqualTo(expectedStatus);
    }

    @Test
    public void getArticleBySlugTest(){
        createArticleInit();

        String url = baseUrl() + "/" + createDto.getSlug();
        ResponseEntity<ArticleListDto> responseEntity = restTemplate.getForEntity(
                url, ArticleListDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);

        ArticleListDto responseBody = responseEntity.getBody();
    }
}