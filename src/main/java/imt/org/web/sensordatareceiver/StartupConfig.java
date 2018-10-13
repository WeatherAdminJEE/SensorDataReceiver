package imt.org.web.sensordatareceiver;

import imt.org.web.sensordatareceiver.publisher.IPublisher;
import imt.org.web.sensordatareceiver.publisher.mqtt.MQTTPublisher;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // Init MQTTPublisher at server startup
        IPublisher mqttPublisher = new MQTTPublisher("SensorDataReceiver");
        servletContextEvent.getServletContext().setAttribute("MQTTPublisher", mqttPublisher);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
