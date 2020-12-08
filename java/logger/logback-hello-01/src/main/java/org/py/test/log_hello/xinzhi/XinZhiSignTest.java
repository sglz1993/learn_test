package org.py.test.log_hello.xinzhi;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;

/**
 * https://github.com/seniverse/seniverse-api-demos
 */
public class XinZhiSignTest {

    private String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";

    private String TIANQI_API_SECRET_KEY = "S5NzQRhXBTBLZul7v"; //

    private String TIANQI_API_USER_ID = "PTZDAscPCIBb615Tc"; //

    /**
     * Generate HmacSHA1 signature with given data string and key
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     */
    private String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = new sun.misc.BASE64Encoder().encode(rawHmac);
        }
        catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    /**
     * Generate the URL to get diary weather
     * @param location
     * @param language
     * @param unit
     * @param start
     * @param days
     * @return
     */
    public String generateGetDiaryWeatherURL(
            String location,
            String language,
            String unit,
            String start,
            String days
    )  throws SignatureException, UnsupportedEncodingException {
        String timestamp = "1607408749599";
//        String timestamp = String.valueOf(System.currentTimeMillis());
        String params = "ts=" + timestamp + "&ttl=1800&uid=" + TIANQI_API_USER_ID;
        String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
//        sig=fPPkLAApHbvHidIrcFxwHuhl1sY%3D
        return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=舞钢" + "&language=zh-Hans" + "&unit=c" + "&start=" + start + "&days=" + days;
//        return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days;
    }

    public static void main(String args[]){
        XinZhiSignTest demo = new XinZhiSignTest();
        try {
            String url = demo.generateGetDiaryWeatherURL(
                    "shanghai",
                    "zh-Hans",
                    "c",
                    "1",
                    "1"
            );
            System.out.println("URL:" + url);
            System.out.println("https://api.seniverse.com/v3/weather/now.json?sig=fPPkLAApHbvHidIrcFxwHuhl1sY%253D&uid=PTZDAscPCIBb615Tc&unit=c&location=%E8%88%9E%E9%92%A2&language=zh-Hans&ttl=1800&ts=1607408749599");
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

    }

}
