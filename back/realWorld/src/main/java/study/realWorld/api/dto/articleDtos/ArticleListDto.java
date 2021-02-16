package study.realWorld.api.dto.articleDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
@Getter
public class ArticleListDto {
    private final List<ArticleDto> articles;

    private final int articlesCount;

    public ArticleListDto(List<ArticleDto> articleDataList) {
        this.articles = articleDataList;
        this.articlesCount = articleDataList.size();
    }
}
