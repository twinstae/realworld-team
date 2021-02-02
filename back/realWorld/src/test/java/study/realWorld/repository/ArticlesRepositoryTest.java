package study.realWorld.repository;

import org.junit.jupiter.api.Test;
import study.realWorld.ArticlesTestingUtil;
import study.realWorld.entity.Articles;


class ArticlesRepositoryTest extends ArticlesTestingUtil {

    @Test
    public void findOneBySlugTest() throws Exception {

        articlesRepository.save(dto.toEntity()); // 이렇게 하면 ArticleCreateDto를 Entity로 바꿔서 repository에 저장.
        Articles articles = articlesRepository.findOneBySlug(dto.getSlug());
        assertEqualToDto(articles, dto);
    }
}