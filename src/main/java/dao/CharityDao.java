package dao;

import models.Beneficiary;
import models.Charity;

import java.util.List;

public interface CharityDao {
    //create
    void add(Charity charity);

    //read
    List<Charity> getAll();
    List<Charity> getAllCharitiesForDonor(int donorId);
    Charity findById(int id);
    Charity findBCharityByUserId(int userId);

    //update
    void update(int id,String description, String trustDeed, String image);

    //delete
    void deleteById(int id);
    void clearAll();
}
