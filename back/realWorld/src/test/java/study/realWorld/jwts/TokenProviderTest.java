package study.realWorld.jwts;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import study.realWorld.jwt.TokenProvider;

import java.security.Key;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest()
public class TokenProviderTest {

    TokenProvider tokenProvider;

    String username;
    String secret;
    String[] authorities = {"USER"};

    @BeforeEach
    public void createTokenProvider () throws Exception {
        String base = "s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6";
        secret = base+base;
        long tokenValidityInSeconds = 60L * 60L * 24L;
        tokenProvider = initTokenProvider(tokenValidityInSeconds);
    }

    private TokenProvider initTokenProvider(long tokenValidityInSeconds) {
        TokenProvider tokenProviderObj = new TokenProvider(secret, tokenValidityInSeconds);
        tokenProviderObj.afterPropertiesSet();
        assertThat(tokenProviderObj).isNotNull();
        return tokenProviderObj;
    }

    @DisplayName("정상적으로 발급된 토큰을 validate 했을 때 True를 반환한다.")
    @Test
    public void validTokenTest() {
        String token = tokenProvider.createToken(createAuthentication());
        assertThat(tokenProvider.validateToken(token)).isTrue();
    }

    //ExpiredJwtException – if the specified JWT is a Claims JWT and the Claims has an
    // expiration time before the time this method is invoked.
    @DisplayName("만료된 JWT 토큰을 validate하면 False를 반환한다.")
    @Test
    public void ExpiredJwtTokenTest() throws Exception {
        long tokenValidityInSeconds = 60L * 60L * 24L * -1;
        TokenProvider provider2 = initTokenProvider(tokenValidityInSeconds);

        Authentication authentication = createAuthentication();
        String token = provider2.createToken(authentication);

        assertThat(tokenProvider.validateToken(token)).isFalse();

        //assertThat("nothing").isEqualTo("error!");
    }

    //parseClaimsJws에 제대로된 token 들어가지 않으면 MalformedJwtException이 발생한다.
    //MalformedJwtException – if the claimsJws string is not a valid JWS
    @DisplayName("서명만 잘못된 토큰을 validate하면 False를 반환한다.")
    @Test
    public void invalidSignatureTest() throws Exception {
        String invalidSignature = "U99Hi4lZH0FmWxh_uKJa5rxzObEdgCVKgLEea02QYlYPGWBNSSTu3b3Us3e1HiNLsYccMkjuudv7fiiHIA95G8";
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImF1dGgiOiJVU0VSIiwiZXhwIjoxNjEzMDIyOTg4fQ."+invalidSignature;
        assertThat(tokenProvider.validateToken(token)).isFalse();
    }

    @DisplayName("잘못된 토큰을 validate하면 False를 반환한다")
    @Test
    public void IllegalArgumentTest() throws Exception {
        assertThat(tokenProvider.validateToken("1234.1234.signature")).isFalse();
    }

    @DisplayName("한글이 포함된 토큰을 validate하면 False를 반환한다")
    @Test
    public void hangulJWTTest() throws Exception {
        assertThat(tokenProvider.validateToken("error.에러.서명")).isFalse();
    }

    @Test
    public void createTokenTest () throws Exception {
        Authentication authentication = createAuthentication();
        String jwt = tokenProvider.createToken(authentication);
        System.out.println("jwt = " + jwt);

        Authentication auth_token = tokenProvider.getAuthentication(jwt);
        System.out.println(auth_token);

        assertThat(tokenProvider.validateToken(jwt)).isTrue();

        Authentication auth = tokenProvider.getAuthentication(jwt);
        // auth (Principal, credential, authorities, )

        assertThat(
                auth.getAuthorities().stream() // authorities = 객체 {authority : "USER" }
                        .map(GrantedAuthority::getAuthority) // authority-> authority.getAuthority()
                        // ["USER", "ADMIN"]
                        .anyMatch(s->s.equals("USER")))
                .isTrue();
        //assertThat("nothing").isEqualTo("error!");
    }



    //Authentication 만들기
    public Authentication createAuthentication() {
        username = "user1";
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return username;
            }
        };
        return new TestingAuthenticationToken(principal, null, authorities);
    }
}
