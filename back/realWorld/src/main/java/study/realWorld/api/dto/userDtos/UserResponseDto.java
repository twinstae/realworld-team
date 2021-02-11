package study.realWorld.api.dto.userDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.realWorld.api.dto.articleDtos.ArticleDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponseDto {
    private UserDto user;
}
