package com.android.basiclib.net.download;

import android.os.AsyncTask;
import android.os.Environment;

import com.android.basiclib.net.NetManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

public class DownloadTask extends AsyncTask<String,Integer,Integer> {

    public static final int TYPE_SUCCESS=0;

    public static final int TYPE_FAILED=1;

    public static final int TYPE_CANCELED=3;

    private DownloadListener listener;

    private boolean isCanceled=false;

    private boolean isPaused=false;

    private int lastProgress;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    /**
     * 这个方法中的所有代码都会在子线程中运行，我们应该在这里处理所有的耗时任务。
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(String... params) {
        InputStream is=null;
        RandomAccessFile savedFile=null;
        File file=null;
        long downloadLength=0;   //记录已经下载的文件长度
        //文件下载地址
        String downloadUrl=params[0];
        //下载文件的名称
        String fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        //下载文件存放的目录
        String directory= Environment.getDownloadCacheDirectory().getPath();
        //创建一个文件
        file=new File(directory+fileName);
        if(file.exists()){
            //如果文件存在的话，得到文件的大小
            downloadLength=file.length();
        }


        /**
         * HTTP请求是有一个Header的，里面有个Range属性是定义下载区域的，它接收的值是一个区间范围，
         * 比如：Range:bytes=0-10000。这样我们就可以按照一定的规则，将一个大文件拆分为若干很小的部分，
         * 然后分批次的下载，每个小块下载完成之后，再合并到文件中；这样即使下载中断了，重新下载时，
         * 也可以通过文件的字节长度来判断下载的起始点，然后重启断点续传的过程，直到最后完成下载过程。
         */
        NetManager netManager = NetManager.create()
                .addHeader("RANGE","bytes="+downloadLength+"-")
                .get(downloadUrl);
        try {
            Response response= netManager.executeSync();
            //得到下载内容的大小
            long contentLength=getContentLength(response);
            if(contentLength==0){
                return TYPE_FAILED;
            }else if(contentLength==downloadLength){
                //已下载字节和文件总字节相等，说明已经下载完成了
                return TYPE_SUCCESS;
            }
            if(response!=null){
                is=response.body().byteStream();
                savedFile=new RandomAccessFile(file,"rw");
                savedFile.seek(downloadLength);//跳过已经下载的字节
                byte[] b=new byte[1024];
                int total=0;
                int len;
                while((len=is.read(b))!=-1){
                    if(isCanceled){
                        return TYPE_CANCELED;
                    }else {
                        total+=len;
                        savedFile.write(b,0,len);
                        //计算已经下载的百分比
                        int progress=(int)((total+downloadLength)*100/contentLength);
                        //注意：在doInBackground()中是不可以进行UI操作的，如果需要更新UI,比如说反馈当前任务的执行进度，
                        //可以调用publishProgress()方法完成。
                        publishProgress(progress);
                    }

                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(is!=null){
                    is.close();
                }
                if(savedFile!=null){
                    savedFile.close();
                }
                if(isCanceled&&file!=null){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    /**
     * 当在后台任务中调用了publishProgress(Progress...)方法之后，onProgressUpdate()方法
     * 就会很快被调用，该方法中携带的参数就是在后台任务中传递过来的。在这个方法中可以对UI进行操作，利用参数中的数值就可以
     * 对界面进行相应的更新。
     * @param values
     */
    protected void onProgressUpdate(Integer...values){
        int progress=values[0];
        if(progress>lastProgress){
            listener.onProgress(progress);
            lastProgress=progress;
        }
    }

    /**
     * 当后台任务执行完毕并通过Return语句进行返回时，这个方法就很快被调用。返回的数据会作为参数
     * 传递到此方法中，可以利用返回的数据来进行一些UI操作。
     * @param status
     */
    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_CANCELED:
                listener.onPause();
                break;
            default:
                break;
        }
    }

    public void  pauseDownload(){
        isPaused=true;
    }

    public void cancelDownload(){
        isCanceled=true;
    }

    /**
     * 得到下载内容的大小
     * @param downloadUrl
     * @return
     */
    private long getContentLength(Response response){
        if(response!=null&&response.isSuccessful()){
            long contentLength=response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return  0;
    }

}
