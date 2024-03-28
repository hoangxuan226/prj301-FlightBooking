/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.aircraft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author phamx
 */
public class AircraftDAO {

    public List<AircraftDTO> list(String keyword) {
        List<AircraftDTO> list = new ArrayList<>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT A.aircraftID, A.registration, A.typeID, B.name"
                    + " FROM Aircraft A JOIN AircraftType B ON A.typeID = B.typeID";
            if (keyword != null && !keyword.isEmpty()) {
                sql += " WHERE A.registration like ? OR B.name like ?";
            }
            PreparedStatement stmt = con.prepareStatement(sql);
            if (keyword != null && !keyword.isEmpty()) {
                stmt.setString(1, "%" + keyword + "%");
                stmt.setString(2, "%" + keyword + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AircraftDTO aircraft = new AircraftDTO();
                aircraft.setAircraftID(rs.getInt("aircraftID"));
                aircraft.setRegistration(rs.getString("registration"));
                aircraft.setTypeID(rs.getInt("typeID"));
                aircraft.setTypeName(rs.getString("name"));
                list.add(aircraft);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in list aircrafts. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

    public AircraftDTO load(int aircraftID) {
        AircraftDTO aircraft = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT A.registration, A.typeID, B.name"
                    + " FROM Aircraft A JOIN AircraftType B ON A.typeID = B.typeID"
                    + " WHERE A.aircraftID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, aircraftID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                aircraft = new AircraftDTO();
                aircraft.setAircraftID(aircraftID);
                aircraft.setRegistration(rs.getString("registration"));
                aircraft.setTypeID(rs.getInt("typeID"));
                aircraft.setTypeName(rs.getString("name"));
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in load aircraft. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return aircraft;
    }

    public boolean update(AircraftDTO aircraft) {
        boolean check = false;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "UPDATE Aircraft"
                    + " SET registration = ?, typeID = ?"
                    + " WHERE aircraftID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, aircraft.getRegistration());
            stmt.setInt(2, aircraft.getTypeID());
            stmt.setInt(3, aircraft.getAircraftID());
            check = stmt.executeUpdate() > 0;
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in update aircraft. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return check;
    }

    public Integer insert(AircraftDTO aircraft) {
        Integer aircraftID = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "INSERT INTO Aircraft(registration, typeID) "
                    + " VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, aircraft.getRegistration());
            stmt.setInt(2, aircraft.getTypeID());

            if (stmt.executeUpdate() == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    aircraftID = rs.getInt(1);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in insert aircraft. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return aircraftID;
    }

    public boolean delete(int aircraftID) {
        boolean check = false;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "DELETE Aircraft"
                    + " WHERE aircraftID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, aircraftID);
            check = stmt.executeUpdate() > 0;
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in delete aircraft. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return check;
    }
}
