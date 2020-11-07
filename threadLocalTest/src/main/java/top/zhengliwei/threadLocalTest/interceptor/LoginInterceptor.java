package top.zhengliwei.threadLocalTest.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.zhengliwei.threadLocalTest.model.BadRequestContext;
import top.zhengliwei.threadLocalTest.model.RequestContext;
import top.zhengliwei.threadLocalTest.model.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 线上是从cookie中取的，这个只是演示
        String token = request.getParameter("token");
        if (!hasLogin(token)) {
            return false;
        }

        // 调用户服务拿到用户信息，写入requestContext的userSession里，这里的userSession实际上是个threadLocal变量
        RequestContext.setUserSession(getUserInfo(token, request));
//        BadRequestContext.setUserSession(getUserInfo(token, request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        // 处理完成后删除userSession
        RequestContext.deleteUserSession();
    }

    // 根据token校验是否登录，也是调用公司统一的用户登录服务校验的，这里直接返回true
    public boolean hasLogin(String token) {
        return true;
    }

    // 线上是根据token查询用户服务，这里为了方便直接从请求参数里读取用户信息
    private UserSession getUserInfo(String token, HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        return UserSession.builder().userId(Integer.valueOf(userId)).userName(userName).build();
    }
}
