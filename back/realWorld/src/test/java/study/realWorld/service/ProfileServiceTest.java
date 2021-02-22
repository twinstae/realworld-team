package study.realWorld.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.TestingUtil;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.profilesDtos.ProfileDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProfileServiceTest extends TestingUtil {

    @Autowired
    ProfilesService profilesService;

    @DisplayName("findByUsername에 찾고자하는 username을 넣으면 ProfileDto에 follwing 여부를 boolean값으로 넣어 리턴한다.")
    @Test
    public void findByUsernameTest() throws Exception {
        createUserInit();
        anotherUserInit();

        ProfileDto responseDto = profilesService.findByUsername(userSignUpDto2.getUsername());

        assertThat(responseDto.getUsername()).isEqualTo(userSignUpDto2.getUsername());
        assertThat(responseDto.isFollowing()).isFalse();
    }

    @Test
    public void followByUsernameTest() throws Exception {
        createUserInit();
        anotherUserInit();

        ProfileDto responseDto = profilesService.followByUsername(userSignUpDto2.getUsername());
        assertThat(responseDto.getUsername()).isEqualTo(userSignUpDto2.getUsername());
        assertThat(responseDto.isFollowing()).isTrue();
    }

    @Transactional
    @Test
    public void unFollowByUsernameTest() throws Exception {
        createUserInit();
        anotherUserInit();

        ProfileDto responseDto = profilesService.followByUsername(userSignUpDto2.getUsername());
        assertThat(responseDto.getUsername()).isEqualTo(userSignUpDto2.getUsername());
        assertThat(responseDto.isFollowing()).isTrue();

        responseDto = profilesService.unFollowByUsername(userSignUpDto2.getUsername());
        assertThat(responseDto.getUsername()).isEqualTo(userSignUpDto2.getUsername());
        assertThat(responseDto.isFollowing()).isFalse();
    }

}
