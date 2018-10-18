package imt.org.web.sensordatareceiver.http;

import imt.org.web.commonmodel.model.MeasureType;
import imt.org.web.commonmodel.model.SensorData;
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

    // POST params constants
    private static final String ID_SENSOR = "idSensor";
    private static final String ID_COUNTRY = "idCountry";
    private static final String ID_CITY = "idCity";
    private static final String GPS_COORDINATES = "gpsCoordinates";
    private static final String MEASURE_TYPE = "measureType";
    private static final String MEASURE_VALUE = "measureValue";
    private static final String TIMESTAMP = "timestamp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if(request.getParameter(ID_SENSOR) == null || request.getParameter(ID_SENSOR) == "") {
            throw new ServletException("Empty parameter : " + ID_SENSOR);
        } else if(request.getParameter(ID_COUNTRY) == null || request.getParameter(ID_COUNTRY) == "") {
            throw new ServletException("Empty parameter : " + ID_COUNTRY);
        } else if(request.getParameter(ID_CITY) == null || request.getParameter(ID_CITY) == "") {
            throw new ServletException("Empty parameter : " + ID_CITY);
        } else if(request.getParameter(GPS_COORDINATES) == null || request.getParameter(GPS_COORDINATES) == "") {
            throw new ServletException("Empty parameter : " + GPS_COORDINATES);
        } else if(request.getParameter(MEASURE_TYPE) == null || request.getParameter(MEASURE_TYPE) == "") {
            throw new ServletException("Empty parameter : " + MEASURE_TYPE);
        } else if(request.getParameter(MEASURE_VALUE) == null || request.getParameter(MEASURE_VALUE) == "") {
            throw new ServletException("Empty parameter : " + MEASURE_VALUE);
        } else if(request.getParameter(TIMESTAMP) == null || request.getParameter(TIMESTAMP) == "") {
            throw new ServletException("Empty parameter : " + TIMESTAMP);
        } else {
            SensorData sensorData = new SensorData(
                Integer.parseInt(request.getParameter(ID_SENSOR)),
                request.getParameter(ID_COUNTRY),
                request.getParameter(ID_CITY),
                request.getParameter(GPS_COORDINATES),
                MeasureType.valueOf(request.getParameter(MEASURE_TYPE)),
                Double.valueOf(request.getParameter(MEASURE_VALUE)),
                Timestamp.valueOf(request.getParameter(TIMESTAMP))
            );
            ((IPublisher) getServletContext().getAttribute("MQTTPublisher")).publish(sensorData);
        }
    }
}
