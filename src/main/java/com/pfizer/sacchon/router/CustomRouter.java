package com.pfizer.sacchon.router;

import com.pfizer.sacchon.resource.DoctorRecordsImpl;
import com.pfizer.sacchon.resource.DoctorResourceImpl;
import com.pfizer.sacchon.resource.PatientResourceImpl;
import com.pfizer.sacchon.resource.PingServerResource;
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


        router.attach("/doctor/patients", DoctorResourceImpl.class); //Get FreePatients
        router.attach("/doctor/{id}", DoctorResourceImpl.class); //Delete doctor's account, Put Patient's Doctor
        router.attach("/patient/record/{id}", DoctorResourceImpl.class); //POST notification's user{Note}

        router.attach("/doctor/note", DoctorRecordsImpl.class); //POST note
        router.attach("/record/{id}", DoctorRecordsImpl.class); //GET patientRecords, UPDATE note



        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
