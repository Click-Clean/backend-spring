package me.khw7385.click_clean.interceptor;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {
    private static final String NOT_TOKEN_RESPONSE = "{ \"error\": \"토큰을 찾을 수 없습니다.\" }";
    private static final String INVALID_TOKEN_SIGNATURE = "{ \"error\": \"유효하지 않은 토큰 서명입니다.\" }";
    private static final String EXPIRED_TOKEN = "{ \"error\": \"만료된 토큰입니다.\" }";
    private static final String MALFORMED_TOKEN = "{ \"error\": \"유효하지 않은 JWT 형식입니다.\" }";
    private static final String UNSUPPORTED_TOKEN_ALGORITHM = "{ \"error\": \"지원되지 않은 JWT 토큰 알고리즘입니다.\" }";
    private static final String ILLEGAL_ARGUMENT = "{ \"error\": \"잘못된 입력값이 제공되었습니다.\" }";
    private static final String GENERAL_EXCEPTION = "{ \"error\": \"JWT 토큰 처리 중 오류가 발생했습니다.\" }";

    private final String key;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeaderValue == null){
            response.setStatus(401);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(NOT_TOKEN_RESPONSE);

            return false;
        }

        String accessToken = authorizationHeaderValue.replace("Bearer ", "");

        try{
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

            Long userId = (Long)Jwts.parser()
                    .verifyWith(new SecretKeySpec(keyBytes, "HmacSHA256"))
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload()
                    .get("id");
            request.setAttribute("userId", Objects.requireNonNull(userId));
        }catch(SignatureException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, INVALID_TOKEN_SIGNATURE);
            return false;
        }catch(ExpiredJwtException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, EXPIRED_TOKEN);
            return false;
        }catch(MalformedJwtException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, MALFORMED_TOKEN);
            return false;
        }catch(UnsupportedJwtException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, UNSUPPORTED_TOKEN_ALGORITHM);
            return false;
        }catch(IllegalArgumentException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ILLEGAL_ARGUMENT);
            return false;
        }catch(Exception e){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, GENERAL_EXCEPTION);
            return false;
        }

        return true;
    }
}
