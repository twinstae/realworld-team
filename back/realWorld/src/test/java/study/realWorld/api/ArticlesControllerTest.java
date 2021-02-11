package study.realWorld.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import study.realWorld.ArticlesTestingUtil;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleListDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleResponseDto;
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
        articlesRepository.save(updateDto.toEntity()); // 2번째 article 생성

        ResponseEntity<ArticleListDto> responseEntity = restTemplate.getForEntity(
                baseUrl(), ArticleListDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);

        ArticleListDto responseBody = responseEntity.getBody();
        assert responseBody != null;
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

        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.getForEntity(
                slugUrl(),
                ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);
        assertResponseBodyIsEqualToDto(responseEntity, createDto);
    }

    private void assertResponseBodyIsEqualToDto(
            ResponseEntity<ArticleResponseDto> responseEntity,
            ArticleCreateDto dto
    ) {
        ArticleResponseDto responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertArticlesResponseEqualToDto(responseBody.getArticle(), dto);
    }

    @Test
    public void deleteArticleBySlugTest() throws Exception {
        // given
        createArticleInit();

        // when
        restTemplate.delete(slugUrl());

        // then
        Optional<Articles> result = articlesRepository.findOneBySlug(createDto.getSlug());
        assertThat(result).isEmpty();
    }

//        {
//        "article": {
//        "title": "string",
//                "description": "string",
//                "body": "string",
//
//    }
//    }

    @Test
    public void createArticleTest() throws Exception {
        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.postForEntity(
                baseUrl(), createDto, ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.CREATED);
        assertResponseBodyIsEqualToDto(responseEntity, createDto);
    }

    @Test
    public void updateArticleTest() throws Exception {
        createArticleInit();
        HttpEntity<ArticleCreateDto> requestUpdate = new HttpEntity<>(
                updateDto, new HttpHeaders()
        );
        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.exchange(
                slugUrl(), HttpMethod.PUT, requestUpdate, ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);
        assertResponseBodyIsEqualToDto(responseEntity, updateDto);
    }
}