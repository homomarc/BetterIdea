package com.betteridea.connection;

import android.os.AsyncTask;

public class ServiceExecuter extends AsyncTask<String, Integer, String>{
	@Override
	protected String doInBackground(String... data) {
        String check = null;
        try {
    		if(data[0] == "createUser"){
    			// Data1 = Username / Data2 = Usermail
    			check = Service.createUserData(data[1], data[2]);
    		}else if(data[0] == "changeName"){
    			// Data1 = Username
    			check = Service.changeUsername(data[1]);
    		}else if(data[0] == "changeCredits"){
    			// Data1 = Credits to change
    			check = Service.changeCredits(data[1]);
    		}else if(data[0] == "getCredits"){
    			check = Service.getCredits();
    		}else if(data[0] == "addSpam"){
    			check = Service.addSpam();
    		}else if(data[0] == "showTopic"){
    			// Data1 = TopicID
    			check = Service.showTopic(data[1]);
    		}else if(data[0] == "newRandTopic"){
    			check = Service.getNewRandTopic();
    		}else if(data[0] == "rankList"){
    			check = Service.getRankingList();
    		}else if(data[0] == "setScore"){
    			check = Service.setUserScore();
    		}else if(data[0] == "addIdeaCount"){
    			check = Service.addIdeaCount();
    		}else if(data[0] == "addTopicCount"){
    			check = Service.addTopicCount();
    		}
        } catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}
}

