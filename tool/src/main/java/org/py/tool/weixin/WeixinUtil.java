package org.py.tool.weixin;

import com.google.gson.Gson;
import old.tool.common.DateUtils;
import old.tool.common.Util;
import old.tool.http.httpclient.HelloHttpClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WeixinUtil {


    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
    private static ThreadPoolExecutor executorService;

    static {
        executorService = new ThreadPoolExecutor(1, 3, 5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2000), Executors.defaultThreadFactory(), (r, executor) -> {
            log.error("weixin util pool is full");
        });
    }

    private String ENV = "";
    private String DOMAIN = "";
    private String DEFAULT_WEBHOOKADDR = "";
    private String DEFAULT_TEMPLATE;
    private boolean SYNC = true;

    {
        DEFAULT_TEMPLATE = "### <font color=\"warning\">{DOMAIN}/{ENV}</font> \n" +
                "> 时间：{FORMAT_DATE} \n" +
                "> 时间戳：{TIMESTAMP} \n" +
                "> 来源：{FROM}   \n" +
                "\n" +
                "<font color=\"red\">{MESSAGE}</font>";
    }

    public WeixinUtil(String env, String domain, String defaultWebHookAddr, boolean sync) {
        ENV = env;
        DOMAIN = domain;
        DEFAULT_WEBHOOKADDR = defaultWebHookAddr;
        SYNC = sync;
    }

    public void sendWarning(String webHookAddr, String message) {
        sendWarning(webHookAddr, message, null);
    }

    public void sendWarning(String message) {
        if (StringUtils.isBlank(DEFAULT_WEBHOOKADDR)) {
            throw new InvalidParameterException("webHookAddr is empty");
        }
        sendWarning(DEFAULT_WEBHOOKADDR, message, null);
    }

    public void sendWarning(String message, List<String> mobiles) {
        if (StringUtils.isBlank(DEFAULT_WEBHOOKADDR)) {
            throw new InvalidParameterException("webHookAddr is empty");
        }
        sendWarning(DEFAULT_WEBHOOKADDR, message, mobiles);
    }

    public void sendWarningMD(String message) {
        if (StringUtils.isBlank(DEFAULT_WEBHOOKADDR)) {
            throw new InvalidParameterException("webHookAddr is empty");
        }
        sendWarningByTemplateMD(DEFAULT_WEBHOOKADDR, DEFAULT_TEMPLATE, Util.asMap("MESSAGE", message));
    }

    public void sendWarningByTemplateMD(String webHookAddr, String template, Map<String, String> placeHolder) {
        String message = replaceTemplate2Message(template, placeHolder);
        sendWarningByMD(webHookAddr, message);
    }

    public void sendWarningByMD(String webHookAddr, String message) {
        Map<String, Object> param = new HashMap<>();
        param.put("msgtype", "markdown");
        Map<String, Object> warning = new HashMap<>();
        warning.put("content", message);
        param.put("markdown", warning);
        if (SYNC) {
            try {
                HelloHttpClient.post(webHookAddr, new Gson().toJson(param));
            } catch (Throwable e) {
                log.error("sendWarningByMD error", e);
            }
        } else {
            executorService.submit(() -> {
                try {
                    HelloHttpClient.post(webHookAddr, new Gson().toJson(param));
                } catch (Throwable e) {
                    log.error("sendWarningByMD async error", e);
                }
            });
        }
    }

    public void sendWarning(String webHookAddr, String message, List<String> mobiles) {
        Date now = new Date();
        message = String.format("时间: %s \n 时间戳：%s \n 来源：%s \n 异常信息：%s ",
                DateUtils.formatyMDHms(now), now.getTime(), getHostName(), message);
        if (StringUtils.isNotBlank(ENV) && StringUtils.isNotBlank(DOMAIN)) {
            message = String.format("%s - %s \n %s", DOMAIN, ENV, message);
        }
        Map<String, Object> param = new HashMap<>();
        param.put("msgtype", "text");
        Map<String, Object> warning = new HashMap<>();
        warning.put("content", message);
        if (CollectionUtils.isNotEmpty(mobiles)) {
            warning.put("mentioned_mobile_list", mobiles);
        }
        param.put("text", warning);
        if (SYNC) {
            try {
                HelloHttpClient.post(webHookAddr, new Gson().toJson(param));
            } catch (Throwable e) {
                log.error("sendWarning error", e);
            }
        } else {
            executorService.submit(() -> {
                try {
                    HelloHttpClient.post(webHookAddr, new Gson().toJson(param));
                } catch (Throwable e) {
                    log.error("sendWarning async error", e);
                }
            });
        }
    }

    private String replaceTemplate2Message(String template, Map<String, String> placeHolderParamMap) {
//        if (StringUtils.isEmpty(template)) {
//            throw new InvalidParameterException("template is empty");
//        }
        Map<String, String> placeHolderMap = getBasePlaceHolder();
        if (MapUtils.isNotEmpty(placeHolderParamMap)) {
            placeHolderMap.putAll(placeHolderParamMap);
        }
        for (String key : placeHolderMap.keySet()) {
            template = template.replace(String.format("{%s}", key), placeHolderMap.get(key));
        }
        return template;
    }

    private Map<String, String> getBasePlaceHolder() {
        Date now = new Date();
        return Util.asMap("DOMAIN", DOMAIN, "ENV", ENV, "FORMAT_DATE",
                DateUtils.formatyMDHms(now), "TIMESTAMP", String.valueOf(now.getTime()), "FROM", getHostName());
    }

    private String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
