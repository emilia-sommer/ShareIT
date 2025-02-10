package com.example.shareit;

public class  Angebot {

    private String angebot_name;
    private String angebot_beschreibung;
    private String person_name;
    private String person_id;
    private String favorisiert;
    private String benutzername;
    private String angebot_id;

    // Konstruktor
    public Angebot(String angebot_name, String angebot_beschreibung, String person_name, String person_id, String favorisiert, String benutzername, String angebot_id) {
        this.angebot_name = angebot_name;
        this.angebot_beschreibung = angebot_beschreibung;
        this.person_name = person_name;
        this.person_id = person_id;
        this.favorisiert = favorisiert;
        this.benutzername = benutzername;
        this.angebot_id = angebot_id;
    }

    public String getAngebot_name() {
        return angebot_name;
    }

    public void setAngebot_name(String angebot_name) {
        this.angebot_name = angebot_name;
    }

    public String getAngebot_beschreibung() {
        return angebot_beschreibung;
    }

    public void setAngebot_beschreibung(String angebot_beschreibung) {
        this.angebot_beschreibung = angebot_beschreibung;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getFavorisiert() {
        return favorisiert;
    }

    public void setFavorisiert(String favorisiert) {
        this.favorisiert = favorisiert;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getAngebot_id() {
        return angebot_id;
    }

    public void setAngebot_id(String angebot_id) {
        this.angebot_id = angebot_id;
    }

    @Override
    public String toString() {
        return "Angebot{" +
                "angebot_name='" + angebot_name + '\'' +
                ", angebot_beschreibung='" + angebot_beschreibung + '\'' +
                ", person_name='" + person_name + '\'' +
                ", person_id='" + person_id + '\'' +
                ", favorisiert='" + favorisiert + '\'' +
                ", benutzername='" + benutzername + '\'' +
                ", angebot_id='" + angebot_id + '\'' +
                '}';
    }
}