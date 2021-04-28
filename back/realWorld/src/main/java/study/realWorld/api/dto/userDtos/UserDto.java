package study.realWorld.api.dto.userDtos;

import lombok.*;
import study.realWorld.entity.User;

@Getter
@ToString
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
