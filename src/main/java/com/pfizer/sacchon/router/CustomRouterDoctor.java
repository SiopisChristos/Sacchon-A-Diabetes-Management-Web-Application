package com.pfizer.sacchon.router;

import com.pfizer.sacchon.resource.DoctorNoteImpl;
import com.pfizer.sacchon.resource.PatientResourceImpl;
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

        router.attach("/consultation", DoctorNoteImpl.class); //POST note
        router.attach("/consultation/{id}", DoctorNoteImpl.class); //GET, UPDATE note
        router.attach("/ping",PingServerResource.class);




        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
