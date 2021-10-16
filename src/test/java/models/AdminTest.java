package models;

import dao.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    Admin testAdmin;
    @BeforeAll
    public static void beforeAll(){ //access database
        DB.sql2o= new Sql2o("jdbc:postgresql://localhost:5432/donation_platform_test","moringa","climo");
    }
    @BeforeEach
    public void setUp(){// helper
        testAdmin= new Admin(1, 1);
        testAdmin.save();
    }
    @AfterEach
    void afterEach(){
        try(Connection con = DB.sql2o.open()){
            String clearCharities="DELETE FROM admin *;";
            con.createQuery(clearCharities).executeUpdate();
        }
    }
    @Test
    void instanceOfSave(){
        assertEquals(Admin.getAllCharities().size(), 1);
    }
    @Test
    void instanceApproveCharity(){
        testAdmin.approveCharity();
        assertTrue(Admin.getAllCharities().get(0).isApproval());
    }
    @Test
    void instanceDeleteById(){
        Admin.deleteById(Admin.getAllCharities().get(0).getId());
        assertEquals(Admin.getAllCharities().size(),0);
    }
@Test
    void instanceOfClearAll(){
        Admin.clearAll();
        assertEquals(Admin.getAllCharities().size(),0);
}
@Test
    void instanceOfById(){
        Admin.findCharityById(testAdmin.getId());
        assertEquals(Admin.getAllCharities().get(0).getId(),testAdmin.getId());
}
}