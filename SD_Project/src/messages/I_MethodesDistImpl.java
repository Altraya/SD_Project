/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import controllers.Ordinateur;
import controllers.Sender;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class I_MethodesDistImpl implements I_MethodesDist {

    private static final long serialVersionUID = 123456789;
    private Robot robot;
    private Dimension screenSize;
    private Sender sender; //permet de joindre le sender
    
    public I_MethodesDistImpl(Ordinateur ordi) {
        try {
            this.robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(I_MethodesDistImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.sender = ordi.getSender();
    }
    
    @Override
    public String messageDistant() throws RemoteException {
        return "Message distant envoyé";
    }

    @Override
    public void moveMouse(double x, double y) throws RemoteException {
        
        int localX = (int) (screenSize.getWidth() * x);
        int localY = (int) (screenSize.getWidth() * y);
        this.sender.resetTimestamp();
        this.robot.mouseMove(localX, localY);
        System.out.println("Mouse moved to "+localX+" / "+localY);
    }

    /*Simule un click de souris, envoi l'info a l'ordi distant*/
    @Override
    public synchronized void clickMouse(double x, double y) throws RemoteException {
        System.out.println("Mouse clicked");
        //this.moveMouse(x, y);
        this.sender.resetTimestamp();
        this.robot.mousePress(InputEvent.BUTTON1_MASK);
        this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    @Override
    public synchronized void pressMouse(double x, double y) throws RemoteException {
        System.out.println("Mouse pressed");
        //this.moveMouse(x, y);
        this.sender.resetTimestamp();
        this.robot.mousePress(InputEvent.BUTTON1_MASK);
    }

    @Override
    public synchronized void releaseMouse(double x, double y) throws RemoteException {
        System.out.println("Mouse released");
        //this.moveMouse(x, y);
        this.sender.resetTimestamp();
        this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    @Override
    public synchronized void keyTyped(int keycode) throws RemoteException {
        System.out.println("Key typed");
        this.sender.resetTimestamp();
        this.robot.keyPress(keycode);
        this.robot.keyRelease(keycode);
    }

    @Override
    public synchronized void keyPressed(int keycode) throws RemoteException {
        System.out.println("Key pressed");
    }

    @Override
    public synchronized void keyReleased(int keycode) throws RemoteException {
        System.out.println("Key released");
    }
    
}
