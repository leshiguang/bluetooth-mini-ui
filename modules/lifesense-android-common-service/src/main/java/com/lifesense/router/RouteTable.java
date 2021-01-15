package com.lifesense.router;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by qwerty
 * Create on 2020/12/9
 **/
public class RouteTable {
   private static Map<String, Class> routeMaps = new HashMap<>();

   public static Class match(String host) {
       return routeMaps.get(host);
   }

   public static void register(String host, Class c) {
       routeMaps.put(host, c);
   }
}
