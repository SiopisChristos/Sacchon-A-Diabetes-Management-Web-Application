package com.pfizer.sacchon.router;
import com.pfizer.sacchon.resource.patients.*;
import com.pfizer.sacchon.resource.PingServerResource;

import com.pfizer.sacchon.resource.chiefDoctors.DoctorSubmissionsImpl;
import com.pfizer.sacchon.resource.chiefDoctors.DoctorsNoActivityImpl;
import com.pfizer.sacchon.resource.chiefDoctors.PatientSubmissionsImpl;
import com.pfizer.sacchon.resource.chiefDoctors.PatientsNoActivityImpl;
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

        router.attach("/patient/{id}", PatientResourceImpl.class);
        router.attach("/patient", PatientListResourceImpl.class);
        router.attach("/patient/", PatientResourceImpl.class);

        router.attach("/patient/carb/{id}", CarbResourceImpl.class);
        router.attach("/patient/carb", CarbListResourceImpl.class);
        router.attach("/patient/carb/", CarbListResourceImpl.class);

        router.attach("/patient/glucose/{id}", GlucoseResourceImpl.class);
        router.attach("/patient/glucose", GlucoseListResourceImpl.class);
        router.attach("/patient/glucose/", GlucoseListResourceImpl.class);

        router.attach("/patient/note/", NoteListResourceImpl.class);

        //Doctor Section
        router.attach("/doctor/{id}", DoctorUtilitiesImpl.class); //{post} create doctor
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
        // Get Doctors with no activity for a certain time range
        //URL example: /doctorsInactive?from=2020-10-09&to=2020-10-20
        router.attach("/doctorsInactive", DoctorsNoActivityImpl.class);
        router.attach("/patientsInactive", PatientsNoActivityImpl.class);
        router.attach("/doctorNotes/{id}", DoctorSubmissionsImpl.class);
        router.attach("/patientData/{id}", PatientSubmissionsImpl.class);
        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
