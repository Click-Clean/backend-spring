package me.khw7385.click_clean.repository;

import me.khw7385.click_clean.config.TestConfig;
import me.khw7385.click_clean.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(TestConfig.class)
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("기사 제목과 본문에서 검색 키워드가 존재하는 기사 리스트 반환 테스트")
    void findArticlesByKeywordOrderByCreatedAtDesc(){
        // given
        List<Article> articles = new ArrayList<>();

        for (int i = 0; i < 100; i++){
            Article article = Article.builder()
                    .title("Test title " + i + (i % 2 == 0 ? " keyword" : ""))
                    .body("Test body " + i + (i % 3 == 0 ? " keyword" : ""))
                    .url("test.com")
                    .media("media")
                    .summary("summary")
                    .author("author")
                    .probability(BigDecimal.valueOf(75.4))
                    .category("사회")
                    .build();
            articles.add(article);
            em.persist(article);
        }

        // when
        List<Article> foundArticles
                = articleRepository.findArticlesByKeywordOrderByCreatedAtDesc("keyword", 0, 10);
        // then
        assertThat(foundArticles)
                .isNotNull()
                .hasSize(10)
                .allSatisfy(article ->
                    assertThat(article.getTitle().contains("keyword") || article.getBody().contains("keyword"))
                            .isTrue()
                );

        assertThat(foundArticles)
                .extracting(Article::getCreatedAt)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }
}
