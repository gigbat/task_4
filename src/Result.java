import java.sql.*;

public class Result {
    public static void showCount() throws ClassNotFoundException {
        String userName = "root";
        String password = "root";
        String connectionUrl = "jdbc:mysql://localhost:3307/mydb?verifyServerCertificate=false&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try(Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            Statement statement = connection.createStatement()) {
            ResultSet resultSetFirst = statement.executeQuery("select * from teacher inner join student on (teacher.id = student.id_group and teacher.id >= 2000)");
            ResultSet resultSetSecond = statement.executeQuery("select * from student, course, group, marks where student.id_group = marks.id" +
                    " and marks.name_course = 'Programming' and marks.mark > 4 order by student.last_name, student.first_name ASC");
            ResultSet resultSetTheBestStudent = statement.executeQuery("select count(*) from student, marks where student.id_group = marks.id " +
                    "and marks.mark = 5");
            ResultSet resultSetCountOfStudents = statement.executeQuery("select count(*) from student inner join group on (student.id_group = group.id)");
            ResultSet resultSetCountOfTeachers = statement.executeQuery("select count(*) from teacher inner join group on (teacher.id = group.id)");
            /*while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }*/
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
