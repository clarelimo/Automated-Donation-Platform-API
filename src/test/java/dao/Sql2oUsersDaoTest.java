package dao;

import models.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;

import static org.junit.Assert.*;

public class Sql2oUsersDaoTest {
    private static Sql2oUsersDao usersDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        usersDao = new Sql2oUsersDao(DatabaseRule.sql2o);
        conn = DatabaseRule.sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        usersDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }


    @Test
    public void getAll() {
        User user = setupUser();
        User anotherUser = setupUser();
        assertEquals(2,usersDao.getAll().size());
    }

    @Test
    public void findById() {
        User user = setupUser();
        User anotherUser = setupUser();
        assertEquals("Clare",usersDao.findById(user.getId()).getName());
    }

    @Test
    public void findByEmail() {
        User user = setupUser();
        User anotherUser = setupUser();
        assertEquals("Clare",usersDao.findByEmail(user.getEmail()).getName());
    }


    @Test
    public void getUserByCategory() {
        User user = setupUser();
        User anotherUser = setupUser();
        assertEquals("charity",usersDao.getUser(user.getEmail(),user.getPassword(),user.getCategories()).getCategories());
    }

    @Test
    public void deleteById() {
        User user = setupUser();
        User anotherUser = setupUser();
        assertEquals(2,usersDao.getAll().size());
        usersDao.deleteById(user.getId());
        assertEquals(1,usersDao.getAll().size());
    }

    @Test
    public void clearAll() {
        User user = setupUser();
        User anotherUser = setupUser();
        assertEquals(2,usersDao.getAll().size());
        usersDao.clearAll();
        assertEquals(0,usersDao.getAll().size());
    }

    public User setupUser(){
        User user = new User("Clare", "test@gmail.com","123","charity","0722000000","https://images.unsplash.com/photo-1634141405777-1db04b151366?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxNHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=300&q=60");
        usersDao.add(user);
        return user;
    }
}