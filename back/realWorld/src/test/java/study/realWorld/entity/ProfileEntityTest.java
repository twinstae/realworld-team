package study.realWorld.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.realWorld.TestingUtil;

import javax.transaction.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProfileEntityTest extends TestingUtil {
    @Transactional
    @DisplayName("한 profile이 다른 프로파일을 follow하면 isFollow는 true이다")
    @Test
    public void userSignUpTest(){
        createUserInit();
        anotherUserInit();
        Profile profile1 = profileRepository.findOneByUsername(userSignUpDto.getUsername())
                .orElseThrow(RuntimeException::new);
        Profile profile2 = profileRepository.findOneByUsername(userSignUpDto2.getUsername())
                .orElseThrow(RuntimeException::new);

        profile1.follow(profile2);

        assertThat(profile1.isFollow(profile2)).isTrue();
    }

}
