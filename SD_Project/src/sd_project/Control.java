/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd_project;

/**
 * Classe permettant de prendre le controle d'une machine distante
 * @author karakayn
 */
public class Control 
{
    public Control()
    {
        
    }
    
    /**
     * Fonction permettant d'établir la connexion
     * entre deux ordinateurs
     * @param :
     * @return : un booleen qui vaut true si la connexion a reussi, false sinon
    */
    public boolean connect_gui()
    {
        return false;
    }
    
    /**
     * Fonction qui permet de rompre la connexion
     * Un message est envoyé à l'autre ordinateur pour signaler cette deconnexion
     * @return : true si la deconnexion a réussi, false sinon
     */
    public boolean deconnect_gui()
    {
        return false;
    }
}
