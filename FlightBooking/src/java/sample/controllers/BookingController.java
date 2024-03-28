/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.aircraft.AircraftDAO;
import sample.aircraft.AircraftDTO;
import sample.aircraftType.AircraftTypeDAO;
import sample.aircraftType.AircraftTypeDTO;
import sample.airport.AirportDAO;
import sample.airport.AirportDTO;
import sample.booking.BookingDAO;
import sample.booking.BookingDTO;
import sample.bookingDetail.BookingDetailDAO;
import sample.bookingDetail.BookingDetailDTO;
import sample.flight.FlightDAO;
import sample.flight.FlightDTO;
import sample.members.MemberDAO;
import sample.passengers.PassengerDAO;
import sample.passengers.PassengerDTO;
import sample.utils.EmailUtils;
import sample.utils.Utils;

/**
 *
 * @author phamx
 */
public class BookingController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        FlightDAO flightDao = new FlightDAO();
        AirportDAO airportDao = new AirportDAO();
        BookingDAO bookingDao = new BookingDAO();
        BookingDetailDAO bookingDetailDao = new BookingDetailDAO();
        PassengerDAO passengerDao = new PassengerDAO();
        
        if (action == null) {
            response.sendRedirect("./");
            
        } else if (action.equals("FindFlight")) {
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String numOfPassengers = request.getParameter("numOfPassengers");
            String sDate = request.getParameter("date");
            
            List<FlightDTO> flightList = flightDao.findFlights(from, to, sDate);
            
            AirportDTO fromAirport = airportDao.load(from);
            AirportDTO toAirport = airportDao.load(to);
            
            LocalDate date = null;
            if (sDate != null) {
                date = LocalDate.parse(sDate);
                DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
                sDate = date.format(format);
            }
            request.setAttribute("flightList", flightList);
            request.setAttribute("fromAirport", fromAirport);
            request.setAttribute("toAirport", toAirport);
            request.setAttribute("date", sDate);
            request.setAttribute("numOfPassengers", numOfPassengers);
            request.getRequestDispatcher("flights.jsp").forward(request, response);
            
        } else if (action.equals("SelectFlight")) {
            Integer flightID = null;
            Integer numOfPassengers = null;
            try {
                flightID = Integer.parseInt(request.getParameter("flightID"));
                numOfPassengers = Integer.parseInt(request.getParameter("numOfPassengers"));
            } catch (NumberFormatException ex) {
                log("Parameter flightID/numOfPassengers has wrong format");
            }
            String seatType = request.getParameter("seatType");
            
            HttpSession session = request.getSession(true);
            Enumeration<String> attributes = session.getAttributeNames();
            while (attributes.hasMoreElements()) {
                String next = attributes.nextElement();
                if (!next.equals("loginUser") && !next.equals("role")) {
                    session.removeAttribute(next);
                }
            }
            session.setAttribute("flightID", flightID);
            session.setAttribute("seatType", seatType);
            session.setAttribute("numOfPassengers", numOfPassengers);
            if (numOfPassengers != null && numOfPassengers > 1) {
                request.setAttribute("count", 1);
            }
            request.setAttribute("nextAction", "getInfo");
            request.getRequestDispatcher("passengerForm.jsp").forward(request, response);
            
        } else if (action.equals("getInfo")) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("numOfPassengers") == null) {
                response.sendRedirect("./");
                return;
            }
            
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            LocalDate birthday = null;
            String date = request.getParameter("birthday");
            if (date != null) {
                birthday = LocalDate.parse(date);
            }
            String memberNumber = request.getParameter("memberNumber");
            String email = request.getParameter("email");
            String phoneno = request.getParameter("phoneno");
            Integer count = null;
            try {
                count = Integer.parseInt(request.getParameter("count"));
            } catch (NumberFormatException ex) {
                log("Parameter count has wrong format");
            }
            
            PassengerDTO passenger = new PassengerDTO();
            passenger.setFirstname(firstname);
            passenger.setLastname(lastname);
            passenger.setBirthday(birthday);
            passenger.setEmail(email);
            passenger.setPhoneno(phoneno);

            // if enter membership number, check it
            if (memberNumber != null && !memberNumber.isEmpty()) {
                MemberDAO memberDao = new MemberDAO();
                Integer memberID = memberDao.checkMembership(passenger, memberNumber);
                if (memberID == null) {
                    request.setAttribute("passenger", passenger);
                    request.setAttribute("memberNumber", memberNumber);
                    request.setAttribute("error", "Your membership information is invalid");
                    request.setAttribute("count", count);
                    request.setAttribute("nextAction", "getInfo");
                    request.getRequestDispatcher("passengerForm.jsp").forward(request, response);
                    return;
                } else {
                    passenger.setMemberID(memberID);
                }
            }
            
            int numOfPassengers = (int) session.getAttribute("numOfPassengers");
            List<PassengerDTO> passengerList = (List<PassengerDTO>) session.getAttribute("passengerList");
            if (passengerList == null) {
                passengerList = new ArrayList<>();
                session.setAttribute("passengerList", passengerList);
            }
            if (passengerList.size() < numOfPassengers && (count == null || count == passengerList.size() + 1)) {
                passengerList.add(passenger);
            }
            
            if (passengerList.size() < numOfPassengers) {
                count++;
                request.setAttribute("count", count);
                request.setAttribute("nextAction", "getInfo");
                request.getRequestDispatcher("passengerForm.jsp").forward(request, response);
            } else {
                String seatType = (String) session.getAttribute("seatType");
                int flightID = (int) session.getAttribute("flightID");
                FlightDTO flight = flightDao.load(flightID);
                AircraftDTO aircraft = (new AircraftDAO()).load(flight.getAircraftID());
                AircraftTypeDAO aTDao = new AircraftTypeDAO();
                AircraftTypeDTO aircraftType = aTDao.load(aircraft.getTypeID());
                List<String> allSeats = null;
                List<String> occupiredSeats = flightDao.listOccupiredSeats(flightID, seatType);
                int numOfBizRows = aTDao.countBusinessRows(aircraftType);
                int numOfEcoRows = aTDao.countEconomyRows(aircraftType);
                if (seatType.equals("business")) {
                    allSeats = aTDao.listBusinessSeats(aircraftType);
                    request.setAttribute("rows", numOfBizRows);
                    request.setAttribute("startRow", 1);
                } else if (seatType.equals("economy")) {
                    allSeats = aTDao.listEconomySeats(aircraftType);
                    request.setAttribute("rows", numOfEcoRows);
                    request.setAttribute("startRow", numOfBizRows + 1);
                }
                if (allSeats != null && occupiredSeats != null) {
                    request.setAttribute("allSeats", allSeats);
                    request.setAttribute("occupiredSeats", occupiredSeats);
                }
                request.getRequestDispatcher("seats.jsp").forward(request, response);
            }
            
        } else if (action.equals("selectSeats")) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("passengerList") == null) {
                response.sendRedirect("./");
                return;
            }
            String[] seats = request.getParameterValues("seats");
            session.setAttribute("seats", seats);
            Integer numOfPassengers = (int) session.getAttribute("numOfPassengers");
            List<PassengerDTO> passengerList = (List<PassengerDTO>) session.getAttribute("passengerList");
            Integer flightID = (int) session.getAttribute("flightID");
            FlightDTO flight = flightDao.load(flightID);
            AirportDTO fromAirport = airportDao.load(flight.getFrom());
            AirportDTO toAirport = airportDao.load(flight.getTo());
            DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
            String date = flight.getDepartureDate().format(format);
            String seatType = (String) session.getAttribute("seatType");
            int price = (seatType.equals("business") ? flight.getBusinessPrice() : flight.getEconomyPrice());
            int totalPrice = price * seats.length;
            
            request.setAttribute("flight", flight);
            request.setAttribute("fromAirport", fromAirport);
            request.setAttribute("toAirport", toAirport);
            request.setAttribute("date", date);
            request.setAttribute("seatType", seatType);
            request.setAttribute("numOfSeats", seats.length);
            request.setAttribute("numOfPassengers", numOfPassengers);
            request.setAttribute("passengerList", passengerList);
            request.setAttribute("price", price);
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("nextAction", "checkout");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            
        } else if (action.equals("checkout")) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("seats") == null) {
                response.sendRedirect("./");
                return;
            }
            
            String[] seats = (String[]) session.getAttribute("seats");
            String seatType = (String) session.getAttribute("seatType");
            int flightID = (int) session.getAttribute("flightID");
            List<PassengerDTO> passengerList = (List<PassengerDTO>) session.getAttribute("passengerList");
            Integer totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
            Integer price = Integer.parseInt(request.getParameter("price"));
            
            BookingDTO booking = new BookingDTO();
            booking.setFlightID(flightID);
            String reservationCode = null;
            do {
                reservationCode = Utils.generatePNR(6);
            } while (bookingDao.checkDuplicateCode(reservationCode));
            booking.setReservationCode(reservationCode);
            booking.setTotalPrice(totalPrice);
            
            Integer bookingID = null;
            Integer passengerID = null;
            bookingID = bookingDao.insert(booking);
            boolean success = true;
            if (bookingID != null) {
                for (int i = 0; i < passengerList.size(); i++) {
                    passengerID = passengerDao.insert(passengerList.get(i));
                    if (passengerID != null) {
                        BookingDetailDTO detail = new BookingDetailDTO();
                        detail.setBookingID(bookingID);
                        detail.setPassengerID(passengerID);
                        detail.setSeat(seats[i]);
                        detail.setSeatType(seatType);
                        detail.setPrice(price);
                        success = bookingDetailDao.insert(detail);
                        if (!success) {
                            break;
                        }
                    }
                }
            }
            
            String[] mainMsg = null;
            String[] extraMsg = null;
            if (success) {
                for (PassengerDTO passenger : passengerList) {
                    EmailUtils.sendBookingSuccessEmail(passenger.getLastname(), passenger.getFirstname(), passenger.getEmail(), reservationCode);
                }
                mainMsg = new String[2];
                mainMsg[0] = "Booking successed";
                mainMsg[1] = "Your reservation code is " + reservationCode;
                extraMsg = new String[2];
                extraMsg[0] = "Thanks for booking and believing in us";
                extraMsg[1] = "Hope you enjoy our flight and have a nice day!";
            } else {
                mainMsg = new String[1];
                mainMsg[0] = "Booking unsuccessed";
                extraMsg = new String[2];
                extraMsg[0] = "Something went wrong";
                extraMsg[1] = "Please try again later!";
            }
            request.setAttribute("mainMsg", mainMsg);
            request.setAttribute("extraMsg", extraMsg);
            Enumeration<String> attributes = session.getAttributeNames();
            while (attributes.hasMoreElements()) {
                String next = attributes.nextElement();
                if (!next.equals("loginUser") && !next.equals("role")) {
                    session.removeAttribute(next);
                }
            }
            request.getRequestDispatcher("notification.jsp").forward(request, response);
            
        } else if (action.equals("SearchReservation")) {
            String reservationCode = request.getParameter("reservationCode");
            String lastname = request.getParameter("lastname");
            
            BookingDTO booking = bookingDao.load(reservationCode);
            if (booking != null) {
                List<BookingDetailDTO> bookingDetailList = bookingDetailDao.list(booking.getBookingID());
                List<PassengerDTO> passengerList = new ArrayList<>();
                List<String> seats = new ArrayList<>();
                boolean found = false;
                for (BookingDetailDTO detail : bookingDetailList) {
                    PassengerDTO passenger = passengerDao.load(detail.getPassengerID());
                    if (passenger.getLastname().equalsIgnoreCase(lastname)) {
                        found = true;
                    }
                    passengerList.add(passenger);
                    seats.add(detail.getSeat());
                }
                if (found) {
                    FlightDTO flight = flightDao.load(booking.getFlightID());
                    AirportDTO fromAirport = airportDao.load(flight.getFrom());
                    AirportDTO toAirport = airportDao.load(flight.getTo());
                    String seatType = bookingDetailList.get(0).getSeatType();
                    Integer price = bookingDetailList.get(0).getPrice();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
                    String date = flight.getDepartureDate().format(format);
                    
                    HttpSession session = request.getSession(true);
                    session.setAttribute("reservationCode", reservationCode);
                    session.setAttribute("lastname", lastname);
                    
                    request.setAttribute("flight", flight);
                    request.setAttribute("fromAirport", fromAirport);
                    request.setAttribute("toAirport", toAirport);
                    request.setAttribute("date", date);
                    request.setAttribute("seatType", seatType);
                    request.setAttribute("numOfSeats", seats.size());
                    request.setAttribute("numOfPassengers", passengerList.size());
                    request.setAttribute("passengerList", passengerList);
                    request.setAttribute("price", price);
                    request.setAttribute("totalPrice", booking.getTotalPrice());
                    request.setAttribute("nextAction", "endManage");
                    request.getRequestDispatcher("cart.jsp").forward(request, response);
                    return;
                }
            }
            request.setAttribute("reservationCode", reservationCode);
            request.setAttribute("lastname", lastname);
            request.setAttribute("page", "reservation");
            request.setAttribute("error", "Your booking cannot be found. Please check the information you have submitted and try again");
            request.getRequestDispatcher("HomepageController").forward(request, response);
            
        } else if (action.equals("Modify")) {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect("./");
                return;
            }
            
            PassengerDTO passenger = null;
            if (session.getAttribute("reservationCode") != null) {
                Integer passengerID = Integer.parseInt(request.getParameter("passengerID"));
                passenger = passengerDao.load(passengerID);
            } else if (session.getAttribute("passengerList") != null) {
                Integer index = Integer.parseInt(request.getParameter("index"));
                List<PassengerDTO> passengerList = (List<PassengerDTO>) session.getAttribute("passengerList");
                passenger = passengerList.get(index);
                request.setAttribute("index", index);
            }
            String memberNumber = null;
            if (passenger.getMemberID() != 0) {
                MemberDAO memberDao = new MemberDAO();
                memberNumber = memberDao.getMemberNumber(passenger.getEmail());
            }
            request.setAttribute("memberNumber", memberNumber);
            request.setAttribute("passenger", passenger);
            request.setAttribute("nextAction", "Update");
            request.setAttribute("button", "Confirm");
            request.getRequestDispatcher("passengerForm.jsp").forward(request, response);
            
        } else if (action.equals("Update")) {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect("./");
                return;
            }
            Integer index = null;
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (NumberFormatException ex) {
                log("Parameter index has wrong format");
            }
            Integer passengerID = null;
            try {
                passengerID = Integer.parseInt(request.getParameter("passengerID"));
            } catch (NumberFormatException ex) {
                log("Parameter passengerID has wrong format");
            }
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            LocalDate birthday = null;
            String date = request.getParameter("birthday");
            if (date != null) {
                birthday = LocalDate.parse(date);
            }
            String memberNumber = request.getParameter("memberNumber");
            String email = request.getParameter("email");
            String phoneno = request.getParameter("phoneno");
            
            PassengerDTO passenger = new PassengerDTO();
            passenger.setPassengerID(passengerID);
            passenger.setFirstname(firstname);
            passenger.setLastname(lastname);
            passenger.setBirthday(birthday);
            passenger.setEmail(email);
            passenger.setPhoneno(phoneno);

            // if enter membership number, check it
            if (memberNumber != null && !memberNumber.isEmpty()) {
                MemberDAO memberDao = new MemberDAO();
                Integer memberID = memberDao.checkMembership(passenger, memberNumber);
                if (memberID == null) {
                    request.setAttribute("passenger", passenger);
                    request.setAttribute("index", index);
                    request.setAttribute("memberNumber", memberNumber);
                    request.setAttribute("error", "Your membership information is invalid");
                    request.setAttribute("nextAction", "Update");
                    request.getRequestDispatcher("passengerForm.jsp").forward(request, response);
                    return;
                } else {
                    passenger.setMemberID(memberID);
                }
            } else {
                passenger.setMemberID(0);
            }
            
            if (session.getAttribute("reservationCode") != null) {
                passengerDao.update(passenger);
                String reservationCode = (String) session.getAttribute("reservationCode");
                String name = (String) session.getAttribute("lastname");
                String url = "BookingController?action=SearchReservation";
                url += "&reservationCode=" + reservationCode;
                url += "&lastname=" + name;
                request.getRequestDispatcher(url).forward(request, response);
            } else if (session.getAttribute("passengerList") != null) {
                List<PassengerDTO> passengerList = (List<PassengerDTO>) session.getAttribute("passengerList");
                passengerList.set(index, passenger);
                
                Integer numOfPassengers = (int) session.getAttribute("numOfPassengers");
                Integer flightID = (int) session.getAttribute("flightID");
                FlightDTO flight = flightDao.load(flightID);
                AirportDTO fromAirport = airportDao.load(flight.getFrom());
                AirportDTO toAirport = airportDao.load(flight.getTo());
                DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
                String departureDate = flight.getDepartureDate().format(format);
                String[] seats = (String[]) session.getAttribute("seats");
                String seatType = (String) session.getAttribute("seatType");
                int price = (seatType.equals("business") ? flight.getBusinessPrice() : flight.getEconomyPrice());
                int totalPrice = price * seats.length;
                
                request.setAttribute("flight", flight);
                request.setAttribute("fromAirport", fromAirport);
                request.setAttribute("toAirport", toAirport);
                request.setAttribute("date", departureDate);
                request.setAttribute("seatType", seatType);
                request.setAttribute("numOfSeats", seats.length);
                request.setAttribute("numOfPassengers", numOfPassengers);
                request.setAttribute("passengerList", passengerList);
                request.setAttribute("price", price);
                request.setAttribute("totalPrice", totalPrice);
                request.setAttribute("nextAction", "checkout");
                request.getRequestDispatcher("cart.jsp").forward(request, response);
            }
            
        } else if (action.equals("endManage")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Enumeration<String> attributes = session.getAttributeNames();
                while (attributes.hasMoreElements()) {
                    String next = attributes.nextElement();
                    if (!next.equals("loginUser") && !next.equals("role")) {
                        session.removeAttribute(next);
                    }
                }
            }
            response.sendRedirect("./");
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
