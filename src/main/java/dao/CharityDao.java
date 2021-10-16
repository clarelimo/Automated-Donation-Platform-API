package dao;

import models.Charity;

import java.util.List;

public interface CharityDao {
    //create
    void add(Charity charity);

    //read
    List<Charity> getAll();
    List<Charity> getAllCharitiesForDonor(int salesId);
    Charity findById(int id);

    //update
    void update(String description, String trustDeed, String image);

    //delete
    void deleteById(int id);
    void clearAll();
}
