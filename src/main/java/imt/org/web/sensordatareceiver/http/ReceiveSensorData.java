package imt.org.web.sensordatareceiver.http;

import imt.org.web.commonmodel.MeasureType;
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
        } else if(request.getParameter("gpsCoordinates") == null || request.getParameter("gpsCoordinates") == "") {
            throw new ServletException("Empty GPS coordinates");
        } else if(request.getParameter("measureType") == null || request.getParameter("measureType") == "") {
            throw new ServletException("Empty measure type");
        } else if(request.getParameter("measureValue") == null || request.getParameter("measureValue") == "") {
            throw new ServletException("Empty measure value");
        } else if(request.getParameter("timestamp") == null || request.getParameter("timestamp") == "") {
            throw new ServletException("Empty timestamp");
        } else {
            SensorData sensorData = new SensorData(
                Integer.parseInt(request.getParameter("idSensor")),
                request.getParameter("idCountry"),
                request.getParameter("idCity"),
                request.getParameter("gpsCoordinates"),
                setMeasureType(request.getParameter("measureType")),
                Double.valueOf(request.getParameter("measureValue")),
                Timestamp.valueOf(request.getParameter("timestamp"))
            );
            ((IPublisher) getServletContext().getAttribute("MQTTPublisher")).publish(sensorData);
        }
    }

    /**
     * Set MeasureType from main args
     * @param measureType Main measure type arg
     * @return MeasureType
     */
    public MeasureType setMeasureType(String measureType) {
        switch(measureType) {
            case "TEMPERATURE":
                return MeasureType.TEMPERATURE;
            case "ATM_PRESSURE":
                return MeasureType.ATM_PRESSURE;
            case "WIND_SPEED":
                return MeasureType.WIND_SPEED;
            case "WIND_DIRECTION":
                return MeasureType.WIND_DIRECTION;
            default:
                return null;
        }
    }
}
