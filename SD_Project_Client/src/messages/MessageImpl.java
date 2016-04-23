/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author karakayn
 */

public class MessageImpl extends UnicastRemoteObject implements Message {
    public MessageImpl() throws RemoteException {
        super();
    }

    public String messageDistant() throws RemoteException {
        return "Message distant envoyé";
    }
}