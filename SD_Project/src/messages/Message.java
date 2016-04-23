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
 * Définition d'une interface qui contient les méthodes qui peuvent être appelées à distance
 * @author karakayn
 */

public interface Message extends Remote {
	public String messageDistant() throws RemoteException;
}
