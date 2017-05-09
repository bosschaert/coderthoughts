package org.coderthoughts.presencefilter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

@Component(service = ServletContextHelper.class, property = {
        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=" + FilterServletContext.NAME,
        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH + "=/test" })
public class FilterServletContext extends ServletContextHelper {
    public static final String NAME = "org.coderthoughts.presencefilter";
}
