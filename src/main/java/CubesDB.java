import java.sql.*;

/**
 * Created by hl4350hb on 4/5/2017.
 */
public class CubesDB {
    // Defines the driver to be used.
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // Connection string.
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cubes";
    static final String USER = "";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException err) {
            System.out.println("Error starting driver.");
            err.printStackTrace();
            System.exit(-1);
        }

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {



            String commandText = "create table if not exists cubestbl (Name varchar(50), Time_Taken double)";
            statement.executeUpdate(commandText);
            System.out.println("Successfully created table");


            String[] allNames = {"Cubestormer II robot", "Fakhri Raihaan (using his feet)", "Ruxin Liu (age 3)", "Mats Valk (human record holder)"};
            double[] allTimes = {5.270, 27.93, 99.33, 6.27};

            for (int x = 0; x < allNames.length; x++) {
                String prepStatInsert = "insert into cubestbl values ( ? , ? )";
                PreparedStatement psInsert = connection.prepareStatement(prepStatInsert);
                psInsert.setString(1, allNames[x]);
                psInsert.setDouble(2, allTimes[x]);
                psInsert.executeUpdate();

//                String newRecord = "insert into cubestbl values ('" + allNames[x] + "', " + allTimes[x] + ")";
//                statement.executeUpdate(newRecord);
            }


            String fetchAll = "select * from cubestbl";
            ResultSet myData = statement.executeQuery(fetchAll);

            while (myData.next()) {
                String myName = myData.getString("Name");
                double myTime = myData.getFloat("Time_Taken");
                System.out.println("Name: " + myName + "  Time: " + myTime);
            }


            String wantNewEntry = Input.getStringInput("Want to add a new entry? (Y/N)");
            while (!wantNewEntry.equalsIgnoreCase("N")) {
                String solver = Input.getStringInput("Enter name:");
                double time_taken = Input.getDoubleInput("Enter time:");
                String prepStatInsert = "insert into cubestbl values ( ? , ? )";
                PreparedStatement psInsert = connection.prepareStatement(prepStatInsert);
                psInsert.setString(1, solver);
                psInsert.setDouble(2, time_taken);
                psInsert.executeUpdate();
                wantNewEntry = Input.getStringInput("Want to add a new entry? (Y/N)");

            }


            myData.close();
            statement.close();
            connection.close();
        }
        catch (SQLException err) {
            System.out.println("Error with query.");
            err.printStackTrace();
        }
    }
}
