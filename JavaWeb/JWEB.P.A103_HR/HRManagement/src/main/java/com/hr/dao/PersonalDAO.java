package com.hr.dao;

import com.hr.model.Personal;
import com.hr.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PersonalDAO {

    public boolean insert(Personal p) {
        String sql = "INSERT INTO Personal (first_name, last_name, gender, telephone, email, region, hobbies, description) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setString(3, p.getGender());
            ps.setString(4, nullIfEmpty(p.getTelephone()));
            ps.setString(5, nullIfEmpty(p.getEmail()));
            ps.setString(6, p.getRegion());
            ps.setString(7, nullIfEmpty(p.getHobbies()));
            ps.setString(8, nullIfEmpty(p.getDescription()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Personal> searchByFirstName(String firstName) {
        List<Personal> list = new ArrayList<>();
        String sql = "SELECT * FROM Personal WHERE first_name LIKE ? ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + firstName.trim() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Personal> getAll() {
        List<Personal> list = new ArrayList<>();
        String sql = "SELECT * FROM Personal ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Personal findById(int id) {
        String sql = "SELECT * FROM Personal WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Personal p) {
        String sql = "UPDATE Personal SET first_name=?, last_name=?, gender=?, telephone=?, " +
                     "email=?, region=?, hobbies=?, description=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setString(3, p.getGender());
            ps.setString(4, nullIfEmpty(p.getTelephone()));
            ps.setString(5, nullIfEmpty(p.getEmail()));
            ps.setString(6, p.getRegion());
            ps.setString(7, nullIfEmpty(p.getHobbies()));
            ps.setString(8, nullIfEmpty(p.getDescription()));
            ps.setInt(9, p.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Personal WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Personal mapRow(ResultSet rs) throws SQLException {
        Personal p = new Personal();
        p.setId(rs.getInt("id"));
        p.setFirstName(rs.getString("first_name"));
        p.setLastName(rs.getString("last_name"));
        p.setGender(rs.getString("gender"));
        p.setTelephone(rs.getString("telephone"));
        p.setEmail(rs.getString("email"));
        p.setRegion(rs.getString("region"));
        p.setHobbies(rs.getString("hobbies"));
        p.setDescription(rs.getString("description"));
        return p;
    }

    private String nullIfEmpty(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}
