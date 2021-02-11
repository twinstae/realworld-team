package study.realWorld.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.realWorld.api.dto.userDtos.UserSignUpDto;
import study.realWorld.repository.UserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    UserSignUpDto userSignUpDto = UserSignUpDto
            .builder()
            .username("홍길동")
            .email("test@naver.com")
            .password("t1e2s3t4")
            .build();

    @AfterEach
    protected void tearDown() {
        userRepository.deleteAll();
    }

    @DisplayName("userSipnUpDto로 가입하면,repository에 같은 email을 가진 user가 존재한다")
    @Test
    public void userSaveTest(){
        userService.signUp(userSignUpDto);

        assertThat(userRepository.findByEmail(userSignUpDto.getEmail())).isNotEmpty();
    }

    @DisplayName("이미 존재하는 이메일로 가입하려 하면, RuntimeException을 던진다")
    @Test
    public void userSaveWithAlreadyExistEmailTest(){
        userService.signUp(userSignUpDto);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> {
            userService.signUp(userSignUpDto);
        });
    }

}
