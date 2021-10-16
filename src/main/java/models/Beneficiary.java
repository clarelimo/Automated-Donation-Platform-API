package models;

import java.util.Objects;

public class Beneficiary {
    private String name;
    private String testimony;
    private String image;
    private int charityId;

    private  int id;

    public Beneficiary(String name, String testimony, String image, int charityId) {
        this.name = name;
        this.testimony = testimony;
        this.image = image;
        this.charityId = charityId;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

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

    public String getTestimony() {
        return testimony;
    }

    public void setTestimony(String testimony) {
        this.testimony = testimony;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beneficiary that = (Beneficiary) o;
        return name.equals(that.name) &&
                testimony.equals(that.testimony) &&
                image.equals(that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, testimony, image);
    }
}
