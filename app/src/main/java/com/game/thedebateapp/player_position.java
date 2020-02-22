package com.game.thedebateapp;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class player_position {
    private Long PlayerPosition;

    public player_position() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public player_position(Long playerposition) {
        PlayerPosition=playerposition;

    }

    public Long getPlayerPosition() {
        return PlayerPosition;
    }

    public void setPlayerPosition(Long playerPosition) {
        PlayerPosition = playerPosition;
    }

}


