package study.realWorld.repository;

import org.junit.jupiter.api.Test;
import study.realWorld.ArticlesTestingUtil;
import study.realWorld.entity.Articles;


class ArticlesRepositoryTest extends ArticlesTestingUtil {

    @Test
    public void findOneBySlugTest() throws Exception {
        createArticleInit();

        Articles articles = articlesRepository.findOneBySlug(dto.getSlug());
        assertEqualToDto(articles, dto);
    }
}