package cn.wangsr.filter;

import com.alibaba.fastjson.JSON;
import model.result.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author: wjl
 * @description:
 * @time: 2019/12/10 0010 13:37
 */
@Component
@WebFilter(urlPatterns = { "/*" }, filterName = "myFilter")
public class MyFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);
    private final String REQUEST_ID = "request_id";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("filter init ...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        //请求唯一id
        String uid =  UUID.randomUUID().toString();
        MDC.put(REQUEST_ID,uid);
        request.setAttribute(REQUEST_ID,uid);
        //jwt 校验
        boolean result = false;
        if(result){
            ResultMessage resultMessage = ResultMessage.builder()
                    .code(52010)
                    .message("auth failed!")
                    .build();
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(resultMessage));
            return;
        }
        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {
        logger.info("filter destroy ...");
    }
}
