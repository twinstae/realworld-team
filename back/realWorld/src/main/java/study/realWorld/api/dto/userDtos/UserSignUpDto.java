package study.realWorld.api.dto.userDtos;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.realWorld.entity.Articles;
import study.realWorld.entity.User;

@Getter
@NoArgsConstructor
public class UserSignUpDto {

    private String username;
    private String email;;
    private String password;

    @Builder
    public UserSignUpDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public User toEntity(){

        return User
                .builder()
                .userName(this.username)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
