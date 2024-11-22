package me.khw7385.click_clean.dto;

public class VoteDto {
    public record Request(){

    }
    public record Command(Long userId, Long articleId, Boolean value){

    }
    public record Response(Integer trueValue, Integer falseValue){
    }
}
