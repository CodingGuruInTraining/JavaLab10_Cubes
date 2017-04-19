import java.sql.*;

/**
 * Created by hl4350hb on 4/5/2017.
 */
public class CubesDB {
    // Defines the driver to be used.
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // Connection string.
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cubes";
    static final String USER = "abc";
    static final String PASSWORD = "one";
//    static final String table_name = "cubestbl";

    public static void main(String[] args) {

        String table_name = "cubestbl";
        String name_col = "Bot_Name";
        String time_col = "Time_Taken";

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

            String dropTable = "drop table if exists " + table_name;
            statement.executeUpdate(dropTable);

            String commandText = "create table if not exists " + table_name + " (" + name_col + " varchar(50), " + time_col + " double)";
            statement.executeUpdate(commandText);
            System.out.println("Successfully created table");


            String[] allNames = {"Cubestormer II robot", "Fakhri Raihaan (using his feet)", "Ruxin Liu (age 3)", "Mats Valk (human record holder)"};
            double[] allTimes = {5.270, 27.93, 99.33, 6.27};

            String prepStatInsert = "insert into " + table_name + " values ( ? , ? )";
            PreparedStatement psInsert = connection.prepareStatement(prepStatInsert);
            String prepStatUpdate = "update " + table_name + " set " + time_col + " = ? where " + name_col + " = ?";
            PreparedStatement psUpdate = connection.prepareStatement(prepStatUpdate);
            String prepStatDelete = "delete from " + table_name + " where " + name_col + " like ?";
            PreparedStatement psDelete = connection.prepareStatement(prepStatDelete);
            String prepStatSearch = "select * from " + table_name + " where " + name_col + " = ?";
            PreparedStatement psSearch = connection.prepareStatement(prepStatSearch);


            for (int x = 0; x < allNames.length; x++) {

                psInsert.setString(1, allNames[x]);
                psInsert.setDouble(2, allTimes[x]);
                psInsert.executeUpdate();

//                String newRecord = "insert into cubestbl values ('" + allNames[x] + "', " + allTimes[x] + ")";
//                statement.executeUpdate(newRecord);
            }


            String fetchAll = "select * from " + table_name;
            ResultSet myData = statement.executeQuery(fetchAll);

            while (myData.next()) {
                String myName = myData.getString(name_col);
                double myTime = myData.getFloat(time_col);
                System.out.println(String.format("Name: %s Time: %.3f", myName, myTime));
//                System.out.println("Name: " + myName + "  Time: " + myTime);
            }


            String menu_question = "What action would you like to take:\n" +
                    "1 -- Add New Entry\n" +
                    "2 -- Update An Entry\n"  +
                    "3 -- Exit Program";

            boolean wants_more = true;
            int action_number = Input.getPositiveIntInput(menu_question);

//            while (wants_more == true) {
            do {
                switch (action_number) {
                    case 1:
                        String bot_name = Input.getStringInput("Enter name:");
                        double time_taken = Input.getDoubleInput("Enter time:");
                        psInsert.setString(1, bot_name);
                        psInsert.setDouble(2, time_taken);
                        psInsert.executeUpdate();
                        System.out.println(bot_name + " has been added!");
                        break;
                    case 2:
                        String which_bot = Input.getStringInput("Enter name of bot:");
                        double new_time = Input.getDoubleInput("Enter new time:");

//                        psSearch.setString(1, which_bot);
//                        System.out.println(psSearch.toString());
//                        ResultSet myData2 = statement.executeQuery(prepStatSearch);
////                        myData.next();
//                        int search_count = myData.getFetchSize();
//                        if (search_count == 1) {
//
//                        }





//                        myData = statement.executeQuery(fetchAll);
//                        if (myData.getString(which_bot).isEmpty()) {
//                            System.out.println("There is no bot");
//                        }



                        psUpdate.setString(2, which_bot);
                        psUpdate.setDouble(1, new_time);
                        psUpdate.executeUpdate();



//                        psDelete.setString(1, which_bot);
//                        psDelete.executeUpdate();
////
//                        psInsert.setString(1, which_bot);
//                        psInsert.setDouble(2, new_time);
//                        psInsert.executeUpdate();




//                        psUpdate.setDouble(1, new_time);
//                        psUpdate.setString(2, which_bot);
//                        psUpdate.executeUpdate();
                        break;
                    case 3:
                        wants_more = false;
                        break;
                    default:
                        System.out.println("That is not a valid input. Try again.");
                        break;
                }
                if (action_number != 3) {
                    action_number = Input.getPositiveIntInput(menu_question);
                }
            }
            while (wants_more == true);


//            String wantNewEntry = Input.getStringInput("Want to add a new entry? (Y/N)");
//            while (!wantNewEntry.equalsIgnoreCase("N")) {
//                String bot_name = Input.getStringInput("Enter name:");
//                double time_taken = Input.getDoubleInput("Enter time:");
////                String prepStatInsert = "insert into " + table_name + " values ( ? , ? )";
////                PreparedStatement psInsert = connection.prepareStatement(prepStatInsert);
//                psInsert.setString(1, bot_name);
//                psInsert.setDouble(2, time_taken);
//                psInsert.executeUpdate();
//                wantNewEntry = Input.getStringInput("Want to add a new entry? (Y/N)");
//
//            }


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


