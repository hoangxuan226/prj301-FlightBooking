/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.passengers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import sample.utils.DBUtils;

/**
 *
 * @author phamx
 */
public class PassengerDAO {

    public Integer insert(PassengerDTO passenger) {
        Integer passengerID = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT max(passengerID) AS maxID FROM Passengers";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int maxID = 0;
            if (rs.next()) {
                maxID = rs.getInt("maxID");
            }
            if (passenger.getMemberID() == 0) {
                sql = "INSERT INTO Passengers(passengerID, firstname, lastname, birthday, email, phoneno)"
                        + " VALUES (?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO Passengers(passengerID, firstname, lastname, birthday, email, phoneno, memberID)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            }
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maxID + 1);
            stmt.setString(2, passenger.getFirstname());
            stmt.setString(3, passenger.getLastname());
            stmt.setString(4, passenger.getBirthday().toString());
            stmt.setString(5, passenger.getEmail());
            stmt.setString(6, passenger.getPhoneno());
            if (passenger.getMemberID() != 0) {
                stmt.setInt(7, passenger.getMemberID());
            }
            if (stmt.executeUpdate() > 0) {
                passengerID = maxID + 1;
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in insert passenger. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return passengerID;
    }

    public PassengerDTO load(int passengerID) {
        PassengerDTO passenger = null;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT firstname, lastname, birthday, email, phoneno, memberID"
                    + " FROM Passengers"
                    + " WHERE passengerID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, passengerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                passenger = new PassengerDTO();
                passenger.setPassengerID(passengerID);
                passenger.setFirstname(rs.getString("firstname"));
                passenger.setLastname(rs.getString("lastname"));
                passenger.setBirthday(rs.getDate("birthday").toLocalDate());
                passenger.setEmail(rs.getString("email"));
                passenger.setPhoneno(rs.getString("phoneno"));
                passenger.setMemberID(rs.getInt("memberID"));
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in load passenger. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return passenger;
    }

    public boolean update(PassengerDTO passenger) {
        boolean check = false;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "UPDATE Passengers"
                    + " SET firstname = ?, lastname = ?, birthday = ?, email = ?, phoneno = ? , memberID = ?"
                    + " WHERE passengerID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, passenger.getFirstname());
            stmt.setString(2, passenger.getLastname());
            stmt.setString(3, passenger.getBirthday().toString());
            stmt.setString(4, passenger.getEmail());
            stmt.setString(5, passenger.getPhoneno());
            if (passenger.getMemberID() > 0) {
                stmt.setInt(6, passenger.getMemberID());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }
            stmt.setInt(7, passenger.getPassengerID());
            check = stmt.executeUpdate() > 0;
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in update passenger. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return check;
    }
}
