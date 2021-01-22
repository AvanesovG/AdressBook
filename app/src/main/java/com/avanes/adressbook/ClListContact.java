package com.avanes.adressbook;

public class ClListContact {

    String id, name, number, img;

    public ClListContact(String id, String name, String number, String img) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String compareTo(ClListContact d) {
        return this.name = d.getName();
    }
}
