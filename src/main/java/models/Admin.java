package models;

import dao.DB;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Objects;

public class Admin {

    private int charityid;
    private boolean approval;
    private int id;


    public Admin( int charityid) {

        this.charityid = charityid;
        this.approval = false;
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
            String saveAdmin= "INSERT INTO admin(  charityid, approval ) VALUES (:charityid,:approval)";
            this.id= (int) con.createQuery(saveAdmin, true)
                    .addParameter("charityid", this.charityid)
                    .addParameter("approval", this.approval)
                    .executeUpdate()
                    .getKey();

        }catch (Sql2oException err){
            System.out.println("Error:::: "+ err);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return charityid == admin.charityid && approval == admin.approval && id == admin.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(charityid, approval, id);
    }

    public static List<Admin> admin_getAllCharities(){
        try(Connection con= DB.sql2o.open()){
            String getAll= "SELECT * FROM admin";
            return con.createQuery(getAll).executeAndFetch(Admin.class);
        }
    }
    public static Admin admin_findCharityById(int id){
        try(Connection con= DB.sql2o.open()) {
            String getById = "select * from admin where charityid=:id";
            return con.createQuery(getById).addParameter("id",id).executeAndFetchFirst(Admin.class);
        }
    }
    public  void approveCharity(){
        try(Connection con= DB.sql2o.open()){
            String approve ="UPDATE admin SET approval=:approval WHERE id=:id";
            con.createQuery(approve).addParameter("approval",true).addParameter("id", this.id).executeUpdate();
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
    public static void deleteAdminById(int id){
        try(Connection con= DB.sql2o.open()){
            String delete = "DELETE FROM admin * WHERE id=:id";
            con.createQuery(delete).addParameter("id",id).executeUpdate();
        }

    }
    public static Charity getCharityById(int id){
        try(Connection con= DB.sql2o.open()){
            String getCharityById = "Select * from charities where id=:id";
            return con.createQuery(getCharityById).addParameter("id",id).executeAndFetchFirst(Charity.class);
        }
    }
    public static void delete_user_charity(int id){
          try (Connection con= DB.sql2o.open()){
              String deleteCharity= "DELETE FROM users * WHERE id=:id";
              con.createQuery(deleteCharity).addParameter("id",id).executeUpdate();
          }
    }
    public static void admin_delete_charity(int id){
        try(Connection con = DB.sql2o.open()){
            String deleteCharity= "Delete from charities * where id = :id";
            con.createQuery(deleteCharity).addParameter("id",id).executeUpdate();
        }
    }


    public static void clearAll(){
        try(Connection con= DB.sql2o.open()){
            String delete = "DELETE FROM admin *";
            con.createQuery(delete).executeUpdate();
        }
    }


}
