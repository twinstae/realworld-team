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

        //findBySlug로 찾은 것을 dto로 포장해서 올려준다.

        createArticleInit();
        ArticleResponseDto responseDto = articlesService.findBySlug(dto.getSlug());

        System.out.println(responseDto);
        assertThat(responseDto.getTitle()).isEqualTo(dto.getTitle());
    }

}
