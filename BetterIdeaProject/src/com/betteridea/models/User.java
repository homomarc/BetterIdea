package com.betteridea.models;

public class User {

	 private static User instance = null;
	   protected User() {
	      // Exists only to defeat instantiation.
	   }
	   public static User getInstance() {
	      if(instance == null) {
	         instance = new User();
	      }
	      return instance;
	   }
}
