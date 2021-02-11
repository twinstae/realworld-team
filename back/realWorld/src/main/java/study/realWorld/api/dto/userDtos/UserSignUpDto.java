package study.realWorld.api.dto.userDtos;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.realWorld.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpDto {

    private String username;
    private String email;;
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder){
        return User
                .builder()
                .userName(this.username)
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .build();
    }
}
