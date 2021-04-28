package study.realWorld.api.dto.articleDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleListDto {
    private List<ArticleDto> articles;
    private long articlesCount;
}
