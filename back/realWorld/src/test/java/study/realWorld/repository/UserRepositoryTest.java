package study.realWorld.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Authority;
import study.realWorld.entity.User;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserRepository userRepository;

    String email = "user1@gmail.com";

    @BeforeEach
    public void init() throws Exception {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        authorityRepository.save(authority);

        User user = User
                .builder()
                .userName("user1")
                .email(email)
                .password("123")
                .authorities(Collections.singleton(authority))
                .build();

        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @DisplayName("Email을 가지고 User를 Authorities와 join해서 가져온다.")
    @Test
    public void findUserWithAuthoritiesTest() throws Exception {
        User user = userRepository.findOneWithAuthoritiesByEmail(email).orElseThrow(ResourceNotFoundException::new);

        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getAuthorities().isEmpty()).isFalse();
    }
}