package dao;

import models.Beneficiary;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oBeneficiaryDao implements BeneficiaryDao {
    private final Sql2o sql2o;

    public Sql2oBeneficiaryDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Beneficiary beneficiary) {
        String sql = "INSERT INTO beneficiaries (name,testimony,image) VALUES(:name,:testimony,:image)";
        try (Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql,true)
                    .bind(beneficiary)
                    .executeUpdate()
                    .getKey();
            beneficiary.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Beneficiary> getAll() {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("SELECT * FROM beneficiaries")
                    .executeAndFetch(Beneficiary.class);
        }
    }

    @Override
    public Beneficiary findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM beneficiaries WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Beneficiary.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from beneficiaries WHERE id = :id";
        try  (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from beneficiaries";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
