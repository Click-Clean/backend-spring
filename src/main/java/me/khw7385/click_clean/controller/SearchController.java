package me.khw7385.click_clean.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.dto.PageResult;
import me.khw7385.click_clean.dto.SearchDto;
import me.khw7385.click_clean.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="검색 API")
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @Operation(summary = "키워드 검색", description = "제목과 본문에 키워드가 포함되는 기사를 조회한다.")
    @Parameters(value = {
            @Parameter(name = "keyword", description = "검색 키워드"),
            @Parameter(name = "page", description = "페이지 번호"),
            @Parameter(name = "size", description = "페이지 크기")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기사 검색 완료"),
            @ApiResponse(responseCode = "400", description = "없음")
    })
    @GetMapping("/api/search")
    public ResponseEntity<PageResult<List<SearchDto.Response>>> getArticles(@RequestParam("keyword") String keyword, @RequestParam("page") int page, @RequestParam("size") int size){
        List<SearchDto.Response> response = searchService.searchArticles(new SearchDto.Command(keyword), page, size);
        return ResponseEntity.ok(new PageResult<List<SearchDto.Response>>(HttpStatus.OK.value(), page, response.size(), response));
    }
}
