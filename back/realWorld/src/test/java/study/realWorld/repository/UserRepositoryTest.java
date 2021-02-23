package study.realWorld.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.realWorld.TestingUtil;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.User;


import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends TestingUtil {
    String email = "user1@gmail.com";

    @DisplayName("Email을 가지고 User를 Authorities와 join해서 가져온다.")
    @Test
    public void findUserWithAuthoritiesTest() throws Exception {
        createUserInit();

        User user = userRepository.findOneWithAuthoritiesByEmail(email).orElseThrow(ResourceNotFoundException::new);

        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getAuthorities().isEmpty()).isFalse();
    }
}