package org.py.common.weixin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.py.common.http.HttpClientUtilV1;
import org.py.common.internals.Ordered;
import org.py.common.util.DateUtil;
import org.py.common.util.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Ordered
@Slf4j
public class WeixinWarningUtil implements WarningUtil{


    private static ThreadPoolExecutor executorService;

    static {
        executorService = new ThreadPoolExecutor(1, 3, 5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2000), Executors.defaultThreadFactory(), (r, executor) -> {
            log.error("weixin util pool is full");
        });
    }

    private String ENV = "";
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

    public WeixinWarningUtil() {
        ENV = "local";
        DEFAULT_WEBHOOKADDR = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=1c5f4d91-be16-4988-af76-c4d44a1af8dc";
    }

    public WeixinWarningUtil(String env, String domain, String defaultWebHookAddr, boolean sync) {
        ENV = env;
        DEFAULT_WEBHOOKADDR = defaultWebHookAddr;
        SYNC = sync;
    }

    public void sendWarning(String webHookAddr, String message) {
        sendWarning(webHookAddr, message, null);
    }

    @Override
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

    @Override
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
                HttpClientUtilV1.postJson(webHookAddr, param);
            } catch (Throwable e) {
                log.error("sendWarningByMD error", e);
            }
        } else {
            executorService.submit(() -> {
                try {
                    HttpClientUtilV1.postJson(webHookAddr, param);
                } catch (Throwable e) {
                    log.error("sendWarningByMD async error", e);
                }
            });
        }
    }

    public void sendWarning(String webHookAddr, String message, List<String> mobiles) {
        ZonedDateTime now = ZonedDateTime.now();
        message = String.format("时间: %s \n 时间戳：%s \n 来源：%s \n 异常信息：%s ",
                DateUtil.getStrDate(now), now.toInstant().toEpochMilli(), getHostName(), message);
        if (StringUtils.isNotBlank(ENV)) {
            message = String.format("%s \n %s", ENV, message);
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
                HttpClientUtilV1.postJson(webHookAddr, param);
            } catch (Throwable e) {
                log.error("sendWarning error", e);
            }
        } else {
            executorService.submit(() -> {
                try {
                    HttpClientUtilV1.postJson(webHookAddr, param);
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
        ZonedDateTime now = ZonedDateTime.now();
        return Util.asMap( "ENV", ENV, "FORMAT_DATE",
                DateUtil.getStrDate(now), "TIMESTAMP", String.valueOf(now.toInstant().toEpochMilli()), "FROM", getHostName());
    }

    private String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
