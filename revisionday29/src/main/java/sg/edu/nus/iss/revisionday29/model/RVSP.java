package sg.edu.nus.iss.revisionday29.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class RVSP implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime confirmationDate;
    private String comments;
    private int totalCount;
    private String foodType;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public static RVSP createFromResults(SqlRowSet rs) {
        RVSP rvsp = new RVSP();
        rvsp.setId(rs.getInt("id"));
        rvsp.setName(rs.getString("name"));
        rvsp.setEmail(rs.getString("email"));
        rvsp.setPhone(rs.getString("phone"));
        rvsp.setConfirmationDate(getDateFromString(rs.getString("confirmation_date")));
        rvsp.setComments(rs.getString("comments"));
        rvsp.setFoodType(rs.getString("food_type"));
        return rvsp;
    }

    public static RVSP convertFromJSON(String json) throws IOException {
        RVSP rvsp = new RVSP();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jsObj = jr.readObject();
            rvsp.setName(jsObj.getString("name"));
            rvsp.setEmail(jsObj.getString("email"));
            rvsp.setPhone(jsObj.getString("phone"));
            rvsp.setConfirmationDate(getDateFromString(jsObj.getString("confirmation_date")));
            rvsp.setComments(jsObj.getString("comments"));
            rvsp.setFoodType(jsObj.getString("food_type"));
        }
        return rvsp;
    }

    public static LocalDateTime getDateFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime dateTime = LocalDateTime.parse(date,
                formatter);
        return dateTime;
    }

}
