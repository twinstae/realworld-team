package study.realWorld.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.userDtos.UserDto;
import study.realWorld.api.dto.userDtos.UserResponseDto;
import study.realWorld.api.dto.userDtos.UserSignUpDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl(){
        return "http://localhost:" + port + "/api/user";
    }

    @DisplayName("회원가입을 요청을 보내면 status는 Created이고 가입된 user를 반환한다.")
    @Test
    public void signUpTest() {
        // 회원가입 dto를 만든다
        UserSignUpDto userSignUpDto = UserSignUpDto
                .builder()
                .username("홍길동")
                .email("test@naver.com")
                .password("t1e2s3t4")
                .build();

        // restTemplate으로 요청을 보내고
        ResponseEntity<UserResponseDto> responseEntity = restTemplate.postForEntity(
                baseUrl(),
                userSignUpDto,
                UserResponseDto.class
        );

        // status는 created이다
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // user 정보가 올바르게 들어가 있다.
        UserResponseDto responseBody = responseEntity.getBody();
        UserDto userDto = responseBody.getUser();

        assertThat(userDto.getUsername()).isEqualTo(userSignUpDto.getUsername());
        assertThat(userDto.getEmail()).isEqualTo(userSignUpDto.getEmail());
    }
    // 올바른 로그인 요청을 보내면 status는 OK이고 로그인된 user를 jwt 토큰과 함께 반환한다.
}