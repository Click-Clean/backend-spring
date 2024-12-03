package me.khw7385.click_clean.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.dto.PageResult;
import me.khw7385.click_clean.dto.Result;
import me.khw7385.click_clean.dto.ScrapDto;
import me.khw7385.click_clean.service.ScrapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="스크랩 API")
@RestController
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;

    @Operation(summary = "스크랩 생성", description = "기사에 대한 스크랩을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 생성 완료"),
            @ApiResponse(responseCode = "400", description = "기사 ID 에 해당하는 기사 없음, 사용자 ID에 해당하는 사용자 없음, 이미 스크랩한 기사입니다.")
    })
    @PostMapping("/api/scrap/save")
    public ResponseEntity<Result> createScrap(@RequestBody ScrapDto.Request request, @RequestAttribute("userId") Long userId){
        scrapService.saveScrap(request.toCommand(null, userId));

        return ResponseEntity.ok(new Result(HttpStatus.CREATED.value(), null));
    }
    @Operation(summary = "스크랩 리스트 조회", description = "사용자가 스크랩한 기사에 대한 정보를 보여준다.")
    @Parameters(value = {
            @Parameter(name = "page", description = "페이지 번호"),
            @Parameter(name = "size", description = "페이지 크기")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 리스트 조회 완료"),
            @ApiResponse(responseCode = "400", description = "사용자 ID에 해당하는 사용자 없음")
    })
    @GetMapping("/api/scraps")
    public ResponseEntity<PageResult<List<ScrapDto.Response>>> getScraps(@RequestParam("page") int page, @RequestParam("size") int size, @RequestAttribute("userId") Long userId){
        List<ScrapDto.Response> scraps = scrapService.findScraps(new ScrapDto.Command(null, userId, null), page, size);
        return ResponseEntity.ok(new PageResult<List<ScrapDto.Response>>(HttpStatus.OK.value(), page, scraps.size(), scraps));
    }
    @Operation(summary = "스크랩 삭제", description = "스크랩 ID에 해당하는 스크랩 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 삭제 완료"),
            @ApiResponse(responseCode = "400", description = "스크랩 ID 에 해당하는 스크랩 없음")
    })
    @DeleteMapping("/api/scrap/delete/{id}")
    public ResponseEntity<Result> deleteScrap(@PathVariable("id") Long id){
        scrapService.removeScrap(new ScrapDto.Command(id, null, null));
        return ResponseEntity.ok(new Result(HttpStatus.OK.value(), null));
    }
}
