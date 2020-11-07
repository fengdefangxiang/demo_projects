package top.zhengliwei.threadLocalTest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSession {
    private Integer userId;
    private String userName;
}
