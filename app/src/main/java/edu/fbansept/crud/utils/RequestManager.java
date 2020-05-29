package edu.fbansept.crud.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class RequestManager {
    public static final String url = "http://192.168.0.50:8080/";
    //public static final String url = "https://atosdevbackend.herokuapp.com/api/";
    private static RequestManager instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private RequestManager(Context context) {
        requestQueue = getRequestQueue(context);

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized RequestManager getInstance(Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public void addToRequestQueue(Context context, Request req) {
        getRequestQueue(context).add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}