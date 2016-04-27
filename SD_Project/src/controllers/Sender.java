/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.rmi.RemoteException;

import messages.MethodesDist;

/**
 * Permet d'envoyer des messages a notre autre client
 * @author karakayn
 */
public class Sender {

    private boolean enabled;
    private MethodesDist remote;
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
                this.remote.clickMouse(localX, localY);
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

    public void enable(MethodesDist remote) {
        this.remote = remote;
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
        this.remote = null;
    }

    public void setRemote(MethodesDist remote) {
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
