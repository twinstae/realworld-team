package study.realWorld.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import study.realWorld.TestingUtil;
import study.realWorld.entity.Profile;
import study.realWorld.entity.User;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserServiceTest extends TestingUtil {

    @DisplayName("userSipnUpDto로 가입하면,repository에 같은 email을 가진 user가 존재한다")
    @Test
    public void userSignUpTest(){
        authorityInit();

        userService.signUp(userSignUpDto);

        Optional<User> user = userRepository.findOneWithAuthoritiesByEmail(userSignUpDto.getEmail());
        Optional<Profile> profile = profileRepository.findOneByUsername(userSignUpDto.getUsername());
        assertThat(user).isNotEmpty();
        assertThat(profile).isNotEmpty();
    }

    // 테스트에서 기대하는 결과가 뭐지?
    //

    @DisplayName("이미 가입한 이메일로 다시 가입하려 하면, RuntimeException을 던진다")
    @Test
    public void userSignUpWithAlreadyExistEmailTest(){
        authorityInit();

        userService.signUp(userSignUpDto); // 이미 가입

        Assertions.assertThrows(
                RuntimeException.class,
                () -> {
            userService.signUp(userSignUpDto); // 다시 가입
        });
    }

}
