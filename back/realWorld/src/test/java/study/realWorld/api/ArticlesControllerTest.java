package study.realWorld.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import study.realWorld.TestingUtil;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleListDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleResponseDto;
import study.realWorld.api.dto.commentsDtos.CommentCreateDto;
import study.realWorld.api.dto.commentsDtos.CommentDto;
import study.realWorld.api.dto.commentsDtos.CommentListDto;
import study.realWorld.api.dto.commentsDtos.CommentResponseDto;
import study.realWorld.entity.Articles;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticlesControllerTest extends TestingUtil {
    private String baseUrl(){
        return "http://localhost:" + port + "/api/articles";
    }

    private String slugUrl(){
        return baseUrl() + "/" + createDto.getSlug();
    }

    private String wrongSlugUrl() {
        return baseUrl() + "/잘못된슬러그";
    }

    private String favoriteUrl(){
        return slugUrl() + "/favorite";
    }

    private String commentSlugUrl() { return slugUrl() + "/comments";}

    @DisplayName("/api/articles에 get 요청을 보내면 status는 ok이고 모든 articleList를 받는다.")
    @Test
    public void getArticleListTest() {
        createUserAndArticleInit();
        articlesService.create(updateDto); // 2번째 article 생성
        System.out.println("\n2번째 article 생성 끝\n");

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
    public void createUserAndArticleInitTest(){
        createUserAndArticleInit();
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

        assertArticlesResponseEqualToDto(extractArticleDto(responseEntity), dto);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void deleteArticleBySlugTest() throws Exception {
        // given
        createUserAndArticleInit();

        // when
        ResponseEntity<?> responseEntity = deleteRequestWithToken(token);

        // then
        assertStatus(responseEntity, HttpStatus.NO_CONTENT);

        Optional<Articles> result = articlesRepository.findOneWithAuthorBySlug(slugUrl());
        assertThat(result).isEmpty();
    }


    @Test
    public void createArticleTest() throws Exception {
        createUserInit();
        HttpEntity<ArticleCreateDto> entity = new HttpEntity<>(createDto, getHttpHeadersWithToken(token));
        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.postForEntity(
                baseUrl(), entity, ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.CREATED);
        assertResponseBodyIsEqualToDto(responseEntity, createDto);
    }

    @Test
    public void updateArticleTest() throws Exception {
        createUserAndArticleInit();

        ResponseEntity<ArticleResponseDto> responseEntity = updateRequestWithToken(token);

        assertStatus(responseEntity, HttpStatus.OK);
        assertResponseBodyIsEqualToDto(responseEntity, updateDto);
    }

    private ResponseEntity<ArticleResponseDto> updateRequestWithToken(String token) {
        HttpEntity<ArticleCreateDto> requestUpdate = new HttpEntity<>(
                updateDto, getHttpHeadersWithToken(token)
        );
        return restTemplate.exchange(
                slugUrl(), HttpMethod.PUT, requestUpdate, ArticleResponseDto.class
        );
    }

    @DisplayName("한 유저는 다른 유저의 Article을 수정할 수 없다.")
    @Test
    public void AnotherUserCannotUpdateArticle() throws Exception {
        createUserAndArticleInit();
        anotherUserInit();

        ResponseEntity<ArticleResponseDto> responseEntity = updateRequestWithToken(token2);

        assertStatus(responseEntity, HttpStatus.UNAUTHORIZED);
    }

    @DisplayName("한 유저는 다른 유저의 Article을 삭제할 수 없다.")
    @Test
    public void AnotherUserCannotDeleteArticle() throws Exception {
        createUserAndArticleInit();
        anotherUserInit();

        ResponseEntity<?> responseEntity = deleteRequestWithToken(token2);

        assertStatus(responseEntity, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<Map> deleteRequestWithToken(String token) {
        HttpEntity<?> requestUpdate = new HttpEntity<>(
            null, getHttpHeadersWithToken(token)
    );

        return restTemplate.exchange(
                slugUrl(), HttpMethod.DELETE, requestUpdate, Map.class
        );
    }

    @Test
    public void favoriteArticleBySlugTest() throws Exception {
        createUserAndArticleInit();
        userService.signIn(userSignInDto);

        System.out.println("요청 시작");

        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.postForEntity(
                favoriteUrl(), getHttpEntityWithToken(), ArticleResponseDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);

        ArticleDto articleDto = extractArticleDto(responseEntity);
        assertThat(articleDto.getFavoritesCount()).isEqualTo(1);
        assertThat(articleDto.isFavorited()).isTrue();
    }

    @Test
    public void unfavoriteArticleBySlugTest() throws Exception {
        favoriteArticleBySlugTest();

        ResponseEntity<ArticleResponseDto> responseEntity = restTemplate.exchange(
                favoriteUrl(), HttpMethod.DELETE, getHttpEntityWithToken(), ArticleResponseDto.class
        );

        ArticleDto articleDto = extractArticleDto(responseEntity);
        assertThat(articleDto.getFavoritesCount()).isEqualTo(0);
        assertThat(articleDto.isFavorited()).isFalse();
    }

    private ArticleDto extractArticleDto(ResponseEntity<ArticleResponseDto> responseEntity) {
        ArticleResponseDto responseBody = responseEntity.getBody();
        assert responseBody != null;
        return responseBody.getArticle();
    }

    @DisplayName("slug로 Comment를 Post하면 comment가 저장된다. ")
    @Test
    public void postCommentBySlugTest() throws Exception {
        createUserAndArticleInit();

        HttpEntity<CommentCreateDto> entity = new HttpEntity<>(commentCreateDto, getHttpHeadersWithToken(token));

        ResponseEntity<CommentResponseDto> responseEntity = restTemplate.postForEntity(
                commentSlugUrl(), entity, CommentResponseDto.class);

        assertStatus(responseEntity, HttpStatus.CREATED);
        CommentResponseDto responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertThat(responseEntity.getBody().getCommentDto().getBody()).isEqualTo(commentCreateDto.getBody());
    }

    @DisplayName("slug로 Comments를 Get하면 comments들을 가져온다")
    @Test
    public void getCommentsBySlugTest() throws Exception {
        createUserAndArticleInit();
        commentService.addCommentToArticleBySlug(createDto.getSlug(),commentCreateDto);
        commentService.addCommentToArticleBySlug(createDto.getSlug(),commentCreateDto2);

        ResponseEntity<CommentListDto> responseEntity = restTemplate.exchange(
                commentSlugUrl(), HttpMethod.GET, getHttpEntityWithToken(), CommentListDto.class
        );

        assertStatus(responseEntity, HttpStatus.OK);

        CommentListDto responseBody = responseEntity.getBody();

        assert responseBody != null;
        assertThat(responseBody.getComments().size()).isEqualTo(2);

        CommentDto firstComment = responseBody.getComments().get(0);
        assertThat(firstComment.getBody()).isEqualTo("이 글은 참 좋군요.");
    }
}