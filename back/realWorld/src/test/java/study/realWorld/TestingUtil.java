package study.realWorld;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.userDtos.UserSignInDto;
import study.realWorld.api.dto.userDtos.UserSignUpDto;
import study.realWorld.api.dto.userDtos.UserWithTokenDto;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Authority;
import study.realWorld.entity.User;
import study.realWorld.repository.ArticlesRepository;
import study.realWorld.repository.AuthorityRepository;
import study.realWorld.repository.UserRepository;
import study.realWorld.service.UserService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingUtil {

    @Autowired
    protected ArticlesRepository articlesRepository;
    @Autowired
    protected UserService userService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected AuthorityRepository authorityRepository;

    protected final String title = "제목";
    protected final String description = "개요";
    protected final String body = "내용";
    protected User user;
    protected String token;
    protected String token2;

    protected final UserSignUpDto userSignUpDto = UserSignUpDto
            .builder()
            .username("홍길동")
            .email("test@naver.com")
            .password("t1e2s3t4")
            .build();

    protected final UserSignUpDto userSignUpDto2 = UserSignUpDto
            .builder()
            .username("홍길동글")
            .email("test2@naver.com")
            .password("t1e2s3t45")
            .build();

    protected final UserSignInDto userSignInDto = UserSignInDto
            .builder()
            .email("test@naver.com")
            .password("t1e2s3t4")
            .build();

    protected final UserSignInDto userSignInDto2 = UserSignInDto
            .builder()
            .email("test2@naver.com")
            .password("t1e2s3t45")
            .build();

    protected final ArticleCreateDto createDto = ArticleCreateDto
            .builder()
            .title(title)
            .description(description)
            .body(body)
            .build();

    protected final ArticleCreateDto updateDto = ArticleCreateDto
            .builder()
            .title("타이틀")
            .description("디스크립션")
            .body("바디")
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

    private void authorityInit() {
        Authority authority = new Authority("ROLE_USER"); //권한 생성
        authorityRepository.save(authority); // 권한 저장
    }

    protected void createUserInit() {
        authorityInit();

        userService.signUp(userSignUpDto);
        token = getToken(userSignInDto);

        user = userService.getUserWithAuthorities(userSignUpDto.getEmail());
        System.out.println(user.getAuthorities());
    }

    private String getToken(UserSignInDto signInDto) {
        UserWithTokenDto userWithTokenDto = userService.signIn(signInDto);
        return userWithTokenDto.getToken();
    }

    protected void anotherUserInit(){
        userService.signUp(userSignUpDto2);
        token2 = getToken(userSignInDto2);
    };

    protected void createUserAndArticleInit(){
        createUserInit();
        articlesRepository.save(createDto.toEntity(user));
    }

    // todo: init을 해서 token이 있을 때만 호출 가능하게 만들자!
    protected HttpHeaders getHttpHeadersWithToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token "+token);
        return headers;
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
        userRepository.deleteAll();
        authorityRepository.deleteAll();
        user = null;
        token = null;
    }
}