package study.realWorld.repository;

import org.junit.jupiter.api.Test;
import study.realWorld.TestingUtil;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Articles;


class ArticlesRepositoryTest extends TestingUtil {

    @Test
    public void findOneBySlugTest() throws Exception {
        createUserAndArticleInit();

        Articles articles = articlesRepository.findOneBySlug(createDto.getSlug()).orElseThrow(ResourceNotFoundException::new);
        assertArticlesEqualToDto(articles, createDto);
    }
}