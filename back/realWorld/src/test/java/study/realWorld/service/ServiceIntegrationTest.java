package study.realWorld.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.realWorld.TestingUtil;
import study.realWorld.api.dto.articleDtos.ArticleDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ServiceIntegrationTest extends TestingUtil {
    @Autowired
    ArticlesService articlesService;
    @Autowired
    ProfilesService profilesService;

    @BeforeEach
    public void setUp(){
        createUserInit();
        anotherUserInit();
        getToken(userSignInDto);
        createArticleInit();
    }

    @DisplayName("article을 favortie한 다음에 findBySlug하면 isFavorited는 true이고 getFavoritesCount는 1이다")
    @Test
    public void getFavoritedArticleTest(){
        articlesService.favoriteArticleBySlug(createDto.getSlug());

        ArticleDto articleDto = articlesService.findBySlug(createDto.getSlug());

        assertThat(articleDto.isFavorited()).isEqualTo(true);
        assertThat(articleDto.getFavoritesCount()).isEqualTo(1);
    }
}
