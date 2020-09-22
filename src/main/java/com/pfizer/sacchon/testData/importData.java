package com.pfizer.sacchon.testData;

import com.pfizer.sacchon.security.dao.DatabaseCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class importData {

    public static void main(String[] args) {


        try (
                Connection conn = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USER, DatabaseCredentials.PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                conn.setAutoCommit(false);

                String sql;
                sql = "Use " + DatabaseCredentials.DB_SCHEMA;
                stmt.executeUpdate(sql);

                String query1 = "INSERT INTO Doctor ( creationDate, username, firstName, lastName, phoneNumber, dateOfBirth, specialty) VALUES ( '2020-06-09', 'gpapado', 'Giannis', 'Papadopoulos', '6983456378' , '1984-02-08' ,'kardiologos')," +
                 "('2020-09-09',  'egiannako', 'Eleni', 'Giannakopoulou', '6983784978' , '1990-01-07' ,'pathologos')," +
                  "( '2020-08-06', 'mpmeka', 'Maria', 'Mpeka', '6947556378' , '1987-10-01' ,'orthopedikos')," +
                 "('2020-08-07',  'pkosto', 'Petros', 'Kostopoulos', '6983789865' , '1991-10-21' ,'pathologos')," +
                  "( '2020-09-08', 'kagiab', 'Katerina', 'Giabou', '6940127835' , '1984-07-05' ,'ofthalmiatros')," +
                  "( '2020-09-09', 'opetri', 'Orestis', 'Petridis', '6904791211' , '1967-03-03' ,'pathologos')," +
                        "('2020-09-22', 'vanikol', 'Vaggelis', 'Nikolaou', '6947820142','1966-05-01', 'pathologos')";

                String query2 = "INSERT INTO Patient ( username, firstName, lastName, address, city, zipCode, phoneNumber, dateOfBirth, doctor_id, creationDate) " +
                        "VALUES (  'ekelid', 'Eirini', 'Kelidou', 'Lassani 6', 'Thessaloniki', '57013', '6983323232', '1995-05-05', 1, '2020-08-10')," +
                 "(  'vparask', 'Vasilis', 'Paraskevas', 'Leoforos oxi 6', 'Thessaloniki', '57078', '6986426625', '1995-01-10',1, '2020-07-10'),"+
                 "(  'gstath', 'Giorgos', 'Stathis', 'Navarinou 35', 'Thessaloniki', '55014', '6981047890', '1995-05-05', 4, '2020-08-07'),"+
                 "(  'xsiop', 'Xristos', 'Siopis', 'Lassani 6', 'Thessaloniki', '78452', '6945781240', '1995-01-12', 4, '2020-09-10'),"+
                 "(  'empelo', 'Eleni', 'Mpelo', 'Koufitsa 12', 'Kozani', '50100', '6940123459', '1993-10-07', 6, '2020-06-10'),"+
                "(  'mntino', 'Marios', 'Ntinopoulos', 'Aetoraxis 7', 'Athina', '16422', '6904783254', '1970-02-04', 1, '2020-07-22'),"+
                "(  'kmarkou', 'Kostantinos', 'Markou', 'Argyriadi 2', 'Patra', '78412', '6912487512', '1967-01-01', 4, '2020-09-01'),"+
                        "('thepavl', 'Theodoros', 'Pavlou', 'Thiseos 3', 'Thessaloniki', '57013', '6974512032', '1980-02-10', null, '2020-09-22')";


                String query3 = "INSERT INTO Carb ( date, gram, patient_id) VALUES ( '2020-09-14', 892.7 , 1 ),"+
                 "( '2020-09-15', 1000 , 1 ),"+
                 "( '2020-09-04', 700.3 , 2 ),"+
                "( '2020-09-14', 870.5 , 3 ),"+
                "( '2020-09-10', 892.7 , 3 ),"+
                 "( '2020-08-14', 892.7 , 7 ),"+
                 "( '2020-08-12', 892.7 , 5 ),"+
                 "( '2020-09-14', 892.7 , 4 ),"+
                 "( '2020-09-16', 892.7 , 1 )";


                String query4 = "INSERT INTO Glucose ( dateTime, measurement, patient_id) VALUES ( '2020-09-16 23:59:59', 80.4 , 2),"+
                 "( '2020-08-16 10:59:59', 140 , 4),"+
                "( '2020-09-14 14:14:59', 90.5 , 1 ),"+
                 "( '2020-09-14 20:00:59', 88.1 , 1 ),"+
                 "( '2020-08-10 21:49:59', 87.4 , 6 ),"+
                 "( '2020-09-09 15:39:59', 120.3 , 7 ),"+
                 "( '2020-09-10 18:19:59', 89.5, 5 ),"+
                 "( '2020-09-13 22:05:59', 84 , 2 ),"+
                 "( '2020-09-12 19:00:59', 87, 3 )";

                String query5 = "INSERT INTO UserTable ( username, password, role) VALUES ('gpapado','gpapado','doctor')," +
                        " ('admin','admin','admin')";

                stmt.executeUpdate(query1);
                stmt.executeUpdate(query2);
                stmt.executeUpdate(query3);
                stmt.executeUpdate(query4);
                stmt.executeUpdate(query5);

                conn.commit();
                conn.close();

            } catch (SQLException e) {
                if (conn != null) {
                    // we need to catch the SQLException
                    // that happens during query executions
                    // in order to command the DB to rollback
                    System.out.println(e);
                    System.out.println("Roll Back took place");
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            // when is this going to be called?
            e.printStackTrace();
        }

    }
}

