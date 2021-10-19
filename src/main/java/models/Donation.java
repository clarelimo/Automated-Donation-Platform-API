package models;

import dao.DB;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.sql.Time;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.List;

public class Donation {
    private int userid;
    private int charityid;
    private boolean anonymity;
    private String frequency;
    private Timestamp reminderdate;
    private String paymentmode;
    private int id;

    public Donation(int userid, int charityid, boolean anonymity, String frequency, Timestamp reminderdate, String paymentmode) {
        this.userid = userid;
        this.charityid = charityid;
        this.anonymity = anonymity;
        this.frequency = frequency;
        this.reminderdate = reminderdate;
        this.paymentmode = paymentmode;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCharityid() {
        return charityid;
    }

    public void setCharityid(int charityid) {
        this.charityid = charityid;
    }

    public boolean isAnonymity() {
        return anonymity;
    }

    public void setAnonymity(boolean anonymity) {
        this.anonymity = anonymity;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Timestamp getReminderdate() {
        return reminderdate;
    }

    public void setReminderdate(Timestamp reminderdate) {
        this.reminderdate = reminderdate;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void save(){
        try(Connection con = DB.sql2o.open()){//save approvals
            String saveAdmin= "INSERT INTO donors( userid, charityid, anonymity,frequency,reminderdate,paymentmode ) VALUES (:userid, :charityid,:anonymity,:frequency,:reminderdate,:paymentmode)";
            this.id= (int) con.createQuery(saveAdmin, true)
                    .addParameter("userid", this.userid)
                    .addParameter("charityid", this.charityid)
                    .addParameter("reminderdate",this.reminderdate)
                    .addParameter("anonymity",this.anonymity)
                    .addParameter("frequency", this.frequency)
                    .addParameter("paymentmode",this.paymentmode)
                    .executeUpdate()
                    .getKey();

        }catch (Sql2oException err){
            System.out.println("Error:::: "+ err);
        }
    }

    public static List<Donation> getDonors(){
        try(Connection con= DB.sql2o.open()){
            String getAll= "SELECT * FROM donors";
            return con.createQuery(getAll).executeAndFetch(Donation.class);
        }
    }

    public static List<Donation> getDonationsByUserId(int userid){
        try(Connection con= DB.sql2o.open()){
            String getAll = "SELECT * FROM donors WHERE userid=:userid";
            return con.createQuery(getAll).addParameter("userid", userid).executeAndFetch(Donation.class);
        }
    }

    public  static  List<Donation> getAllNonAnonymousDonorsForACharity(int charityId){
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM donors WHERE charityId = :charityId AND anonymity = :anonymity")
                    .addParameter("charityId", charityId)
                    .addParameter("anonymity", true)
                    .executeAndFetch(Donation.class);
        }
    }
    public  static List<Donation> getAllDonationsPerCharity(int charityid){
        try (Connection con = DB.sql2o.open()){

            return con.createQuery("SELECT * FROM donors WHERE charityid=:charityid")
                    .addParameter("charityid", charityid)
                    .executeAndFetch(Donation.class);
        }
    }

    public static Donation findDonorById(int id){
        try(Connection con= DB.sql2o.open()) {
            String getById = "select * from donors where id=:id";
            return con.createQuery(getById).addParameter("id",id).executeAndFetchFirst(Donation.class);
        }
    }

    public static List<Donation> findDonationByAnonymity(){
        try(Connection con = DB.sql2o.open()){
            String getByAnonymity= "select * from donors WHERE anonymity=:anonymity";
            return  con.createQuery(getByAnonymity).addParameter("anonymity",false).executeAndFetch(Donation.class);
        }
    }
    public static List<Donation> findDonationByNonAnonymity(){
        try(Connection con = DB.sql2o.open()){
            String getByAnonymity= "select * from donors WHERE anonymity=:anonymity";
            return  con.createQuery(getByAnonymity).addParameter("anonymity",true).executeAndFetch(Donation.class);
        }
    }

    public static void deleteById(int id){
        try(Connection con= DB.sql2o.open()){
            String delete = "DELETE FROM donors WHERE id=:id";
            con.createQuery(delete).addParameter("id",id).executeUpdate();
        }

    }

    public Charity getCharityById(int id){
        try(Connection con= DB.sql2o.open()){
            String getCharityById = "Select * from charities where id=:id";
            return con.createQuery(getCharityById).addParameter("id",id).executeAndFetchFirst(Charity.class);
        }
    }

    public static void clearAll(){
        try(Connection con= DB.sql2o.open()){
            String delete = "DELETE FROM donors *";
            con.createQuery(delete).executeUpdate();
        }
    }
    public static List<User> user_getAllCharities(){
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("select * from users where categories=:categories")
                    .addParameter("categories", "Charity")
                    .executeAndFetch(User.class);
        }
    }

}
