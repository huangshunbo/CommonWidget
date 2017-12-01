package com.android.basiclib.cryption;


import android.text.TextUtils;
import android.util.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * <br> ClassName:   TraderRSAUtil
 * <br> Description: RSA签名工具
 * <br>
 * <br> Date:        2017/8/2 16:36
 */
public class TraderRSAUtil {
    /**
     *<br> Description: 签名处理
     *<br> Date:        2017/8/2 16:37
     * @param prikeyvalue
     *                  私钥
     * @param sign_str
     *                  签名源内容
     * @return
     *                  签名文
     */
    public static String sign(String prikeyvalue, String sign_str) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(prikeyvalue,Base64.DEFAULT));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            Signature signet = Signature.getInstance("SHA1withRSA");
            signet.initSign(myprikey);
            signet.update(sign_str.getBytes("UTF-8"));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return Base64.encodeToString(signed, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *<br> Description: 签名验证
     *<br> Date:        2017/8/2 16:39
     * @param pubkeyvalue
     *                  公钥
     * @param oid_str
     *                  源文
     * @param signed_str
     *                  签名结果串
     * @return
     *                  验证成功与否
     */
    public static boolean checksign(String pubkeyvalue, String oid_str, String signed_str) {
        try {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64.decode(pubkeyvalue,Base64.DEFAULT));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
            byte[] signed = Base64.decode(signed_str,Base64.DEFAULT);
            Signature signetcheck = Signature.getInstance("SHA1withRSA");
            signetcheck.initVerify(pubKey);
            signetcheck.update(oid_str.getBytes("UTF-8"));
            return signetcheck.verify(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *<br> Description: 加密过程
     *<br> Date:        2017/8/2 16:41
     * @param publicKey
     *                  公钥
     * @param plainTextData
     *                  明文数据
     * @return
     *                  加密串
     * @throws Exception
     */
    public static String encrypt(String publicKey, String plainTextData) throws Exception {
        if (TextUtils.isEmpty(publicKey)) {
            throw new Exception("加密公钥为空, 请设置");
        }
        X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64.decode(publicKey,Base64.DEFAULT));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] output = cipher.doFinal(plainTextData.getBytes("UTF-8"));
            return Base64.encodeToString(output, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     *<br> Description: 解密过程
     *<br> Date:        2017/8/2 16:41
     *<br> Date:        2017/8/2 16:41
     * @param privateKey
     *                  私钥
     * @param cipherData
     *                  密文数据
     * @return
     *                  明文
     * @throws Exception
     *                  解密过程中的异常信息
     */
    public static String decrypt(String privateKey, String cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }

        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey,Base64.DEFAULT));
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, myprikey);
            byte[] output = cipher.doFinal(Base64.decode(cipherData,Base64.DEFAULT));
            return new String(output);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}

