/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd_project;

import messages.MessageImpl;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

/**
 * Classe permettant de prendre le controle d'une machine distante
 * Sert de serveur
 * @author karakayn
 */
public class Control 
{
    /**
     * Liste des clients actuellement connectés à notre système
     */
    private int idServeur;
    private ArrayList<Client> clients;
    private int port; //port sur lequel est lancé le serveur
    public Control(int port) throws UnknownHostException
    {
        this.port = port;
        System.setProperty("java.security.policy","file:./security.policy");
        this.run_server(this.port);
    }
    
    /**
     * Permet de lancer le serveur
     * @param port : port sur lequel lancer le serveur
     */
    private void run_server(int port) throws UnknownHostException
    {
        try 
        {
            System.out.println("********* Lancement du serveur sur le port : "+port+"*********");
            LocateRegistry.createRegistry(port);

            System.out.println("Mise en place du Security Manager ...");

            if (System.getSecurityManager() == null) {

                System.setSecurityManager(new SecurityManager());

            }

            MessageImpl localMessageObject = new MessageImpl();

            String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/Message";

            System.out.println("Enregistrement de l'objet avec l'url : " + url);

            Naming.rebind(url, localMessageObject);

            System.out.println("Serveur lancé");

        } catch (RemoteException e) {

            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Fonction permettant d'établir la connexion
     * entre deux ordinateurs
     * @param client : le client a connecter
     * @return : un booleen qui vaut true si la connexion a reussi, false sinon
    */
    public boolean connect_gui(Client client)
    {
        return false;
    }
    
    /**
     * Fonction qui permet de rompre la connexion
     * Un message est envoyé à l'autre ordinateur pour signaler cette deconnexion
     * @param client: le client a deconnecter
     * @return : true si la deconnexion a réussi, false sinon
     */
    public boolean deconnect_gui(Client client)
    {
        return false;
    }
    
}
