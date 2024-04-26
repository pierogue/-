package JDBC;

import java.sql.*;
import java.util.List;

public class JDBC_EX1
{

    public static void main( String[] args ) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:32768/DictionaryDatabase";
        String username = "root";
        String password = "pass";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("We're connected");
            do {
                System.out.println("Enter word:");
                String word = new java.util.Scanner(System.in).nextLine();
                List<String> list = findWord(connection, word);
                if (list.size() == 0) {
                    System.out.println("No such word in dictionary");
                } else {
                    System.out.println("Translate:");
                    for (String s : list) {
                        System.out.println(s);
                    }
                }
            } while (true);
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static List<String> findWord(Connection connection, String word) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("select translate from dictionary where word = '" + word + "'");
        List<String> list = new java.util.ArrayList<>();
        while(set.next()) {
            list.add(set.getString("translate"));
        }
        return list;
    }
}
