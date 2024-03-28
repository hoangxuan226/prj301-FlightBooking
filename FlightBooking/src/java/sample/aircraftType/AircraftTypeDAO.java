/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.aircraftType;

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
public class AircraftTypeDAO {

    public List<AircraftTypeDTO> list(String keyword) {
        List<AircraftTypeDTO> list = new ArrayList<>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT typeID, name, numberBusinessSeats, numberEconomySeats"
                    + " FROM AircraftType";
            if (keyword != null && !keyword.isEmpty()) {
                sql += " WHERE name like ?";
            }
            PreparedStatement stmt = con.prepareStatement(sql);
            if (keyword != null && !keyword.isEmpty()) {
                stmt.setString(1, "%" + keyword + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AircraftTypeDTO type = new AircraftTypeDTO();
                type.setTypeID(rs.getInt("typeID"));
                type.setName(rs.getString("name"));
                type.setNumberBusinessSeats(rs.getInt("numberBusinessSeats"));
                type.setNumberEconomySeats(rs.getInt("numberEconomySeats"));
                list.add(type);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in search aircraft types. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

    public AircraftTypeDTO load(int typeID) {
        AircraftTypeDTO type = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT name, numberBusinessSeats, numberEconomySeats"
                    + " FROM AircraftType"
                    + " WHERE typeID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, typeID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                type = new AircraftTypeDTO();
                type.setTypeID(typeID);
                type.setName(rs.getString("name"));
                type.setNumberBusinessSeats(rs.getInt("numberBusinessSeats"));
                type.setNumberEconomySeats(rs.getInt("numberEconomySeats"));
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in load aircraft types. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return type;
    }

    public boolean update(AircraftTypeDTO type) {
        boolean check = false;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "UPDATE AircraftType"
                    + " SET name = ?, numberBusinessSeats = ?, numberEconomySeats = ?"
                    + " WHERE typeID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, type.getName());
            stmt.setInt(2, type.getNumberBusinessSeats());
            stmt.setInt(3, type.getNumberEconomySeats());
            stmt.setInt(4, type.getTypeID());

            check = stmt.executeUpdate() > 0;
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in upload aircraft types. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return check;
    }

    public Integer insert(AircraftTypeDTO type) {
        Integer typeID = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT max(typeID) AS maxID FROM AircraftType";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int maxID = 0;
            if (rs.next()) {
                maxID = rs.getInt("maxID");
            }
            sql = "INSERT INTO AircraftType(typeID, name, numberBusinessSeats, numberEconomySeats) "
                    + " VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maxID + 1);
            stmt.setString(2, type.getName());
            stmt.setInt(3, type.getNumberBusinessSeats());
            stmt.setInt(4, type.getNumberEconomySeats());

            if (stmt.executeUpdate() > 0) {
                typeID = maxID + 1;
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in insert aircraft types. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return typeID;
    }

    public boolean delete(int typeID) {
        boolean check = false;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "DELETE AircraftType"
                    + " WHERE typeID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, typeID);
            check = stmt.executeUpdate() > 0;
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in delete aircraft types. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return check;
    }

    public List<String> listBusinessSeats(AircraftTypeDTO aircraftType) {
        List<String> seats = new ArrayList<>();
        int numOfSeats = aircraftType.getNumberBusinessSeats();
        char[] arr = {'A', 'F', 'C', 'D'};

        for (int i = 0; i < numOfSeats;) {
            String code = String.format("%d%c", i / 4 + 1, arr[i % 4]);
            seats.add(code);
            i++;
        }
        return seats;
    }

    public List<String> listEconomySeats(AircraftTypeDTO aircraftType) {
        List<String> seats = new ArrayList<>();
        int numOfEco = aircraftType.getNumberEconomySeats();
        char[] arr = {'A', 'F', 'B', 'E', 'C', 'D'};

        int numOfBiz = aircraftType.getNumberBusinessSeats();
        int numOfBizRows = (int) Math.ceil(numOfBiz * 1.0 / 4);

        for (int i = 0; i < numOfEco;) {
            String code = String.format("%d%c", numOfBizRows + (i / 6) + 1, arr[i % 6]);
            seats.add(code);
            i++;
        }
        return seats;
    }

    public int countBusinessRows(AircraftTypeDTO aircraftType) {
        int numOfSeats = aircraftType.getNumberBusinessSeats();
        return (int) Math.ceil(numOfSeats * 1.0 / 4);
    }

    public int countEconomyRows(AircraftTypeDTO aircraftType) {
        int numOfSeats = aircraftType.getNumberEconomySeats();
        return (int) Math.ceil(numOfSeats * 1.0 / 6);
    }
}
