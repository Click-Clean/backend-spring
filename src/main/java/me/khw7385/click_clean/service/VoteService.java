package me.khw7385.click_clean.service;

import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.domain.Article;
import me.khw7385.click_clean.domain.User;
import me.khw7385.click_clean.domain.Vote;
import me.khw7385.click_clean.dto.VoteDto;
import me.khw7385.click_clean.exception.ClickCleanException;
import me.khw7385.click_clean.exception.error_code.ArticleErrorCode;
import me.khw7385.click_clean.exception.error_code.UserErrorCode;
import me.khw7385.click_clean.exception.error_code.VoteErrorCode;
import me.khw7385.click_clean.repository.ArticleRepository;
import me.khw7385.click_clean.repository.UserRepository;
import me.khw7385.click_clean.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void vote(VoteDto.Command command){
        User user = userRepository.findById(command.userId()).orElseThrow(() -> new ClickCleanException(UserErrorCode.USER_NOT_FOUND_ERROR));
        Article article = articleRepository.findById(command.articleId()).orElseThrow(() -> new ClickCleanException(ArticleErrorCode.ARTICLE_NOT_FOUND_ERROR));

        Optional<Vote> optionalVote = voteRepository.findByUserAndArticle(user, article);
        if(optionalVote.isPresent()){
            Vote vote = optionalVote.get();
            if(command.value() == vote.isVoteValue()){
                throw new ClickCleanException(VoteErrorCode.DUPLICATE_VOTE_VALUE_ERROR);
            }
            voteRepository.remove(vote);
        }
        voteRepository.save(toEntity(user, article, command.value()));
    }

    @Transactional
    public void cancelVote(VoteDto.Command command){
        User user = userRepository.findById(command.userId()).orElseThrow(() -> new ClickCleanException(UserErrorCode.USER_NOT_FOUND_ERROR));
        Article article = articleRepository.findById(command.articleId()).orElseThrow(() -> new ClickCleanException(ArticleErrorCode.ARTICLE_NOT_FOUND_ERROR));
        Optional<Vote> optionalVote = voteRepository.findByUserAndArticle(user, article);

        if (optionalVote.isPresent()){
            voteRepository.remove(optionalVote.get());
        }else{
            throw new ClickCleanException(VoteErrorCode.VOTE_NOT_FOUND_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public VoteDto.Response countVote(VoteDto.Command command){
        Article article = articleRepository.findById(command.articleId()).orElseThrow(() -> new ClickCleanException(ArticleErrorCode.ARTICLE_NOT_FOUND_ERROR));
        int trueCount = voteRepository.countTrueVotes(article);
        int falseCount = voteRepository.countFalseVotes(article);

        return new VoteDto.Response(trueCount, falseCount);
    }


    private Vote toEntity(User user, Article article, boolean value){
        return Vote.builder()
                .voteValue(value)
                .user(user)
                .article(article)
                .build();
    }
}
