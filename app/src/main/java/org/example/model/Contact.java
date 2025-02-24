package org.example.model;

public class Contact {
    private int id;
    private String name;
    private int phone;

    public Contact(int id, String name, int phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Abonent{");
        stringBuilder.append("id = ").append(id);
        stringBuilder.append(", name = ").append(name).append('\'');
        stringBuilder.append(", phone = ").append(phone);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
