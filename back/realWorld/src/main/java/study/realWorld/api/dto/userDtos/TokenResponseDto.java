package study.realWorld.api.dto.userDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenResponseDto {
    private UserWithTokenDto user;
}
