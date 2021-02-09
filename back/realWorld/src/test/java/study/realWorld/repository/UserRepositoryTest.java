package study.realWorld.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.realWorld.entity.Authority;
import study.realWorld.entity.User;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserRepositoryTest {


    @Autowired
    UserRepository userRepository;

    @Test
    public void init() throws Exception {

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();


        User user = User
                .builder()
                .userName("user1")
                .email("user1@gmail.com")
                .password("123")
                .activated(true)
                .authorities((Collections.singleton(authority)))
                .build();

        userRepository.save(user);
    }

    @Test
    public void findtest() throws Exception {
        // given

        // when

        // then

    }


}