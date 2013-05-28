package com.pse.trainingappdroid;


public class Login
{
	//private variables
    int _idUser;
    String _user;
    String _password;
     
    // Empty constructor
    public Login(){
         
    }
    
    //constructor
    public Login(int idUser, String user, String password){
        this._idUser = idUser;
        this._user = user;
        this._password = password;
    }
     
    //constructor
    public Login(String user, String password){
        this._user = user;
        this._password = password;
    }
    //getting IdUser
    public int getIdUser(){
        return this._idUser;
    }
     
    //setting IdUser
    public void setIdUser(int idUser){
        this._idUser = idUser;
	
    }
    
    //getting user
    public String getUser(){
        return this._user;
    }
     
    //setting user
    public void setUser(String user){
        this._user = user;
    }
     
    //getting password
    public String getPassword(){
        return this._password;
    }
     
    //setting password
    public void setPassword(String password){
        this._password = password;
    }
}
