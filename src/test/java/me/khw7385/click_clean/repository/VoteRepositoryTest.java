package me.khw7385.click_clean.repository;

import me.khw7385.click_clean.config.TestConfig;
import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.domain.User;
import me.khw7385.click_clean.domain.Vote;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

@DataJpaTest
@Import(TestConfig.class)
public class VoteRepositoryTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private VoteRepository voteRepository;

    private User user1;
    private User user2;
    private Article article;

    @BeforeEach
    void beforeEach(){
        user1 = User.builder()
                .username("사용자1")
                .email("user1@gmail.com")
                .refreshToken("abcd")
                .social(User.SocialLogin.KAKAO)
                .build();
        user2 = User.builder()
                .username("사용자2")
                .email("user2@gmail.com")
                .refreshToken("abcd")
                .social(User.SocialLogin.KAKAO)
                .build();

        article = Article.builder()
                .title("뉴스 기사1")
                .body("뉴스 내용1")
                .summary("뉴스 요약1")
                .media("언론사1")
                .category("사회")
                .author("기자1")
                .url("기사1.com")
                .probability(BigDecimal.valueOf(11.1))
                .build();

        em.persist(user1);
        em.persist(user2);
        em.persist(article);
    }

    @Test
    @DisplayName("좋아요, 싫어요 투표 데이터 개수 세기")
    void countVotes(){
        // given
        Vote vote1 = Vote.builder()
                .user(user1)
                .article(article)
                .voteValue(true)
                .build();
        Vote vote2 = Vote.builder()
                .user(user2)
                .article(article)
                .voteValue(true)
                .build();
        voteRepository.save(vote1);
        voteRepository.save(vote2);

        // when
        int trueCount = voteRepository.countTrueVotes(article);
        int falseCount = voteRepository.countFalseVotes(article);

        // then
        Assertions.assertThat(trueCount).isEqualTo(2);
        Assertions.assertThat(falseCount).isEqualTo(0);
    }
}
