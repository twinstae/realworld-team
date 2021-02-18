package study.realWorld.api;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.realWorld.TestingUtil;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleResponseDto;
import study.realWorld.api.dto.profilesDtos.ProfileResponseDto;
import study.realWorld.service.ProfilesService;

import static org.assertj.core.api.Assertions.assertThat;


public class ProfilesControllerTest  extends TestingUtil {

    @LocalServerPort
    private int port;

    @Autowired
    private ProfilesService profilesService;

    @Autowired
    private TestRestTemplate restTemplate;

    private String profileUrl(){
        return "http://localhost:" + port + "/api/profiles";
    }

    private String fullProfileUrl(){
        return profileUrl() + "/" + profileDto.getUsername();
    }

    @Test
    public void findProfileByUsername() throws Exception {
        createUserInit();

        ResponseEntity<ProfileResponseDto> responseEntity = restTemplate.getForEntity(
                fullProfileUrl(),
                ProfileResponseDto.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo( HttpStatus.OK);
//        assertResponseBodyIsEqualToDto(responseEntity, createDto);
//
//        ProfileResponseDto responseBody = responseEntity.getBody();
//        assert responseBody != null;
//        assertArticlesResponseEqualToDto(responseBody.getArticle(), dto);
//
//
//        protected void assertArticlesResponseEqualToDto(ArticleDto articleDto, ArticleCreateDto testDto) {
//            AssertionsForClassTypes.assertThat(articleDto.getTitle()).isEqualTo(testDto.getTitle());
//            AssertionsForClassTypes.assertThat(articleDto.getDescription()).isEqualTo(testDto.getDescription());
//            AssertionsForClassTypes.assertThat(articleDto.getBody()).isEqualTo(testDto.getBody());
    }

}
