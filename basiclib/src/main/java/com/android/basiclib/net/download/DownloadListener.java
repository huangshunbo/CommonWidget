package com.android.basiclib.net.download;

public interface DownloadListener {


    /**
     * 通知当前的下载进度
     * @param progress
     */
    void onProgress(int progress);

    /**
     * 通知下载成功
     */
    void onSuccess();

    /**
     * 通知下载失败
     */
    void onFailed();

    /**
     * 通知下载暂停事件
     */
    void onPause();
    /**
     * 通知下载暂停事件
     */
    void onCancel();

}
