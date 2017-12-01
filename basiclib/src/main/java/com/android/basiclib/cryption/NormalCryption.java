package com.android.basiclib.cryption;

/**
 * <br> ClassName:   NormalCryption
 * <br> Description:  默认不加密
 *
 * <br> Author:      huangshunbo
 * <br> Date:        2017/11/30 17:41
 */
public class NormalCryption implements AbstractCryption {
    @Override
    public String encrypt(String str) {
        return str;
    }

    @Override
    public String decrypt(String str) {
        return str;
    }
}
