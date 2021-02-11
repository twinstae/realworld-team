package study.realWorld.api.dto.userDtos;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import study.realWorld.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpDto {

    private String username;
    private String email;;
    private String password;

    public User toEntity(){
        return User
                .builder()
                .userName(this.username)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
