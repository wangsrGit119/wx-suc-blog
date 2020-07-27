package cn.wangsr.aop;

import com.alibaba.fastjson.JSON;
import ip.HttpIpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author WJL
 */
@Component
@Aspect
public class LogAspect {
    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 主切点
     */
    @Pointcut("execution(* cn.wangsr.controller..*.*(..))")
    public void pointCutOne() {
    }

    @Before("pointCutOne()")
    public void  doBefore(JoinPoint joinPoint) throws UnsupportedEncodingException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String method = request.getMethod();
        StringBuffer paramsValue = new StringBuffer();
        Object paramsName=null;
        // get请求
        if (HttpMethod.GET.toString().equals(method)) {
            String queryString = request.getQueryString();
            if (!StringUtils.isEmpty(queryString)) {
                paramsName= JSON.parseObject(JSON.toJSONString(joinPoint.getSignature())).get("parameterNames");
                paramsValue.append( URLDecoder.decode(queryString,"UTF-8"));
            }
        } else {
            //其他请求
            Object[] paramsArray = joinPoint.getArgs();
            paramsName= JSON.parseObject(JSON.toJSONString(joinPoint.getSignature())).get("parameterNames");
            for (Object o :paramsArray){
                paramsValue.append(o+" ");
            }
        }
        String ip = HttpIpUtils.getClientIp(request);
        logger.info("URLParamName  : " + paramsName);
        logger.info("URLParamValue  : " + paramsValue);
        logger.info("URL:  {}, HTTP_METHOD:  {}, IP:  {}, Method:  {} ",request.getRequestURL().toString(),request.getMethod(), ip,joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
    }



    /**
     * 处理返回结果  后期日志入库
     * @param result
     */
    @AfterReturning(returning = "result",pointcut = "pointCutOne()")
    public void getResult(Object result){

    }
}
