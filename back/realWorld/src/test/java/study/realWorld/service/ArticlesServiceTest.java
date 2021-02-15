package study.realWorld.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.realWorld.TestingUtil;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.entity.Articles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ArticlesServiceTest extends TestingUtil {

    @Autowired
    ArticlesService articlesService;

    @Test
    public void findBySlug() throws Exception {
        createUserAndArticleInit();

        ArticleDto responseDto = articlesService.findBySlug(createDto.getSlug());

        assertThat(responseDto.getTitle()).isEqualTo(createDto.getTitle());
    }

    @Test
    public void deleteBySlugTest() throws Exception {
        // given
        createUserAndArticleInit();

        // when
        articlesService.deleteBySlug(createDto.getSlug());

        // then
        Optional<Articles> result = articlesRepository.findOneBySlug(createDto.getSlug());
        assertThat(result).isEmpty();
    }

    @Test
    public void saveTest() throws Exception {
        ArticleDto articleDto = articlesService.save(createDto);
        assertDtoIsEqualTo(articleDto,createDto);
    }

    @Test
    public void updateArticleBySlug(){
        articlesService.save(createDto);
        ArticleDto updatedArticleDto = articlesService.updateArticleBySlug(createDto.getSlug(), updateDto);

        assertDtoIsEqualTo(updatedArticleDto, updateDto);
    }
}
