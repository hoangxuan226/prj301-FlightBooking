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
import sample.aircraftType.AircraftTypeDAO;
import sample.aircraftType.AircraftTypeDTO;

/**
 *
 * @author phamx
 */
public class AircraftTypeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("admin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        AircraftTypeDAO dao = new AircraftTypeDAO();
        List<AircraftTypeDTO> list = null;
        String keyword = request.getParameter("keyword");

        if (action == null || action.equals("List") || action.equals("Search")) {
            list = dao.list(keyword);
            request.setAttribute("keyword", keyword);
            request.setAttribute("list", list);
            request.getRequestDispatcher("aircraftTypeList.jsp").forward(request, response);

        } else if (action.equals("Detail")) {
            Integer typeID = null;
            try {
                typeID = Integer.parseInt(request.getParameter("typeID"));
            } catch (NumberFormatException ex) {
                log("Parameter typeID has wrong format");
            }
            AircraftTypeDTO type = null;
            if (typeID != null) {
                type = dao.load(typeID);
            }
            request.setAttribute("type", type);
            request.getRequestDispatcher("aircraftTypeDetails.jsp").forward(request, response);

        } else if (action.equals("Edit")) {
            Integer typeID = null;
            try {
                typeID = Integer.parseInt(request.getParameter("typeID"));
            } catch (NumberFormatException ex) {
                log("Parameter typeID has wrong format");
            }
            AircraftTypeDTO type = null;
            if (typeID != null) {
                type = dao.load(typeID);
            }
            request.setAttribute("type", type);
            request.setAttribute("nextaction", "Update");
            request.getRequestDispatcher("aircraftTypeEdit.jsp").forward(request, response);

        } else if (action.equals("Update")) {
            Integer typeID = null;
            Integer numberBusinessSeats = null;
            Integer numberEconomySeats = null;
            try {
                typeID = Integer.parseInt(request.getParameter("typeID"));
                numberBusinessSeats = Integer.parseInt(request.getParameter("numberBusinessSeats"));
                numberEconomySeats = Integer.parseInt(request.getParameter("numberEconomySeats"));
            } catch (NumberFormatException ex) {
                log("Parameter typeID/numberBusinessSeats/numberEconomySeats has wrong format");
            }
            String name = request.getParameter("name");

            AircraftTypeDTO type = null;
            if (typeID != null) {
                type = dao.load(typeID);
                type.setName(name);
                type.setNumberBusinessSeats(numberBusinessSeats);
                type.setNumberEconomySeats(numberEconomySeats);
                dao.update(type);
                type = dao.load(typeID);
            }
            request.setAttribute("type", type);
            request.getRequestDispatcher("aircraftTypeDetails.jsp").forward(request, response);

        } else if (action.equals("Add")) {
            request.setAttribute("nextaction", "Insert");
            request.getRequestDispatcher("aircraftTypeEdit.jsp").forward(request, response);

        } else if (action.equals("Insert")) {
            String name = request.getParameter("name");
            Integer numberBusinessSeats = null;
            Integer numberEconomySeats = null;
            try {
                numberBusinessSeats = Integer.parseInt(request.getParameter("numberBusinessSeats"));
                numberEconomySeats = Integer.parseInt(request.getParameter("numberEconomySeats"));
            } catch (NumberFormatException ex) {
                log("Parameter numberBusinessSeats/numberEconomySeats has wrong format");
            }

            AircraftTypeDTO type = new AircraftTypeDTO();
            type.setName(name);
            if (numberBusinessSeats != null) {
                type.setNumberBusinessSeats(numberBusinessSeats);
            }
            if (numberEconomySeats != null) {
                type.setNumberEconomySeats(numberEconomySeats);
            }
            Integer typeID = dao.insert(type);
            if (typeID != null) {
                type = dao.load(typeID);
                request.setAttribute("type", type);
            }
            request.getRequestDispatcher("aircraftTypeDetails.jsp").forward(request, response);

        } else if (action.equals("Delete")) {
            Integer typeID = null;
            try {
                typeID = Integer.parseInt(request.getParameter("typeID"));
            } catch (NumberFormatException ex) {
                log("Parameter typeID has wrong format");
            }

            if (typeID != null) {
                dao.delete(typeID);
            }
            response.sendRedirect("AircraftTypeController");
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
