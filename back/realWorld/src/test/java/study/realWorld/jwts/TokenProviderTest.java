package study.realWorld.jwts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import study.realWorld.jwt.TokenProvider;

import java.security.Principal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest()
public class TokenProviderTest {

    TokenProvider tokenProvider;

    @BeforeEach
    public void createTokenProvider () throws Exception {
        String base = "s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6";
        String secret = base+base;
        long tokenValidityInSeconds = 60L * 60L * 24L;
        tokenProvider = new TokenProvider(secret, tokenValidityInSeconds);
        tokenProvider.afterPropertiesSet();
        System.out.println(tokenProvider);
        assertThat(tokenProvider).isNotNull();
    }

    @Test
    public void createTokenTest () throws Exception {
        String username = "user1";

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return username;
            }
        };
        String[] authorities = {"USER"};

        Authentication authentication = new TestingAuthenticationToken(principal, null, authorities);
        System.out.println("authentication = " + authentication);

        String jwt = tokenProvider.createToken(authentication);
        System.out.println("jwt = " + jwt);

        Authentication auth_token = tokenProvider.getAuthentication(jwt);
        System.out.println(auth_token);

        assertThat(tokenProvider.validateToken(jwt)).isTrue();

        assertThat("nothing").isEqualTo("error!");
    }
}
