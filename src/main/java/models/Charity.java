package models;

import java.util.Objects;

public class Charity {
    private String description;
    private String trustDeed;
    private String image;
    private int userId;
    private int donorId;
    private int beneficiaryId;

    public Charity(String description, String trustDeed, String image, int userId, int donorId, int beneficiaryId) {
        this.description = description;
        this.trustDeed = trustDeed;
        this.image = image;
        this.userId = userId;
        this.donorId = donorId;
        this.beneficiaryId = beneficiaryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrustDeed() {
        return trustDeed;
    }

    public void setTrustDeed(String trustDeed) {
        this.trustDeed = trustDeed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public int getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(int beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charity charity = (Charity) o;
        return userId == charity.userId &&
                donorId == charity.donorId &&
                beneficiaryId == charity.beneficiaryId &&
                description.equals(charity.description) &&
                trustDeed.equals(charity.trustDeed) &&
                image.equals(charity.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, trustDeed, image, userId, donorId, beneficiaryId);
    }
}
