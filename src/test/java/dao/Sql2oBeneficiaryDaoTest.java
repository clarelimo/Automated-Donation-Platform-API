package dao;

import models.Beneficiary;
import models.Charity;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;

import static org.junit.Assert.assertEquals;


public class Sql2oBeneficiaryDaoTest {
    private static Sql2oBeneficiaryDao beneficiaryDao;
    private static Sql2oCharityDao charityDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        beneficiaryDao = new Sql2oBeneficiaryDao(DatabaseRule.sql2o);
        charityDao = new  Sql2oCharityDao(DatabaseRule.sql2o);
        conn = DatabaseRule.sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        beneficiaryDao.clearAll();
        charityDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void getAll() {
        Beneficiary beneficiary = setupBeneficiary();
        Beneficiary anotherBeneficiary = setupBeneficiary();
        assertEquals(2,beneficiaryDao.getAll().size());
    }

    @Test
    public void getAllBeneficiariesForACharity() {
        String description = "To improve the life  opportunities of youth aged 10-24 through strategic empowerment for sustainable development";
        String trustDeed = "https://assets.publishing.service.gov.uk/government/uploads/system/uploads/attachment_data/file/586357/GD2.pdf";
        String image = "https://images.unsplash.com/photo-1593642634367-d91a135587b5?ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHw2fHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=300&q=60";

        Charity charity = new Charity(description, trustDeed,image,2,1);
        charityDao.add(charity);

        Beneficiary beneficiary = new Beneficiary("upendo","the best","profile",charity.getId());
        Beneficiary beneficiary2 = new Beneficiary("inua","the dada","profile",charity.getId());
        beneficiaryDao.add(beneficiary);
        beneficiaryDao.add(beneficiary2);

        assertEquals(2,beneficiaryDao.getAllBeneficiariesForACharity(charity.getId()).size());
    }

    @Test
    public void findById() {
        Beneficiary beneficiary = setupBeneficiary();
        String name = beneficiaryDao.findById(beneficiary.getId()).getName();
        assertEquals("Clare", name);
    }

    @Test
    public void deleteById() {
        Beneficiary beneficiary = setupBeneficiary();
        Beneficiary anotherBeneficiary = setupBeneficiary();
        assertEquals(2,beneficiaryDao.getAll().size());
        beneficiaryDao.deleteById(beneficiary.getId());
        assertEquals(1,beneficiaryDao.getAll().size());
    }

    @Test
    public void clearAll() {
        Beneficiary beneficiary = setupBeneficiary();
        Beneficiary anotherBeneficiary = setupBeneficiary();
        assertEquals(2,beneficiaryDao.getAll().size());
        beneficiaryDao.clearAll();
        assertEquals(0,beneficiaryDao.getAll().size());
    }

    public Beneficiary setupBeneficiary(){
        Beneficiary beneficiary = new Beneficiary("Clare", "Its been the best!","https://images.unsplash.com/photo-1634149136898-f7168613ac7f?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw4fHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=300&q=60",1);
        beneficiaryDao.add(beneficiary);
        return beneficiary;
    }
}