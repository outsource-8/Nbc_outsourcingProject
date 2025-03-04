package com.example.nbc_outsourcingproject.config.jwt;

import com.example.nbc_outsourcingproject.domain.auth.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getRequestURI();

        List<String> whitelist = List.of("/swagger-ui/**", "/v3/api-docs/**");

        if (whitelist.stream().anyMatch(url::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        if(url.startsWith("/user")) {
            chain.doFilter(request, response);
            return;
        }
        log.info("확인 {}", httpRequest.getHeader("Authorization"));
        String bearerJwt = httpRequest.getHeader("Authorization");
        log.info("지워진 상태의 값에 bearer 가 붙어서 출력: {}", bearerJwt);

        if (bearerJwt == null) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT 토큰이 필요합니다");
            return;
        }

//        log.info("bearer 가 있는지 확인 {}", bearerJwt);
        String jwt = jwtUtil.substringToken(bearerJwt);

//        log.info("bearer 가 없어야 맞음 {}", jwt);
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies == null) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 접근입니다");
            return;
        }

        Cookie refreshToken = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("token")).findFirst().orElse(null);
        if (refreshToken == null) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"로그인 해주세요");
            return;
        }

        try {

            Claims claims = jwtUtil.extractToken(jwt);
            if (claims == null) {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효한 JWT 토큰이 아닙니다");
                return;
            }

            UserRole userRole = UserRole.valueOf(claims.get("userRole", String.class));

            httpRequest.setAttribute("userId", Long.parseLong(claims.getSubject()));
            httpRequest.setAttribute("email", claims.get("email"));
            httpRequest.setAttribute("userRole", claims.get("userRole"));

            if (url.startsWith("/owner")) {
                // 관리자 권한이 없는 경우 403을 반환합니다.
                if (!UserRole.OWNER.equals(userRole)) {
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 없습니다.");
                    return;
                }
                chain.doFilter(request, response);
                return;
            }

            chain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
        } catch (Exception e) {
            log.error("Invalid JWT token, 유효하지 않는 JWT 토큰 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않는 JWT 토큰입니다.");
        }

    }
}
