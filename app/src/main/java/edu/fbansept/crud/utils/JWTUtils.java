package edu.fbansept.crud.utils;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JWTUtils {

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    public static JSONObject getBody(String JWTEncoded) throws UnsupportedEncodingException, JSONException {
        String[] split = JWTEncoded.split("\\.");
        return  new JSONObject(getJson(split[1]));
    }
}