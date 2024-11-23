package me.khw7385.click_clean.controller;

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

@RestController
@RequiredArgsConstructor
public class ArticleViewController {
    private ArticleViewService articleViewService;

    @GetMapping("/api/article/{id}")
    public ResponseEntity<Result> getArticle(@PathVariable("id")Long articleId, @CookieValue(value = "userViewId", required = false)String userViewId, HttpServletResponse servletResponse) {
        if(userViewId == null){
            Cookie cookie = makeCookie();
            userViewId = cookie.getValue();
            servletResponse.addCookie(cookie);
        }
        ViewDto.ArticleResponse response = articleViewService.viewArticle(new ViewDto.Command(userViewId, articleId));
        return ResponseEntity.ok(new Result(HttpStatus.OK.value(), response));
    }

    @GetMapping("/api/article/views")
    public ResponseEntity<Result> getArticle(){
        List<ViewDto.ViewResponse> response = articleViewService.findArticlesTop5(new ViewDto.Command(null, null));

        return ResponseEntity.ok(new Result(HttpStatus.OK.value(), response));
    }

    private Cookie makeCookie(){
        String userViewId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("userViewId", userViewId);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
