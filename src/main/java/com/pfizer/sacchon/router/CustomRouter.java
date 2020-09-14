package com.pfizer.sacchon.router;

import com.pfizer.sacchon.resource.CarbResourceImpl;
import com.pfizer.sacchon.resource.GlucoseResourceImpl;
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
        router.attach("/patient/carb/", CarbResourceImpl.class);
        router.attach("/patient/glucose/", GlucoseResourceImpl.class);

        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
