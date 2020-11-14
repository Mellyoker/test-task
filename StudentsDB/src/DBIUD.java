import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;

public class DBIUD implements reader {

    private final String url = "jdbc:mysql://localhost/studentsdata?serverTimezone=Europe/Moscow&useSSL=false";
    private final String username = "root";
    private final String password = "1234567890";

    public void startDBIUD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                String sql = "CREATE TABLE students (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), surname VARCHAR(50),patronymic VARCHAR(50), birthday DATE, class VARCHAR(50))";
                statement.executeUpdate(sql);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String tmp = "";

                while (true) {
                    System.out.println("Введите одну из команд( 1.добавить, 2.удалить, 3.список, 4.выход)");
                    tmp = reader.readLine();

                    if (tmp.equalsIgnoreCase("добавить") || tmp.equalsIgnoreCase("1")) {
                        insert(statement);
                    } else if (tmp.equalsIgnoreCase("удалить") || tmp.equalsIgnoreCase("2")) {
                        System.out.println("введите id студента");
                        delete(statement, Integer.parseInt(reader.readLine()));
                    } else if (tmp.equalsIgnoreCase("список") || tmp.equalsIgnoreCase("3")) {
                        select(statement);
                    } else if (tmp.equalsIgnoreCase("выход") || tmp.equals("4")){
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insert(Statement statement) throws SQLException {

        String name;
        String surname;
        String patronymic;
        String birthday;
        String group;

        try {
            System.out.println("Введите имя");
            name = readString();

            System.out.println("Введите фамилию");
            surname = readString();

            System.out.println("Введите отчество");
            patronymic = readString();

            System.out.println("Введите дату рождения (гггг:мм:дд)");
            birthday = readString();

            System.out.println("Введите группу");
            group = readString();

            String SQLCommand = String.format("INSERT students (name, surname, patronymic, birthday, class) " +
                            "VALUES ('%s', '%s', '%s', '%s', '%s')",
                    name, surname, patronymic, birthday, group);

            statement.executeUpdate(SQLCommand);
        } catch (IOException e) {
            System.out.println("Не верный формат строки");
        } catch (MysqlDataTruncation ex) {
            System.out.println("Не правильный формат даты.");
        }


    }

    private void delete(Statement statement, int id) throws SQLException {
        String SQLCommand = String.format("DELETE FROM students WHERE id = %d", id);
        statement.executeUpdate(SQLCommand);
    }

    private void select(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

        String name;
        String surname;
        String patronymic;
        String birthday;
        String group;
        int id;


        while (resultSet.next()) {
            name = resultSet.getString("name");
            surname = resultSet.getString("surname");
            patronymic = resultSet.getString("patronymic");
            birthday = resultSet.getDate("birthday").toString();
            group = resultSet.getString("class");
            id = resultSet.getInt("Id");

            System.out.printf("Имя : %s | Фамилия: %s | Отчество: %s | Дата рождения: %s | Группа: %s | Уникальный номер: %d \n", name, surname, patronymic, birthday, group, id);
        }
    }

}
