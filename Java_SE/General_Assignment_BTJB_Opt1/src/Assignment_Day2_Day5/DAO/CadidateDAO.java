package Assignment_Day2_Day5.DAO;

import Assignment_Day2_Day5.Common.DbConnection;
import Assignment_Day2_Day5.Entities.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CadidateDAO {
    private static final Logger infoLogger = Logger.getLogger("infoFile");
    private static final Logger errorLogger = Logger.getLogger("errorFile");

    public boolean isIDExist(String id) throws SQLException {
        String sql = "SELECT 1 FROM Candidate WHERE CandidateId = ?";
        infoLogger.info("Query: " + sql + " | Params: " + id);
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            errorLogger.error("Exception in isIDExist: " + e.getMessage(), e);
            throw e;
        }
    }

    public void insertCandidate(Candidate c) throws SQLException {
        String sql = "INSERT INTO Candidate(CandidateId, FullName, BirthDay, Phone, Email, Candidate_type) VALUES(?, ?, ?, ?, ?, ?)";
        infoLogger.info("Query: " + sql + " | Params: " + c.getCandidateID());
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCandidateID());
            ps.setString(2, c.getFullName());
            ps.setString(3, c.getBirthDay());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getEmail());
            ps.setInt(6, c.getCandidateType().getValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            errorLogger.error("Exception in insertCandidate: " + e.getMessage(), e);
            throw e;
        }
    }

    public void insertExtraInfo(Candidate c) throws SQLException {
        try {
            if (c instanceof Experience exp) {
                String sql = "INSERT INTO Experience(CandidateId, ExpInYear, ProSkill) VALUES(?, ?, ?)";
                infoLogger.info("Query: " + sql + " | Params: " + exp.getCandidateID());
                try (Connection con = DbConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, exp.getCandidateID());
                    ps.setInt(2, exp.getExpInYear());
                    ps.setString(3, exp.getProSkill());
                    ps.executeUpdate();
                }
            } else if (c instanceof Fresher f) {
                String sql = "INSERT INTO Fresher(CandidateId, Graduation_date, Graduation_rank, Education) VALUES(?, ?, ?, ?)";
                infoLogger.info("Query: " + sql + " | Params: " + f.getCandidateID());
                try (Connection con = DbConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, f.getCandidateID());
                    ps.setString(2, f.getGraduationDate());
                    ps.setString(3, f.getGraduationRank());
                    ps.setString(4, f.getEducation());
                    ps.executeUpdate();
                }
            } else if (c instanceof Intern i) {
                String sql = "INSERT INTO Intern(CandidateId, Majors, Semester, University_name) VALUES(?, ?, ?, ?)";
                infoLogger.info("Query: " + sql + " | Params: " + i.getCandidateID());
                try (Connection con = DbConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, i.getCandidateID());
                    ps.setString(2, i.getMajors());
                    ps.setInt(3, i.getSemester());
                    ps.setString(4, i.getUniversityName());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            errorLogger.error("Exception in insertExtraInfo: " + e.getMessage(), e);
            throw e;
        }
    }

    public List<Candidate> getAllCandidates() throws SQLException {
        List<Candidate> list = new ArrayList<>();
        String sql = """
                SELECT c.*, e.ExpInYear, e.ProSkill,
                       f.Graduation_date, f.Graduation_rank, f.Education,
                       i.Majors, i.Semester, i.University_name
                FROM Candidate c
                LEFT JOIN Experience e ON c.CandidateId = e.CandidateId
                LEFT JOIN Fresher f    ON c.CandidateId = f.CandidateId
                LEFT JOIN Intern i     ON c.CandidateId = i.CandidateId
                """;
        infoLogger.info("Query: " + sql.trim());
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("CandidateId");
                String name = rs.getString("FullName");
                String dob = rs.getString("BirthDay");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                int type = rs.getInt("Candidate_type");

                Candidate c = switch (type) {
                    case 0 -> new Experience(id, name, dob, phone, email,
                            rs.getInt("ExpInYear"), rs.getString("ProSkill"));
                    case 1 -> new Fresher(id, name, dob, phone, email,
                            rs.getString("Graduation_date"), rs.getString("Graduation_rank"), rs.getString("Education"));
                    case 2 -> new Intern(id, name, dob, phone, email,
                            rs.getString("Majors"), rs.getInt("Semester"), rs.getString("University_name"));
                    default -> null;
                };

                if (c != null) list.add(c);
            }
        } catch (SQLException e) {
            errorLogger.error("Exception in getAllCandidates: " + e.getMessage(), e);
            throw e;
        }
        return list;
    }

    public void insertCandidateUpdatable(Candidate c) throws SQLException {
        String sql = "SELECT * FROM Candidate WHERE 1=0";
        infoLogger.info("InsertUpdatable Candidate: " + c.getCandidateID());
        try {
            try (Connection con = DbConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql,
                         ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                 ResultSet rs = ps.executeQuery()) {
                rs.moveToInsertRow();
                rs.updateString("CandidateId", c.getCandidateID());
                rs.updateString("FullName", c.getFullName());
                rs.updateString("BirthDay", c.getBirthDay());
                rs.updateString("Phone", c.getPhone());
                rs.updateString("Email", c.getEmail());
                rs.updateInt("Candidate_type", c.getCandidateType().getValue());
                rs.insertRow();
            }

            if (c instanceof Experience exp) {
                String sql2 = "SELECT * FROM Experience WHERE 1=0";
                try (Connection con = DbConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql2,
                             ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                     ResultSet rs = ps.executeQuery()) {
                    rs.moveToInsertRow();
                    rs.updateString("CandidateId", exp.getCandidateID());
                    rs.updateInt("ExpInYear", exp.getExpInYear());
                    rs.updateString("ProSkill", exp.getProSkill());
                    rs.insertRow();
                }
            } else if (c instanceof Fresher f) {
                String sql2 = "SELECT * FROM Fresher WHERE 1=0";
                try (Connection con = DbConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql2,
                             ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                     ResultSet rs = ps.executeQuery()) {
                    rs.moveToInsertRow();
                    rs.updateString("CandidateId", f.getCandidateID());
                    rs.updateString("Graduation_date", f.getGraduationDate());
                    rs.updateString("Graduation_rank", f.getGraduationRank());
                    rs.updateString("Education", f.getEducation());
                    rs.insertRow();
                }
            } else if (c instanceof Intern i) {
                String sql2 = "SELECT * FROM Intern WHERE 1=0";
                try (Connection con = DbConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql2,
                             ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                     ResultSet rs = ps.executeQuery()) {
                    rs.moveToInsertRow();
                    rs.updateString("CandidateId", i.getCandidateID());
                    rs.updateString("Majors", i.getMajors());
                    rs.updateInt("Semester", i.getSemester());
                    rs.updateString("University_name", i.getUniversityName());
                    rs.insertRow();
                }
            }
        } catch (SQLException e) {
            errorLogger.error("Exception in insertCandidateUpdatable: " + e.getMessage(), e);
            throw e;
        }
    }

    public void updateCandidateUpdatable(Candidate c) throws SQLException {
        infoLogger.info("UpdateUpdatable Candidate: " + c.getCandidateID());
        try {
            String sql = "SELECT * FROM Candidate WHERE CandidateId = ?";
            try (Connection con = DbConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql,
                         ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ps.setString(1, c.getCandidateID());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        rs.updateString("FullName", c.getFullName());
                        rs.updateString("BirthDay", c.getBirthDay());
                        rs.updateString("Phone", c.getPhone());
                        rs.updateString("Email", c.getEmail());
                        rs.updateRow();
                    }
                }
            }

            if (c instanceof Experience exp) {
                String sql2 = "SELECT * FROM Experience WHERE CandidateId = ?";
                try (Connection con = DbConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql2,
                             ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    ps.setString(1, exp.getCandidateID());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            rs.updateInt("ExpInYear", exp.getExpInYear());
                            rs.updateString("ProSkill", exp.getProSkill());
                            rs.updateRow();
                        }
                    }
                }
            } else if (c instanceof Fresher f) {
                String sql2 = "SELECT * FROM Fresher WHERE CandidateId = ?";
                try (Connection con = DbConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql2,
                             ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    ps.setString(1, f.getCandidateID());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            rs.updateString("Graduation_date", f.getGraduationDate());
                            rs.updateString("Graduation_rank", f.getGraduationRank());
                            rs.updateString("Education", f.getEducation());
                            rs.updateRow();
                        }
                    }
                }
            } else if (c instanceof Intern i) {
                String sql2 = "SELECT * FROM Intern WHERE CandidateId = ?";
                try (Connection con = DbConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql2,
                             ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    ps.setString(1, i.getCandidateID());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            rs.updateString("Majors", i.getMajors());
                            rs.updateInt("Semester", i.getSemester());
                            rs.updateString("University_name", i.getUniversityName());
                            rs.updateRow();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            errorLogger.error("Exception in updateCandidateUpdatable: " + e.getMessage(), e);
            throw e;
        }
    }
}