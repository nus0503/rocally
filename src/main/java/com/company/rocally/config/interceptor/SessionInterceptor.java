package com.company.rocally.config.interceptor;

import com.company.rocally.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@Component
public class SessionInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logID";

    private final HttpSession httpSession;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid);

        //@RequestMapping: HandlerMethod
        //정적 리소스: ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("REQUSET [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(response.isCommitted());
//        if (Objects.nonNull(httpSession.getAttribute("user"))) {
//            SessionUser user = (SessionUser) httpSession.getAttribute("user");
//            modelAndView.addObject("username", user.getName());
//        }

//        HttpSession session = request.getSession(false); // 현재 세션을 가져옴, 없으면 null 반환
//        if (session != null) {
//            SessionUser user = (SessionUser) session.getAttribute("user");
//            if (user != null && modelAndView != null) {
//                modelAndView.addObject("username", user.getName());
//            }
//        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (principal instanceof PrincipalDetails && modelAndView != null) {
            PrincipalDetails userInfo = (PrincipalDetails) principal;
            modelAndView.addObject("auth", userInfo.getUser());
//            modelAndView.addObject("role", userInfo.getUser().getRole());
        }
        if (principal instanceof OAuth2User && modelAndView != null) {
            DefaultOAuth2User userInfo = (DefaultOAuth2User) principal;
            modelAndView.addObject("auth", userInfo.getAttributes());
            GrantedAuthority firstAuthority  = userInfo.getAuthorities().iterator().next();
            modelAndView.addObject("firstRole", firstAuthority);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
