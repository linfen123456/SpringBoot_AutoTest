package com.example.jekins.demo.rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA非对称加密 唯一广泛接受并实现 数据加密&数字签名 公钥加密、私钥解密 私钥加密、公钥解密
 *
 * @date 2019-6-6 11:27:31
 */
public class RSADemo {
    /**
     * 加密字符
     */
    private static String src = "hello";
    /**
     * 公钥
     */
    private static RSAPublicKey rsaPublicKey;
    /**
     * 私钥
     */
    private static RSAPrivateKey rsaPrivateKey;

    static {
        // 1、初始化密钥
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);// 64的整倍数
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            System.out.println("Public Key : " + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
            System.out.println("Private Key : " + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 公钥加密，私钥解密
     */
    public static void pubEn2PriDe() throws Exception {
        //公钥加密
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("公钥加密，私钥解密 --加密: " + Base64.encodeBase64String(result));

        //私钥解密
        keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        result = cipher.doFinal(result);
        System.out.println("公钥加密，私钥解密 --解密: " + new String(result));
    }

    /**
     * 私钥加密，公钥解密
     *
     * @throws Exception
     */
    public static void priEn2PubDe() throws Exception {
        //私钥加密
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("私钥加密，公钥解密 --加密 : " + Base64.encodeBase64String(result));

        //公钥解密
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        result = cipher.doFinal(result);
        System.out.println("私钥加密，公钥解密   --解密: " + new String(result));
    }


    public static void main(String[] args) throws Exception {
        pubEn2PriDe();
        System.out.println("------------------------------------");
        priEn2PubDe();
    }
}
