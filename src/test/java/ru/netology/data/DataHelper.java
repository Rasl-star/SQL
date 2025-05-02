package ru.netology.data;

import lombok.Value;
import com.github.javafaker.Faker;

import java.sql.*;
import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static AuthInfo getAuthInfoWithTestData() {
        return new AuthInfo("vasya", "qwerty123"); // пользователь из базы
    }

    private static String generateRandomLogin() {
        return faker.name().username();
    }

    private static String generateRandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo generateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));
    }

    public static VerificationCode getVerificationCodeFor(String login) {
        String code = null;
        String sql = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                code = rs.getString("code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new VerificationCode(code);
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }
}

