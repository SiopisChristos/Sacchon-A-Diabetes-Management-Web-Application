package com.pfizer.sacchon.router;
import com.pfizer.sacchon.resource.PatientListResourceImpl;
import com.pfizer.sacchon.resource.PatientResourceImpl;
import com.pfizer.sacchon.resource.PingServerResource;
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
        router.attach("/patient", PatientListResourceImpl.class);
        router.attach("/patient/", PatientResourceImpl.class);

        router.attach("/patient/carb/{id}", CarbResourceImpl.class);
        router.attach("/patient/carb", CarbListResourceImpl.class);
        router.attach("/patient/carb/", CarbListResourceImpl.class);

        router.attach("/patient/glucose/{id}", GlucoseResourceImpl.class);
        router.attach("/patient/glucose", GlucoseListResourceImpl.class);
        router.attach("/patient/glucose/", GlucoseListResourceImpl.class);

        router.attach("/patient/note/", NoteListResourceImpl.class);
        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
