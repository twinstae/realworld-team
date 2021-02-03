package study.realWorld.api;

import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.realWorld.ArticlesTestingUtil;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.dto.ArticleListDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.dto.ArticleResponseDto;
import study.realWorld.entity.Articles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlesControllerTest extends ArticlesTestingUtil {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl(){
        return "http://localhost:" + port + "/api/articles";
    }


    private String slugUrl(){
        return baseUrl() + "/" + articles.getSlug();
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

        System.out.println(url);
        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.getForEntity(
                url, ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);

        ArticleResponseDto responseBody = responseEntity.getBody();
        assertArticlesResponseEqualToDto(responseBody.getArticle(), createDto);
    }

    @Test
    public void deleteArticleBySlugTest() throws Exception {
        // given
        createArticleInit();
       // String url = baseUrl() + "/" + createDto.getSlug();

        // when
        restTemplate.delete(slugUrl());
        // then
        Optional<Articles> result = Optional.ofNullable(articlesRepository.findOneBySlug(createDto.getSlug()));
        assertThat(result).isEmpty();

    }


}