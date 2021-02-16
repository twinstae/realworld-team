package study.realWorld.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.realWorld.api.dto.userDtos.UserSignUpDto;
import study.realWorld.entity.Authority;
import study.realWorld.entity.User;
import study.realWorld.repository.AuthorityRepository;
import study.realWorld.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    protected AuthorityRepository authorityRepository;

    UserSignUpDto userSignUpDto = UserSignUpDto
            .builder()
            .username("홍길동")
            .email("test@naver.com")
            .password("t1e2s3t4")
            .build();

    @AfterEach
    protected void tearDown() {
        userRepository.deleteAll();
        authorityRepository.deleteAll();
    }

    @DisplayName("userSipnUpDto로 가입하면,repository에 같은 email을 가진 user가 존재한다")
    @Test
    public void userSignUpTest(){
        Authority authority = new Authority("ROLE_USER"); //권한 생성
        authorityRepository.save(authority); // 권한 저장

        userService.signUp(userSignUpDto);
        Optional<User> user = userRepository.findOneWithAuthoritiesByEmail(userSignUpDto.getEmail());
        assertThat(user).isNotEmpty();
        assertThat(user.get().getAuthorities()).isNotNull();
    }

    @DisplayName("이미 가입한 이메일로 다시 가입하려 하면, RuntimeException을 던진다")
    @Test
    public void userSignUpWithAlreadyExistEmailTest(){
        Authority authority = new Authority("ROLE_USER"); //권한 생성
        authorityRepository.save(authority); // 권한 저장

        userService.signUp(userSignUpDto); // 이미 가입

        Assertions.assertThrows(
                RuntimeException.class,
                () -> {
            userService.signUp(userSignUpDto); // 다시 가입
        });
    }

}
