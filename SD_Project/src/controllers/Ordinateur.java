/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.I_MethodesDist;
import messages.MethodesDist;

/**
 * Class représentant un ordinateur : Une partie sender : qui est envoi tout ce
 * qui est fait sur cette ordi Une partie receiver : qui est en fait ce qui
 * recoit les informations sur les différents évenements à gerer
 *
 * @author karakayn
 */
public class Ordinateur {

    private int id;
    private I_MethodesDist receiver;
    private Sender sender;
    private ArrayList<Integer> portDecouteOccupes = new ArrayList<Integer>(); //not used, servira a bind plusieurs port

    public Ordinateur(Sender sender) {
        this.sender = sender;
        System.out.println("sender ; "+sender);
        System.setProperty("java.security.policy", "file:./security.policy");
        int port;
        System.out.println("Entrez le port de lancement du serveur");
        Scanner scan = new Scanner(System.in);
        port = Integer.parseInt(scan.nextLine());
        portDecouteOccupes.add(port);
        try {
            this.run_server(port);
        } catch (UnknownHostException | MalformedURLException ex) {
            Logger.getLogger(Ordinateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Permet de lancer le serveur
     *
     * @param port : port sur lequel lancer le serveur
     */
    private void run_server(int port) throws UnknownHostException, MalformedURLException {
        try {
            System.out.println("********* Lancement du serveur sur le port : " + port + "*********");
            LocateRegistry.createRegistry(port);
                   
            MethodesDist localMessageObject = new MethodesDist(this);
            
            String url = "rmi://127.0.0.1:"+port+"/Message";

            System.out.println("Enregistrement de l'objet avec l'url : " + url);

            Naming.rebind(url, localMessageObject);

            System.out.println("Serveur lancé");

        } catch (RemoteException e) {

            e.printStackTrace();
        }
    }

    /**
     * Fonction permettant d'établir la connexion entre deux ordinateurs
     *
     * @param ip : ip de l'ordinateur auquel on veut se connecter
     * @param port : port sur lequel on veut se connecter
     */
    public void connect_gui(String ip, int port) {

        // on se connecte en rmi et on instancie un objet après le /
        int essais = 0;
        int EssaisMax = 8;
        while ((essais < EssaisMax) && this.receiver == null) {
            essais += 1;
            System.out.println("Essais de connexion n°" + essais + " sur " + EssaisMax + " ...");
            try {
                this.receiver = (I_MethodesDist) Naming.lookup("rmi://" + ip + ":" + port+"/Message");
                System.out.println("Ordinateur connecté à " + ip + " sur le port " + port);
                this.sender.enable(receiver);
            } catch (MalformedURLException | RemoteException | NotBoundException e) {
                System.err.println("Echec de l'essai");
                e.printStackTrace();
                try {
                    synchronized (this) {
                        this.wait(2000);
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (this.receiver == null) {
            System.err.println("L'ordinateur n'as pas réussi à se connecter après " + EssaisMax + " essais.");
            System.exit(-1);
        }

    }

    /**
     * Fonction qui permet de rompre la connexion Un message est envoyé à
     * l'autre ordinateur pour signaler cette deconnexion
     */
    public void deconnect_gui() {
        System.out.println("Deconnexion");
        this.sender.disable();
        this.receiver = null;
    }

    /**
     * @return the receiver
     */
    public I_MethodesDist getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(I_MethodesDist receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the sender
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
