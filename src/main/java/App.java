import com.google.gson.Gson;

import dao.DB;
import dao.Sql2oBeneficiaryDao;
import dao.Sql2oCharityDao;
import dao.Sql2oUsersDao;

import exceptions.ApiException;
import models.*;
import org.sql2o.Sql2o;
import static spark.Spark.*;
import org.sql2o.Connection;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args){
        Sql2oCharityDao charityDao;
        Sql2oUsersDao usersDao;
        Sql2oBeneficiaryDao beneficiaryDao;
        Gson gson = new Gson();
        Connection conn;
        port(getHerokuAssignedPort());

        charityDao = new Sql2oCharityDao(DB.sql2o);
        usersDao = new Sql2oUsersDao(DB.sql2o);
        beneficiaryDao = new Sql2oBeneficiaryDao(DB.sql2o);
        conn = DB.sql2o.open();

        get("/", "application/json", (req, res) -> {
            System.out.println(beneficiaryDao.getAll());

            if(beneficiaryDao.getAll().size() > 0){
                return gson.toJson(beneficiaryDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no beneficiaries are currently listed in the database.\"}";
            }
        });

        //create user
        post("api/users/register", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            usersDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        //get users
        get("api/users", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            return gson.toJson(usersDao.getAll());
        });

        //get user by id
        get("api/users/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int userId = Integer.parseInt(req.params("id"));
            User userToFind = usersDao.findById(userId);
            if (userToFind == null){
                throw new ApiException(404, String.format("No user with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(userToFind);
        });

        //get user by email
        get("api/users/login/:email", "application/json", (req, res) -> { //accept a request in format JSON from an app
            String email = req.params("email");
            User userToFind = usersDao.findByEmail(email);
            if (userToFind == null){
                throw new ApiException(404, String.format("No user with the email: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(userToFind);
        });

        //get user by category


        //create beneficiary
        post("api/beneficiary/:charityId/new", "application/json", (req, res) -> {
            int charityId = Integer.parseInt(req.params("charityId"));

            Beneficiary beneficiary = gson.fromJson(req.body(), Beneficiary.class);
            beneficiary.setCharityId(charityId);
            beneficiaryDao.add(beneficiary);
            res.status(201);
            return gson.toJson(beneficiary);
        });

        //get all beneficiaries
        get("api/beneficiaries", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            return gson.toJson(beneficiaryDao.getAll());
        });

        //get all beneficiaries for a charity
        get("api/beneficiaries/:charityId", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int charityId = Integer.parseInt(req.params("charityId"));
            Charity charityToFind = charityDao.findById(charityId);
            if (charityToFind == null){
                throw new ApiException(404, String.format("No Charity with the id: \"%s\" exists", req.params("charityId")));
            }
            else if (beneficiaryDao.getAllBeneficiariesForACharity(charityId).size()==0){
                return "{\"message\":\"I'm sorry, but no beneficiaries are listed for this charity.\"}";
            }
            else {
                return gson.toJson(beneficiaryDao.getAllBeneficiariesForACharity(charityId));
            }
        });

        //create charity
        post("api/charity/:donorId/:userId/new", "application/json", (req, res) -> {
            Charity charity = gson.fromJson(req.body(), Charity.class);
            int donorId = Integer.parseInt(req.params("donorId"));
            int userId = Integer.parseInt(req.params("userId"));
            charity.setDonorId(donorId);
            charity.setUserId(userId);
            charityDao.add(charity);
            res.status(201);
            return gson.toJson(charity);
        });

        //get all charities
        get("api/charities", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            return gson.toJson(charityDao.getAll());
        });

        //get all non anonymous donors for a charity
        get("api/donors/:charityId", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int charityId = Integer.parseInt(req.params("charityId"));
            Charity charityToFind = charityDao.findById(charityId);

            if (charityToFind == null){
                throw new ApiException(404, String.format("No Charity with the id: \"%s\" exists", req.params("charityId")));
            }
            else if (Donation.getAllNonAnonymousDonorsForACharity(charityId).size()==0){
                return "{\"message\":\"I'm sorry, but no non anonymous donors are listed for this charity.\"}";
            }
            else {
                return gson.toJson(Donation.getAllNonAnonymousDonorsForACharity(charityId));
            }
        });


        //update charity
        put("api/charity/:id", "application/json", (req, res) -> {
            Charity charity = gson.fromJson(req.body(), Charity.class);
            int charityId = Integer.parseInt(req.params("id"));
            charityDao.update(charityId,charity.getDescription(),charity.getTrustDeed(),charity.getImage());

            res.status(201);
            return gson.toJson(charity);
        });

        after((req, res) ->{
            res.type("application/json");
        });


        //start donation...........
        post("/api/donation/new", "application/json", (request, response)->{//create donations
            Donation newDonation = gson.fromJson(request.body(), Donation.class);
            newDonation.save();
            response.status(201);
            return gson.toJson(newDonation);
        });
        get("/api/donation/all","application/json",(request, response)->{//get all donations
            if(Donation.getDonors().size()>0) {
                return gson.toJson(Donation.getDonors());
            }else
                return "{\"message\":\"I'm sorry, but no donations are currently listed in the database.\"}";
        });
        get("/api/donations/:id", "application/json", (request, response) -> {//get donations by id
            int userId= Integer.parseInt((request.params("id")));
            if (Donation.findDonorById(userId) == null){
                throw new ApiException(404, String.format("No donation with the id: \"%s\" exists", request.params("id")));
            }
            return gson.toJson(Donation.findDonorById(userId));
        });
       get("/api/donations/donors/:donorsId", "application/json",(request,response)->{//get donations by donorsId
           int userId= Integer.parseInt((request.params("donorsId")));
           if (Donation.getDonationsByUserId(userId) == null){
               throw new ApiException(404, String.format("No donor with the id: \"%s\" exists", request.params("id")));
           }
           return gson.toJson(Donation.getDonationsByUserId(userId));
       });
       get("/api/donations/nonAnonymous/:charityId","application/json", ((request, response) -> {//get anonymous donation info
           int charityId= Integer.parseInt((request.params("charityId")));
           return gson.toJson(Donation.getAllNonAnonymousDonorsForACharity(charityId));
       }));
       delete("/api/donations/deleteby/:id","application/json", ((request, response) -> {//delete donations by id
           int id= Integer.parseInt((request.params("id")));
           Donation.deleteById(id);
           return gson.toJson(Donation.getDonors());
       }));
       delete("/api/donations/clearall", "application/json", ((request, response) -> {//delete all donations
           Donation.clearAll();
           return gson.toJson(Donation.getDonors());
       }));
       //end of donation............
        //start of admin......
        post("/api/admin/newrequest/", "application/json",((request, response) -> { //new requests
            Admin admin = gson.fromJson(request.body(), Admin.class);
            admin.save();
            response.status(201);
            return gson.toJson(admin);
        }));
        get("/api/admin/getallcharites/" ,"application/json",(request,response)->{//get all charities
            if(Admin.getAllCharities().size()>0){
            return gson.toJson(Admin.getAllCharities());}
            return "{\"message\":\"I'm sorry, but no charities are currently listed in the database.\"}";
        });
        get("api/admin/findbycharityid/:id", "application/json",(request,response)->{//get charity by id
            int id= Integer.parseInt((request.params("id")));

            return gson.toJson(Admin.findCharityById(id));

        });
        put("/api/admin/approve/:charityId", "application/json",((request, response) -> {//update approval
            int id= Integer.parseInt((request.params("charityId")));
            Admin.findCharityById(id).approveCharity();
            return gson.toJson(Admin.getAllApprove());
        }));
        get("/api/admin/getallapprove/", "application/json",((request, response) -> {
            return gson.toJson(Admin.getAllApprove());


        }));
        delete("/api/deletebyid/:id","application/json",((request, response) -> {
            int id= Integer.parseInt((request.params("id")));
            Admin.deleteById(id);
            return gson.toJson(Admin.getAllCharities());
        }));

        delete("/api/admin/deleteAll/", "application/json",((request, response) -> {
            Admin.clearAll();
            return gson.toJson(Admin.getAllCharities());
        }));



        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });
    }

}
