package study.realWorld.api.dto.userDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.realWorld.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String username;
    private String email;

    public static UserDto fromUser(User user){
        return UserDto
                .builder()
                .username(user.getUserName())
                .email(user.getEmail())
                .build();
    }
}
