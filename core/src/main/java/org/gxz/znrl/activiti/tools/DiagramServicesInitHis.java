package org.gxz.znrl.activiti.tools;

import org.restlet.routing.Router;

/**
 * Created by moxz on 2014/8/11.
 */
public class DiagramServicesInitHis {
    public static void attachResources(Router router) {
        router.attach("/process-instance-his/{processInstanceId}/highlights", ProcessInstanceHighlightsResourceHis.class);
    }
}
