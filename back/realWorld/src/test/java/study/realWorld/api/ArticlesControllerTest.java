package study.realWorld.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.realWorld.api.dto.ArticleListDto;
import study.realWorld.api.dto.ArticleResponseDto;

import static org.assertj.core.api.Assertions.assertThat;


//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticlesControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void articleResponseDtoTest() {
        String title = "제목";
        String description = "개요";
        String body = "내용";

        ArticleResponseDto dto = ArticleResponseDto
                .builder()
                .title(title)
                .description(description)
                .body(body)
                .build();

        assertThat(dto.getTitle()).isEqualTo(title);
        assertThat(dto.getDescription()).isEqualTo(description);
        assertThat(dto.getBody()).isEqualTo(body);
    }

    @Test
    public void getArticleListTest() {
        String title = "제목";
        String description = "개요";
        String body = "내용";

        String baseUrl = "http://localhost:" + port + "/api/articles";

        // restTemplate? http요청을 모의해주는 친구 = mockMvc perform

        ResponseEntity<ArticleListDto> responseEntity = restTemplate.getForEntity(
                baseUrl, ArticleListDto.class
        );

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        ArticleListDto responseBody = responseEntity.getBody();

        assertThat(responseBody.getArticlesCount()).isEqualTo(2);

        ArticleResponseDto first = responseBody.getArticles().get(0);

        assertThat(first.getTitle()).isEqualTo(title);
        assertThat(first.getDescription()).isEqualTo(description);
        assertThat(first.getBody()).isEqualTo(body);
    }
}