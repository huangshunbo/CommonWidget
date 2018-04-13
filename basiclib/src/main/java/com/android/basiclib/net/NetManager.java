package com.android.basiclib.net;

import android.os.Environment;
import android.text.TextUtils;

import com.android.basiclib.MApplication;
import com.android.basiclib.cryption.AbstractCryption;
import com.android.basiclib.cryption.NormalCryption;
import com.android.basiclib.net.callback.AbstractCallBack;
import com.android.basiclib.net.exception.ApiException;
import com.android.basiclib.net.util.NetUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;

/*
*
* 1.配置 (超时 header params cookie SSL)
* 2.上传下载 服务端用七牛
* 3.Request类型 Callback类型
* 4.缓存 OkHttp只对get请求做缓存，post不会缓存
* 5.加解密
* 6.支持本地mock数据
* */
public class NetManager implements Callback{

    private OkHttpClient okHttpClient;
    private Request.Builder builder;
    private Request request;
    private HashMap<String,String> params = new HashMap<>();
    private HashMap<String,String> headers = new HashMap<>();
    private int readTimeOut = 60000;
    private int connectTimeOut = 60000;
    private int writeTimeOut = 60000;
    private String url;
    private REQUEST_TYPE type;
    private static final long cacheSize = 1024 * 1024 * 20;// 默认缓存文件最大限制大小20M
    private static final String cacheDir = Environment.getExternalStorageDirectory() + "/netcaches";
    private Cache cache = new Cache(new File(cacheDir), cacheSize);
    private int cacheTime = 60000;
    private SSLSocketFactory sslSocketFactory = null;
    private AbstractCallBack abstractCallBack;
    private AbstractCryption abstractCryption = new NormalCryption();
    private boolean useCookie = false;

    private String localMockData = "";

    private NetManager(){
        okHttpClient = new OkHttpClient();
    }
    public static NetManager create(){
        return new NetManager();
    }

    public NetManager get(String url){
        this.url = url;
        type = REQUEST_TYPE.GET;
        return this;
    }
    public NetManager post(String url){
        this.url = url;
        type = REQUEST_TYPE.POST;
        return this;
    }
    public NetManager addParam(String key,String value){
        params.put(key,value);
        return this;
    }
    public NetManager addParams(HashMap hashMap){
        if(hashMap != null) {
            params.putAll(hashMap);
        }
        return this;
    }
    public NetManager addHeader(String key,String value){
        headers.put(key,value);
        return this;
    }
    public NetManager addHeaders(HashMap hashMap){
        if(hashMap != null) {
            headers.putAll(hashMap);
        }
        return this;
    }
    public NetManager setConnectTimeOut(int time){
        if(time > 0){
            connectTimeOut = time;
        }
        return this;
    }
    public NetManager setReadTimeOut(int time){
        if(time > 0){
            readTimeOut = time;
        }
        return this;
    }
    public NetManager setWriteTimeOut(int time){
        if(time > 0){
            writeTimeOut = time;
        }
        return this;
    }
    public NetManager setCacheSize(long cacheSize){
        if(cacheSize > 0) {
            cache = new Cache(new File(cacheDir), cacheSize);
        }
        return this;
    }
    public NetManager setCacheTime(int time){
        if(time > 0){
            cacheTime = time;
        }
        return  this;
    }
    public NetManager setCryption(AbstractCryption cryption){
        abstractCryption = cryption;
        return this;
    }
    public NetManager useCookie(boolean useCookie){
        this.useCookie = useCookie;
        return this;
    }
    public NetManager upload(){
        return this;
    }
    public NetManager download(){
        return this;
    }
    public void setCertificates(InputStream... certificates)
    {
        try
        {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates)
            {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try
                {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e)
                {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            sslSocketFactory = sslContext.getSocketFactory();


        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public NetManager addLocalMockData(int rawId){
        InputStream is = MApplication.application.getResources().openRawResource(rawId);
        InputStreamReader read = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(read);
        StringBuffer sb = new StringBuffer();
        String s;
        try {
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        addLocalMockData(sb.toString());
        return this;
    }
    public NetManager addLocalMockData(String mockData){
        localMockData = mockData;
        return this;
    }
    public Response executeSync() throws IOException {
        buildConfig();
        return okHttpClient.newCall(request).execute();
    }
    public void execute(){
        execute(null);
    }
    public void execute(AbstractCallBack callback){
        abstractCallBack = callback;
        buildConfig();
        if(abstractCallBack != null){
            abstractCallBack.onStart();
        }
        okHttpClient.newCall(request).enqueue(this);
    }

    private void buildConfig() {
        builder = new Request.Builder();
        if(type == REQUEST_TYPE.GET){
            builder.url(NetUtils.handleGetUrl(url,params));
            builder.get();
        }else {
            builder.url(url);
            builder.post(NetUtils.handlePostParams(params,abstractCryption));
        }
        builder.headers(NetUtils.handleHeaders(headers));
        builder.cacheControl(new CacheControl.Builder().maxStale(cacheTime,TimeUnit.MILLISECONDS).build());

        request = builder.build();
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeOut,TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeOut,TimeUnit.MILLISECONDS)
                .cache(cache)
                .build();
        if(sslSocketFactory != null){
            okHttpClient = okHttpClient.newBuilder().sslSocketFactory(sslSocketFactory).build();
        }
        if(useCookie){
            okHttpClient = okHttpClient.newBuilder().cookieJar(new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }
                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            }).build();
        }
        if(!TextUtils.isEmpty(localMockData)){
            mockResponse(localMockData);
        }
    }

    private void mockResponse(final String mockData) {
        okHttpClient = okHttpClient.newBuilder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                ResponseBody responseBody = new ResponseBody() {
                    @Override
                    public MediaType contentType() {
                        return MediaType.parse("application/x-www-form-urlencoded");
                    }

                    @Override
                    public long contentLength() {
                        return mockData.length();
                    }

                    @Override
                    public BufferedSource source() {
                        return Okio.buffer(Okio.source(new ByteArrayInputStream(mockData.getBytes())));
                    }
                };
                Response response = chain.proceed(request);
                response = response.newBuilder()
                        .code(200)
                        .message("success")
                        .body(responseBody)
                        .build();
                return response;
            }
        }).build();
    }

    public void executeWithController(){

    }

    @Override
    public void onFailure(Call call, IOException e) {
        if(abstractCallBack != null){
            abstractCallBack.onFailure(new ApiException(ApiException.ERROR_NET_SERVICE_404,"onFailure"));
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(abstractCallBack != null){
            Object obj = abstractCallBack.parseResponse(abstractCryption.decrypt(response.body().string()));
            if(obj != null){
                abstractCallBack.onSuccess(obj);
            }

        }
    }

    public enum REQUEST_TYPE{
        GET,POST
    }
}
