package study.realWorld.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.realWorld.TestingUtil;

import javax.transaction.Transactional;

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
        initFollow();

        assertThat(profile1.isFollow(profile2)).isTrue();
        assertThat(profile2.isFollowedBy(profile1)).isTrue();
    }

    private void initFollow() {
        profile1.follow(profile2); //1이 2를 팔로우했다.그러면 Profile1의 follower가 1이 되고, Profilie2의 follwee가 1이된다.
    }

    // unfollow 테스트
    @Transactional
    @DisplayName("한 Profile이 다른 Profile을 unFollow하면 isFollow는 false이다")
    @Test
    public void unFollowTest() throws Exception {
        setUp();
        initFollow();

        profile1.unfollow(profile2);

        assertThat(profile1.isFollow(profile2)).isFalse();
    }

    @Transactional
    @DisplayName("profile1이 profile2를 follow했을 때, profile2의 followers를 가져오면 한 명이다.")
    @Test
    public void getFollowersTest() throws Exception {
        setUp();
        initFollow();

        assertThat(profile2.getFollowers().size()).isEqualTo(1);
    }

    @Transactional
    @DisplayName("profile1이 profile2를 follow했을 때, profile1의 followees를 가져오면 한 명이다.")
    @Test
    public void getFolloweesTest() throws Exception {
        setUp();
        initFollow();

        assertThat(profile1.getFollowees().size()).isEqualTo(1);
    }

    @Transactional
    @DisplayName("profile이 article을 favortie하면... -> profile이 article을 haveFavorited는 true")
    @Test
    public void favoriteTest(){
        setUp();
        createArticleInit();
        Articles article1 = articlesService.getArticleBySlugOr404(createDto.getSlug());

        profile1.favorite(article1);

        assertThat(profile1.haveFavorited(article1)).isEqualTo(true);
        assertThat(article1.getFavoritesCount()).isEqualTo(1);
    }

    @Transactional
    @DisplayName("profile이 article을 favortie하면... -> profile이 article을 haveFavorited는 true")
    @Test
    public void unfavoriteTest(){
        favoriteTest();
        Articles article1 = articlesService.getArticleBySlugOr404(createDto.getSlug());

        profile1.unfavorite(article1);

        assertThat(profile1.haveFavorited(article1)).isEqualTo(false);
        assertThat(article1.getFavoritesCount()).isEqualTo(0);
    }
}
