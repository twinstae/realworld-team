package study.realWorld.jwts;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import study.realWorld.jwt.TokenProvider;

import java.security.Key;
import java.security.Principal;
import java.util.Date;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest()
public class TokenProviderTest {

    TokenProvider tokenProvider;

    String secret;
    String AUTHORITIES_KEY = "auth";
    Date validity;

    @BeforeEach
    public void createTokenProvider () throws Exception {
        String base = "s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6";
        secret = base+base;
        long tokenValidityInSeconds = 60L * 60L * 24L;
        validity = new Date( (new Date()).getTime() + tokenValidityInSeconds);
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


        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);


        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();

        System.out.println("token parsing = " + Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token));

        assertThat(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().containsValue("USER"));
        //assertThat("nothing").isEqualTo("error!");
    }
}
