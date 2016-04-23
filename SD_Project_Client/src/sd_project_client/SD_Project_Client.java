/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd_project_client;

import java.io.IOException;
import java.rmi.Naming;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;

/**
 *
 * @author karakayn
 */
public class SD_Project_Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {    
        System.out.println("Num de port pour se co ? : ");
        Scanner sc = new Scanner(System.in);
        int port = Integer.parseInt(sc.nextLine());
        String command = "-Djava.rmi.server.hostname=localhost";
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
            Logger.getLogger(SD_Project_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        tryConnexion(port);
        
    
    }
    
    /**
     * Tente de se connecter
     * @param port : numero de port auquel se connecter
     * @return false si il ne reussi pas a se connecter
     */
    private static boolean tryConnexion(int port)
    {
        try {
            // on se connecte en rmi et on instancie un objet après le /
            Message b =(Message) Naming.lookup("rmi://"+port+"/Message");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    
}
