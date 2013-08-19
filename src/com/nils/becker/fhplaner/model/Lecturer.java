package com.nils.becker.fhplaner.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nils on 8/18/13.
 */
public class Lecturer {

    private String dozentkuerzel;
    private int fb_nr;
    private String vorname;
    private String nachname;
    private String anrede;
    private String titel;
    private String funktion;
    private String tel_intern;
    private String email;
    private String homepage;
    private int raum_kz;

    public Lecturer(String dozentkuerzel, int fb_nr, String vorname, String nachname, String anrede, String titel, String funktion, String tel_intern, String email, String homepage, int raum_kz) {
        this.dozentkuerzel = dozentkuerzel;
        this.fb_nr = fb_nr;
        this.vorname = vorname;
        this.nachname = nachname;
        this.anrede = anrede;
        this.titel = titel;
        this.funktion = funktion;
        this.tel_intern = tel_intern;
        this.email = email;
        this.homepage = homepage;
        this.raum_kz = raum_kz;
    }

    public String getDozentkuerzel() {
        return dozentkuerzel;
    }

    public void setDozentkuerzel(String dozentkuerzel) {
        this.dozentkuerzel = dozentkuerzel;
    }

    public int getFb_nr() {
        return fb_nr;
    }

    public void setFb_nr(int fb_nr) {
        this.fb_nr = fb_nr;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getFunktion() {
        return funktion;
    }

    public void setFunktion(String funktion) {
        this.funktion = funktion;
    }

    public String getTel_intern() {
        return tel_intern;
    }

    public void setTel_intern(String tel_intern) {
        this.tel_intern = tel_intern;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getRaum_kz() {
        return raum_kz;
    }

    public void setRaum_kz(int raum_kz) {
        this.raum_kz = raum_kz;
    }

    public Lecturer(JSONObject jsonObject) {
        try {
            this.dozentkuerzel = jsonObject.getString("dozentkuerzel");
            this.fb_nr = jsonObject.getInt("fb_nr");
            this.vorname = jsonObject.getString("vorname");
            this.nachname = jsonObject.getString("nachname");
            this.anrede = jsonObject.getString("anrede");
            this.titel = jsonObject.getString("titel");
            this.funktion = jsonObject.getString("funktion");
            this.tel_intern = jsonObject.getString("tel_intern");
            this.email = jsonObject.getString("email");
            this.homepage = jsonObject.getString("homepage");
            this.raum_kz = jsonObject.getInt("raum_kz");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
