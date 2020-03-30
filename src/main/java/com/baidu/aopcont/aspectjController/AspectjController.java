package com.baidu.aopcont.aspectjController;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 使用aspectj的形式对切点进行拦截
 *   1、可以定义多个切点
 *   2、可以组合切点  &&  ||
 *   3、可以获取到当前类
 *   4、获取当前方法
 *   5、获取到请求的参数
 *   6、还可以获取当前类的代理类
 */
@Aspect
@Component
public class AspectjController {
    @Pointcut("execution(* com.baidu.aopcont.controller.*.*(..))")
    public void controllerCut(){}

    @Pointcut("com.baidu.aopcont.aspectjController.AspectjController.controllerCut()")
    public void controllerCut2(){}


    @Around("execution(* com.baidu.aopcont.controller.*.*(..))")
    public Object beforeRun(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();

        // 获取切点下的方法名称
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        System.out.println(methodName);

        // 获取当前class
        Object aThis = joinPoint.getThis();
        String thisStr = aThis.toString();
        thisStr = thisStr.substring(thisStr.lastIndexOf(".")+1,thisStr.lastIndexOf("@"));
        System.out.println(thisStr);

        Object target = joinPoint.getTarget();
        System.out.println(target);

        // 获取切点下的参数
        Object arg = args[0];
        Class<?> argClazz = arg.getClass();
        if (Map.class.isAssignableFrom(argClazz)) {
            Map<String, Object> map = (Map<String, Object>) arg;
            Object name = map.get("name");
            System.out.println(name);
        }

        // before 的操作
        Object result = joinPoint.proceed(args);
        // after 的操作

        // 获取当前类下的代理类：处理service类中非事务方法调用事务方法时使用
        Object currentProxy = AopContext.currentProxy();
        // IService service = (IService)currentProxy;

        // return result 程序继续执行
        return result;
    }
}
