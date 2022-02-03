package cn.zq.spshot.common.entity;

/**
 * @author 华韵流风
 * @ClassName StateCode
 * @Date 2021/11/27 20:38
 * @packageName cn.zq.spshot.common.entity
 * @Description 统一返回码
 */
public class StatusCode {
    /**
     * 成功
     */
    public static final Integer OK = 20000;
    /**
     * 失败
     */
    public static final Integer FAIL = 20001;
    /**
     * 账号或密码错误
     */
    public static final Integer USER_OR_PASSWORD_ERROR = 20002;
    /**
     * 权限不足
     */
    public static final Integer NO_PERMISSION = 20003;
    /**
     * 验证码错误
     */
    public static final Integer VERIFICATION_CODE_ERROR = 20004;
    /**
     * 调用失败
     */
    public static final Integer RPC_ERROR = 20005;
}
