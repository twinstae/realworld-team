package study.realWorld.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.realWorld.ArticlesTestingUtil;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.entity.Articles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ArticlesServiceTest extends ArticlesTestingUtil {

    @Autowired
    ArticlesService articlesService;

    @Test
    public void findBySlug() throws Exception {
        createArticleInit();

        ArticleDto responseDto = articlesService.findBySlug(createDto.getSlug());

        assertThat(responseDto.getTitle()).isEqualTo(createDto.getTitle());
    }

    @Test
    public void deleteBySlugTest() throws Exception {
        // given
        createArticleInit();

        // when
        articlesService.deleteBySlug(articles.getSlug());

        Optional<Articles> result = articlesRepository.findOneBySlug(articles.getSlug());

        // then
        assertThat(result).isEmpty();
    }

    @Test
    public void saveTest() throws Exception {

        ArticleDto articleDto = articlesService.save(createDto);


        assertDtoIsEqualTo(articleDto,createDto);

    }


}
