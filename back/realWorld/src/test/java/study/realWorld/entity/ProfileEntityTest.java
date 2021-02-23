package study.realWorld.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.realWorld.TestingUtil;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProfileEntityTest extends TestingUtil {

    Profile profile1;
    Profile profile2;

    private void setUp(){
        createUserInit();
        anotherUserInit();

        profile1 = profileRepository.findOneByUsername(userSignUpDto.getUsername())
                .orElseThrow(RuntimeException::new);
        profile2 = profileRepository.findOneByUsername(userSignUpDto2.getUsername())
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    @DisplayName("한 profile이 다른 프로파일을 follow하면 isFollow는 true이다")
    @Test
    public void followTest(){
        setUp();

        profile1.follow(profile2);

        assertThat(profile1.isFollow(profile2)).isTrue();
    }

    // unfollow 테스트
    @Transactional
    @DisplayName("한 Profile이 다른 Profile을 unFollow하면 isFollow는 false이다")
    @Test
    public void unFollowTest() throws Exception {
        setUp();
        profile1.follow(profile2);

        profile1.unfollow(profile2);

        List<Profile> followers = profile2.getFollowers();
        assertThat(profile1.isFollow(profile2)).isFalse();
    }

    @Transactional
    @DisplayName("profile1이 profile2를 follow했을 때, profile2의 followers를 가져오면 한 명이다.")
    @Test
    public void getFollowersTest() throws Exception {
        setUp();
        profile1.follow(profile2);

        List<Profile> followers = profile2.getFollowers();
        System.out.println(followers);
        assertThat(followers.size()).isEqualTo(1);
    }

    @Transactional
    @DisplayName("profile1이 profile2를 follow했을 때, profile1의 followees를 가져오면 한 명이다.")
    @Test
    public void getFolloweesTest() throws Exception {
        setUp();
        profile1.follow(profile2);

        assertThat(profile1.getFollowees().size()).isEqualTo(1);
    }
}
