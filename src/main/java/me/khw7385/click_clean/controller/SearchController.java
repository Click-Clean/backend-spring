package me.khw7385.click_clean.controller;

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

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/api/search")
    public ResponseEntity<PageResult> getArticles(@RequestParam("keyword") String keyword, @RequestParam("page") int page, @RequestParam("size") int size){
        List<SearchDto.Response> response = searchService.searchArticles(new SearchDto.Command(keyword), page, size);
        return ResponseEntity.ok(new PageResult(HttpStatus.OK.value(), page, response.size(), response));
    }
}
