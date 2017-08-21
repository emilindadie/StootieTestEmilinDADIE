package com.example.emili.stootietestemilindadie.donnee;


/**
 * Created by emili on 09/08/2017.
 */

public class Stoot {

    private long id;
    private String prenom;

    private String nom;

    private String adresse;

    private int prix;

    private float distance;

    private String duration;
    private double longitude;
    private double latitude;

    private String urlPhotoProfil;

    public Stoot(long id, String prenom, String nom, String adresse, double longitude, double latitude, int prix, float distance, String duration){

        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
        this.longitude = longitude;
        this.latitude = latitude;
        this.prix = prix;
        this.distance = distance;
        this.duration = duration;


    }

    public Stoot(long id, String prenom, String nom, String adresse, double longitude, double latitude, int prix, float distance , String duration, String urlPhotoProfil){

        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
        this.longitude = longitude;
        this.latitude = latitude;
        this.prix = prix;
        this.distance = distance;
        this.duration = duration;
        this.urlPhotoProfil = urlPhotoProfil;

    }


    public String getUrlPhotoProfil(){
        return urlPhotoProfil;
    }

    public float getDistance(){

        return distance;
    }


    public String getDuration(){

        return duration;
    }

    public String getPrenom(){

        return prenom;
    }

    public String getNom(){

        return nom;
    }


    public String getAdresse(){

        return  adresse;
    }


    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }
    public int getPrix(){

        return prix;
    }

    public long getId(){
        return id;
    }

}
