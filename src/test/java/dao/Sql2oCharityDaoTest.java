package dao;

import models.Charity;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;

import static org.junit.Assert.*;

public class Sql2oCharityDaoTest {
    private static Sql2oCharityDao charityDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        charityDao = new Sql2oCharityDao(DatabaseRule.sql2o);
        conn = DatabaseRule.sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        charityDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void add() {
    }

    @Test
    public void getAll() {
        Charity charity = setupCharity();
        Charity anotherCharity = anotherCharity();
        assertEquals(2,charityDao.getAll().size());

    }

    @Test
    public void getAllCharitiesForDonor() {
        Charity charity = setupCharity();
        Charity anotherCharity = anotherCharity();
        assertEquals(2,charityDao.getAllCharitiesForDonor(1).size());
    }

    @Test
    public void findById() {
    }

    @Test
    public void update() {
    }

    @Test
    public void deleteById() {
        Charity charity = setupCharity();
        Charity anotherCharity = anotherCharity();
        assertEquals(2,charityDao.getAll().size());
        charityDao.deleteById(charity.getId());
        assertEquals(1,charityDao.getAll().size());
    }

    @Test
    public void clearAll() {
        Charity charity = setupCharity();
        Charity anotherCharity = anotherCharity();
        assertEquals(2,charityDao.getAll().size());
        charityDao.clearAll();
        assertEquals(0,charityDao.getAll().size());
    }

    public Charity setupCharity(){
        String description = "To improve the life  opportunities of youth aged 10-24 through strategic empowerment for sustainable development";
        Charity charity = new Charity(description, "https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/586357/GD2.pdf","https://images.unsplash.com/photo-1586227740560-8cf2732c1531?ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=300&q=60",1,1,1);
        return charity;
    }

    public Charity anotherCharity(){
        String description = "To improve the life  opportunities of youth aged 10-24 through strategic empowerment for sustainable development";
        Charity charity = new Charity(description, "https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/586357/GD2.pdf","https://images.unsplash.com/photo-1593642634367-d91a135587b5?ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHw2fHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=300&q=60",1,1,1);
        return charity;
    }
}