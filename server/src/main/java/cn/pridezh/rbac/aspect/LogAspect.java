package cn.pridezh.rbac.aspect;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Controller 入参出参日志
 * @author PrideZH
 * @since 2022/8/5 12:59
 */
@AllArgsConstructor
@Aspect
@Component
@Slf4j
public class LogAspect {

    private HttpServletRequest request;

    // 切入点
    @Pointcut("execution(public * cn.pridezh.rbac.controller.*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 请求 controller 名称
        String controllerTitle = getControllerMethodTitle(joinPoint);
        // 方法路径
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        // IP地址
        String iP = getIp(request);
        // 请求入参
        String requestParam = Arrays.toString(joinPoint.getArgs());

        log.info("\n[CONT S],{},method->{},IP->{},param->{}",
                controllerTitle, methodName, iP,
                StringUtils.abbreviate(requestParam, 512));

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        log.info("\n[CONT E],{},time->{}ms,result->{}",
                controllerTitle, System.currentTimeMillis() - startTime,
                StringUtils.abbreviate(result.toString(), 512));
        return result;
    }

    /**
     * 通过 @ApiOperation 获取 Controller 的方法名
     */
    private String getControllerMethodTitle(ProceedingJoinPoint joinPoint) {
        Method[] methods = joinPoint.getSignature().getDeclaringType().getMethods();
        for (Method method : methods) {
            if (StringUtils.equalsIgnoreCase(method.getName(), joinPoint.getSignature().getName())) {
                ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                if (ObjectUtils.isNotEmpty(apiOperation) && StringUtils.isNotBlank(apiOperation.value())) {
                    return apiOperation.value();
                }
            }
        }
        return "Not ApiOperation";
    }

    /**
     * 获取目标主机的ip
     */
    private String getIp(HttpServletRequest request) {
        List<String> ipHeadList =
                Stream.of("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "X-Real-IP")
                        .toList();
        for (String ipHead : ipHeadList) {
            if (checkIP(request.getHeader(ipHead))) {
                return request.getHeader(ipHead).split(",")[0];
            }
        }
        return "0:0:0:0:0:0:0:1".equals(request.getRemoteAddr()) ? "127.0.0.1" : request.getRemoteAddr();
    }

    /**
     * 检查ip存在
     */
    private boolean checkIP(String ip) {
        return !(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip));
    }

}