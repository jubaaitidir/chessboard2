package com.echecs.api.model;



//import lombok.Data;
import javax.persistence.*;
//import javax.persistence.Table;
//import java.sql.Date;


//@JsonIgnoreProperties(value={"email","mdp"})

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue
    protected int id;

    @Column(nullable = false)
    private String nom;

    private String prenom;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String mdp;


    //constructeur par défaut
    public User(){

    }


    public User(int id, String nom,String prenom, String email, String mdp){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp=mdp;
    }

    public User(int id , String email, String mdp){

        this.id=id;
        this.email=email;
        this.mdp=mdp;
    }

    public User(int id){

        this.id=id;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
/*
    public Date getDate_nais() {
        return date_nais;
    }

    public void setDate_nais(Date date_nais) {
        this.date_nais = date_nais;
    }
*/
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                '}';
    }
}
