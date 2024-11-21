package me.khw7385.click_clean.controller;

import lombok.RequiredArgsConstructor;
import me.khw7385.click_clean.dto.PageResult;
import me.khw7385.click_clean.dto.Result;
import me.khw7385.click_clean.dto.ScrapDto;
import me.khw7385.click_clean.service.ScrapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/api/scrap/save")
    public ResponseEntity<Result> createScrap(@RequestBody ScrapDto.Request request, @RequestParam("user_id") Long userId){
        scrapService.saveScrap(request.toCommand(userId));

        return ResponseEntity.ok(new Result(HttpStatus.CREATED.value(), null));
    }

    @GetMapping("/api/scraps")
    public ResponseEntity<PageResult> getScraps(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("user_id") Long userId){
        List<ScrapDto.Response> scraps = scrapService.findScraps(new ScrapDto.Command(null, userId, null), page, size);
        return ResponseEntity.ok(new PageResult(HttpStatus.OK.value(), page, scraps.size(), scraps));
    }

    @DeleteMapping("/api/scrap/delete/{id}")
    public ResponseEntity<Result> deleteScrap(@PathVariable("id") Long id){
        scrapService.removeScrap(new ScrapDto.Command(id, null, null));
        return ResponseEntity.ok(new Result(HttpStatus.OK.value(), null));
    }
}
