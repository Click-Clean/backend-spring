package me.khw7385.click_clean.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.dto.Result;
import me.khw7385.click_clean.dto.ViewDto;
import me.khw7385.click_clean.service.ArticleViewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name="기사 조회 API")
@RestController
@RequiredArgsConstructor
public class ArticleViewController {
    private final ArticleViewService articleViewService;
    @Operation(summary = "단일 기사 조회", description = "기사 ID 에 해당하는 기사 정보를 보여준다.")
    @Parameters(value = {
            @Parameter(name = "id", description = "기사 ID"),
            @Parameter(name = "userViewId", description = "사용자를 식별하기 위한 UUID 값")
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "기사 조회 완료"),
        @ApiResponse(responseCode = "400", description = "기사 ID 에 해당하는 기사 없음")
    })
    @GetMapping("/api/article/{id}")
    public ResponseEntity<Result<ViewDto.ArticleResponse>> getArticle(@PathVariable("id")Long articleId, @CookieValue(value = "userViewId", required = false)String userViewId, HttpServletResponse servletResponse) {
        if(userViewId == null){
            Cookie cookie = makeCookie();
            userViewId = cookie.getValue();
            servletResponse.addCookie(cookie);
        }
        ViewDto.ArticleResponse response = articleViewService.viewArticle(new ViewDto.Command(userViewId, articleId));
        return ResponseEntity.ok(new Result<>(HttpStatus.OK.value(), response));
    }

    @Operation(summary = "기사 조회수 TOP 5 조회", description = "조회 수가 높은 기사를 내림차순으로 보여준다. 기사는 기사 ID, 기사제목으로 이루어진다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기사 조회수 TOP 5 조회 완료"),
            @ApiResponse(responseCode = "400", description = "없음")
    })
    @GetMapping("/api/article/views")
    public ResponseEntity<Result<List<ViewDto.ViewResponse>>> getArticle(){
        List<ViewDto.ViewResponse> response = articleViewService.findArticlesTop5(new ViewDto.Command(null, null));

        return ResponseEntity.ok(new Result<>(HttpStatus.OK.value(), response));
    }

    private Cookie makeCookie(){
        String userViewId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("userViewId", userViewId);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
