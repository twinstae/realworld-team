package study.realWorld.jwts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Articles;
import study.realWorld.jwt.TokenProvider;
import study.realWorld.repository.ArticlesRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest()
public class TokenProviderTest {

    TokenProvider tokenProvider;

    @BeforeEach
    public void createTokenProvider () throws Exception {
        String secret = "s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6";
        long tokenValidityInSeconds = 60L * 60L * 24L;
        tokenProvider = new TokenProvider(secret, tokenValidityInSeconds);
        tokenProvider.afterPropertiesSet();
        System.out.println(tokenProvider);
        assertThat(tokenProvider).isNotNull();
    }

    @Test
    public void createTokenTest () throws Exception {

    }
}
