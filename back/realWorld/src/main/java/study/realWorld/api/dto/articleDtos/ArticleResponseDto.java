package study.realWorld.api.dto.articleDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleResponseDto {
    private ArticleDto article;
}
