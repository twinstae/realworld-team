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
    public void followTest(){
        createUserInit();
        anotherUserInit();

        Profile profile1 = profileRepository.findOneByUsername(userSignUpDto.getUsername())
                .orElseThrow(RuntimeException::new);
        Profile profile2 = profileRepository.findOneByUsername(userSignUpDto2.getUsername())
                .orElseThrow(RuntimeException::new);

        profile1.follow(profile2);

        assertThat(profile1.isFollow(profile2)).isTrue();
    }

    // unfollow 테스트
    @Transactional
    @DisplayName("한 Profile이 다른 Profile을 unFollow하면 isFollow는 false이다")
    @Test
    public void unFollowTest() throws Exception {
        createUserInit();
        anotherUserInit();

        Profile profile1 = profileRepository.findOneByUsername(userSignUpDto.getUsername())
                .orElseThrow(RuntimeException::new);
        Profile profile2 = profileRepository.findOneByUsername(userSignUpDto2.getUsername())
                .orElseThrow(RuntimeException::new);

        profile1.follow(profile2);
        assertThat(profile1.isFollow(profile2)).isTrue();
        profile1.unfollow(profile2);
        assertThat(profile1.isFollow(profile2)).isFalse();
    }


    // isFollow 테스트 이미 위에서 했는데 어떻게 확인..?
    @Test
    public void isFollowTest() throws Exception {
        // given

        // when

        // then

    }

}
