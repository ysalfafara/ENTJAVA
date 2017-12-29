package apc.entjava.pal;
import apc.entjava.pal.dataobjects.LoginDao;
import apc.entjava.pal.services.LoginService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name="users")
@RequestScoped
public class Users {
    @ManagedProperty(value = "#{authBean}")
    private AuthBean authBean;

    public AuthBean getAuthBean() {
        return authBean;
    }

    public void setAuthBean(AuthBean authBean) {
        this.authBean = authBean;
    }

    private LoginService loginService = new LoginDao();

    public String login() {
        if (loginService.login(username, password)) {
            authBean.setLoggedUsername(username);
            return "home";
        } else {
            return "error";
        }
    }

    /* USER INFORMATION */
    private int Id;
    private String firstName;
    private String lastName;
    private String gender;
    private String mobileNum;
    private String email;
    private String country;
    private String city;
    private String address;
    private String username;
    private String password;
    private String confirmPass;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String saveUserAcc(Users newUserAcc) {
        authBean.setLoggedUsername(username);
        return LoginDao.saveUserAccInDB(newUserAcc);
    }

    public String editUserAcc(Users editUserAcc) {
        return AuthBean.editUserAccInDB(editUserAcc);
    }

    public String updateUserAcc(Users updateUserAcc) {
        return LoginDao.updateUserAccInDB(updateUserAcc);
    }

    /* USER DONATION */
    private String donationType;
    private String donationAmount;

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public String getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(String donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String saveUserDonation(Users newUserDonation) {
        return authBean.saveUserDonationInDB(newUserDonation);
    }


}



