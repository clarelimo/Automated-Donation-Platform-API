package dao;

import models.Beneficiary;
import models.Charity;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oCharityDao implements CharityDao{
    private final Sql2o sql2o;

    public Sql2oCharityDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Charity charity) {
        String sql = "INSERT INTO charities (userid,donorid,description,trustdeed,image) VALUES(:userId,:donorId, :description,:trustDeed,:image)";
        try (Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql,true)
                    .bind(charity)
                    .executeUpdate()
                    .getKey();
            charity.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Charity> getAll() {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("SELECT * FROM charities")
                    .executeAndFetch(Charity.class);
        }
    }

    @Override
    public List<Charity> getAllCharitiesForDonor(int donorId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM charities WHERE donorId = :donorId")
                    .addParameter("donorId", donorId)
                    .executeAndFetch(Charity.class);
        }
    }

    @Override
    public Charity findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM charities WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Charity.class);
        }
    }

    @Override
    public void update(int id,String description, String trustDeed, String image) {
        String sql = "UPDATE charities SET (description,trustdeed,image) = (:description,:trustDeed,:image)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("description", description)
                    .addParameter("trustdeed", trustDeed)
                    .addParameter("image", image)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from charities WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from charities";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
