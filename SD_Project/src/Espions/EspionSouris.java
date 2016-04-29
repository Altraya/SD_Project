/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Espions;

import controllers.Sender;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

/**
 * Permet d'espionner les évènements de la souris
 * @author karakayn
 */
public class EspionSouris implements NativeMouseInputListener{

    private Sender sender;
    
    public EspionSouris(Sender sender)
    {
        super();
        this.sender = sender;
    }
    
    @Override
    public void nativeMouseClicked(NativeMouseEvent nme) {
        System.out.println("MouseClickedNative | x = "+nme.getX()+" | y = "+ nme.getY());
        this.sender.sendClick(nme.getX(), nme.getY());
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nme) {
        System.out.println("MousePressedNative | x = "+nme.getX()+" | y = "+ nme.getY());
        this.sender.sendMousePress(nme.getX(), nme.getY());
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nme) {
        System.out.println("MouseReleasedNative | x = "+nme.getX()+" | y = "+ nme.getY());
        this.sender.sendMouseReleased(nme.getX(), nme.getY());
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nme) {
        this.sender.sendMouseMove(nme.getX(), nme.getY());
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nme) {
        this.sender.SendMouseDragged(nme.getX(), nme.getY());
    }
    
}
