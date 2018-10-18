package imt.org.web.sensordatareceiver;

import imt.org.web.sensordatareceiver.publisher.IPublisher;
import imt.org.web.sensordatareceiver.publisher.mqtt.MQTTPublisher;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupConfig implements ServletContextListener {

    /**
     * Init MQTTPublisher at server startup
     * @param servletContextEvent servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        IPublisher mqttPublisher = new MQTTPublisher("SensorDataReceiver");
        servletContextEvent.getServletContext().setAttribute("MQTTPublisher", mqttPublisher);
    }

    /**
     * Unused
     * @param servletContextEvent servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        throw new UnsupportedOperationException();
    }
}
