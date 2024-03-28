/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.members.MemberDAO;
import sample.members.MemberDTO;
import sample.utils.EmailUtils;
import sample.utils.Utils;

/**
 *
 * @author phamx
 */
public class MemberController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        MemberDAO dao = new MemberDAO();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("role") == null) {

            if (action == null) {
                response.sendRedirect("login.jsp");

            } else if (action.equals("Register")) {
                String lastname = request.getParameter("lastname");
                String firstname = request.getParameter("firstname");
                String birthday = request.getParameter("birthday");
                String gender = request.getParameter("gender");
                String email = request.getParameter("email");
                String phoneno = request.getParameter("phoneno");
                String nationality = request.getParameter("nationality");
                String numberID = request.getParameter("numberID");
                String password = request.getParameter("password");

                MemberDTO member = new MemberDTO();
                member.setFirstname(firstname);
                member.setLastname(lastname);
                if (birthday != null) {
                    member.setBirthday(LocalDate.parse(birthday));
                }
                member.setGender(gender);
                member.setEmail(email);
                member.setPhoneno(phoneno);
                member.setNationality(nationality);
                member.setNumberID(numberID);

                boolean isValid = true;
                if (dao.isEmailRegistered(email)) {
                    request.setAttribute("errorEmail", "Your email was registered");
                    isValid = false;
                }
                if (member.getBirthday() != null && !Utils.isSixteenYearsOld(member.getBirthday())) {
                    request.setAttribute("errorBirthday", "Only people age 16+ can register");
                    isValid = false;
                }
                if (!isValid) {
                    request.setAttribute("member", member);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }

                member.setPassword(password);
                Integer memberID = null;
                if (birthday != null) {
                    memberID = dao.insert(member);
                }
                if (memberID != null) {
                    String memberNumber = dao.getMemberNumber(email);
                    String[] extraMsg = new String[2];
                    extraMsg[0] = "Thank you for becoming a member of HVH airways";
                    if (EmailUtils.sendRegisterSuccessEmail(lastname, firstname, email, memberNumber)) {
                        extraMsg[1] = "We have sent you the membership number via your email";
                    } else {
                        extraMsg[1] = "You can log in to see your membership number in your profile";
                    }
                    request.setAttribute("mainMsg", "Registration successed");
                    request.setAttribute("extraMsg", extraMsg);
                    request.getRequestDispatcher("notification.jsp").forward(request, response);
                } else {
                    request.setAttribute("mainMsg", "Registration unsuccessed");
                    request.setAttribute("extraMsg", "Your account cannot be created at this time");
                    request.getRequestDispatcher("notification.jsp").forward(request, response);
                }

            }

        } else if (session.getAttribute("role").equals("admin")) {
            List<MemberDTO> memberList = null;
            String keyword = request.getParameter("keyword");
            request.setAttribute("manage", "member");

            if (action == null || action.equals("Search")) {
                memberList = dao.list(keyword);
                request.setAttribute("keyword", keyword);
                request.setAttribute("memberList", memberList);
                request.getRequestDispatcher("admin.jsp").forward(request, response);

            } else if (action.equals("Detail")) {
                Integer memberID = null;
                try {
                    memberID = Integer.parseInt(request.getParameter("memberID"));
                } catch (NumberFormatException ex) {
                    log("Parameter memberID has wrong format");
                }
                MemberDTO member = null;
                if (memberID != null) {
                    member = dao.load(memberID);
                }
                request.setAttribute("member", member);
                request.getRequestDispatcher("memberDetails.jsp").forward(request, response);
            }

        } else if (session.getAttribute("role").equals("member")) {
            MemberDTO member = (MemberDTO) session.getAttribute("loginUser");

            if (action == null) {
                request.setAttribute("member", member);
                request.getRequestDispatcher("memberDetails.jsp").forward(request, response);

            } else if (action.equals("Edit")) {
                request.setAttribute("member", member);
                request.getRequestDispatcher("memberEdit.jsp").forward(request, response);

            } else if (action.equals("Update")) {
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String birthday = request.getParameter("birthday");
                String gender = request.getParameter("gender");
                String email = request.getParameter("email");
                String phoneno = request.getParameter("phoneno");
                String nationality = request.getParameter("nationality");
                String numberID = request.getParameter("numberID");

                MemberDTO newInfo = new MemberDTO();
                newInfo.setMemberID(member.getMemberID());
                newInfo.setFirstname(firstname);
                newInfo.setLastname(lastname);
                if (birthday != null) {
                    newInfo.setBirthday(LocalDate.parse(birthday));
                }
                newInfo.setGender(gender);
                newInfo.setEmail(email);
                newInfo.setPhoneno(phoneno);
                newInfo.setNationality(nationality);
                newInfo.setNumberID(numberID);
                if (email != null && !email.equals(member.getEmail()) && dao.isEmailRegistered(email)) {
                    request.setAttribute("member", newInfo);
                    request.setAttribute("error", "The email was registered");
                    request.getRequestDispatcher("memberEdit.jsp").forward(request, response);
                } else {
                    if (birthday != null) {
                        dao.update(newInfo);
                        member = dao.load(newInfo.getMemberID());
                        session.setAttribute("loginUser", member);
                    }
                    request.setAttribute("member", member);
                    request.getRequestDispatcher("memberDetails.jsp").forward(request, response);
                }

            } else if (action.equals("Delete")) {
                dao.delete(member.getMemberID());
                response.sendRedirect("LoginController?action=Logout");
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
