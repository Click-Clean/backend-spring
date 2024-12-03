package me.khw7385.click_clean.dto;

public class VoteDto {
    public record Request(Long id, Boolean value){
        public Command toCommand(Long userId){
            return new Command(userId, id, value);
        }
    }
    public record Command(Long userId, Long articleId, Boolean value){
    }
    public record Response(Integer trueValue, Integer falseValue){
    }
}
