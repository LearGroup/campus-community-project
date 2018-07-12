package com.example.chen1.uncom.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.chen1.uncom.bean.ThinkerBean;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by chen1 on 2017/12/18.
 */

public class AsynLoadImageUtils {

    private int MaxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);// kB
    private LruCache<String,Bitmap> cache=null;
    private ExecutorService executorService= Executors.newFixedThreadPool(5);
    private final Handler handler=new Handler();
    private Context context;
    public AsynLoadImageUtils(){
        Log.v("init,....................",".......................");
    }

    public Bitmap loadBitmap(final String url, final ImageCallback callback, final Context context, final LruCache<String,Bitmap> cache) {
        this.context=context;
        this.cache=cache;

            Bitmap soft=cache.get(url);
            if(soft!=null){
                Log.v("命中！！！！","success............");
                callback.ImageLoaded(soft);
            }

          executorService.submit(new Runnable() {
            @Override
            public void run() {
                Log.v("loadImageURL:",url);
                if(url.indexOf("luban_disk_cache")!=-1){
                  final Bitmap bitmap=  BitmapFactory.decodeFile(url);
                  cache.put(url, bitmap);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.ImageLoaded(bitmap);
                        }
                    });
                }else{
                    Flowable.just(url)
                            .map(new Function<String,Bitmap >() {
                                @Override
                                public Bitmap apply(@NonNull String list) throws Exception {
                                    // 同步方法直接返回压缩后的文件
                                    File file= Luban.with(context).load(list).get(list);
                                    Log.v("file path :","..........."+file.getAbsolutePath());
                                    return BitmapFactory.decodeFile(file.getAbsolutePath());
                                }
                            })
                            .subscribe(new Consumer<Bitmap>() {
                                @Override
                                public void accept(final Bitmap bitmap) throws Exception {
                                    cache.put(url, bitmap);
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.ImageLoaded(bitmap);
                                        }
                                    });

                                }
                            });
                }

            }
        });
        return null;
    }

    public interface  ImageCallback{
        public void ImageLoaded(Bitmap bitmap);
    }


    public  ArrayList<ArrayList<String>> addImageUrlList(ThinkerBean thinkerBean){
        ArrayList<ArrayList<String>> imageUrlList=null;
        String[] origin_url=null;
        String[] cache_url=null;
        String[] online_url=null;
        if(thinkerBean.getImgUrl()!=null && thinkerBean.getImgUrl().length()>0){
            origin_url=thinkerBean.getImgUrl().split(",");
        }
        if(thinkerBean.getImgCacheUrl()!=null&&thinkerBean.getImgCacheUrl().length()>0){
            cache_url=thinkerBean.getImgCacheUrl().split(",");
        }
        if(thinkerBean.getImgOnlineUrl()!=null && thinkerBean.getImgOnlineUrl().length()>0){
            online_url=thinkerBean.getImgOnlineUrl().split(",");
        }
        imageUrlList=new ArrayList<ArrayList<String>>();
        if(origin_url!=null && origin_url.length>=1){
            for (int i=0;i<origin_url.length;i++){
                if(i%2==0){
                    ArrayList<String> item=new ArrayList<String>();
                    if( cache_url!=null && cache_url.length>i){
                        item.add(cache_url[i]);
                    }else if(online_url!=null && online_url.length>i && cache_url.length<i){
                        item.add(online_url[i]);
                    }else{
                        item.add(origin_url[i]);
                    }
                    imageUrlList.add(item);
                }else{
                    if(cache_url!=null && cache_url.length>i){
                        imageUrlList.get(i/2).add(cache_url[i]);
                    }else if(online_url!=null &&  online_url.length>i && cache_url.length<i){
                        imageUrlList.get(i/2).add(online_url[i]);
                    }else{
                        imageUrlList.get(i/2).add(origin_url[i]);
                    }
                }
            }
        }
        return imageUrlList;
    }

    public  ArrayList<ArrayList<String>> addImageUrlList(String  list){
        ArrayList<ArrayList<String>> imageUrlList=new ArrayList<>();
        String []   origin_url=list.split(",");
        for (int i = 0; i < origin_url.length; i++) {
            if(i%2==0){
                ArrayList<String> item=new ArrayList<String>();
                item.add(origin_url[i]);
                imageUrlList.add(item);
            }else{
                imageUrlList.get(i/2).add(origin_url[i]);
            }
        }
        return  imageUrlList;
    }

    public void clearCache(){
            cache=null;
    }
}
