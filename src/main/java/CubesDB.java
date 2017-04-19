import java.sql.*;

/**
 * This program connects to a database and provides input and update functions
 * to rubics cube solving robots.
 */
public class CubesDB {
    // Defines the driver to be used.
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // Connection string.
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cubes";
    static final String USER = "";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        // Defines table name variable along with its columns.
        String table_name = "cubestbl";
        String name_col = "Bot_Name";
        String time_col = "Time_Taken";

        // Exception handler.
        try {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException err) {
            System.out.println("Error starting driver.");
            err.printStackTrace();
            System.exit(-1);
        }

        // Exception handler for connecting to database.
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {

            // Drops table to bring back to original data.
            String dropTable = "drop table if exists " + table_name;
            statement.executeUpdate(dropTable);

            // Creates table.
            String commandText = "create table if not exists " + table_name + " (" + name_col + " varchar(50), " + time_col + " double)";
            statement.executeUpdate(commandText);
            System.out.println("Successfully created table");

            // Makes two arrays of static values.
            String[] allNames = {"Cubestormer II robot", "Fakhri Raihaan (using his feet)", "Ruxin Liu (age 3)", "Mats Valk (human record holder)"};
            double[] allTimes = {5.270, 27.93, 99.33, 6.27};

            // Creates prepared statements for adding and updating entries.
            String prepStatInsert = "insert into " + table_name + " values ( ? , ? )";
            PreparedStatement psInsert = connection.prepareStatement(prepStatInsert);
            String prepStatUpdate = "update " + table_name + " set " + time_col + " = ? where " + name_col + " = ?";
            PreparedStatement psUpdate = connection.prepareStatement(prepStatUpdate);

            // Loops through arrays and adds to table.
            for (int x = 0; x < allNames.length; x++) {
                psInsert.setString(1, allNames[x]);
                psInsert.setDouble(2, allTimes[x]);
                psInsert.executeUpdate();
            }

            // Gathers all the entries.
            String fetchAll = "select * from " + table_name;
            ResultSet myData = statement.executeQuery(fetchAll);

            // Displays all the data.
            while (myData.next()) {
                String myName = myData.getString(name_col);
                double myTime = myData.getFloat(time_col);
                System.out.println(String.format("Name: %s Time: %.3f", myName, myTime));
            }

            // Menu options string.
            String menu_question = "What action would you like to take:\n" +
                    "1 -- Add New Entry\n" +
                    "2 -- Update An Entry\n"  +
                    "3 -- Exit Program";

            // Sets loop indicator boolean.
            boolean wants_more = true;
            // Gets input from User and performs action based on input.
            int action_number = Input.getPositiveIntInput(menu_question);
            do {
                switch (action_number) {
                    case 1:     // Add new entry.
                        String bot_name = Input.getStringInput("Enter name:");
                        double time_taken = Input.getDoubleInput("Enter time:");
                        psInsert.setString(1, bot_name);
                        psInsert.setDouble(2, time_taken);
                        psInsert.executeUpdate();
                        System.out.println(bot_name + " has been added!");
                        break;
                    case 2:     // Update entry.
                        // Gets inputs from User and performs update query.
                        String which_bot = Input.getStringInput("Enter name of bot:");
                        double new_time = Input.getDoubleInput("Enter new time:");
                        psUpdate.setString(2, which_bot);
                        psUpdate.setDouble(1, new_time);
                        psUpdate.executeUpdate();
                        break;
                    case 3:
                        // Sets boolean for ending loop.
                        wants_more = false;
                        break;
                    default:
                        System.out.println("That is not a valid input. Try again.");
                        break;
                }
                // Displays menu again to get new action.
                if (action_number != 3) {
                    action_number = Input.getPositiveIntInput(menu_question);
                }
            }
            while (wants_more == true);

            // Closes connection objects.
            myData.close();
            statement.close();
            connection.close();
        }
        // Catch for broad SQL exceptions.
        catch (SQLException err) {
            System.out.println("Error with query.");
            err.printStackTrace();
        }
    }
}


