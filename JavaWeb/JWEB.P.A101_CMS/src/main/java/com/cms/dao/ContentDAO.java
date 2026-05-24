package com.cms.dao;

import com.cms.model.Content;
import com.cms.utils.DBconnection;
import com.microsoft.sqlserver.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ContentDAO {

    public List<Content> findAll() {
        String sql = "select * from Content";
        List<Content> list = new ArrayList<>();
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                Content content = new Content();
                content.setContent(rs.getString("Content"));
                content.setBrief(rs.getString("Brief"));
                content.setTitle(rs.getString("Title"));

                list.add(content);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public boolean create(Content content) {
        String sql = "insert into Content (Content, Brief, Title, AuthorId) values (?, ?, ?, ?)";
        try(Connection conn = DBconnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            ps.setString(1, content.getContent());
            ps.setString(2, content.getBrief());
            ps.setString(3, content.getTitle());
            ps.setInt(4, content.getAuthorID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Content> search(String keyword) throws SQLException {
        String sql = "select * from Content where Title like ? or Brief like ?";
        String like = "%" + keyword + "%";
        List<Content> list = new ArrayList<>();
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Content content = new Content();
                content.setId(rs.getInt("id"));
                content.setContent(rs.getString("Content"));
                content.setBrief(rs.getString("Brief"));
                content.setTitle(rs.getString("Title"));
                content.setCreateDate(rs.getObject("CreateDate", LocalDateTime.class));
                list.add(content);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean delete(int id, int authorId) throws SQLException {
        String sql = "DELETE FROM Content WHERE id = ? AND AuthorId = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, authorId);

            int rowsDeleted = ps.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean update(Content content) {
        String sql = "UPDATE Content SET Title = ?, Brief = ?, Content = ? " +
                "WHERE id = ? AND AuthorId = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, content.getTitle());
            ps.setString(2, content.getBrief());
            ps.setString(3, content.getContent());
            ps.setInt(4, content.getId());
            ps.setInt(5, content.getAuthorID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Content findById(int id) throws SQLException {
        String sql = "SELECT * FROM Content WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Content c = new Content();
                c.setId(rs.getInt("id"));
                c.setTitle(rs.getString("Title"));
                c.setBrief(rs.getString("Brief"));
                c.setContent(rs.getString("Content"));
                return c;
            }
        }
        return null;
    }
}
