/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author phamx
 */
public class BookingDAO {

    public BookingDTO load(String reservationCode) {
        BookingDTO booking = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT bookingID, flightID, totalPrice"
                    + " FROM Booking"
                    + " WHERE reservationCode = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, reservationCode);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                booking = new BookingDTO();
                booking.setBookingID(rs.getInt("bookingID"));
                booking.setFlightID(rs.getInt("flightID"));
                booking.setReservationCode(reservationCode);
                booking.setTotalPrice(rs.getInt("totalPrice"));
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in load booking reservation code. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return booking;
    }

    public Integer insert(BookingDTO booking) {
        Integer bookingID = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT max(bookingID) AS maxID FROM Booking";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int maxID = 0;
            if (rs.next()) {
                maxID = rs.getInt("maxID");
            }
            sql = "INSERT INTO Booking(bookingID, flightID, reservationCode, totalPrice) "
                    + " VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maxID + 1);
            stmt.setInt(2, booking.getFlightID());
            stmt.setString(3, booking.getReservationCode());
            stmt.setInt(4, booking.getTotalPrice());
            if (stmt.executeUpdate() > 0) {
                bookingID = maxID + 1;
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in insert booking. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return bookingID;
    }

    public boolean checkDuplicateCode(String code) {
        boolean check = false;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT 1 "
                    + " FROM Booking"
                    + " WHERE reservationCode COLLATE SQL_Latin1_General_Cp1_CS_AS = ? COLLATE SQL_Latin1_General_Cp1_CS_AS";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in insert booking. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return check;
    }

    public List<Integer> listIDs(int flightID) {
        List<Integer> list = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT bookingID"
                    + " FROM Booking"
                    + " WHERE flightID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, flightID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("bookingID"));
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in list bookingID by flightID. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

}
