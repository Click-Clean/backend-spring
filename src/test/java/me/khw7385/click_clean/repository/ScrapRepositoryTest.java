package me.khw7385.click_clean.repository;

import me.khw7385.click_clean.config.TestConfig;
import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.domain.Category;
import me.khw7385.click_clean.domain.Scrap;
import me.khw7385.click_clean.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
@Import(TestConfig.class)
public class ScrapRepositoryTest {

    @Autowired
    private ScrapRepository scrapRepository;

    @Autowired
    private TestEntityManager em;

    private User user;
    private Article article1;
    private Article article2;

    @BeforeEach
    void beforeEach(){
        user = User.builder()
                .username("aaa")
                .email("aaa@gmail.com")
                .refreshToken("abcd")
                .social(User.SocialLogin.KAKAO)
                .build();

        article1 = Article.builder()
                .title("뉴스 기사1")
                .body("뉴스 내용1")
                .summary("뉴스 요약1")
                .media("언론사1")
                .category(Category.SOCIETY)
                .author("기자1")
                .url("기사1.com")
                .probability(BigDecimal.valueOf(11.1))
                .build();
        article2 = Article.builder()
                .title("뉴스 기사2")
                .body("뉴스 내용2")
                .summary("뉴스 요약2")
                .media("언론사2")
                .category(Category.SOCIETY)
                .author("기자2")
                .url("기사2.com")
                .probability(BigDecimal.valueOf(22.2))
                .build();

        em.persist(user);
        em.persist(article1);
        em.persist(article2);
    }

    @Test
    @DisplayName("사용자 아이디와 기사 아이디로 스크렙 데이터 조회, 이 때 스크랩 데이터는 없을 수도 있다.")
    void findByUserAndArticle(){
        //given
        Scrap scrap1 = Scrap.builder()
                .user(user)
                .article(article1)
                .build();

        scrapRepository.save(scrap1);

        //when
        Scrap foundScrap1 = scrapRepository.findByUserAndArticle(user, article1).get();
        Scrap foundScrap2 = scrapRepository.findByUserAndArticle(user, article2).orElse(null);

        //then
        Assertions.assertThat(foundScrap1).isNotNull();
        Assertions.assertThat(foundScrap2).isNull();
    }

    @Test
    @DisplayName("사용자 아이디로 스크랩 데이터 조회, 기사 데이터도 함께 조회")
    void findScrapsById(){
        //given
        Scrap scrap1 = Scrap.builder()
                .user(user)
                .article(article1)
                .build();

        Scrap scrap2 = Scrap.builder()
                .user(user)
                .article(article2)
                .build();


        scrapRepository.save(scrap1);
        scrapRepository.save(scrap2);
        //when
        List<Scrap> scraps = scrapRepository.findScrapsByUser(user, 0, 10);
        Article foundArticle1 = scraps.get(0).getArticle();
        Article foundArticle2 = scraps.get(1).getArticle();
        //then
        Assertions.assertThat(scraps.size()).isEqualTo(2);
        Assertions.assertThat(foundArticle1.getTitle()).isEqualTo("뉴스 기사1");
        Assertions.assertThat(foundArticle2.getTitle()).isEqualTo("뉴스 기사2");
    }
}
