/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author phamx
 */
public class FlightDAO {

    public List<FlightDTO> findFlights(String from, String to, String date) {
        List<FlightDTO> list = new ArrayList<>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT flightID, flightNumber, aircraftID, departureTime, durationInMinutes, economyPrice, businessPrice, remainingEconomySeats, remainingBusinessSeats"
                    + " FROM Flight"
                    + " WHERE [from] = ? AND [to] = ? AND departureDate = ?"
                    + " AND CAST(departureDate AS DATETIME) + CAST(departureTime AS DATETIME) > DATEADD(HOUR, 3, GETDATE())";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, from);
            stmt.setString(2, to);
            stmt.setString(3, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FlightDTO flight = new FlightDTO();

                LocalDate departureDate = LocalDate.parse(date);
                LocalTime departureTime = rs.getTime("departureTime").toLocalTime();
                int durationInMinutes = rs.getInt("durationInMinutes");

                LocalDateTime departureDateTime = LocalDateTime.of(departureDate, departureTime);
                LocalDateTime arrivalDateTime = departureDateTime.plusMinutes(durationInMinutes);

                LocalDate arrivalDate = arrivalDateTime.toLocalDate();
                LocalTime arrivalTime = arrivalDateTime.toLocalTime();
                long daysDifference = departureDate.until(arrivalDate, ChronoUnit.DAYS);

                flight.setFlightID(rs.getInt("flightID"));
                flight.setFlightNumber(rs.getString("flightNumber"));
                flight.setAircraftID(rs.getInt("aircraftID"));
                flight.setFrom(from);
                flight.setTo(to);
                flight.setDepartureDate(departureDate);
                flight.setDepartureTime(departureTime);
                flight.setDuration(durationInMinutes);
                flight.setArrivalDate(arrivalDate);
                flight.setArrivalTime(arrivalTime);
                flight.setDaysDifference(daysDifference);
                flight.setEconomyPrice(rs.getInt("economyPrice"));
                flight.setBusinessPrice(rs.getInt("businessPrice"));
                flight.setRemainingEconomySeats(rs.getInt("remainingEconomySeats"));
                flight.setRemainingBusinessSeats(rs.getInt("remainingBusinessSeats"));
                list.add(flight);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in findFlights. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

    public List<FlightDTO> list(String keyword) {
        List<FlightDTO> list = new ArrayList<>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT flightID, flightNumber, aircraftID, [from], [to], departureDate, departureTime, durationInMinutes, economyPrice, businessPrice, remainingEconomySeats, remainingBusinessSeats"
                    + " FROM Flight";
            if (keyword != null && !keyword.isEmpty()) {
                sql += " WHERE flightNumber like ? OR [from] like ? OR [to] like ? OR departureDate like ?";
            }
            PreparedStatement stmt = con.prepareStatement(sql);
            if (keyword != null && !keyword.isEmpty()) {
                stmt.setString(1, "%" + keyword + "%");
                stmt.setString(2, "%" + keyword + "%");
                stmt.setString(3, "%" + keyword + "%");
                stmt.setString(4, "%" + keyword + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FlightDTO flight = new FlightDTO();
                flight.setFlightID(rs.getInt("flightID"));
                flight.setFlightNumber(rs.getString("flightNumber"));
                flight.setAircraftID(rs.getInt("aircraftID"));
                flight.setFrom(rs.getString("from"));
                flight.setTo(rs.getString("to"));
                flight.setDepartureDate(rs.getDate("departureDate").toLocalDate());
                flight.setDepartureTime(rs.getTime("departureTime").toLocalTime());
                flight.setDuration(rs.getInt("durationInMinutes"));
                flight.setEconomyPrice(rs.getInt("economyPrice"));
                flight.setBusinessPrice(rs.getInt("businessPrice"));
                flight.setRemainingEconomySeats(rs.getInt("remainingEconomySeats"));
                flight.setRemainingBusinessSeats(rs.getInt("remainingBusinessSeats"));
                list.add(flight);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in list flights. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }

    public FlightDTO load(int flightID) {
        FlightDTO flight = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT A.flightID, A.flightNumber, A.aircraftID, B.registration, A.[from], A.[to], A.departureDate, A.departureTime, A.durationInMinutes, A.economyPrice, A.businessPrice, A.remainingEconomySeats, A.remainingBusinessSeats"
                    + " FROM Flight A JOIN Aircraft B ON A.aircraftID = B.aircraftID"
                    + " WHERE flightID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, flightID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                flight = new FlightDTO();

                LocalDate departureDate = rs.getDate("departureDate").toLocalDate();
                LocalTime departureTime = rs.getTime("departureTime").toLocalTime();
                int durationInMinutes = rs.getInt("durationInMinutes");

                LocalDateTime departureDateTime = LocalDateTime.of(departureDate, departureTime);
                LocalDateTime arrivalDateTime = departureDateTime.plusMinutes(durationInMinutes);

                LocalDate arrivalDate = arrivalDateTime.toLocalDate();
                LocalTime arrivalTime = arrivalDateTime.toLocalTime();
                long daysDifference = departureDate.until(arrivalDate, ChronoUnit.DAYS);

                flight.setFlightID(rs.getInt("flightID"));
                flight.setFlightNumber(rs.getString("flightNumber"));
                flight.setAircraftID(rs.getInt("aircraftID"));
                flight.setAircraftReg(rs.getString("registration"));
                flight.setFrom(rs.getString("from"));
                flight.setTo(rs.getString("to"));
                flight.setDepartureDate(departureDate);
                flight.setDepartureTime(departureTime);
                flight.setDuration(durationInMinutes);
                flight.setArrivalDate(arrivalDate);
                flight.setArrivalTime(arrivalTime);
                flight.setDaysDifference(daysDifference);
                flight.setEconomyPrice(rs.getInt("economyPrice"));
                flight.setBusinessPrice(rs.getInt("businessPrice"));
                flight.setRemainingEconomySeats(rs.getInt("remainingEconomySeats"));
                flight.setRemainingBusinessSeats(rs.getInt("remainingBusinessSeats"));
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in load flight. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return flight;
    }

    public boolean update(FlightDTO flight) {
        boolean check = false;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "UPDATE Flight"
                    + " SET flightNumber = ?, aircraftID = ?, [from] = ?, [to] = ?, departureDate = ?, departureTime = ?, durationInMinutes = ?, economyPrice = ?, businessPrice = ?"
                    + " WHERE flightID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, flight.getFlightNumber());
            stmt.setInt(2, flight.getAircraftID());
            stmt.setString(3, flight.getFrom());
            stmt.setString(4, flight.getTo());
            stmt.setString(5, flight.getDepartureDate().toString());
            stmt.setString(6, flight.getDepartureTime().toString());
            stmt.setInt(7, flight.getDuration());
            stmt.setInt(8, flight.getEconomyPrice());
            stmt.setInt(9, flight.getBusinessPrice());
            stmt.setInt(10, flight.getFlightID());

            check = stmt.executeUpdate() > 0;
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in update flight. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return check;
    }

    public Integer insert(FlightDTO flight) {
        Integer flightID = null;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "INSERT INTO Flight(flightNumber, aircraftID, [from], [to], departureDate, departureTime, durationInMinutes, economyPrice, businessPrice) "
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, flight.getFlightNumber());
            stmt.setInt(2, flight.getAircraftID());
            stmt.setString(3, flight.getFrom());
            stmt.setString(4, flight.getTo());
            stmt.setString(5, flight.getDepartureDate().toString());
            stmt.setString(6, flight.getDepartureTime().toString());
            stmt.setInt(7, flight.getDuration());
            stmt.setInt(8, flight.getEconomyPrice());
            stmt.setInt(9, flight.getBusinessPrice());

            if (stmt.executeUpdate() == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    flightID = rs.getInt(1);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in insert flight. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return flightID;
    }

    public boolean delete(int flightID) {
        boolean check = false;
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "DELETE Flight"
                    + " WHERE flightID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, flightID);
            check = stmt.executeUpdate() > 0;
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in delete flight. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return check;
    }

    public List<String> listOccupiredSeats(int flightID, String type) {
        List<String> list = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            String sql = "SELECT B.seat"
                    + " FROM Booking A JOIN BookingDetail B ON A.bookingID = B.bookingID"
                    + " WHERE A.flightID = ? AND B.seatType = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, flightID);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String seat = rs.getString("seat");
                list.add(seat);
            }
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error in list occupired seats. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }
}
