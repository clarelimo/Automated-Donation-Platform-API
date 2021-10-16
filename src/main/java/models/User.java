package models;

import java.util.Objects;

public class User {
    private String name;
    private String email;
    private String password;
    private String categories;
    private String phone_number;
    private String image;
    private int id;

    public User(String name, String email, String password, String categories, String phone_number, String image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.categories = categories;
        this.phone_number = phone_number;
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
                categories.equals(user.categories) &&
                phone_number.equals(user.phone_number) &&
                image.equals(user.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, categories, phone_number, image);
    }
}
