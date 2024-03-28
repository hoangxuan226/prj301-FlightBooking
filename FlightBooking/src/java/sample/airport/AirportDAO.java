package sample.airport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author phamx
 */
public class AirportDAO {

    public List<AirportDTO> list() {
        List<AirportDTO> list = new ArrayList<>();

        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT airportCode, name, city"
                    + " FROM Airport";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AirportDTO airport = new AirportDTO();
                airport.setAirportCode(rs.getString("airportCode"));
                airport.setName(rs.getString("name"));
                airport.setCity(rs.getString("city"));
                list.add(airport);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in getAllAirports. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

    public AirportDTO load(String airportCode) {
        AirportDTO airport = null;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT name, city"
                    + " FROM Airport"
                    + " WHERE airportCode = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, airportCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                airport = new AirportDTO();
                airport.setAirportCode(airportCode);
                airport.setName(rs.getString("name"));
                airport.setCity(rs.getString("city"));
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in getAirport. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return airport;
    }
}
