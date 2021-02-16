package study.realWorld.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import study.realWorld.TestingUtil;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleListDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleResponseDto;
import study.realWorld.entity.Articles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlesControllerTest extends TestingUtil {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl(){
        return "http://localhost:" + port + "/api/articles";
    }

    private String slugUrl(){
        return baseUrl() + "/" + createDto.getSlug();
    }

    private String wrongSlugUrl() {
        return baseUrl() + "/잘못된슬러그";
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

    @DisplayName("/api/articles에 get 요청을 보내면 status는 ok이고 모든 articleList를 받는다.")
    @Test
    public void getArticleListTest() {
        createUserAndArticleInit();
        articlesRepository.save(updateDto.toEntity(user)); // 2번째 article 생성

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

    @DisplayName("/api/articles/{slug}로 get 요청을 보내면 status는 ok이고 slug에 해당하는 article을 받는다")
    @Test
    public void getArticleBySlugTest(){
        createUserAndArticleInit();

        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.getForEntity(
                slugUrl(),
                ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);
        assertResponseBodyIsEqualToDto(responseEntity, createDto);
    }

    @DisplayName("/api/articles/{잘못된슬러그}로 get 요청을 보내면 status는 Not found 이다.")
    @Test
    public void getArticleByWrongSlugTest(){
        createUserAndArticleInit();

        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.getForEntity(
                wrongSlugUrl(),
                ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.NOT_FOUND);
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
        createUserAndArticleInit();

        // when
        restTemplate.delete(slugUrl());

        // then
        Optional<Articles> result = articlesRepository.findOneBySlug(slugUrl());
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
        createUserInit();
        HttpEntity<ArticleCreateDto> entity = new HttpEntity<>(createDto, getHttpHeadersWithToken());
        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.postForEntity(
                baseUrl(), entity, ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.CREATED);
        assertResponseBodyIsEqualToDto(responseEntity, createDto);
    }

    @Test
    public void updateArticleTest() throws Exception {
        createUserAndArticleInit();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(userSignInDto.getEmail(),userSignInDto.getPassword());

        HttpEntity<ArticleCreateDto> requestUpdate = new HttpEntity<>(
                updateDto, headers
        );
        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.exchange(
                slugUrl(), HttpMethod.PUT, requestUpdate, ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);
        assertResponseBodyIsEqualToDto(responseEntity, updateDto);
    }

    @DisplayName("다른 유저는 다른 유저의 Article을 수정할 수 없다.")
    @Test
    public void AnotherUserCannotUpdateArticle() throws Exception {

        createUserAndArticleInit();
        anOtherUserInit();



        assertDtoIsEqualTo(updatedArticleDto, updateDto);
    }
}