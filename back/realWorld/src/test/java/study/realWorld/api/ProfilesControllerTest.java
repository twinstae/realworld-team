package study.realWorld.api;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.realWorld.TestingUtil;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleResponseDto;
import study.realWorld.api.dto.profilesDtos.ProfileResponseDto;
import study.realWorld.service.ProfilesService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class ProfilesControllerTest  extends TestingUtil {
    private String profileUrl(){
        return "http://localhost:" + port + "/api/profiles";
    }

    private String fullProfileUrl(){
        return profileUrl() + "/" + userSignUpDto2.getUsername();
    }

    @BeforeEach
    public void setUp(){
        createUserInit();
        anotherUserInit();
    }

    @Test
    public void findProfileByUsername() throws Exception {
        ResponseEntity<ProfileResponseDto> responseEntity = restTemplate.exchange(
                fullProfileUrl(),
                HttpMethod.GET,
                getHttpEntityWithToken(),
                ProfileResponseDto.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void followingByUsernameTest() throws Exception {
        ResponseEntity<ProfileResponseDto> responseEntity = restTemplate.postForEntity(
                fullProfileUrl()+"/follow",
                getHttpEntityWithToken(),
                ProfileResponseDto.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void followerByUsernameTest() throws Exception {
        followingByUsernameTest();

        ResponseEntity<ProfileResponseDto> responseEntity = restTemplate.exchange(
                fullProfileUrl()+"/follow",
                HttpMethod.DELETE,
                getHttpEntityWithToken(),
                ProfileResponseDto.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
