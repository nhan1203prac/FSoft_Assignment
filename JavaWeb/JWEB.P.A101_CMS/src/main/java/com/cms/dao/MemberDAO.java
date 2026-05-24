package com.cms.dao;

import com.cms.model.Member;
import com.cms.utils.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MemberDAO {

    public boolean checkExist(String username, String email) {
        if ((username == null || username.isEmpty()) && (email == null || email.isEmpty())) {
            return false;
        }

        String sql = "SELECT id FROM Member WHERE Username = ? OR Email = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean register(Member member) {
        String sql = "INSERT INTO Member (Username, Email, Password) "
                + "VALUES (?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            LocalDateTime now = LocalDateTime.now();

            ps.setString(1, member.getUsername());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPassword());

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Member login(String email, String password) {
        String sql = "SELECT * FROM Member WHERE Email = ? AND Password = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Member m = new Member();
                    m.setId(rs.getInt("id"));
                    m.setUsername(rs.getString("Username"));
                    m.setEmail(rs.getString("Email"));
                    return m;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProfile(Member member) throws SQLException {
        String sql = "UPDATE Member SET FirstName = ?, LastName = ?, Phone = ?, Description = ? WHERE Email = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getFirstName());
            ps.setString(2, member.getLastName());
            ps.setString(3, member.getPhone());
            ps.setString(4, member.getDescription());
            ps.setString(5, member.getEmail());
            return ps.executeUpdate() > 0;
        }
    }
}
