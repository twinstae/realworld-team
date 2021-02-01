package study.realWorld.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class LoginUserResponseDto {

    private String username;
    private String email;

    @Builder
    public LoginUserResponseDto(String username, String email) {
        this.username = username;
        this.email = email;
    }


}
