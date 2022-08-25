package com.the.demosimple;


public class PetsModel {
   String name;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getContet_url() {
        return contet_url;
    }

    String image;
    String date;

    public PetsModel(String name, String image, String date, String contet_url) {
        this.name = name;
        this.image = image;
        this.date = date;
        this.contet_url = contet_url;
    }

    String contet_url;

}

