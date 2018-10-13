package imt.org.web.sensordatareceiver.http;

import imt.org.web.commonmodel.Measure;
import imt.org.web.commonmodel.SensorData;
import imt.org.web.sensordatareceiver.publisher.IPublisher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

/**
 * Receive SensorData servlet
 */
@WebServlet(name = "ReceiveSensorData", urlPatterns = "/sensorData")
public class ReceiveSensorData extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if(request.getParameter("idSensor") == null || request.getParameter("idSensor") == "") {
            throw new ServletException("Empty sensor ID");
        } else if(request.getParameter("idCountry") == null || request.getParameter("idCountry") == "") {
            throw new ServletException("Empty country ID");
        } else if(request.getParameter("idCity") == null || request.getParameter("idCity") == "") {
            throw new ServletException("Empty city ID");
        } else if(request.getParameter("temperature") == null || request.getParameter("temperature") == "") {
            throw new ServletException("Empty temparature");
        } else if(request.getParameter("windSpeed") == null || request.getParameter("windSpeed") == "") {
            throw new ServletException("Empty windSpeed");
        } else if(request.getParameter("pressure") == null || request.getParameter("pressure") == "") {
            throw new ServletException("Empty pressure");
        } else if(request.getParameter("timestamp") == null || request.getParameter("timestamp") == "") {
            throw new ServletException("Empty timestamp");
        } else {
            Measure measure = new Measure(
                Double.valueOf(request.getParameter("temperature")),
                Double.valueOf(request.getParameter("windSpeed")),
                Double.valueOf(request.getParameter("pressure"))
            );
            SensorData sensorData = new SensorData(
                Integer.parseInt(request.getParameter("idSensor")),
                request.getParameter("idCountry"),
                request.getParameter("idCity"),
                measure,
                Timestamp.valueOf(request.getParameter("timestamp"))
            );
            ((IPublisher) getServletContext().getAttribute("MQTTPublisher")).publish(sensorData);
        }
    }
}
