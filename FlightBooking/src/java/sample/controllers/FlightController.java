/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.aircraft.AircraftDAO;
import sample.aircraft.AircraftDTO;
import sample.airport.AirportDAO;
import sample.airport.AirportDTO;
import sample.booking.BookingDAO;
import sample.bookingDetail.BookingDetailDAO;
import sample.bookingDetail.BookingDetailDTO;
import sample.flight.FlightDAO;
import sample.flight.FlightDTO;
import sample.passengers.PassengerDAO;
import sample.passengers.PassengerDTO;

/**
 *
 * @author phamx
 */
public class FlightController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("admin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        FlightDAO dao = new FlightDAO();
        List<FlightDTO> flightList = null;
        request.setAttribute("manage", "flight");

        if (action == null || action.equals("Search")) {
            String keyword = request.getParameter("keyword");
            flightList = dao.list(keyword);
            request.setAttribute("keyword", keyword);
            request.setAttribute("flightList", flightList);
            request.getRequestDispatcher("admin.jsp").forward(request, response);

        } else if (action.equals("Detail")) {
            Integer flightID = null;
            try {
                flightID = Integer.parseInt(request.getParameter("flightID"));
            } catch (NumberFormatException ex) {
                log("Parameter flightID has wrong format");
            }
            FlightDTO flight = null;
            if (flightID != null) {
                flight = dao.load(flightID);
            }
            request.setAttribute("flight", flight);
            request.getRequestDispatcher("flightDetails.jsp").forward(request, response);

        } else if (action.equals("Edit")) {
            Integer flightID = null;
            try {
                flightID = Integer.parseInt(request.getParameter("flightID"));
            } catch (NumberFormatException ex) {
                log("Parameter flightID has wrong format");
            }
            FlightDTO flight = null;
            if (flightID != null) {
                flight = dao.load(flightID);
            }
            AircraftDAO aircraftDao = new AircraftDAO();
            List<AircraftDTO> aircraftList = aircraftDao.list("");
            AirportDAO airportDao = new AirportDAO();
            List<AirportDTO> airportList = airportDao.list();
            request.setAttribute("flight", flight);
            request.setAttribute("aircraftList", aircraftList);
            request.setAttribute("airportList", airportList);
            request.setAttribute("nextaction", "Update");
            request.getRequestDispatcher("flightEdit.jsp").forward(request, response);

        } else if (action.equals("Update")) {
            Integer flightID = null;
            Integer aircraftID = null;
            Integer duration = null;
            Integer economyPrice = null;
            Integer businessPrice = null;
            try {
                flightID = Integer.parseInt(request.getParameter("flightID"));
                aircraftID = Integer.parseInt(request.getParameter("aircraftID"));
                duration = Integer.parseInt(request.getParameter("duration"));
                economyPrice = Integer.parseInt(request.getParameter("economyPrice"));
                businessPrice = Integer.parseInt(request.getParameter("businessPrice"));
            } catch (NumberFormatException ex) {
                log("Parameter flightID/aircraftID/duration/economyPrice/businessPrice has wrong format");
            }
            String flightNumber = request.getParameter("flightNumber");
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            LocalDate departureDate = null;
            String date = request.getParameter("departureDate");
            if (date != null) {
                departureDate = LocalDate.parse(date);
            }
            LocalTime departureTime = null;
            String time = request.getParameter("departureTime");
            if (time != null) {
                departureTime = LocalTime.parse(time);
            }
            FlightDTO flight = null;
            if (flightID != null) {
                flight = dao.load(flightID);
                flight.setFlightNumber(flightNumber);
                flight.setAircraftID(aircraftID);
                flight.setFrom(from);
                flight.setTo(to);
                flight.setDepartureDate(departureDate);
                flight.setDepartureTime(departureTime);
                flight.setDuration(duration);
                flight.setEconomyPrice(economyPrice);
                flight.setBusinessPrice(businessPrice);
                dao.update(flight);
                flight = dao.load(flightID);
            }
            request.setAttribute("flight", flight);
            request.getRequestDispatcher("flightDetails.jsp").forward(request, response);

        } else if (action.equals("Add")) {
            AircraftDAO aircraftDao = new AircraftDAO();
            List<AircraftDTO> aircraftList = aircraftDao.list("");
            AirportDAO airportDao = new AirportDAO();
            List<AirportDTO> airportList = airportDao.list();
            request.setAttribute("aircraftList", aircraftList);
            request.setAttribute("airportList", airportList);
            request.setAttribute("nextaction", "Insert");
            request.getRequestDispatcher("flightEdit.jsp").forward(request, response);

        } else if (action.equals("Insert")) {
            Integer aircraftID = null;
            Integer duration = null;
            Integer economyPrice = null;
            Integer businessPrice = null;
            try {
                aircraftID = Integer.parseInt(request.getParameter("aircraftID"));
                duration = Integer.parseInt(request.getParameter("duration"));
                economyPrice = Integer.parseInt(request.getParameter("economyPrice"));
                businessPrice = Integer.parseInt(request.getParameter("businessPrice"));
            } catch (NumberFormatException ex) {
                log("Parameter aircraftID/duration/economyPrice/businessPrice has wrong format");
            }
            String flightNumber = request.getParameter("flightNumber");
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            LocalDate departureDate = null;
            String date = request.getParameter("departureDate");
            if (date != null) {
                departureDate = LocalDate.parse(date);
            }
            LocalTime departureTime = null;
            String time = request.getParameter("departureTime");
            if (time != null) {
                departureTime = LocalTime.parse(time);
            }

            FlightDTO flight = new FlightDTO();
            Integer flightID = null;
            if (aircraftID != null && duration != null && economyPrice != null && businessPrice != null) {
                flight.setFlightNumber(flightNumber);
                flight.setFrom(from);
                flight.setTo(to);
                flight.setDepartureDate(departureDate);
                flight.setDepartureTime(departureTime);
                flight.setAircraftID(aircraftID);
                flight.setDuration(duration);
                flight.setEconomyPrice(economyPrice);
                flight.setBusinessPrice(businessPrice);
                flightID = dao.insert(flight);
            }
            if (flightID != null) {
                flight = dao.load(flightID);
                request.setAttribute("flight", flight);
            }
            request.getRequestDispatcher("flightDetails.jsp").forward(request, response);

        } else if (action.equals("Delete")) {
            Integer flightID = null;
            try {
                flightID = Integer.parseInt(request.getParameter("flightID"));
            } catch (NumberFormatException ex) {
                log("Parameter flightID has wrong format");
            }

            if (flightID != null) {
                dao.delete(flightID);
            }
            response.sendRedirect("FlightController");

        } else if (action.equals("ViewSeats")) {
            Integer flightID = null;
            try {
                flightID = Integer.parseInt(request.getParameter("flightID"));
            } catch (NumberFormatException ex) {
                log("Parameter flightID has wrong format");
            }
            BookingDAO bookingDao = new BookingDAO();
            if (flightID != null) {
                List<Integer> bookingIdList = bookingDao.listIDs(flightID);
                BookingDetailDAO bookingDetailDao = new BookingDetailDAO();
                List<BookingDetailDTO> detailList = new ArrayList<>();
                for (Integer bookingID : bookingIdList) {
                    detailList.addAll(bookingDetailDao.list(bookingID));
                }
                Map<Integer, PassengerDTO> passengerList = new HashMap<>();
                PassengerDAO passengerDao = new PassengerDAO();
                for (BookingDetailDTO detail : detailList) {
                    PassengerDTO passenger = passengerDao.load(detail.getPassengerID());
                    passengerList.put(passenger.getPassengerID(), passenger);
                }
                request.setAttribute("detailList", detailList);
                request.setAttribute("passengerList", passengerList);
                request.getRequestDispatcher("bookedSeats.jsp").forward(request, response);
            }

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
