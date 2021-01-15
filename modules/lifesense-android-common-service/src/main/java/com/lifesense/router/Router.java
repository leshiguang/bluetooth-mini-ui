package com.lifesense.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Create by qwerty
 * Create on 2020/12/9
 **/
public class Router {
    private Context context;

    public static Router getInstance() {
        return SingleHolder.getInstance();
    }

    private Router() {
    }

    private static class SingleHolder {
        static Router router = new Router();

        public static Router getInstance() {
            return router;
        }
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    public void push(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        RouterData routerData = parseUrl(url);
        Class clazz = RouteTable.match(routerData.host);
        Intent intent = new Intent(context, clazz);
        injectParams(intent, routerData.params);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    private void injectParams(Intent intent, Map<String, Object> params) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() instanceof Boolean) {
                intent.putExtra(entry.getKey(), (boolean) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                intent.putExtra(entry.getKey(), (int) entry.getValue());
            } else {
                intent.putExtra(entry.getKey(), (String) entry.getValue());
            }
        }
    }


    private RouterData parseUrl(String url) {
        RouterData routerData = new RouterData();
        Uri uri = Uri.parse(url);
        routerData.setHost(uri.getHost());
        Set<String> paramNames = uri.getQueryParameterNames();
        Map<String, Object> params = new HashMap<>();
        if (paramNames != null) {
            for (String name : paramNames) {
                String param = uri.getQueryParameter(name);
                if (isBoolean(param)) {
                    boolean boolParam = Boolean.parseBoolean(param);
                    params.put(name, boolParam);
                } else if (isInteger(param)) {
                    int intParam = Integer.parseInt(param);
                    params.put(name, intParam);
                } else {
                    params.put(name, param);
                }
            }
        }
        routerData.setParams(params);
        return routerData;
    }

    private boolean isBoolean(String value) {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return true;
        }
        return false;
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private class RouterData {
        String host;
        String path;
        Map<String, Object> params;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }
    }
}
