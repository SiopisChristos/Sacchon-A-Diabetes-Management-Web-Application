#
  <h2 align="center">Sacchon app</h2>

### About the project 
The project aim is to develop the Sacchon appand deliver it ready to be released. The  requirements are  given by the Sacchon  Consulting Enterprise(a fictional company). 
The information system must contain:
 * A User  Interface  to  submit  and  view  medical  data  by  the  patients  and doctors.
 * An Administration   Console with user   management, monitoring, and reporting capabilities of the system usage.</p>

For the above features to be considered complete, the following functionality must be provided:
* From  the  backend view, a corresponding  call with a  valid  JSON  object containing  all  needed  information must  be  available,  according  to  the specification of each case.
* From the frontend view, a corresponding page consuming the service from the backend and presenting the suitable information must be available.

### Overview
<p style="text-align: justify;">The patient uses the system to store their data at any time. 
After a month of data recording  has  elapsed,  a  doctor can  review  this  data  and  provide  advice  to  the patient for the next month.This process is repeated until the patient or the doctor unregisters from the system.The Chief Doctor is an officer who can view reports which monitor the activity of patients and doctors in the system.
<br>


### Functionallity of user roles:
### **MediDataRepo** 
#### The patient can:
  - manage their account 
     - sign up for an account 
     - remove the account
 - store their data
    - blood glucose level (date, time, measured in mg/dL)
    - carb intake (measured in grams)
  - view 
     - their average daily blood glucose level over a user specified period 
     - their average carb intake over a user  specified period 
     - the current and past consultations from doctors 
  - update 
     - modify incorrect submitted data
     - delete incorrect submitted data<br>

### **Doctor advice**
#### The doctor can:
- manage their account
   - sign up for an account
   - remove the account 
- view patient record
   - browse the data for a single patient(patients data and consultations)
- search
   - find patients that have not had a consultation in the last month 
- consult
   - provide advice to a patient for the upcoming month (name of medication and dosage)
- update
   - modify a consultation to a patient


### **Reporter**
This section is available only to the Chief Doctors in the application. The reports available are:
1. The information submissions(personal monitor data) of a patient over a time range 
2. The information submissions (consultations) of a doctor over a time range
3. The list of the patients who are waiting for a consultation and the time elapsed since they needed to have one
4. The list of the patients with no activity over a timerange
5. The list of the doctors with no activity over a time range<br><br>


### The user story of the application
<p style="text-align: justify;">Initially a patient has not doctor consulting them. As soon as a month of data recording has passed, the patient is ready to be consulted by a doctor.</p>
<p style="text-align: justify;">Doctors search for patients for which advice is pending. Each doctor can see all the patients that they consult and the patients that are new in the system. Doctors cannot see patients that are consulted by other doctors. All doctors can see the new patients.</p>
<p style="text-align: justify;">When a doctor selects to consult a new patient, the patient becomes managed by this doctor exclusively.</p>
<p style="text-align: justify;">When a doctor leaves the system, their patients become available again for other doctors to consult. Past consultations of these patients continue to refer to the doctor who is leaving the system. If a doctor modifies a consultation, the patient must see a warning as soon as they enter the system, so that they know that some important information must be reviewed.</p>
<p style="text-align: justify;">A month is the period from the day of consultation to the same date next month (say, from the 15th of June to the 14th of July).It is not a calendar month. The next month for a patient starts as soon as a doctor has provided a consultation. Until the new consultation arrives, the previous one is considered valid.<p>
<br>

### The development process for the application
1. Devide the main project into subprojects (**BackEnd - FrontEnd**)
2. Model the entities
3. Find the endpoints of the application. We create all necessary breakpoint for the connection with app.
All calls can sent and get data in **JSON** formatting.<br> So in upgraded version of this app, can used by IoT devices. 
4. Implement the functionality of the project in backend
5. Create the database of project and connect it with backend of the application
6. Test the **HTTP** calls with **POSTMAN**
7. Implement the functionality of endpoints in FrontEnd using Angular Framework and CSS Bootstrap Framework<br>


## Installation guide

#### For the backend
* JetBrains IntelliJ IDEA
* Microsoft SQL Server 2019
* Java 8 (or greater)
* Lombook depedency
* Maven (To manage depedencies and the software development lifecycle)
* Hibernate
* Restlet Framework<br><br>


#### For the FrontEnd
* Angular Framework 
* HTML CSS
* Bootstrap framework
* Visual Studio Code
* Open source images
* Google charts

#### For testing REST calls
* Postman

#### Version control 
* Git
* Github repository<br><br>

### Guide to run the project 

1. Copy this link [Sacchon app](https://github.com/codehub-learn/pfizer-se-team1.git)

2. Clone the project in to your system <br>
```$ git clone https://github.com/codehub-learn/pfizer-se-team1.git ```<br>
3. Create a new database named Sacchon 
4. Open the backend folder with (Intellij)
5. Change the update database to create 
for the creation of new database<br>
```  <property name="hibernate.hbm2ddl.auto" value="update" />```</br>
6. Press Shift + F10 to RUN the project
7. Change the previous line into update 
8. Open the FrontEnd folder with Visual Studio Code  
9. Open a new terminal and redirect to folder named FrontEnd
10. Press the command ```npm install``` to install npm packages
11. Press the command ```ng serve```
12. Now open a browser tab into url: localhost:4200<br>
 
 **Now you can use the application**
* You have not got entries in your database
* You can register a new patient or doctor and submit and then view their data.<br>

This project impemented during the bootcamp for software engineers, organized by **Pfizer** and **Codehub**.

This application created by:
Christos Siopis
Eirini Kelidou
Georgios Stathis
Vasilis Paraskevas


