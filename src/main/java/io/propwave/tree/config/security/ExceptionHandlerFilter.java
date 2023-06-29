package io.propwave.tree.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Error;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException e) {
            setErrorResponse(response, Error.NOT_FOUND_EXCEPTION);
        } catch (ExpiredJwtException e) {
            // 토큰의 유효기간 만료
            setErrorResponse(response, Error.TOKEN_EXPIRED_ERROR);
        } catch (MalformedJwtException e) {
            // JWT의 형식이 아닌경우
            setErrorResponse(response, Error.MALFORMED_TOKEN_ERROR);
        } catch (IllegalArgumentException e) {
            // 토큰이 없는 경우
            setErrorResponse(response, Error.NULL_TOKE_ERROR);
        } catch (SignatureException e) {
            setErrorResponse(response, Error.TOKEN_SIGNATURE_EXCEPTION);
        }
    }
    private void setErrorResponse(HttpServletResponse response, Error error) {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setCharacterEncoding("UTF-8");
        response.setStatus(error.getCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<ApiResponse<Object>> responseEntity = new ResponseEntity<>(ApiResponse.error(error), error.getCode());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
