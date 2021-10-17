package models;

import dao.DB;
import dao.DatabaseRule;
import dao.Sql2oCharityDao;
import dao.Sql2oUsersDao;
import org.junit.jupiter.api.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    Admin testAdmin;
    private static Sql2oCharityDao charityDao;
    private static Sql2oUsersDao usersDao;
    private static Connection conn;
    @BeforeAll
    public static void beforeAll(){ //access database
        DB.sql2o= new Sql2o("jdbc:postgresql://localhost:5432/donation_platform_test","softwaredev","1234");
        charityDao = new Sql2oCharityDao(DatabaseRule.sql2o);
        usersDao = new Sql2oUsersDao(DatabaseRule.sql2o);
        conn = DatabaseRule.sql2o.open();
    }
    @BeforeEach
    public void setUp(){// helper
        testAdmin= new Admin( 1);
        testAdmin.save();
        setupCharity();
    }
    @AfterEach
    void afterEach(){
        try(Connection con = DB.sql2o.open()){
            String clearCharities="DELETE FROM admin *;";
            con.createQuery(clearCharities).executeUpdate();
            System.out.println("clearing database");
            charityDao.clearAll();
            usersDao.clearAll();
        }
    }
    @Test
    void instanceOfSave(){
        assertEquals(Admin.admin_getAllCharities().size(), 1);
    }
    @Test
    void instanceApproveCharity(){
        testAdmin.approveCharity();
        assertTrue(Admin.admin_getAllCharities().get(0).isApproval());
    }
    @Test
    void instanceDeleteById(){
        Admin.deleteAdminById(Admin.admin_getAllCharities().get(0).getId());
        assertEquals(Admin.admin_getAllCharities().size(),0);
    }
@Test
    void instanceOfClearAll(){
        Admin.clearAll();
        assertEquals(Admin.admin_getAllCharities().size(),0);
}
@Test
    void instanceOfById(){
        Admin.admin_findCharityById(testAdmin.getCharityid());
        assertEquals(Admin.admin_getAllCharities().get(0).getCharityid(),testAdmin.getCharityid());
}
@Test
    void instanceOfGetCharity(){
    Admin.admin_findCharityById(testAdmin.getCharityid());
    assertEquals(Admin.getCharityById(testAdmin.getCharityid()).getId(), testAdmin.getCharityid());
}
    public Charity setupCharity(){
        String description = "To improve the life  opportunities of youth aged 10-24 through strategic empowerment for sustainable development";
        Charity charity = new Charity(description, "https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/586357/GD2.pdf","https://images.unsplash.com/photo-1586227740560-8cf2732c1531?ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=300&q=60",1,1);
        charityDao.add(charity);
        return charity;
    }
}