package databaseNew;

import entity.LEVEL;
import entity.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RatingRepository {

    private DBHandler dbHandler = new DBHandler();

    public void addGameResultToDB(Rating rating) throws SQLException {
        Connection connection = dbHandler.getConnection();
        if (rating.getLevel() == LEVEL.EASY) {
            String query = "INSERT INTO easyRating(username, level, dueDate, playedAt, gameTime)" + "VALUE (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rating.getUsername());
            preparedStatement.setString(2, String.valueOf(rating.getLevel()));
            preparedStatement.setDate(3, rating.getDueDate());
            preparedStatement.setTime(4, rating.getPlayedAt());
            preparedStatement.setTime(5, rating.getGameTime());

            preparedStatement.execute();

        } else if (rating.getLevel() == LEVEL.MODERATE) {
            String query = "INSERT INTO moderateRating(username, level, dueDate, playedAt, gameTime)" + "VALUE (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rating.getUsername());
            preparedStatement.setString(2, String.valueOf(rating.getLevel()));
            preparedStatement.setDate(3, rating.getDueDate());
            preparedStatement.setTime(4, rating.getPlayedAt());
            preparedStatement.setTime(5, rating.getGameTime());

            preparedStatement.execute();

        } else if (rating.getLevel() == LEVEL.HARD) {
            String query = "INSERT INTO hardRating(username, level, dueDate, playedAt, gameTime)" + "VALUE (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rating.getUsername());
            preparedStatement.setString(2, String.valueOf(rating.getLevel()));
            preparedStatement.setDate(3, rating.getDueDate());
            preparedStatement.setTime(4, rating.getPlayedAt());
            preparedStatement.setTime(5, rating.getGameTime());

            preparedStatement.execute();
        }
    }

    public ArrayList<Rating> getAllRatingsFromDB(Rating rating) throws SQLException {
        if (rating.getLevel() == LEVEL.EASY) {
            ArrayList<Rating> easyRating = new ArrayList<>();
            String query = "SELECT * FROM easyRating";
            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Rating rating1 = new Rating(
                        result.getInt("place"),
                        result.getInt("id"),
                        result.getString("username"),
                        LEVEL.valueOf(result.getString("level")),
                        result.getDate("dueDate"),
                        result.getTime("playedAt"),
                        result.getTime("gameTime")
                );
                easyRating.add(rating1);
            }
            return easyRating;

        } else if (rating.getLevel() == LEVEL.MODERATE) {
            ArrayList<Rating> moderateRating = new ArrayList<>();
            String query = "SELECT * FROM moderateRating";
            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Rating rating2 = new Rating(
                        result.getInt("place"),
                        result.getInt("id"),
                        result.getString("username"),
                        LEVEL.valueOf(result.getString("level")),
                        result.getDate("dueDate"),
                        result.getTime("playedAt"),
                        result.getTime("gameTime")
                );
                moderateRating.add(rating2);
            }
            return moderateRating;

        } else if (rating.getLevel() == LEVEL.HARD) {
            ArrayList<Rating> hardRaiting = new ArrayList<>();
            String query = "SELECT * FROM hardRating";
            PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Rating raiting3 = new Rating(
                        result.getInt("place"),
                        result.getInt("id"),
                        result.getString("username"),
                        LEVEL.valueOf(result.getString("level")),
                        result.getDate("dueDate"),
                        result.getTime("playedAt"),
                        result.getTime("gameTime")
                );
                hardRaiting.add(raiting3);
            }
            return hardRaiting;
        }
        return null;
    }
}

