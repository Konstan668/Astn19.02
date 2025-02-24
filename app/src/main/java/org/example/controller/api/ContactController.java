package org.example.controller.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.Contact;
import org.example.service.ContactDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ContactController implements HttpHandler {
    private final ContactDAO contactDAO;

    public ContactController() throws SQLException {
        contactDAO = new ContactDAO();

    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "GET" -> handleGetRequest(exchange);
                case "POST" -> handlePostRequest(exchange);
                case "PUT" -> handlePutRequest(exchange);
                case "DELETE" -> handleDeleteRequest(exchange);
                default -> throw new SQLException("Unsupported method");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleGetRequest(HttpExchange exchange) throws SQLException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            int id = Integer.parseInt(query.split("=")[1]);
            Contact contact = contactDAO.getContact(id);
            System.out.println(contact);
        } else {
            List<Contact> contacts = contactDAO.getAllContacts();
            contacts.forEach(System.out::println);
        }
    }

    private void handlePostRequest(HttpExchange exchange) throws SQLException, IOException {
        String[] params = new String(exchange.getRequestBody().readAllBytes()).split("&");
        String name = params[1].split("=")[1];
        int phone = Integer.parseInt(params[2].split("=")[1]);
        Contact contact = new Contact(0, name, phone);
        contactDAO.createContact(contact);
        System.out.println("Contact created successfully");
    }

    private void handlePutRequest(HttpExchange exchange) throws SQLException, IOException {
        String[] params = new String(exchange.getRequestBody().readAllBytes()).split("&");
        int id = Integer.parseInt(params[0].split("=")[1]);
        String name = params[1].split("=")[1];
        int phone = Integer.parseInt(params[2].split("=")[1]);
        Contact contact = new Contact(id, name, phone);
        contactDAO.updateContact(contact);
        System.out.println("Contact updated successfully");
    }

    private void handleDeleteRequest(HttpExchange exchange) throws SQLException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            int id = Integer.parseInt(query.split("=")[1]);
            contactDAO.deleteContact(id);
            System.out.println("Contact deleted successfully");
        } else {
            System.out.println("Invalid request");
        }
    }
}
