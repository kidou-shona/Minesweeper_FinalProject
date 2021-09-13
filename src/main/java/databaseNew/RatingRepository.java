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
        String query = "INSERT INTO rating(username, level, gameTime)" + "VALUE (?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, rating.getUsername());
        preparedStatement.setString(2, String.valueOf(rating.getLevel()));
        preparedStatement.setInt(3, rating.getGameTime());

        preparedStatement.execute();
    }

    public ArrayList<Rating> getAllRatingsFromDB(LEVEL level) throws SQLException {
        ArrayList<Rating> ratings = new ArrayList<>();
        String query = "SELECT * FROM rating WHERE level = " + level.toString() + "ORDER BY 'gameTime' DESC LIMIT 10;";
        PreparedStatement preparedStatement = dbHandler.getConnection().prepareStatement(query);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            Rating rating = new Rating(
                    result.getInt("id"),
                    result.getString("username"),
                    LEVEL.valueOf(result.getString("level")),
                    result.getTimestamp("dueDate"),
                    result.getInt("gameTime")
            );
            ratings.add(rating);
        }
        return ratings;
    }
}


