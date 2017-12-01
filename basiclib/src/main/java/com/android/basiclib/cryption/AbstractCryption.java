package com.android.basiclib.cryption;

public interface AbstractCryption {
    //加密
    String encrypt(String str);
    //解密
    String decrypt(String str);
}
