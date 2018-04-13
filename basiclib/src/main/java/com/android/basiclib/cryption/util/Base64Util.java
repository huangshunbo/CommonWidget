package com.android.basiclib.cryption.util;

import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Base64 工具类
 * 可逆 字节码的编码方式之一
 * 用处：
 * 计算机中任何数据都是按ascii码存储的，而ascii码的128～255之间的值是不可见字符。
 * 而在网络上交换数据时，比如说从A地传到B地，往往要经过多个路由设备，由于不同的设备对字符的处理方式有一些不同，
 * 这样那些不可见字符就有可能被处理错误，这是不利于传输的。
 * 所以就先把数据先做一个Base64编码，统统变成可见字符，这样出错的可能性就大降低了
 */
public class Base64Util {
    /**
     * Base64加密
     *
     * @param string 加密字符串
     * @return 加密结果字符串
     */
    public static String base64EncodeStr(String string) {
        if (TextUtils.isEmpty(string)) return "";
        return Base64.encodeToString(string.getBytes(), Base64.DEFAULT);
    }

    /**
     * Base64解密
     *
     * @param string 解密字符串
     * @return 解密结果字符串
     */
    public static String base64DecodedStr(String string) {
        if (TextUtils.isEmpty(string)) return "";
        return new String(Base64.decode(string, Base64.DEFAULT));
    }

    /**
     * Base64加密
     *
     * @param file 加密文件
     * @return 加密结果字符串
     */
    public static String base64EncodeFile(File file) {
        if (null == file) return "";

        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * Base64解密
     *
     * @param filePath 解密文件路径
     * @param code     解密文件编码
     * @return 解密结果文件
     */
    public static File base64DecodedFile(String filePath, String code) {
        if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(code)) {
            return null;
        }

        FileOutputStream fos = null;
        File desFile = new File(filePath);
        try {
            byte[] decodeBytes = Base64.decode(code.getBytes(), Base64.DEFAULT);
            fos = new FileOutputStream(desFile);
            fos.write(decodeBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return desFile;
    }
}
