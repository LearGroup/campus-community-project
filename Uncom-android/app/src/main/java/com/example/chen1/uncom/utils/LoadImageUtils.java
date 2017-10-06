package com.example.chen1.uncom.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chen1 on 2017/10/4.
 */

public class LoadImageUtils {

    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }


    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public DiskLruCache openBitmapDiskLruCache(Context context){
        DiskLruCache mDiskLruCache = null;
        try {
            File cacheDir = getDiskCacheDir(context, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mDiskLruCache;
    }


    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    public static void getFirendHeaderImage(String url, Context context, ImageView imageView){
        if(url==null){
            url="http://uncomapp.oss-cn-beijing.aliyuncs.com/user_head/default-cion.png";
        }
        ImageLoader imageLoader=new ImageLoader(CoreApplication.newInstance().getRequestQueue(), new BitMapCache());
        if(CoreApplication.newInstance().getRequestQueue().getCache().get(url)!=null){
            byte[] data=CoreApplication.newInstance().getRequestQueue().getCache().get(url).data;
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(data,0, data.length));
            Log.v("imageCache", String.valueOf(data));
        }else{

            ImageLoader.ImageListener listener= ImageLoader.getImageListener(imageView,R.mipmap.zhiwu,R.mipmap.ic_launcher);
            imageLoader.get(url,listener);
        }
    }

}
