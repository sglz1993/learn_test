package old.tool.cert;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * @author pengyue.du
 * @version 1.0
 * @date 2019/12/17 17:37
 */
public class RSACerVerifyTool {


    public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
        try {
            Key key=keystore.getKey(alias,password);
            if(key instanceof PrivateKey) {
                Certificate cert= keystore.getCertificate(alias);
                PublicKey publicKey=cert.getPublicKey();
                return new KeyPair(publicKey,(PrivateKey)key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }

    public static PublicKey getPublicKey(File cerFile) throws Exception {
        InputStream ins = new FileInputStream(cerFile);
        //创建x.509工厂类
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        //创建证书实例
        X509Certificate oCer = (X509Certificate) cf.generateCertificate(ins);
        return oCer.getPublicKey();
    }


    public static void verify(File keystoreFile, File cerFile, String keyStoreType, char[] password, String alias) throws Exception{
        KeyStore keystore=KeyStore.getInstance(keyStoreType);
//        BASE64Encoder encoder=new BASE64Encoder();
        Base64.Encoder encoder = Base64.getEncoder();
        keystore.load(new FileInputStream(keystoreFile),password);
        KeyPair keyPair=getPrivateKey(keystore,alias,password);
        PrivateKey privateKey=keyPair.getPrivate();
        String encoded = new String(encoder.encode(privateKey.getEncoded()));
        System.out.println(String.format("私钥：%s", encoded));
        String keystorePublicKey = new String(encoder.encode(keyPair.getPublic().getEncoded()));
        System.out.println(String.format("keystore公钥：%s", keystorePublicKey));

        PublicKey publicKey = getPublicKey(cerFile);
        String strPublicKey = new String(encoder.encode(publicKey.getEncoded()));
        System.out.println(String.format("cer公钥：%s", strPublicKey));


        if(!strPublicKey.equals(keystorePublicKey)){
            throw new RuntimeException("公钥key不一致");
        }
    }


    public static void main(String args[]) throws Exception{
        File keystoreFile = new File("/Users/pengyue.du/Documents/config/cer\\product\\xiaodai\\xiaodai.keystore");
        File cerFile = new File("/Users/pengyue.du/Documents/config/cer\\product\\xiaodai\\xiaodai.cer");
        String keyStoreType = KeyStore.getDefaultType();
        char[] password = "201708".toCharArray();
        String alias = "xiaodai";
        verify(keystoreFile, cerFile, keyStoreType, password, alias);
        System.out.println("恭喜恭喜！！！！！！！ 校验结束，秘钥正确");
    }

}
