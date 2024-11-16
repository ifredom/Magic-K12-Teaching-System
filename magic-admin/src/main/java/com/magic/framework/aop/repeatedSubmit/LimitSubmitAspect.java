package com.magic.framework.aop.repeatedSubmit;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.LFUCache;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magic.framework.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * 防止重复提交切面
 * 1. 前提是比如有一个下单提交接口，接口参数中一定有订单号，以及用户Id,用户Id和订单号作为唯一索引，防止一个用户多次提交相同的订单
 * 2. 先针对某一方法拦截
 * 3. 在通过注解的方式，获取注解的参数，判断是否重复提交
 * @author <NAME>
 */
@Aspect
@Component
@Slf4j
public class LimitSubmitAspect {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final LFUCache<Object, Object> cache = CacheUtil.newLFUCache(100,20);

    private static final String USER_ID = "userId";

    @Pointcut("execution(* com.magic.module.sys.controller.SysUserController.*(..))")
    public void pointcut() {}

    @Around("@annotation(repeatSubmitLimitParam)")
    public Object aroundSummit(ProceedingJoinPoint joinPoint, RepeatSubmitLimit repeatSubmitLimitParam) throws Throwable {
        log.info("@Before通知执行");

        // 获取注解上的参数: 时间
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        RepeatSubmitLimit repeatSubmitLimitAnnotation = method.getAnnotation(RepeatSubmitLimit.class);
        int limitTime = repeatSubmitLimitAnnotation.limitTime();

        String lockKey = getLockKey(joinPoint,repeatSubmitLimitAnnotation);
        Object result = cache.get(lockKey,false);

        log.info("aroundSummit lockKey:{} result:{}", lockKey, result);

        if(result != null){
            throw new CustomException("订单重复提交",8888);
        }
        cache.put(lockKey, USER_ID, limitTime*1000L);
//        cache.put(lockKey, StpUtil.getLoginId(), limitTime * 1000L);

        log.info("before通知执行结束");

        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Exception in {}.{}() with cause = '{}' and exception = '{}'", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * ParameterNameDiscoverer 接口及其实现（如 DefaultParameterNameDiscoverer）用于在运行时发现Java方法参数的名称。
     * 这在反射操作中特别有用，因为Java的反射API默认不提供方法参数的名称。
     * DefaultParameterNameDiscoverer 是Spring框架提供的一个实现，它利用Java编译器生成的调试信息（如果可用）来发现参数名称。
     */
    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * ExpressionParser 接口及其实现（如Spring表达式语言（SpEL）的 SpelExpressionParser）用于解析和计算表达式。
     * SpEL是Spring框架提供的一种强大的表达式语言，用于在运行时查询和操作对象图。
     * SpelExpressionParser 是SpEL的一个解析器实现，它可以将字符串形式的表达式解析为可执行的表达式对象，进而执行表达式并获取结果。
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    private String getLockKey(ProceedingJoinPoint joinPoint,RepeatSubmitLimit repeatSubmitLimitParam){
        String businessKey = repeatSubmitLimitParam.businessKey();
        Boolean userLimit = repeatSubmitLimitParam.userLimit();
        String businessParam = repeatSubmitLimitParam.businessParam();

        if(userLimit){
            businessKey = businessKey + ":" + USER_ID;
//            businessKey = businessKey + ":" + StpUtil.getLoginId();
        }

        if(StrUtil.isNotBlank(businessParam)){
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            EvaluationContext context = new MethodBasedEvaluationContext(null, method, joinPoint.getArgs(), NAME_DISCOVERER);
            String key = PARSER.parseExpression(businessParam).getValue(context, String.class);
            businessKey = businessKey + ":" + DigestUtil.md5Hex(key);
        }
        return businessKey;
    }
}