/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
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

/**
 *
 * @author phamx
 */
public class AircraftController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("admin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        AircraftDAO dao = new AircraftDAO();
        List<AircraftDTO> aircraftList = null;
        String keyword = request.getParameter("keyword");
        request.setAttribute("manage", "aircraft");

        if (action == null || action.equals("Search")) {
            aircraftList = dao.list(keyword);
            request.setAttribute("keyword", keyword);
            request.setAttribute("aircraftList", aircraftList);
            request.getRequestDispatcher("admin.jsp").forward(request, response);

        } else if (action.equals("Detail")) {
            Integer aircraftID = null;
            try {
                aircraftID = Integer.parseInt(request.getParameter("aircraftID"));
            } catch (NumberFormatException ex) {
                log("Parameter aircraftID has wrong format");
            }
            AircraftDTO aircraft = null;
            if (aircraftID != null) {
                aircraft = dao.load(aircraftID);
            }
            request.setAttribute("aircraft", aircraft);
            request.getRequestDispatcher("aircraftDetails.jsp").forward(request, response);

        } else if (action.equals("Edit")) {
            Integer aircraftID = null;
            try {
                aircraftID = Integer.parseInt(request.getParameter("aircraftID"));
            } catch (NumberFormatException ex) {
                log("Parameter aircraftID has wrong format");
            }
            AircraftDTO aircraft = null;
            if (aircraftID != null) {
                aircraft = dao.load(aircraftID);
            }
            AircraftTypeDAO typeDao = new AircraftTypeDAO();
            List<AircraftTypeDTO> typeList = typeDao.list("");
            request.setAttribute("typeList", typeList);
            request.setAttribute("aircraft", aircraft);
            request.setAttribute("nextaction", "Update");
            request.getRequestDispatcher("aircraftEdit.jsp").forward(request, response);

        } else if (action.equals("Update")) {
            Integer aircraftID = null;
            Integer typeID = null;
            try {
                aircraftID = Integer.parseInt(request.getParameter("aircraftID"));
                typeID = Integer.parseInt(request.getParameter("typeID"));
            } catch (NumberFormatException ex) {
                log("Parameter aircraftID/typeID has wrong format");
            }
            String registration = request.getParameter("registration");

            AircraftDTO aircraft = null;
            if (aircraftID != null) {
                aircraft = dao.load(aircraftID);
            }
            if (aircraft != null) {
                aircraft.setRegistration(registration);
                aircraft.setTypeID(typeID);
                dao.update(aircraft);
                aircraft = dao.load(aircraftID);
            }
            request.setAttribute("aircraft", aircraft);
            request.getRequestDispatcher("aircraftDetails.jsp").forward(request, response);

        } else if (action.equals("Add")) {
            AircraftTypeDAO typeDao = new AircraftTypeDAO();
            List<AircraftTypeDTO> typeList = typeDao.list("");
            request.setAttribute("typeList", typeList);
            request.setAttribute("nextaction", "Insert");
            request.getRequestDispatcher("aircraftEdit.jsp").forward(request, response);

        } else if (action.equals("Insert")) {
            String registration = request.getParameter("registration");
            Integer typeID = null;
            try {
                typeID = Integer.parseInt(request.getParameter("typeID"));
            } catch (NumberFormatException ex) {
                log("Parameter typeID has wrong format");
            }

            AircraftDTO aircraft = new AircraftDTO();
            aircraft.setRegistration(registration);
            if (typeID != null) {
                aircraft.setTypeID(typeID);
            }
            Integer aircraftID = dao.insert(aircraft);
            if (aircraftID != null) {
                aircraft = dao.load(aircraftID);
                request.setAttribute("aircraft", aircraft);
            }
            request.getRequestDispatcher("aircraftDetails.jsp").forward(request, response);

        } else if (action.equals("Delete")) {
            Integer aircraftID = null;
            try {
                aircraftID = Integer.parseInt(request.getParameter("aircraftID"));
            } catch (NumberFormatException ex) {
                log("Parameter aircraftID has wrong format");
            }

            if (aircraftID != null) {
                dao.delete(aircraftID);
            }
            response.sendRedirect("AircraftController");
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
