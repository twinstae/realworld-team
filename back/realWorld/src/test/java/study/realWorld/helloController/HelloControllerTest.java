package study.realWorld.helloController;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import study.realWorld.api.dto.LoginUserResponseDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {


//    @Autowired
//    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


//    @Test
//    public void helloControllerTest() throws Exception {
//
//        String hello = "hello";
//        mockMvc.perform(get("/hello"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("hello"));
//
//    }

    @Test
    public void securityConfigTest() throws Exception {

        String baseUrl = "http://localhost:" + port + "/api/hello";

        ResponseEntity<String> result = restTemplate.getForEntity(baseUrl,String.class);

        System.out.println("result = " + result);
        System.out.println("result.getBody() = " + result.getBody());
        System.out.println("result.getHeaders() = " + result.getHeaders());

//
//        Assertions.assertThat(result.getStatusCode())
//                .isEqualTo(HttpStatus.OK);
//        assertThat(result.getBody()).isEqualTo("hello");
    }


}