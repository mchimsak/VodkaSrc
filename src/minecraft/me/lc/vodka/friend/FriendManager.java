package me.lc.vodka.friend;

import java.util.HashMap;
import java.util.Map;

public class FriendManager {
   static Map friendList = new HashMap();

   public static String getAliasOf(String user) {
      return (String)getFriendList().get(user);
   }
   Runnable r2 = ()-> System.out.println("233333333");

   public static Map getFriendList() {
      return friendList;
   }
}
