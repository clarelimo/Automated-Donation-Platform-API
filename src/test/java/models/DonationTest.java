package models;

import dao.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DonationTest {
    Donation testDonation;
    @BeforeAll
    public static void beforeAll(){ //access database
        DB.sql2o= new Sql2o("jdbc:postgresql://localhost:5432/donation_platform_test","softwaredev","1234");
    }
    @BeforeEach
    public void setUp(){// helper
       testDonation= new Donation(1,1,false, "monthly", new Timestamp(new Date().getTime()),"stripe");
        testDonation.save();
    }
    @AfterEach
    void afterEach(){
        try(Connection con = DB.sql2o.open()){
            String clearCharities="DELETE FROM donors *;";
            con.createQuery(clearCharities).executeUpdate();
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

}