package study.realWorld.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.TestingUtil;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.api.dto.profilesDtos.ProfileListDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProfileServiceTest extends TestingUtil {

    @Autowired
    ProfilesService profilesService;

    @BeforeEach
    public void setUp(){
        createUserInit();
        anotherUserInit();
    }

    @DisplayName("findByUsername에 찾고자하는 username을 넣으면 ProfileDto에 follwing 여부를 boolean값으로 넣어 리턴한다.")
    @Test
    public void findByUsernameTest() throws Exception {
        ProfileDto responseDto = profilesService.findByUsername(userSignUpDto2.getUsername());

        assertThat(responseDto.getUsername()).isEqualTo(userSignUpDto2.getUsername());
        assertThat(responseDto.isFollowing()).isFalse();
    }

    @Test
    public void followByUsernameTest() throws Exception {
        ProfileDto responseDto = profilesService.followByUsername(userSignUpDto2.getUsername());
        assertThat(responseDto.getUsername()).isEqualTo(userSignUpDto2.getUsername());
        assertThat(responseDto.isFollowing()).isTrue();
    }

    @Test
    public void unFollowByUsernameTest() throws Exception {
        followByUsernameTest();

        ProfileDto responseDto = profilesService.unFollowByUsername(userSignUpDto2.getUsername());
        assertThat(responseDto.getUsername()).isEqualTo(userSignUpDto2.getUsername());
        assertThat(responseDto.isFollowing()).isFalse();
    }

    @Test
    public void findProfilesFolloweesByUsernameTest() throws Exception {
        followByUsernameTest();

        ProfileListDto result = profilesService.findProfilesFolloweesByUsername(userSignUpDto.getUsername());

        assertUserNameInResult(result, userSignUpDto2.getUsername());
    }

    private void assertUserNameInResult(ProfileListDto result, String username) {
        System.out.println(result.getProfileList());
        assertThat(result.getProfileList().stream()
                .anyMatch(profileDto -> profileDto.getUsername().equals(username)))
                .isTrue();
    }

    @Transactional
    @Test
    public void findProfilesFollowersByUsernameTest() throws Exception {
        followByUsernameTest();

        ProfileListDto result = profilesService.findProfilesFollowersByUsername(userSignUpDto2.getUsername());
        assertUserNameInResult(result, userSignUpDto.getUsername());
    }
}
