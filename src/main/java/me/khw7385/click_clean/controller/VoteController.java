package me.khw7385.click_clean.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.dto.Result;
import me.khw7385.click_clean.dto.VoteDto;
import me.khw7385.click_clean.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "투표 API")
@RestController
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    @Operation(summary = "기사 투표", description = "기사에 대하여 투표를 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기사 투표 완료"),
            @ApiResponse(responseCode = "400", description = "기사 ID 에 해당하는 기사 없음, 사용자 ID에 해당하는 사용자 없음, 기사에 대한 중복된 투표 값 존재")
    })
    @PostMapping("/api/vote")
    public ResponseEntity<Result<VoteDto.Response>> createVote(@RequestBody VoteDto.Request request, @RequestAttribute("userId")Long userId){
        voteService.vote(request.toCommand(userId));
        return ResponseEntity.ok(new Result<>(HttpStatus.OK.value(), voteService.countVote(request.toCommand(userId))));
    }
    @Operation(summary = "투표 취소", description = "기사에 대한 투표를 취소한다.")
    @Parameters(value = {
            @Parameter(name = "id", description = "기사 ID")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표 취소 완료"),
            @ApiResponse(responseCode = "400", description = "기사 ID 에 해당하는 기사 없음, 사용자 ID에 해당하는 사용자 없음, 투표값이 없음")
    })
    @DeleteMapping("/api/vote/cancel")
    public ResponseEntity<Result> deleteVote(@RequestParam("id") Long articleId, @RequestAttribute("userId") Long userId){
        voteService.cancelVote(new VoteDto.Command(userId, articleId, null));
        return ResponseEntity.ok(new Result(HttpStatus.OK.value(), voteService.countVote(new VoteDto.Command(userId, articleId, null))));
    }
}
