package entity;

import entity.LEVEL;

import java.sql.Date;
import java.sql.Time;

public class Rating {

    int id;
    String username;
    LEVEL level;
    Date dueDate;
    Time playedAt;
    Time gameTime;

    public Rating() {
    }

    public Rating(int place, int id, String username, LEVEL level, Date dueDate, Time playedAt, Time gameTime) {
    }

    public Rating(int id, String username, LEVEL level, Date dueDate, Time playedAt, Time gameTime) {
        this.id = id;
        this.username = username;
        this.level = level;
        this.dueDate = dueDate;
        this.playedAt = playedAt;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Time getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Time playedAt) {
        this.playedAt = playedAt;
    }

    public Time getGameTime() {
        return gameTime;
    }

    public void setGameTime(Time gameTime) {
        this.gameTime = gameTime;
    }

    @Override
    public String toString() {
        return id +
                " \t" + username +
                " \t" + level +
                " \t" + dueDate +
                " \t" + playedAt +
                " \t" + gameTime;
    }
}
