package com.example.maintpro.client;

public class ClientData {

    private String name,email,image,complaint,key;

    public ClientData() {
    }

    public ClientData(String name, String email, String image,String complaint, String key) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.complaint = complaint;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
