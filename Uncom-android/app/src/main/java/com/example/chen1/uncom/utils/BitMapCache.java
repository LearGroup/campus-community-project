package com.example.chen1.uncom.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by chen1 on 2017/10/4.
 */

public class BitMapCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> mCache;
    public BitMapCache(){
        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }
    @Override
    public Bitmap getBitmap(String url) {

        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {

        mCache.put(url, bitmap);
    }
}
