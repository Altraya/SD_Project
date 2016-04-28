/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface : Message
 * Définition d'une interface qui contient les méthodes qui peuvent être 
 * appelées à distance
 * @author karakayn
 */

public interface I_MethodesDist {
	public String messageDistant() throws RemoteException;
        
        public void moveMouse(double x, double y) throws RemoteException;

	public void clickMouse(double x, double y) throws RemoteException;
        
        public void pressMouse(double x, double y) throws RemoteException;
        
        public void releaseMouse(double x, double y) throws RemoteException;
        
        public void keyTyped(int keycode) throws RemoteException;
        
        public void keyPressed(int keycode) throws RemoteException;
        
        public void keyReleased(int keycode) throws RemoteException;
}
