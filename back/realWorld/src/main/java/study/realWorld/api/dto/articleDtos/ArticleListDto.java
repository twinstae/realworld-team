package study.realWorld.api.dto.articleDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
@Getter
public class ArticleListDto {
    @JsonProperty("articles")
    private final List<ArticleDto> articles;

    @JsonProperty("articlesCount")
    private final int articlesCount;

    public ArticleListDto(List<ArticleDto> articleDataList) {
        this.articles = articleDataList;
        this.articlesCount = articleDataList.size();
    }
}
