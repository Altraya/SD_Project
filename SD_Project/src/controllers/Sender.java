/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import messages.I_MethodesDist;

import messages.MethodesDistImpl;

/**
 * Permet d'envoyer des messages a notre autre client
 * @author karakayn
 */
public class Sender {

    private boolean enabled;
    private I_MethodesDist remote;
    private Dimension screenSize;
    private long lastReceivedTimestamp;

    public Sender() {
        this.enabled = false;
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Envoi l'evenement click a l'ordinateur distant
     *
     * @param x : coordonnée x
     * @param y : coordonnée y
     */
    public void sendClick(int x, int y) {
        if (enabled && readyToSend()) {
            double localX = ((double) x) / this.screenSize.getWidth();
            double localY = ((double) y) / this.screenSize.getHeight();
            try {
                System.out.println("Send a click in the remote computer with "+localX+" and "+localY);
                this.remote.clickMouse(localX, localY);
                System.out.println("Send a click in the remote computer with "+localX+" and "+localY);
            } catch (RemoteException e) {
                System.err.println("Failed to send mouse clicked event.");
            }
        }
    }

    /**
     * Envoi l'evenement mouse move
     *
     * @param x : coordonnee x du déplacement
     * @param y : coordonnee y du déplacement
     */
    public void sendMouseMove(int x, int y) {
        if (enabled && readyToSend()) {
            double localX = ((double) x) / this.screenSize.getWidth();
            double localY = ((double) y) / this.screenSize.getHeight();
            try {
                this.remote.moveMouse(localX, localY);
            } catch (RemoteException e) {
                System.err.println("Failed to send mouse moved event.");
            }
        }
    }
    
    public void sendMousePress(int x, int y)
    {
        if(enabled && readyToSend())
        {
            double localX = ((double) x) / this.screenSize.getWidth();
            double localY = ((double) y) / this.screenSize.getHeight();
            try {
                this.remote.pressMouse(localX, localY);
            } catch (RemoteException e) {
                System.err.println("Failed to send mouse pressed event.");
            }
        }
    }
    
    public void sendMouseReleased(int x, int y)
    {
        if(enabled && readyToSend())
        {
            double localX = ((double) x) / this.screenSize.getWidth();
            double localY = ((double) y) / this.screenSize.getHeight();
            try {
                this.remote.releaseMouse(localX, localY);
            } catch (RemoteException e) {
                System.err.println("Failed to send mouse released event.");
            }
        }
    }
    
    public void sendKeyTyped(int keyCode)
    {
        if(enabled && readyToSend())
        {
            try {
                this.remote.keyTyped(keyCode);
            } catch (RemoteException e) {
                System.err.println("Failed to send key typed event.");
            }
        }
    }

    public void enable(I_MethodesDist remote) {
        this.remote = remote;
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
        this.remote = null;
    }

    public void setRemote(I_MethodesDist remote) {
        this.remote = remote;
    }

    public void resetTimestamp() {
        this.lastReceivedTimestamp = System.nanoTime();
    }

    private boolean readyToSend() {
        long fiveSec = (long) (5 * Math.pow(10, 9));
        return System.nanoTime() > lastReceivedTimestamp + fiveSec;
    }
}
