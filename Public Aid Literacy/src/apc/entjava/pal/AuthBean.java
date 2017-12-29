package apc.entjava.pal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@ManagedBean
@SessionScoped
public class AuthBean implements Serializable {
    private static String loggedUsername;

    public String getLoggedUsername() {
        return loggedUsername;
    }

    public void setLoggedUsername(String loggedUsername) {
        this.loggedUsername = loggedUsername;
    }

    public List<Users> getUserList() {
        List<Users> list = new ArrayList<Users>();
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/public_aid_literacy", "pal", "palpass");

            String sql = "SELECT * FROM users WHERE username='" + loggedUsername + "'";
            ps= con.prepareStatement(sql);
            rs= ps.executeQuery();

            while (rs.next()) {
                Users usr = new Users();
                usr.setId(rs.getInt("userId"));
                usr.setFirstName(rs.getString("firstName"));
                usr.setLastName(rs.getString("lastName"));
                usr.setGender(rs.getString("gender"));
                usr.setMobileNum(rs.getString("mobileNum"));
                usr.setEmail(rs.getString("email"));
                usr.setCountry(rs.getString("country"));
                usr.setCity(rs.getString("city"));
                usr.setAddress(rs.getString("address"));
                usr.setUsername(rs.getString("username"));
                usr.setPassword(rs.getString("password"));

                list.add(usr);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                con.close();
                ps.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return list;
    }


    public static String editUserAccInDB(Users editUserAcc) {
        Users editRecord = null;
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/public_aid_literacy", "pal", "palpass");

            String sql = "SELECT * FROM users WHERE username='" + loggedUsername + "'";
            ps= con.prepareStatement(sql);
            rs= ps.executeQuery();

            if(rs != null) {
                rs.next();
                editRecord = new Users();
                editRecord.setId(rs.getInt("userId"));
                editRecord.setFirstName(rs.getString("firstName"));
                editRecord.setLastName(rs.getString("lastName"));
                editRecord.setGender(rs.getString("gender"));
                editRecord.setMobileNum(rs.getString("mobileNum"));
                editRecord.setEmail(rs.getString("email"));
                editRecord.setCountry(rs.getString("country"));
                editRecord.setCity(rs.getString("city"));
                editRecord.setAddress(rs.getString("address"));
                editRecord.setUsername(rs.getString("username"));
                editRecord.setPassword(rs.getString("password"));

                sessionMapObj.put("editRecordObj", editRecord);

            }
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return "/edit_account.xhtml?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
}
