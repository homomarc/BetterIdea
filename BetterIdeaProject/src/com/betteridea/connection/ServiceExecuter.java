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
    			// Data1 = Credits to change / Data2 = authorID
    			check = Service.changeCredits(data[1], data[2]);
    		}else if(data[0] == "getCredits"){
    			check = Service.getCredits();
    		}else if(data[0] == "addSpam"){
    			// Data1 = Credits to change / Data2 = authorID
    			check = Service.addSpam(data[1], data[2]);
    		}else if(data[0] == "showTopic"){
    			// Data1 = TopicID
    			check = Service.showTopic(data[1]);
    		}else if(data[0] == "newRandTopic"){
    			for(int i=0; i<5; i++){
    				check = Service.getNewRandTopic();
    			}
    		}else if(data[0] == "rankList"){
    			check = Service.getRankingList();
    		}else if(data[0] == "setScore"){
    			check = Service.setUserScore();
    		}else if(data[0] == "addTopic"){
    			Service.addTopicCount();
    			// Data1 = topicTitle / Data2 = topicDescription
    			check = Service.addTopic(data[1], data[2]);
    		}else if(data[0] == "addIdea"){
    			// Data1 = text / Data2 = topicID
    			Service.addIdeaCount();
    			check = Service.addIdea(data[1], data[2]);
    		}else if(data[0] == "allUserTopics"){
    			check = Service.allUserTopic();
    		}else if(data[0] == "getUserRank"){
    			check = Service.getUserRank();
    		}else if(data[0] == "uncover"){
    			check = Service.uncoverIdea(data[1]);
    		}else if(data[0] == "valuate"){
    			check = Service.valuateIdea(data[1]);
    		}else if(data[0] == "closeTopic"){
    			check = Service.closeTopic(data[1]);
    		}
    		return check;
        } catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
}

