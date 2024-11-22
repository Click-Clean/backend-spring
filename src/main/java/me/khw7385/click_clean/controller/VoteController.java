package me.khw7385.click_clean.controller;

import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.dto.Result;
import me.khw7385.click_clean.dto.VoteDto;
import me.khw7385.click_clean.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/api/vote")
    public ResponseEntity<Result> createVote(@RequestBody VoteDto.Request request, @RequestParam("userId") Long userId){
        voteService.vote(request.toCommand(userId));
        return ResponseEntity.ok(new Result(HttpStatus.OK.value(), voteService.countVote(request.toCommand(userId))));
    }

    @DeleteMapping("/api/vote/cancel")
    public ResponseEntity<Result> deleteVote(@RequestParam("articleId") Long articleId, @RequestParam("userId") Long userId){
        voteService.cancelVote(new VoteDto.Command(userId, articleId, null));
        return ResponseEntity.ok(new Result(HttpStatus.OK.value(), voteService.countVote(new VoteDto.Command(userId, articleId, null))));
    }
}
