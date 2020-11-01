import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class DataBase {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    public static void test() throws ClassNotFoundException {
        String userName = "root";
        String password = "root";
        String connectionUrl = "jdbc:mysql://localhost:3307/mydb?verifyServerCertificate=false&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {
            createTables(statement);
            putValueIntoTables(connection, statement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTables(Statement statement) throws SQLException {
        statement.executeUpdate("create table IF not exists course (id int auto_increment primary key, course_title varchar(45) not null)");
        statement.executeUpdate("create table IF not exists marks (id int not null, name_course varchar(45) not null, mark int not null)");
        statement.executeUpdate("create table IF not exists student (id int auto_increment primary key, first_name varchar(45) not null, last_name varchar(45) not null, id_group int not null)");
        statement.executeUpdate("create table IF not exists teacher (id int not null, first_name varchar(45) not null, last_name varchar(45) not null, email varchar(45) null, unique(id))");
        statement.executeUpdate("create table IF not exists mydb.group (id int, name varchar(45) not null, unique(name))");
    }

    private static void putValueIntoTables(Connection connection, Statement statement) throws SQLException, IOException {
        System.out.print("Enter \"exit\" to power off program. Enter your tables: ");
        String table = bf.readLine();
        while (!table.equals("exit")) {
            if (table.equals("course")) putValuesForCourse(statement);
            if (table.equals("group")) putValuesForGroup(statement);
            if (table.equals("student")) putValuesOfStudents(statement);
            if (table.equals("mark")) putValuesForMarks(statement);
            if (table.equals("teacher")) putValuesOfTeachers(statement);

            System.out.print("Enter \"exit\" to power off program. Enter your tables: ");
            table = bf.readLine();
        }
    }

    private static void putValuesForCourse(Statement statement) throws SQLException, IOException {
        System.out.print("Enter name of the course: ");
        String courseTitle = bf.readLine();
        statement.executeUpdate("insert into course set course_title = '" + courseTitle + "'");
    }

    private static void putValuesForGroup(Statement statement) throws SQLException, IOException {
        ResultSet resultSet = statement.executeQuery("select * from course");
        while (resultSet.next()) {
            System.out.print(resultSet.getInt(1) + "\t");
            System.out.println(resultSet.getString(2));
        }

        System.out.print("Enter number (id). Select one of the course: ");
        int id = Integer.parseInt(bf.readLine());

        System.out.print("Enter name of the group: ");
        String name = bf.readLine();

        statement.executeUpdate("insert into mydb.group (id, name) values ('" + id + "','" + name + "')");
    }

    private static void putValuesForMarks(Statement statement) throws SQLException, IOException {
        ResultSet resultSet = statement.executeQuery("select * from student");

        while (resultSet.next()) {
            System.out.print(resultSet.getInt(1) + "\t");
            System.out.println(resultSet.getString(2) + "\t");
            System.out.println(resultSet.getString(3) + "\t");
            System.out.println(resultSet.getString(4));
        }

        System.out.print("Enter id of the student: ");
        int id = Integer.parseInt(bf.readLine());

        resultSet = statement.executeQuery("select * from course");

        while (resultSet.next()) {
            System.out.print(resultSet.getInt(1) + "\t");
            System.out.println(resultSet.getString(2));
        }

        System.out.print("Enter course title: ");
        String name = bf.readLine();

        System.out.print("Enter mark for a student (from 0 to 100): ");
        String mark = bf.readLine();

        statement.executeUpdate("insert into marks (id, name_course, mark) values ('" + id + "','" + name + "','" + mark + "')");
    }

    private static void putValuesOfStudents(Statement statement) throws SQLException, IOException {
        System.out.print("Enter first name of a student: ");
        String firstName = bf.readLine();

        System.out.print("Enter last name of a student: ");
        String lastName = bf.readLine();

        ResultSet resultSet = statement.executeQuery("select * from mydb.group");

        while (resultSet.next()) {
            System.out.print(resultSet.getInt(1) + "\t");
            System.out.println(resultSet.getString(2));
        }

        System.out.print("Select group of a student: ");
        int idGroup = Integer.parseInt(bf.readLine());

        statement.executeUpdate("insert into student (first_name, last_name, id_group) values ('" + firstName + "','" + lastName + "','" + idGroup + "')");
    }

    private static void putValuesOfTeachers(Statement statement) throws SQLException, IOException {
        System.out.print("Select course (id): ");
        int idCourse = Integer.parseInt(bf.readLine());

        System.out.print("Enter first name of a teacher: ");
        String firstName = bf.readLine();

        System.out.print("Enter last name of a teacher: ");
        String lastName = bf.readLine();

        System.out.print("Enter email if you have: ");
        String email = bf.readLine();

        statement.executeUpdate("insert into teacher (id, first_name, last_name, email) values ('" + idCourse + "','" + firstName + "','" + lastName + "','" + email + "')");

    }
}
