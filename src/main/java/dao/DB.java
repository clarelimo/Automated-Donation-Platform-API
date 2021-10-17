package dao;

import org.sql2o.Sql2o;

public class DB {
//        public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-23-22-243-103.compute-1.amazonaws.com:5432/d2qjm9ce2ql1m0", "qgdkpnfedstuzr", "3e5dd129ad26ce21b4eddc53e82ff0e549807cf26fb6a0a165cea638b8e01b0c");
        public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/donation_platform_test","softwaredev","1234");
}
