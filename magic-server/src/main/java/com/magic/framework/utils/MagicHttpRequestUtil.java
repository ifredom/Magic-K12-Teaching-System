package com.magic.framework.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 作用: request 工具类
 *
 * @author ifredom
 * @version v0.0.1
 * @date 2024/09/23 23:37
 * @email 1950735817@qq.com
 */
@Slf4j
public class MagicHttpRequestUtil {

    /**
     * 获取request对象
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取requestURI对象
     *
     * @return /sys/user/getInfo?id=1
     */
    public static String getRequestURI() {
        HttpServletRequest request = getRequest();
        String queryString = request.getQueryString();
        if (!StringUtils.hasText(queryString)) {
            return getRequest().getRequestURI();
        }
        return  getRequest().getRequestURI() + "?" + queryString;
    }

    /**
     * 获取getRequestURI
     */
    public static UserAgent getRequestUserAgent() {
        HttpServletRequest request = getRequest();
        return UserAgentUtil.parse(request.getHeader("User-Agent"));

    }

    public static String getRemoteIP() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多次反向代理后会有多个IP值，第一个为真实IP。
        int index = ip.indexOf(',');
        if (index != -1) {
            ip = ip.substring(0, index);
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 取得请求头信息 name:value
     */
    public static Map<String, String> getHeaders() {
        HttpServletRequest request = getRequest();
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取请求体信息
     */
    public static String getBody() {
        HttpServletRequest request = getRequest();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("HttpServletRequest.getBody", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("HttpServletRequest.getBody", e);
                }
            }
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取请求的所有信息
     *
     * 此方法汇总并返回HTTP请求的关键信息，包括远程地址、请求方法和请求URI
     * 它使用了辅助方法来获取远程IP地址，并直接获取当前请求的方法和URI
     *
     * @return 包含远程地址、请求方法和请求URI的字符串
     */
    public static String getAllRequestInfo() {
        return "RemoteAddress: " + getRemoteIP() + StrUtil.CRLF +
                "Method: " + getRequest().getMethod() + StrUtil.CRLF +
                "Uri: " + getRequestURI() + StrUtil.CRLF;
    }
}