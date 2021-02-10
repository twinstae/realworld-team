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
import java.util.Date;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest()
public class TokenProviderTest {

    TokenProvider tokenProvider;

    String username;
    String secret;
    String AUTHORITIES_KEY = "auth";
    String[] authorities = {"USER"};
    Date validity;
    Key key;

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

    //ExpiredJwtException – if the specified JWT is a Claims JWT and the Claims has an
    // expiration time before the time this method is invoked.
    @DisplayName("만료된 JWT 토큰입니다. Test")
    @Test
    public void ExpiredJwtExceptionTokenTest() throws Exception {

        String base = "s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6s1e2c3r4e5t6";
        secret = base+base;
        long tokenValidityInSeconds = 60L * 60L * 24L * -1;
        validity = new Date( (new Date()).getTime() + tokenValidityInSeconds);
        tokenProvider = new TokenProvider(secret, tokenValidityInSeconds);
        tokenProvider.afterPropertiesSet();
        Authentication authentication = createAuthentication();

        String token = createToken(secret,authentication);

        Assertions.assertThrows(ExpiredJwtException.class, () -> {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        });

        //assertThat("nothing").isEqualTo("error!");
    }


    //parseClaimsJws에 제대로된 token 들어가지 않으면 MalformedJwtException이 발생한다.
    //MalformedJwtException – if the claimsJws string is not a valid JWS
    @DisplayName("잘못된 JWT 서명입니다.")
    @Test
    public void MalformedJwtExceptionTest() throws Exception {

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);

        Assertions.assertThrows(MalformedJwtException.class, () -> {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws("errToken").getBody();
        });
    }

    //key를 제대로 생성해주지 않으면 IllegalArgumentException이 발생한다.
    //IllegalArgumentException – if the claimsJws string is null or empty or only whitespace
    @DisplayName("JWT 토큰이 잘못되었습니다.")
    @Test
    public void IllegalArgumentExceptionTest() throws Exception {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Jwts.parserBuilder().setSigningKey(key).build();
        });
    }


    //UnsupportedJwtException – if the claimsJws argument does not represent an Claims JWS
    @DisplayName("지원되지 않는 JWT 토큰입니다.")
    @Test
    public void UnsupportedJwtExceptionTest() throws Exception {
        username = "user1";
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return username;
            }
        };

        //다음의 과정을 거쳐서 authentication을 만든다.
        Authentication authentication = new TestingAuthenticationToken(principal, null, authorities);

        String token = createToken(secret,authentication);

        Assertions.assertThrows(UnsupportedJwtException.class, () -> {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        });
    }


    @Test
    public void createTokenTest () throws Exception {

        Authentication authentication = createAuthentication();

        String jwt = tokenProvider.createToken(authentication);
        System.out.println("jwt = " + jwt);

        Authentication auth_token = tokenProvider.getAuthentication(jwt);
        System.out.println(auth_token);

        assertThat(tokenProvider.validateToken(jwt)).isTrue();


        String token = createToken(secret,authentication);

        System.out.println("token parsing = " + Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token));

        assertThat(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().containsValue("USER"));
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

        //다음의 과정을 거쳐서 authentication을 만든다.
        Authentication authentication = new TestingAuthenticationToken(principal, null, authorities);
        System.out.println("authentication = " + authentication);

        return authentication;
    }

    public String createToken(String secret,Authentication authentication) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);

        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();

        return token;
    }

}
