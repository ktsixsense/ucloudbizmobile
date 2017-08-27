package com.kt.ucloudbizmobile;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by LTR on 2017-08-17.
 */

public class ApiGenerator {

    public static String getSignature(Map<String,String> map, String secretKey) {
        Set s = map.entrySet();
        Iterator it = s.iterator();
        String temp = "";


        while ( it.hasNext() ) {
            Map.Entry entry = (Map.Entry) it.next();

            String key = null;
            String value = null;

            try {
                key = URLEncoder.encode((String) entry.getKey(), "UTF-8").replace("+", "%20");
                value = URLEncoder.encode((String) entry.getValue(), "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException e) { }

            temp += (key + "=" + value + "&");
        }
        temp = temp.substring(0, temp.length()-1);
        temp = temp.toLowerCase();

        Mac mac;
        byte[] encryptedBytes = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            mac.init(keySpec);
            mac.update (temp.getBytes());
            encryptedBytes = mac.doFinal();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] encodedBytesBase64 = Base64.encode(encryptedBytes, Base64.DEFAULT);

        String signature = null;
        try {
            signature = URLEncoder.encode(new String(encodedBytesBase64), "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return signature;
    }

    public static String getCommandString(HashMap<String, String> requests) {
        Set s = requests.entrySet();
        Iterator it = s.iterator();
        String temp = "";

        while ( it.hasNext() ) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value =(String) entry.getValue();
            temp += (key + "=" + value + "&");
        }
        return temp;
    }

    public static String apiGenerator(String _apiKey, String _secretKey, String _command, boolean isM2, String zoneid) {
        HashMap<String, String> requests = new HashMap<String, String>();
        String basicURL = "";
        // 2. URL
        if (isM2)
            basicURL = "https://api.ucloudbiz.olleh.com/server/v2/client/api?";
        else
            basicURL = "https://api.ucloudbiz.olleh.com/server/v1/client/api?";


        // 3. Key - dayer4 key
        //String apiKey = "kizK9RwyBEt1tC5yCC3HfsySST-aaQfz7-pcL3aySgRXBRanIucts0bSjeCtmAtFYwpmouPTl-Q6iOmu9VdMkg";
        //String secretKey = "NmczQzPOE-CoYLbKpvo3UHJSaZ_6e9SC3tJIYsMIoiTJYMWMn8x-DpzBRTzzSkk0xegYz7g2yrvt_8jRrScxHQ";

        String apiKey = _apiKey;
        String secretKey = _secretKey;
        String command = _command;

        // 4. commands
        requests.put("apikey", apiKey);
        //requests.put("productcode", "SSD 100G");
        requests.put("command", command);
        //requests.put("name", "myapitestdisk2");

        // Zone ID 입력할 경우에는 parameter로 던져주고 all을 입력하면 모든 zone 보여주도록
        if(!zoneid.equals("all")) {
            requests.put("zoneid", zoneid);
        }

        // 5. Signature
        Map<String, String> sigMap = new TreeMap<String, String>(requests);
        String signature = getSignature(sigMap, secretKey);

        // 6. requests
        String commandString = getCommandString(requests);

        String finalURL = basicURL + commandString + "signature=" + signature;
        finalURL = finalURL.substring(0, finalURL.length()-3);

        Log.d("URL:", finalURL);

        /*try {
            new getHttpResponse().execute(new URL(finalURL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        return finalURL;
    }
}
