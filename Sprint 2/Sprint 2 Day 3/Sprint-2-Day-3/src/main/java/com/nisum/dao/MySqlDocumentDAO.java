package com.nisum.dao;

import com.nisum.model.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * MySQL implementation of the DocumentDAO interface.
 * Follows SRP by focusing only on data access logic for Document entities.
 * Follows OCP by implementing the DocumentDAO interface, allowing for easy substitution.
 */
public class MySqlDocumentDAO implements DocumentDAO {

    private DataSource dataSource;

    // Constructor injection (Dependency Inversion Principle)
    public MySqlDocumentDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Document save(Document document) {
        String sql = "INSERT INTO documents (title, content, created_date, last_modified_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, document.getTitle());
            ps.setString(2, document.getContent());
            ps.setTimestamp(3, new Timestamp(document.getCreatedDate().getTime()));
            ps.setTimestamp(4, new Timestamp(document.getLastModifiedDate().getTime()));

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating document failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    document.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating document failed, no ID obtained.");
                }
            }

            return document;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Document findById(Long id) {
        String sql = "SELECT * FROM documents WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractDocumentFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Document> findAll() {
        String sql = "SELECT * FROM documents";
        List<Document> documents = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                documents.add(extractDocumentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }

    @Override
    public Document update(Document document) {
        String sql = "UPDATE documents SET title = ?, content = ?, last_modified_date = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, document.getTitle());
            ps.setString(2, document.getContent());
            ps.setTimestamp(3, new Timestamp(document.getLastModifiedDate().getTime()));
            ps.setLong(4, document.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating document failed, no rows affected.");
            }

            return document;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM documents WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Document> findByTitle(String title) {
        String sql = "SELECT * FROM documents WHERE title LIKE ?";
        List<Document> documents = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + title + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    documents.add(extractDocumentFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }

    // Helper method to extract a Document from a ResultSet (DRY principle)
    private Document extractDocumentFromResultSet(ResultSet rs) throws SQLException {
        return new Document(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getTimestamp("created_date"),
            rs.getTimestamp("last_modified_date")
        );
    }
}
