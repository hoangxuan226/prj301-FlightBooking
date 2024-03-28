/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.admins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sample.utils.DBUtils;

/**
 *
 * @author phamx
 */
public class AdminDAO {

    public AdminDTO checkLogin(String username, String password) {
        AdminDTO admin = null;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT name"
                    + " FROM Admins"
                    + " WHERE username = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                admin = new AdminDTO();
                admin.setName(rs.getString("name"));
                admin.setUsername(username);
                admin.setPassword(password);
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in checkLoginAdmin. Details:" + ex.getMessage());
            ex.printStackTrace();
        }
        return admin;
    }
}
