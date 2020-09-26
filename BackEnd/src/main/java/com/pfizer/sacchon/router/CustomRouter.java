package com.pfizer.sacchon.router;
import com.pfizer.sacchon.resource.LoginImpl;
import com.pfizer.sacchon.resource.chiefDoctors.*;
import com.pfizer.sacchon.resource.patients.*;
import com.pfizer.sacchon.resource.PingServerResource;

import com.pfizer.sacchon.resource.doctors.DoctorRecordsImpl;
import com.pfizer.sacchon.resource.doctors.DoctorResourceImpl;
import com.pfizer.sacchon.resource.doctors.DoctorUtilitiesImpl;
import org.restlet.Application;
import org.restlet.routing.Router;

public class CustomRouter {

    private Application application;

    public CustomRouter(Application application) {
        this.application = application;

    }

    public Router createApiRouter() {

        Router router = new Router(application.getContext());

        router.attach("/patient/update/{id}", PatientResourceImpl.class); //{Put -> Update Patient}
        router.attach("/patient", PatientResourceImpl.class); //{Post -> Add Patient, "Delete" Patient}

        router.attach("/patient/carb/{id}", CarbResourceImpl.class);
        router.attach("/patient/carb", CarbListResourceImpl.class);
        router.attach("/patient/carb/", CarbListResourceImpl.class);

        router.attach("/patient/glucose/{id}", GlucoseResourceImpl.class);
        router.attach("/patient/glucose", GlucoseListResourceImpl.class);
        router.attach("/patient/glucose/", GlucoseListResourceImpl.class);

        router.attach("/patient/note/", NoteListResourceImpl.class);

        //Doctor Section
        router.attach("/doctor", DoctorUtilitiesImpl.class); //{post} create doctor
        router.attach("/doctor/see/myPatients", DoctorUtilitiesImpl.class); //{Get} myPatients

        router.attach("/doctor/see/freePatients", DoctorResourceImpl.class); //{Get} FreePatients
        router.attach("/doctor/account", DoctorResourceImpl.class); //{Delete} doctor's account
        router.attach("/doctor/account/{id}", DoctorResourceImpl.class); //{Put} [A doctor chooses a free Patient]
        router.attach("/postNote", DoctorRecordsImpl.class); //{POST} note

        //{GET} patientRecordsList [id is PatientId], {UPDATE} note [id is NoteId]
        router.attach("/record/{id}", DoctorRecordsImpl.class);

        //User section
        router.attach("/patient/record/{id}", DoctorResourceImpl.class); //{POST} notification's user for a Note


        //Chief Section
        // Get Doctors with no activity over a time range
        // Get Patients with no activity over a time range
        // Get Doctor's notes over a time range
        // Get Patient's data over a time range
        //URL example: /doctorsInactive?from=2020-10-09&to=2020-10-20
        router.attach("/doctorsInactive", DoctorsNoActivityImpl.class);
        router.attach("/patientsInactive", PatientsNoActivityImpl.class);
        router.attach("/doctorNotes/{id}", DoctorSubmissionsImpl.class);
        router.attach("/patientData/{id}", PatientSubmissionsImpl.class);
        router.attach("/patientsInactiveAndTime", WaitingConsultationAndTimeImpl.class);
        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/login", LoginImpl.class); //{POST} notification's user for a Note
        return router;
    }
}
