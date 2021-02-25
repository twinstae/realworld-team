package study.realWorld.service;

import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("findBySlug함수에 slug를 넘기면 ArticleDto를 리턴한다")
    @Test
    public void findBySlug() throws Exception {
        createUserAndArticleInit();

        ArticleDto responseDto = articlesService.findBySlug(createDto.getSlug());

        assertThat(responseDto.getTitle()).isEqualTo(createDto.getTitle());
        assertInitState(responseDto);
    }

    private void assertInitState(ArticleDto responseDto) {
        assertThat(responseDto.getAuthor().isFollowing()).isEqualTo(false);
        assertThat(responseDto.getFavoritesCount()).isEqualTo(0);
    }

    @DisplayName("deleteBySlug에 slug를 넘기면 article이 삭제되어 존재하지 않는다.")
    @Test
    public void deleteBySlugTest() throws Exception {
        // given
        createUserAndArticleInit();
        assertThat(getCreatedArticle()).isPresent();

        // when
        articlesService.deleteBySlug(createDto.getSlug());

        // then
        Optional<Articles> result = getCreatedArticle();
        assertThat(result).isEmpty();
    }

    @DisplayName("createDto를 저장하면 DB에 저장하고, 똑같은 내용의 Dto를 돌려준다.")
    @Test
    public void saveTest() throws Exception {
        createUserInit();
        assertThat(getCreatedArticle()).isEmpty();

        ArticleDto articleDto = articlesService.save(createDto);

        assertDtoIsEqualTo(articleDto, createDto);
        assertThat(getCreatedArticle()).isPresent();
        assertInitState(articleDto);
    }

    private Optional<Articles> getCreatedArticle() {
        return articlesRepository.findOneWithAuthorBySlug(createDto.getSlug());
    }

    @DisplayName("createDto를 저장하면 DB에 저장하고, 똑같은 내용의 Dto를 돌려준다.")
    @Test
    public void updateArticleBySlug(){
        createUserAndArticleInit();
        ArticleDto updatedArticleDto = articlesService.updateArticleBySlug(createDto.getSlug(), updateDto);

        assertDtoIsEqualTo(updatedArticleDto, updateDto);
        assertInitState(updatedArticleDto);
    }
}
