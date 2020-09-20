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

                String query1 = "INSERT INTO Doctor ( username, firstName, lastName, phoneNumber, dateOfBirth, specialty) VALUES (  'gpapado', 'Giannis', 'Papadopoulos', '6983456378' , '1984-02-08' ,'kardiologos')";
                String query2 = "INSERT INTO Doctor ( username, firstName, lastName, phoneNumber, dateOfBirth, specialty) VALUES (  'egiannako', 'Eleni', 'Giannakopoulou', '6983784978' , '1990-01-07' ,'pathologos')";
                String query3 = "INSERT INTO Doctor ( username, firstName, lastName, phoneNumber, dateOfBirth, specialty) VALUES (  'mpmeka', 'Maria', 'Mpeka', '6947556378' , '1987-10-01' ,'orthopedikos')";
                String query4 = "INSERT INTO Doctor ( username, firstName, lastName, phoneNumber, dateOfBirth, specialty) VALUES (  'pkosto', 'Petros', 'Kostopoulos', '6983789865' , '1991-10-21' ,'pathologos')";
                String query5 = "INSERT INTO Doctor ( username, firstName, lastName, phoneNumber, dateOfBirth, specialty) VALUES (  'kagiab', 'Katerina', 'Giabou', '6940127835' , '1984-07-05' ,'ofthalmiatros')";
                String query6 = "INSERT INTO Doctor ( username, firstName, lastName, phoneNumber, dateOfBirth, specialty) VALUES (  'opetri', 'Orestis', 'Petridis', '6904791211' , '1967-03-03' ,'pathologos')";

                String query7 = "INSERT INTO Patient ( username, firstName, lastName, address, city, zipCode, phoneNumber, dateOfBirth, doctor_id)" +
                        " VALUES (  'ekelid', 'Eirini', 'Kelidou', 'Lassani 6', 'Thessaloniki', '57013', '6983323232', '1995-05-05', 1)";
                String query8 = "INSERT INTO Patient ( username, firstName, lastName, address, city, zipCode, phoneNumber, dateOfBirth, doctor_id)" +
                        " VALUES (  'vparask', 'Vasilis', 'Paraskevas', 'Leoforos oxi 6', 'Thessaloniki', '57078', '6986426625', '1995-01-10',1)";
                String query9 = "INSERT INTO Patient ( username, firstName, lastName, address, city, zipCode, phoneNumber, dateOfBirth, doctor_id)" +
                        " VALUES (  'gstath', 'Giorgos', 'Stathis', 'Navarinou 35', 'Thessaloniki', '55014', '6981047890', '1995-05-05', 4)";
                String query10 = "INSERT INTO Patient ( username, firstName, lastName, address, city, zipCode, phoneNumber, dateOfBirth, doctor_id)" +
                        " VALUES (  'xsiop', 'Xristos', 'Siopis', 'Lassani 6', 'Thessaloniki', '78452', '6945781240', '1995-01-12', 4)";
                String query11 = "INSERT INTO Patient ( username, firstName, lastName, address, city, zipCode, phoneNumber, dateOfBirth, doctor_id)" +
                        " VALUES (  'empelo', 'Elenei', 'Mpelo', 'Koufitsa 12', 'Kozani', '50100', '6940123459', '1993-10-07', 6)";
                String query12 = "INSERT INTO Patient ( username, firstName, lastName, address, city, zipCode, phoneNumber, dateOfBirth, doctor_id)" +
                        " VALUES (  'mntino', 'Marios', 'Ntinopoulos', 'Aetoraxis 7', 'Athina', '16422', '6904783254', '1970-02-04', 1)";
                String query13 = "INSERT INTO Patient ( username, firstName, lastName, address, city, zipCode, phoneNumber, dateOfBirth, doctor_id)" +
                        " VALUES (  'kmarkou', 'Kostantinos', 'Markou', 'Argyriadi 2', 'Patra', '78412', '6912487512', '1967-01-01', 4)";


                String query14 = "INSERT INTO Carb ( date, gram, patient_id) VALUES ( '2020-09-14', 892.7 , 1 )";
                String query15 = "INSERT INTO Carb ( date, gram, patient_id) VALUES ( '2020-09-15', 1000 , 1 )";
                String query16 = "INSERT INTO Carb ( date, gram, patient_id) VALUES ( '2020-09-04', 700.3 , 2 )";
                String query17 = "INSERT INTO Carb ( date, gram, patient_id) VALUES ( '2020-09-14', 870.5 , 3 )";
                String query18 = "INSERT INTO Carb ( date, gram, patient_id) VALUES ( '2020-09-10', 892.7 , 3 )";
                String query19 = "INSERT INTO Carb ( date, gram, patient_id) VALUES ( '2020-08-14', 892.7 , 7 )";
                String query20 = "INSERT INTO Carb ( date, gram, patient_id) VALUES ( '2020-08-12', 892.7 , 5 )";
                String query21 = "INSERT INTO Carb ( date, gram, patient_id) VALUES ( '2020-09-14', 892.7 , 4 )";
                String query22 = "INSERT INTO Carb ( date, gram, patient_id) VALUES ( '2020-09-16', 892.7 , 1 )";


                String query23 = "INSERT INTO Glucose ( dateTime, measurement, patient_id) VALUES ( '2020-09-16 23:59:59', 80.4 , 2)";
                String query24 = "INSERT INTO Glucose ( dateTime, measurement, patient_id) VALUES ( '2020-08-16 10:59:59', 140 , 4)";
                String query25 = "INSERT INTO Glucose ( dateTime, measurement, patient_id) VALUES ( '2020-09-14 14:14:59', 90.5 , 1 )";
                String query26 = "INSERT INTO Glucose ( dateTime, measurement, patient_id) VALUES ( '2020-09-14 20:00:59', 88.1 , 1 )";
                String query27 = "INSERT INTO Glucose ( dateTime, measurement, patient_id) VALUES ( '2020-08-10 21:49:59', 87.4 , 6 )";
                String query28 = "INSERT INTO Glucose ( dateTime, measurement, patient_id) VALUES ( '2020-09-09 15:39:59', 120.3 , 7 )";
                String query29 = "INSERT INTO Glucose ( dateTime, measurement, patient_id) VALUES ( '2020-09-10 18:19:59', 89.5, 5 )";
                String query30 = "INSERT INTO Glucose ( dateTime, measurement, patient_id) VALUES ( '2020-09-13 22:05:59', 84 , 2 )";
                String query31 = "INSERT INTO Glucose ( dateTime, measurement, patient_id) VALUES ( '2020-09-12 19:00:59', 87, 3 )";

                String query32 = "INSERT INTO UserTable ( username, password, role) VALUES ('gpapado','gpapado','doctor')";

                stmt.executeUpdate(query1);
                stmt.executeUpdate(query2);
                stmt.executeUpdate(query3);
                stmt.executeUpdate(query4);
                stmt.executeUpdate(query5);
                stmt.executeUpdate(query6);
                stmt.executeUpdate(query7);
                stmt.executeUpdate(query8);
                stmt.executeUpdate(query9);
                stmt.executeUpdate(query10);
                stmt.executeUpdate(query11);
                stmt.executeUpdate(query12);
                stmt.executeUpdate(query13);
                stmt.executeUpdate(query14);
                stmt.executeUpdate(query15);
                stmt.executeUpdate(query16);
                stmt.executeUpdate(query17);
                stmt.executeUpdate(query18);
                stmt.executeUpdate(query19);
                stmt.executeUpdate(query20);
                stmt.executeUpdate(query21);
                stmt.executeUpdate(query22);
                stmt.executeUpdate(query23);
                stmt.executeUpdate(query24);
                stmt.executeUpdate(query25);
                stmt.executeUpdate(query26);
                stmt.executeUpdate(query27);
                stmt.executeUpdate(query28);
                stmt.executeUpdate(query29);
                stmt.executeUpdate(query30);
                stmt.executeUpdate(query31);

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

