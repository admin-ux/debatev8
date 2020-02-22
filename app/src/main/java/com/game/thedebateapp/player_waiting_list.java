package com.game.thedebateapp;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
//Description       : This class is the class that is used to build firebase waiting list object
//Inner Workings    :
public class player_waiting_list {
    private String UserID;
    private int PositionInList;
    private boolean InUse;

    public player_waiting_list() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public player_waiting_list(String userid, int positionInList, boolean inUse) {

        UserID=userid;
        PositionInList=positionInList;
        InUse=inUse;

    }

    public int getPositionInList() {
        return PositionInList;
    }

    public void setPositionInList(int positionInList) {
        PositionInList = positionInList;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public boolean getInUse (){
        return InUse;
    }

    public void setInUse (boolean inUse){
        InUse = inUse;
    }
}


