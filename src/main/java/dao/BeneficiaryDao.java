package dao;

import models.Beneficiary;
import models.User;

import java.util.List;

public interface BeneficiaryDao {
    //create
    void add(Beneficiary beneficiary);

    //read
    List<Beneficiary> getAll();
    List<Beneficiary> getAllBeneficiariesForACharity(int charityId);

    Beneficiary findById(int id);

    //delete
    void deleteById(int id);
    void clearAll();
}
