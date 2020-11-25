package old.tool.java_base;

import org.junit.Assert;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * xmly vehicle encode util
 * 签名生成规则：
 *  1. url和order_no直接拼接成一个新字符串
 *  2. 将1中得到的字符串进行Base64编码(注意Base64编码时要设置字符集为utf8)
 *  3. 使用sha1Key对base64EncodedStr进行HMAC-SHA1哈希得到字节数组(注意是字节数组，不要转成十六进制字符串，否则签名计算会出错;一般的HMAC-SHA1算法得到的结果是字节数组的十 六进制表示，请务必留意这里和一般情况不太一样)
 *  4. 对上面得到的字符串进行MD5得到32位字符串，即为sign
 */
public class EncodeUtils {

    /**
     * encode method
     * @param url url
     * @param orderNo order_no
     * @param secret secret
     * @return sign
     */
    public static String encode(String url, String orderNo, String secret) {
        return hmacSHA1Encrypt(String.format("%s%s", url, orderNo), secret);
    }

    private static String hmacSHA1Encrypt(String data, String secret) {
        if(data == null || data.trim().length() == 0) {
            return "";
        }
        try {
            if(secret == null || secret.trim().length() == 0){
                return md5(data.getBytes());
            }
            String e = new String(java.util.Base64.getEncoder().encode(data.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            return md5(hmacSHA1(e.getBytes(StandardCharsets.UTF_8), secret.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static byte[] hmacSHA1(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        return mac.doFinal(data);
    }

    private static String md5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        byte byteData[] = md.digest();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < byteData.length; i++) {
            buffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return buffer.toString();
    }

    public static void main(String[] args) {
        String url = "https://m.ximalaya.com/iot/vehicle-app/pay/goto_make_payment.html?access_token=d1352cc48a5afab38ed85d4595efbe60&sig=e2f2d85024c47fcfd24daa75a42cdcbe&app_key=298fe64544844b2a03fe6c2e6c012fe8&device_id=2064f15909f4313c8a7d75e6499ea10e&client_os_type=4&nonce=KGEZZgWDtRJs4JEXBcwm&xima_order_no=202007303000826002056268900&timestamp=1596099957104";
        String orderNo = "202007303000826002056268900";
        String secret= "ef3d0e7f93e048469a2ebc0af29f0033";
        String sign = "dbe765a514fce4567b5393383e820bff";
        String newSign = encode(url, orderNo, secret);
        System.out.println(newSign);
        Assert.assertTrue(sign.equals(newSign));
    }

}
