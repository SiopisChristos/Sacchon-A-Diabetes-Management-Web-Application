package com.pfizer.sacchon.router;

import com.pfizer.sacchon.repository.CarbRepository;
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
        router.attach("/patient/", PatientResourceImpl.class);

        //The patient can store their data carb intake (measured in grams) *required*
        router.attach("/patient/carb/", CarbResourceImpl.class);

        //The patient can view their average carb intake over a user-specified period *required*
        router.attach("/patient/carb/{id}", CarbResourceImpl.class);

        //Returns carb entry id (testing purposes) *NOT required*
//        router.attach("/patient/carb/{id}", CarbResourceImpl.class);

        //The patient can store their data blood glucose level (date, time, measured in mg/dL) *required*
        router.attach("/patient/glucose/", GlucoseResourceImpl.class);

        //The patient can view the current and past consultations from doctors *required*
        router.attach("/patient/note/", NoteListResourceImpl.class);
        return router;
    }


    public Router publicResources() {
        Router router = new Router();
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
