package com.example.easyimageloadermodule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {
    private static ImageLoader instance;
    private Context context;
    private LruCache<String, Bitmap> memoryCache;
    private ExecutorService executorService;
    private Handler handler;

    private ImageLoader(Context context) {
        this.context = context.getApplicationContext();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        memoryCache = new LruCache<>(cacheSize);
        executorService = Executors.newFixedThreadPool(5);
        handler = new Handler(Looper.getMainLooper());
    }

    public static synchronized ImageLoader getInstance(Context context) {
        if (instance == null) {
            instance = new ImageLoader(context);
        }
        return instance;
    }

    public void loadImage(String url, ImageView imageView) {
        Bitmap bitmap = getBitmapFromMemoryCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            // Set the placeholder image using the resource ID
            imageView.setImageResource(R.drawable.placeholder_image);
            executorService.submit(new ImageLoaderTask(url, imageView));
        }
    }

    private Bitmap getBitmapFromMemoryCache(String url) {
        return memoryCache.get(url);
    }

    private void addBitmapToMemoryCache(String url, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(url) == null) {
            memoryCache.put(url, bitmap);
        }
    }

    private class ImageLoaderTask implements Runnable {
        private String url;
        private ImageView imageView;

        public ImageLoaderTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            try {
                Bitmap bitmap = downloadBitmap(url);
                addBitmapToMemoryCache(url, bitmap);
                handler.post(new DisplayBitmapTask(bitmap, imageView));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap downloadBitmap(String url) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL imageUrl = new URL(url);
            connection = (HttpURLConnection) imageUrl.openConnection();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private class DisplayBitmapTask implements Runnable {
        private Bitmap bitmap;
        private ImageView imageView;

        public DisplayBitmapTask(Bitmap bitmap, ImageView imageView) {
            this.bitmap = bitmap;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            imageView.setImageBitmap(bitmap);
        }
    }
}
