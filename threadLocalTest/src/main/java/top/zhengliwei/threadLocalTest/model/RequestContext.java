package top.zhengliwei.threadLocalTest.model;

public class RequestContext {
    private static final ThreadLocal<UserSession> USER_SESSION = new ThreadLocal<>();

    public static void setUserSession(UserSession userSession) {
        USER_SESSION.set(userSession);
    }

    public static UserSession getUserSession() {
        return USER_SESSION.get();
    }

    public static void deleteUserSession() {
        USER_SESSION.remove();
    }
}
