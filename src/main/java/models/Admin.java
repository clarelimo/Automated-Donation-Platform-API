package models;

import dao.DB;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class Admin {
    private int userid;
    private int charityid;
    private boolean approval;
    private int id;


    public Admin(int userid, int charityid) {
        this.userid = userid;
        this.charityid = charityid;
        this.approval = false;
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
    public boolean isApproval() {
        return approval;
    }
    public void setApproval(boolean approval) {
        this.approval = approval;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void save(){
        try(Connection con = DB.sql2o.open()){//save approvals
            String saveAdmin= "INSERT INTO admin( userid, approval ) VALUES (:userid, :approval)";
            this.id= (int) con.createQuery(saveAdmin, true)
                    .addParameter("userid", this.userid)
                    .addParameter("approval", this.approval)
                    .executeUpdate()
                    .getKey();

        }catch (Sql2oException err){
            System.out.println("Error:::: "+ err);
        }
    }
    public static List<Admin> getAllCharities(){
        try(Connection con= DB.sql2o.open()){
            String getAll= "SELECT * FROM admin";
            return con.createQuery(getAll).executeAndFetch(Admin.class);
        }
    }
    public static Admin findCharityById(int id){
        try(Connection con= DB.sql2o.open()) {
            String getById = "select * from admin where id=:id";
            return con.createQuery(getById).addParameter("id",id).executeAndFetchFirst(Admin.class);
        }
    }
    public  void approveCharity(){
        try(Connection con= DB.sql2o.open()){
            String approve ="UPDATE admin SET approval=:approval WHERE id=:id";
            con.createQuery(approve).addParameter("approval",true).addParameter("id", this.charityid).executeUpdate();
            this.approval=true;
        }catch (Sql2oException err){
            System.out.println("Error:::: "+ err);
        }
    }
    public static List<Admin> getAllApprove(){
        try(Connection con= DB.sql2o.open()){
            String approved = "SELECT * FROM admin WHERE approval=:approval";
            return con.createQuery(approved).addParameter("approval", true).executeAndFetch(Admin.class);
            }
    }
    public static void deleteById(int id){
        try(Connection con= DB.sql2o.open()){
            String delete = "DELETE FROM admin WHERE id=:id";
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
            String delete = "DELETE FROM admin *";
            con.createQuery(delete).executeUpdate();
        }
    }


}
