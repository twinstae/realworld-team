package study.realWorld.api.dto.profilesDtos;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import study.realWorld.entity.Profile;
import study.realWorld.entity.User;

@Getter
@ToString
@NoArgsConstructor
public class ProfileCreateDto {

    private String username;
    private String image;
    private boolean following;

    @Builder
    public ProfileCreateDto(String username,String image,boolean follow) {
        this.username = username;
        this.image = image;
        this.following = follow;
    }

    static public Profile toEntity(User user){
        return Profile
                .builder()
                .user(user)
                .username(user.getUserName())
                .build();
    }

}
