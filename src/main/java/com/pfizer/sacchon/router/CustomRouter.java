package com.pfizer.sacchon.router;

import com.pfizer.sacchon.resource.*;
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


        router.attach("/doctor/see/freePatients", DoctorResourceImpl.class); //Get FreePatients
        router.attach("/doctor/see/myPatients", DoctorUtilitiesImpl.class); //Get myPatients
        router.attach("/doctor/account", DoctorResourceImpl.class); //Delete doctor's account
        router.attach("/doctor/account/{id}", DoctorResourceImpl.class); //, Put Patient's Doctor
        router.attach("/patient/record/{id}", DoctorResourceImpl.class); //POST notification's user{Note}

        router.attach("/postNote", DoctorRecordsImpl.class); //POST note
        router.attach("/record/{id}", DoctorRecordsImpl.class); //GET patientRecords, UPDATE note



        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
