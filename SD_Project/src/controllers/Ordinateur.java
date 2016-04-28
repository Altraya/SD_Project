/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
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
import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.net.Client;
import lipermi.net.Server;
import messages.I_MethodesDist;
import messages.MethodesDistImpl;

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
        MethodesDistImpl mDist = new MethodesDistImpl(this);
        CallHandler callHandler = new CallHandler();
        try {
            callHandler.registerGlobal(I_MethodesDist.class, mDist);
        } catch (LipeRMIException ex) {
            Logger.getLogger(Ordinateur.class.getName()).log(Level.SEVERE, null, ex);
        }
        Server server = new Server();
        try {
            server.bind(port, callHandler);
        } catch (IOException ex) {
            Logger.getLogger(Ordinateur.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Serveur lancé sur le port : " + port);
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
        CallHandler callHandler = new CallHandler();
        Client client = null;
        while ((essais < EssaisMax) && this.receiver == null) {
            essais += 1;
            System.out.println("Essais de connexion n°" + essais + " sur " + EssaisMax + " ...");
            try {
                client = new Client(ip, port, callHandler);
                System.out.println("Ordinateur connecté à " + ip + " sur le port " + port);
                this.receiver = (I_MethodesDist) client.getGlobal(I_MethodesDist.class);
                this.sender.enable(receiver);
            } catch (IOException ex) {
                Logger.getLogger(Ordinateur.class.getName()).log(Level.SEVERE, null, ex);
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
