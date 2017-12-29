package apc.entjava.pal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


@ManagedBean()
@RequestScoped

public class Donation {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String confirmPass;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public String add() {
        int i = 0;

        if(password != null) {
            PreparedStatement ps = null;
            Connection con = null;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/public_aid_literacy", "pal", "palpass");
                String sql = "INSERT INTO users(firstName, lastName, email, username, password) VALUES(?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, username);
                ps.setString(5, password);

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
                return "output";
            }
            else {
                return "error";
            }
        }
        else {
            return "error";
        }
    }
}



