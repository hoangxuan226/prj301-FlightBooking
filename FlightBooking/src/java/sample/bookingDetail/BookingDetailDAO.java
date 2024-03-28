/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.bookingDetail;

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
public class BookingDetailDAO {

    public boolean insert(BookingDetailDTO detail) {
        boolean check = false;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "INSERT INTO BookingDetail(bookingID, passengerID, seat, seatType, discount, price) "
                    + " VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, detail.getBookingID());
            stmt.setInt(2, detail.getPassengerID());
            stmt.setString(3, detail.getSeat());
            stmt.setString(4, detail.getSeatType());
            stmt.setInt(5, detail.getDiscount());
            stmt.setInt(6, detail.getPrice());
            check = stmt.executeUpdate() > 0;
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in insert booking detail. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return check;
    }

    public List<BookingDetailDTO> list(int bookingID) {
        List<BookingDetailDTO> list = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT passengerID, seat, seatType, discount, price"
                    + " FROM BookingDetail"
                    + " WHERE bookingID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BookingDetailDTO detail = new BookingDetailDTO();
                detail.setBookingID(bookingID);
                detail.setPassengerID(rs.getInt("passengerID"));
                detail.setSeat(rs.getString("seat"));
                detail.setSeatType(rs.getString("seatType"));
                detail.setDiscount(rs.getInt("discount"));
                detail.setPrice(rs.getInt("price"));
                list.add(detail);
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in list booking detail by bookingID. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }
}
