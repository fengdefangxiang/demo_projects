package top.zhengliwei.threadLocalTest.model;

public class BadRequestContext {
    private static UserSession USER_SESSION;

    public static void setUserSession(UserSession userSession) {
        USER_SESSION = userSession;
    }

    public static UserSession getUserSession() {
        return USER_SESSION;
    }

    public static void deleteUserSession() {
        USER_SESSION = null;
    }
}
