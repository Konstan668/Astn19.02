package org.example.service;

import org.example.model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private final Connection connection;

    public ContactDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    public void createContact(Contact contact) throws SQLException {
        String query = "INSERT INTO contacts (name, phone) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, contact.getName());
            statement.setInt(2, contact.getPhone());
            statement.executeUpdate();
        }
    }

    public Contact getContact(int id) throws SQLException {
        Contact contact = null;
        String query = "SELECT * FROM contacts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int phone = resultSet.getInt("phone");
                    contact = new Contact(id, name, phone);
                }
            }
        }
        return contact;
    }

    public List<Contact> getAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int phone = resultSet.getInt("phone");
                    contacts.add(new Contact(id, name, phone));
                }
            }
        }
        return contacts;
    }

    public void updateContact(Contact contact) throws SQLException {
        String query = "UPDATE contacts SET name = ?, phone = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, contact.getName());
            statement.setInt(2, contact.getPhone());
            statement.setInt(3, contact.getId());
            statement.executeUpdate();
        }
    }

    public void deleteContact(int id) throws SQLException {
        String query = "DELETE FROM contacts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
