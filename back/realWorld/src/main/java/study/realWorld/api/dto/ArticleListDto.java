package study.realWorld.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ArticleListDto {
    @JsonProperty("articles")
    private final List<ArticleResponseDto> articles;

    @JsonProperty("articlesCount")
    private final int articlesCount;

    public ArticleListDto(List<ArticleResponseDto> articleDataList) {
        this.articles = articleDataList;
        this.articlesCount = articleDataList.size();
    }
}
