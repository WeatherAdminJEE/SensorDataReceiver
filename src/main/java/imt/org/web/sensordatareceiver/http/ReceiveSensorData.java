package imt.org.web.sensordatareceiver.http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ReceiveSensorData", urlPatterns = "/sensorData")
public class ReceiveSensorData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>idSensor" + request.getParameter("idSensor") + "</h1>");
        out.println("<h1>country" + request.getParameter("country") + "</h1>");
        out.println("<h1>city" + request.getParameter("city") + "</h1>");
        out.println("<h1>temperature" + request.getParameter("temperature") + "</h1>");
        out.println("<h1>windSpeed" + request.getParameter("windSpeed") + "</h1>");
        out.println("<h1>pressure" + request.getParameter("pressure") + "</h1>");
        out.println("<h1>timestamp" + request.getParameter("timestamp") + "</h1>");

        System.out.println("<h1>idSensor" + request.getParameter("idSensor") + "</h1>");
        System.out.println("<h1>country" + request.getParameter("country") + "</h1>");
        System.out.println("<h1>city" + request.getParameter("city") + "</h1>");
        System.out.println("<h1>temperature" + request.getParameter("temperature") + "</h1>");
        System.out.println("<h1>windSpeed" + request.getParameter("windSpeed") + "</h1>");
        System.out.println("<h1>pressure" + request.getParameter("pressure") + "</h1>");
        System.out.println("<h1>timestamp" + request.getParameter("timestamp") + "</h1>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
