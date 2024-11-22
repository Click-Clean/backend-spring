package me.khw7385.click_clean.service;

import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.domain.Category;
import me.khw7385.click_clean.domain.User;
import me.khw7385.click_clean.domain.Vote;
import me.khw7385.click_clean.dto.VoteDto;
import me.khw7385.click_clean.exception.ClickCleanException;
import me.khw7385.click_clean.exception.error_code.VoteErrorCode;
import me.khw7385.click_clean.repository.ArticleRepository;
import me.khw7385.click_clean.repository.UserRepository;
import me.khw7385.click_clean.repository.VoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith({MockitoExtension.class})
public class VoteServiceTest {
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private VoteService voteService;

    private Article article;
    private User user1;
    private User user2;
    @BeforeEach
    void beforeEach(){
        article = Article.builder()
                .title("기사1")
                .summary("요약1")
                .media("언론사1")
                .category(Category.ECONOMY)
                .author("작가1")
                .probability(BigDecimal.valueOf(52.4))
                .build();

        user1 = User.builder()
                .username("사용자1")
                .email("user1@gmail.com")
                .refreshToken("abcd")
                .social(User.SocialLogin.KAKAO)
                .build();
    }

    @Test
    @DisplayName("투표시 이미 같은 투표 값이 존재하는 경우 예외 테스트")
    void duplicateVote(){
        // given
        Vote vote = Vote.builder()
                .user(user1)
                .article(article)
                .voteValue(true)
                .build();

        given(userRepository.findById(1L)).willReturn(Optional.of(user1));
        given(articleRepository.findById(1L)).willReturn(Optional.of(article));
        given(voteRepository.findByUserAndArticle(user1, article)).willReturn(Optional.of(vote));
        // when & then
        Assertions.assertThatThrownBy(() -> voteService.vote(new VoteDto.Command(1L, 1L, true)))
                .isInstanceOf(ClickCleanException.class)
                .extracting(e -> ((ClickCleanException)e).getErrorCode())
                .isEqualTo(VoteErrorCode.DUPLICATE_VOTE_VALUE_ERROR);
    }

    @Test
    @DisplayName("투표 취소 시 투표가 존재하지 않는 경우 예외 테스트")
    void cancelVoteNotExist(){
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(user1));
        given(articleRepository.findById(1L)).willReturn(Optional.of(article));
        given(voteRepository.findByUserAndArticle(user1, article)).willReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> voteService.cancelVote(new VoteDto.Command(1L, 1L, null)))
                .isInstanceOf(ClickCleanException.class)
                .extracting(e -> ((ClickCleanException)e).getErrorCode())
                .isEqualTo(VoteErrorCode.VOTE_NOT_FOUND_ERROR);
    }

}
