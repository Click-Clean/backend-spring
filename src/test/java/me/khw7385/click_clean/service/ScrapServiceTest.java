package me.khw7385.click_clean.service;

import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.domain.Category;
import me.khw7385.click_clean.domain.Scrap;
import me.khw7385.click_clean.domain.User;
import me.khw7385.click_clean.dto.ScrapDto;
import me.khw7385.click_clean.exception.ClickCleanException;
import me.khw7385.click_clean.exception.error_code.ScrapErrorCode;
import me.khw7385.click_clean.repository.ArticleRepository;
import me.khw7385.click_clean.repository.ScrapRepository;
import me.khw7385.click_clean.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ScrapServiceTest {
    @Mock
    private ScrapRepository scrapRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ScrapService scrapService;

    private User user;
    private Article article1;
    private Article article2;

    @BeforeEach
    void beforeEach(){
        user = User.builder()
                .username("사용자1")
                .email("user@gmail.com")
                .refreshToken("abcde")
                .social(User.SocialLogin.KAKAO)
                .build();

        article1 = Article.builder()
                .title("기사1")
                .summary("요약1")
                .media("언론사1")
                .category(Category.ECONOMY)
                .author("작가1")
                .probability(BigDecimal.valueOf(52.4))
                .build();

        article2 = Article.builder()
                .title("기사2")
                .summary("요약2")
                .media("언론사2")
                .category(Category.SOCIETY)
                .author("작가2")
                .probability(BigDecimal.valueOf(75.8))
                .build();
    }
    @Test
    @DisplayName("이미 존재하는 기사에 대한 스크랩 저장 시 예외")
    void saveScrapFailsWhenDuplicateExits(){
        // given
        Scrap scrap1 = Scrap.builder()
                .user(user)
                .article(article1)
                .build();
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(articleRepository.findById(1L)).willReturn(Optional.of(article1));
        given(scrapRepository.findByUserAndArticle(user, article1)).willReturn(Optional.of(scrap1));

        // when & then
        Assertions.assertThatThrownBy(() -> scrapService.saveScrap(new ScrapDto.Command(null, 1L, 1L)))
                .isInstanceOf(ClickCleanException.class)
                .extracting(e -> ((ClickCleanException) e).getErrorCode())
                .isEqualTo(ScrapErrorCode.SCRAP_ALREADY_EXISTS_ERROR);
    }

    @Test
    @DisplayName("스크랩 리스트 조회")
    void findScraps(){
        // given
        Scrap scrap1 = Scrap.builder()
                .user(user)
                .article(article1)
                .build();

        Scrap scrap2 = Scrap.builder()
                .user(user)
                .article(article2)
                .build();

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(scrapRepository.findScrapsByUser(user, 0, 10)).willReturn(Arrays.asList(scrap1, scrap2));
        // when
        List<ScrapDto.Response> response = scrapService.findScraps(new ScrapDto.Command(null, 1L, 1L), 0, 10);
        // then
        Assertions.assertThat(response.size()).isEqualTo(2);
    }
}
