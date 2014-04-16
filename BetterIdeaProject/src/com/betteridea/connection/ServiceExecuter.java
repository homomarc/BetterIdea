package com.betteridea.connection;

import android.os.AsyncTask;

public class ServiceExecuter extends AsyncTask<String, Integer, String>{
	@Override
	protected String doInBackground(String... data) {
        String check = null;
        try {
    		if(data[0] == "createUser"){
    			// Data1 = Username / Data2 = Usermail
    			Service.createUserData(data[1], data[2]);
    		}else if(data[0] == "changeName"){
    			// Data1 = Username
    			Service.changeUsername(data[1]);
    		}else if(data[0] == "changeCredits"){
    			// Data1 = Credits to change
    			Service.changeCredits(data[1]);
    		}else if(data[0] == "getCredits"){
    			Service.getCredits();
    		}else if(data[0] == "addSpam"){
    			Service.addSpam();
    		}else if(data[0] == "showTopic"){
    			// Data1 = TopicID
//    			Service.showTopic(data[1]);
    			return "TestAsynchron";
    		}else if(data[0] == "newRandTopic"){
    			Service.getNewRandTopic();
    		}else if(data[0] == "rankList"){
    			Service.getRankingList();
    		}else if(data[0] == "setScore"){
    			Service.setUserScore();
    		}else if(data[0] == "addIdeaCount"){
    			Service.addIdeaCount();
    		}else if(data[0] == "addTopicCount"){
    			Service.addTopicCount();
    		}
        } catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}
}

