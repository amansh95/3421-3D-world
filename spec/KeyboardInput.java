package ass2.spec;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    
    private static boolean[] keys = new boolean[Controls.values().length];
    private static boolean[] isPressed = new boolean[Controls.values().length];
    
    //No key is pressed, Nothing is on(3rd person mode)
    public KeyboardInput() {
        for(int i = 0; i < Controls.values().length; i++) {
            keys[i] = false;
        }
        for(int i = 0; i < Controls.values().length; i++) {
            isPressed[i] = false;
        }
    }
    
    //Methods of KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        keys[keyTypes(e)] = true;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        keys[keyTypes(e)] = false;
        isPressed[keyTypes(e)] = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    
    public static boolean key(Controls k) {
        return keys[k.ordinal()];
    }

    public static boolean keyToggle(Controls k) {
        if(isPressed[k.ordinal()] == false && keys[k.ordinal()] == true){
            isPressed[k.ordinal()] = true;
            keys[k.ordinal()] = false;
            return true;
        }else{
            return false;
        }
    }
    
    public static int keyTypes(KeyEvent k){
        if(k.getKeyCode() == KeyEvent.VK_LEFT)
            return Controls.LEFT.ordinal();
        if(k.getKeyCode() == KeyEvent.VK_RIGHT)
            return Controls.RIGHT.ordinal();
        if(k.getKeyCode() == KeyEvent.VK_UP)
            return Controls.UP.ordinal();
        if(k.getKeyCode() == KeyEvent.VK_DOWN)
            return Controls.DOWN.ordinal();
        if(k.getKeyCode() == KeyEvent.VK_W)
            return Controls.W.ordinal();
        if(k.getKeyCode() == KeyEvent.VK_A)
            return Controls.A.ordinal();
        if(k.getKeyCode() == KeyEvent.VK_S)
            return Controls.S.ordinal();
        if(k.getKeyCode() == KeyEvent.VK_D)
            return Controls.D.ordinal();
        if(k.getKeyCode() == KeyEvent.VK_C)
            return Controls.C.ordinal();
        
        return Controls.unAssigned.ordinal();
    }
}
