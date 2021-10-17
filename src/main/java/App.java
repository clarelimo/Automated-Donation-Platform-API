import com.google.gson.Gson;
import exceptions.ApiException;
import models.Admin;
import models.Donation;
import spark.Spark;

import static spark.Spark.after;
import static spark.Spark.post;

import  static spark.Spark.*;
import static spark.route.HttpMethod.patch;
import static spark.route.HttpMethod.post;

public class App {
    public static void main(String[] args){
        Gson gson= new Gson();
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
