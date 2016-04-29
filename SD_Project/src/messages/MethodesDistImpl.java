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


public class MethodesDistImpl implements I_MethodesDist {

    private static final long serialVersionUID = 123456789;
    private Robot robot;
    private Dimension screenSize;
    private Sender sender; //permet de joindre le sender
    
    public MethodesDistImpl(Ordinateur ordi) {
        try {
            this.robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(MethodesDistImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.sender = ordi.getSender();
    }
    
    @Override
    public String messageDistant() throws RemoteException {
        return "Message distant envoyé";
    }

    @Override
    public synchronized void moveMouse(double x, double y) throws RemoteException {
        
        int localX = (int) (screenSize.getWidth() * x);
        int localY = (int) (screenSize.getHeight() * y);
        this.sender.resetTimestamp();
        this.robot.mouseMove(localX, localY);
        System.out.println("Mouse moved to "+localX+" / "+localY);
    }

    /*Simule un click de souris, envoi l'info a l'ordi distant*/
    @Override
    public synchronized void clickMouse(double x, double y) throws RemoteException {
        System.out.println("Mouse clicked");
        this.moveMouse(x, y);
        this.sender.resetTimestamp();
        this.robot.mousePress(InputEvent.BUTTON1_MASK);
        this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    @Override
    public synchronized void pressMouse(double x, double y) throws RemoteException {
        System.out.println("Mouse pressed");
        this.moveMouse(x, y);
        this.sender.resetTimestamp();
        this.robot.mousePress(InputEvent.BUTTON1_MASK);
    }

    @Override
    public synchronized void releaseMouse(double x, double y) throws RemoteException {
        System.out.println("Mouse released");
        this.moveMouse(x, y);
        this.sender.resetTimestamp();
        this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
    
    public synchronized void draggedMouse(double x, double y) throws RemoteException {
        System.out.println("Mouse dragged");
        this.moveMouse(x, y);
        this.sender.resetTimestamp();
        this.robot.mouseWheel(1);
    }

    @Override
    public synchronized void keyTyped(char charKeybord) throws RemoteException {
        System.out.println("Key typed");
        this.sender.resetTimestamp();
        this.robot.keyPress(charKeybord);
        this.robot.keyRelease(charKeybord);
    }

    @Override
    public synchronized void keyPressed(char charKeybord) throws RemoteException {
        System.out.println("Key pressed");
        this.sender.resetTimestamp();
        this.robot.keyPress(charKeybord);
    }

    @Override
    public synchronized void keyReleased(char charKeybord) throws RemoteException {
        System.out.println("Key released");
        this.sender.resetTimestamp();
        this.robot.keyRelease(charKeybord);
    }
    
}
