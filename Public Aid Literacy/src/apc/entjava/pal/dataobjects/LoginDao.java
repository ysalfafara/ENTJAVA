package apc.entjava.pal.dataobjects;
import apc.entjava.pal.services.LoginService;
import apc.entjava.pal.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.*;


public class LoginDao implements LoginService {
    private DataSource ds;

    public LoginDao() {
        try {
            Context context = new InitialContext();
            this.ds = (DataSource) context.lookup("java:comp/env/jdbc/LoginDB");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    @Override
    public boolean login(String username, String password) {
        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM users WHERE username=? AND password=?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return true;
                else
                    return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String saveUserAccInDB(Users newUserAcc) {
        int i = 0;

        if(newUserAcc.getPassword().equals(newUserAcc.getConfirmPass())) {
            PreparedStatement ps = null;
            Connection con = null;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/public_aid_literacy", "pal", "palpass");
                String sql = "INSERT INTO users(firstName, lastName, gender, mobileNum, email, country, city, address, username, password) VALUES(?,?,?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, newUserAcc.getFirstName());
                ps.setString(2, newUserAcc.getLastName());
                ps.setString(3, newUserAcc.getGender());
                ps.setString(4, newUserAcc.getMobileNum());
                ps.setString(5, newUserAcc.getEmail());
                ps.setString(6, newUserAcc.getCountry());
                ps.setString(7, newUserAcc.getCity());
                ps.setString(8, newUserAcc.getAddress());
                ps.setString(9, newUserAcc.getUsername());
                ps.setString(10, newUserAcc.getPassword());

                i = ps.executeUpdate();
                System.out.println("You now have an account!");
            }
            catch(Exception e) {
                System.out.println(e);
            }

            finally {
                try {
                    con.close();
                    ps.close();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }

            if(i > 0) {
                return "home";
            }
            else {
                return "error";
            }
        }
        else {
            return "error";
        }
    }


    public static String updateUserAccInDB(Users updateUserAcc) {
        int i = 0;

        PreparedStatement ps = null;
        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/public_aid_literacy", "pal", "palpass");
            String sql = "UPDATE users set firstName=?, lastName=?, gender=?, mobileNum=?, email=?, country=?, city=?, address=?, username=?, password=? WHERE username='" + updateUserAcc.getUsername() + "'";
            ps = con.prepareStatement(sql);

            ps.setString(1,updateUserAcc.getFirstName());
            ps.setString(2,updateUserAcc.getLastName());
            ps.setString(3,updateUserAcc.getGender());
            ps.setString(4,updateUserAcc.getMobileNum());
            ps.setString(5,updateUserAcc.getEmail());
            ps.setString(6,updateUserAcc.getCountry());
            ps.setString(7,updateUserAcc.getCity());
            ps.setString(8,updateUserAcc.getAddress());
            ps.setString(9,updateUserAcc.getUsername());
            ps.setString(10,updateUserAcc.getPassword());

            i = ps.executeUpdate();
            System.out.println("You have updated your account!");

        }catch(Exception e) {
            System.out.println(e);
        }

        finally {
            try {
                con.close();
                ps.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        return "profile";
    }

}