package models;

import java.util.Objects;

public class User {
    private String name;
    private String email;
    private String password;
    private String category;
    private String phoneNumber;
    private String image;
    private int id;

    public User(String name, String email, String password, String category, String phoneNumber, String image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.image = image;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                email.equals(user.email) &&
                password.equals(user.password) &&
                category.equals(user.category) &&
                phoneNumber.equals(user.phoneNumber) &&
                image.equals(user.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, category, phoneNumber, image);
    }
}
