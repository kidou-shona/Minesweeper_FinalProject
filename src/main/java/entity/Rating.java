package entity;

import entity.LEVEL;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Rating {

    int id;
    String username;
    LEVEL level;
    Timestamp dueDate;
    int gameTime;

    public Rating(int id, String username, LEVEL level, Timestamp dueDate, int gameTime) {
        this.id = id;
        this.username = username;
        this.level = level;
        this.dueDate = dueDate;
        this.gameTime = gameTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LEVEL getLevel() {
        return level;
    }

    public void setLevel(LEVEL level) {
        this.level = level;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    @Override
    public String toString() {
        return id +
                " \t" + username +
                " \t" + level +
                " \t" + dueDate +
                " \t" + gameTime;
    }
}
