package study.realWorld.api.dto.articleDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleListDto {
    @JsonProperty("articles")
    private List<ArticleDto> articles;

    @JsonProperty("articlesCount")
    private int articlesCount;

    public ArticleListDto(List<ArticleDto> articleDataList) {
        this.articles = articleDataList;
        this.articlesCount = articleDataList.size();
    }
}
