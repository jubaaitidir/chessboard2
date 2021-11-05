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
    public User( String nom,String prenom, String email, String mdp){
   
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


    
    /** 
     * @return int
     */
    public int getId() {
        return id;
    }

    
    /** 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    
    /** 
     * @return String
     */
    public String getNom() {
        return nom;
    }

    
    /** 
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    /** 
     * @return String
     */
    public String getPrenom() {
        return prenom;
    }

    
    /** 
     * @param prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    
    /** 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    
    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

/** 
 * @return String
 */
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

    
    /** 
     * @param mdp
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    
    /** 
     * @return String
     */
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
