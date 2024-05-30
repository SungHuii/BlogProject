package me.sunghui.springbootdeveloper.config.oauth;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import me.sunghui.springbootdeveloper.config.jwt.TokenProvider;
//import me.sunghui.springbootdeveloper.domain.RefreshToken;
//import me.sunghui.springbootdeveloper.domain.User;
//import me.sunghui.springbootdeveloper.repository.RefreshTokenRepository;
//import me.sunghui.springbootdeveloper.service.UserService;
//import me.sunghui.springbootdeveloper.util.CookieUtil;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.io.IOException;
//import java.time.Duration;
//
//@RequiredArgsConstructor
//@Component
//public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    // 스프링 시큐리티의 기본 로직에서는 별도 authenticationSuccessHandler를 지정하지 않으면
//    // 로그인 성공 이후 SimpleUrlAuthenticationSuccessHandler를 사용한다
//    // 일반적인 로직은 동일하게 작동하고, 토큰과 관련된 작업만 추가 처리하기 위해
//    // 상속 받은 뒤 메서드 오버라이드
//    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
//    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
//    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
//    public static final String REDIRECT_PATH = "/articles";
//
//    private final TokenProvider tokenProvider;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final OAuth2AuthorizationRequestBasedOnCookieRepository
//            authorizationRequestRepository;
//    private final UserService userService;
//
//    // 상속받은 메서드 오버라이드
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response, Authentication authentication) throws IOException {
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//
//        User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"));
//
//        // 1 리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
//        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
//        saveRefreshToken(user.getId(), refreshToken); // 데이터베이스에 유저 아이디와 함께 저장
//        addRefreshTokenToCookie(request, response, refreshToken); // 클라이언트에서 액세스 토큰이 만료되면 재발급 요청하도록 쿠키에 리프레시 토큰 저장
//        // 2 액세스 토큰 생성 -> 패스에 액세스 토큰 추가
//        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
//        String targetUrl = getTargetUrl(accessToken); // 쿠키에서 리다이렉트 경로가 담긴 값을 가져와 쿼리 파라미터에 액세스 토큰 추가
//        // 3 인증 관련 설정값, 쿠키 제거
//        // 인증 프로세스를 진행하면서 세션과 쿠키에 임시로 저장해둔 인증 관련 데이터 제거
//        // 기본 제공 메서드는 그대로 호출하고 removeAuthorizationRequestCookies()를 추가로 호출해 OAuth 인증을 위해
//        // 저장된 정보도 삭제함
//        clearAuthenticationAttributes(request, response);
//        // 4 리다이렉트 ( 2번에서 만든 URL로 리다이렉트 )
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//    }
//
//    // 생성된 리프레시 토큰을 전달받아 데이터베이스에 저장
//    private void saveRefreshToken(Long userId, String newRefreshToken) {
//        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
//                .map(entity -> entity.update(newRefreshToken))
//                .orElse(new RefreshToken(userId, newRefreshToken));
//    }
//
//    // 생성된 리프레시 토큰을 쿠키에 저장
//    private void addRefreshTokenToCookie(HttpServletRequest request,
//                                         HttpServletResponse response, String refreshToken) {
//        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
//        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
//        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
//    }
//
//    // 인증 관련 설정값, 쿠키 제거
//    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
//        super.clearAuthenticationAttributes(request);
//        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
//    }
//
//    // 액세스 토큰을 패스에 추가
//    private String getTargetUrl(String token) {
//        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
//                .queryParam("token", token)
//                .build()
//                .toUriString();
//    } // 326
//}

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.sunghui.springbootdeveloper.config.jwt.TokenProvider;
import me.sunghui.springbootdeveloper.domain.RefreshToken;
import me.sunghui.springbootdeveloper.domain.User;
import me.sunghui.springbootdeveloper.repository.RefreshTokenRepository;
import me.sunghui.springbootdeveloper.service.UserService;
import me.sunghui.springbootdeveloper.util.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/articles";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"));

        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        saveRefreshToken(user.getId(), refreshToken);
        addRefreshTokenToCookie(request, response, refreshToken);

        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build()
                .toUriString();
    }
}