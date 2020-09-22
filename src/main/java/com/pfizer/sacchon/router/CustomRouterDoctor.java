package com.pfizer.sacchon.router;

import com.pfizer.sacchon.resource.doctors.DoctorRecordsImpl;
import com.pfizer.sacchon.resource.PingServerResource;
import org.restlet.Application;
import org.restlet.routing.Router;

public class CustomRouterDoctor {

    private Application application;

    public CustomRouterDoctor(Application application) {
        this.application = application;

    }

    public Router createApiRouter() {

        Router router = new Router(application.getContext());

        router.attach("/record", DoctorRecordsImpl.class); //POST note
        router.attach("/record/{id}", DoctorRecordsImpl.class); //GET patientRecords, UPDATE note

        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
