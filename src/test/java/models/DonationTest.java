package models;

import dao.DB;
import dao.DatabaseRule;
import dao.Sql2oCharityDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class DonationTest {
    Donation testDonation;
    Sql2oCharityDao charityDao;
    @BeforeAll
    public static void beforeAll(){ //access database
        DB.sql2o= new Sql2o("jdbc:postgresql://localhost:5432/donation_platform_test","moringa","climo");
    }
    @BeforeEach
    public void setUp(){// helper
       testDonation= new Donation(1,1,false, "monthly", new Timestamp(new Date().getTime()),"stripe");
        testDonation.save();
        charityDao = new Sql2oCharityDao(DatabaseRule.sql2o);
    }
    @AfterEach
    void afterEach(){
        charityDao.clearAll();
        try(Connection con = DB.sql2o.open()){
            String clearCharities="DELETE FROM donors *;";
            String clear="DELETE FROM charities *;";
            con.createQuery(clearCharities).executeUpdate();
            con.createQuery(clear).executeUpdate();
        }
    }
    @Test
    void instanceOfSave(){
        assertEquals(Donation.getDonors().size(), 1);
    }

    @Test
    void instanceDeleteById(){
        Donation.deleteById(Donation.getDonors().get(0).getId());
        assertEquals(Admin.getAllCharities().size(),0);
    }
    @Test
    void instanceOfClearAll(){
        Donation.clearAll();
        assertEquals(Donation.getDonors().size(),0);
    }
    @Test
    void instanceOfById(){
        Donation.findDonorById(testDonation.getId());
        assertEquals(Donation.getDonors().get(0).getId(),testDonation.getId());
    }

   @Test
    public void getAllNonAnonymousDonorsForACharity() {
        Charity charity = setupCharity();
        testDonation= new Donation(1,charity.getId(),false, "monthly", new Timestamp(new Date().getTime()),"stripe");
        testDonation.save();
        testDonation= new Donation(1,charity.getId(),false, "monthly", new Timestamp(new Date().getTime()),"stripe");
        testDonation.save();

        assertEquals(2,Donation.getAllNonAnonymousDonorsForACharity(charity.getId()).size());
    }

    public Charity setupCharity(){
        String description = "To improve the life  opportunities of youth aged 10-24 through strategic empowerment for sustainable development";
        Charity charity = new Charity(description, "https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/586357/GD2.pdf","https://images.unsplash.com/photo-1586227740560-8cf2732c1531?ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=300&q=60",1,1,1);
        charityDao.add(charity);
        return charity;
    }

    public Donation setUpDonor(){// helper
        testDonation= new Donation(1,1,false, "monthly", new Timestamp(new Date().getTime()),"stripe");
        testDonation.save();
        return testDonation;
    }


}