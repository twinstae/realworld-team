package study.realWorld.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.realWorld.ArticlesTestingUtil;
import study.realWorld.api.dto.ArticleResponseDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ArticlesServiceTest extends ArticlesTestingUtil {

    @Autowired
    ArticlesService articlesService;

    @Test
    public void findBySlug() throws Exception {
        createArticleInit();

        ArticleResponseDto responseDto = articlesService.findBySlug(dto.getSlug());

        assertThat(responseDto.getTitle()).isEqualTo(dto.getTitle());
    }

}
