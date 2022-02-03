package cn.zq.spshot.data.interceptor;

import cn.zq.spshot.common.aop.HasPermissions;
import cn.zq.spshot.common.entity.Result;
import cn.zq.spshot.common.entity.StatusCode;
import cn.zq.spshot.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 华韵流风
 * @ClassName PermissionAspect
 * @Date 2022/1/16 18:37
 * @packageName cn.zq.spshot.common.aop
 * @Description TODO
 */
@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 目标方法
     */
    @Pointcut("@annotation(cn.zq.spshot.common.aop.HasPermissions)")
    private void permission() {
    }


    /**
     * 目标方法调用之前执行
     */
    @Before("permission()")
    public void doBefore() {
        System.out.println("================== step 2: before ==================");
    }

    /**
     * 目标方法调用之后执行
     */
    @After("permission()")
    public void doAfter() {
        System.out.println("================== step 4: after ==================");
    }


    /**
     * 具体验证过程
     */
    @Around("permission()")
    public Result doAround(ProceedingJoinPoint point) throws Throwable {
        System.out.println("================== step 1: around ==================");
        //验证token
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String auth = request.getHeader("Auth");
        if (auth == null || "".equals(auth)) {
            return new Result(StatusCode.FAIL, false, "未登录");
        }
        //获取权限
        if (StringUtils.startsWith(auth, "Bearer") && !"".equals(auth.substring(7))) {
            String token = auth.substring(7);
            Claims claims = jwtUtil.parseJwt(token);
            String id = claims.getId();
            Set<String> members = redisTemplate.opsForSet().members("permissions_" + id);
            if (members == null) {
                return new Result(StatusCode.NO_PERMISSION, false, "权限不足");
            }
            //获取注解上的值
            Method method = ((MethodSignature) point.getSignature()).getMethod();
            HasPermissions annotation = method.getAnnotation(HasPermissions.class);
            String[] values = annotation.values();
            //判断是否有相应权限
            boolean b = members.containsAll(new HashSet<>(Arrays.asList(values)));
            if (!b) {
                return new Result(StatusCode.NO_PERMISSION, false, "权限不足");
            }
            return (Result) point.proceed();
        }
        return new Result(StatusCode.NO_PERMISSION, false, "权限不足");
    }

}
