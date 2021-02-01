package study.realWorld.api;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.realWorld.api.dto.LoginUserResponseDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void LoginResponseDtoTest() {


//        {
//            "user": {
//            "username": "string",
//                    "email": "string",
//        }
//        }

        String username = "MemberA";
        String email = "a@a.com";

        LoginUserResponseDto dto = LoginUserResponseDto
                .builder()
                .username(username)
                .email(email)
                .build();

        assertThat(dto.getUsername()).isEqualTo(username);
        assertThat(dto.getEmail()).isEqualTo(email);
    }

    @Test
    public void getArticleListTest() {
        String username = "MemberA";
        String email = "a@a.com";

        String baseUrl = "http://localhost:" + port + "/api/loginUser";

        // restTemplate? http요청을 모의해주는 친구 = mockMvc perform

        ResponseEntity<LoginUserResponseDto> responseEntity = restTemplate.getForEntity(
                baseUrl, LoginUserResponseDto.class
        );

        System.out.println(responseEntity);

        Assertions.assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

    }
}
