import java.sql.*;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        String userName = "root";
        String password = "root";
        String connectionUrl = "jdbc:mysql://localhost:3307/mydb?verifyServerCertificate=false&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try(Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table qwe");
            statement.executeUpdate("create table IF not exists qwe(id MEDIUMINT not null AUTO_INCREMENT, name VARCHAR(30) not null, PRIMARY KEY(id));");
            statement.executeUpdate("insert into qwe (name) values('Inferno')");
            statement.executeUpdate("insert into qwe set name = 'Solomon Key'");

            ResultSet resultSet = statement.executeQuery("select * from qwe");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(2));
                System.out.println("----------------------------------");
            }

            System.out.println("+++++++++++++++++++++++++++++++++++");

            ResultSet resultSet1 = statement.executeQuery("select name from qwe where id = 2");
            while (resultSet1.next()) {
                System.out.println(resultSet1.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
