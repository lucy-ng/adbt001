package game.Controllers;
import game.Bodies.Toast;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class ToastController implements KeyListener {
    private static final float walkingSpeed = 8;
    private static final float jumpingSpeed = 27;
    private Toast toast;

    public ToastController(Toast toast) {
        this.toast = toast;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    // Controls
    public void keyPressed(KeyEvent e){
        playerMove(e);
    }

    @Override
    public void keyReleased(KeyEvent e){
        playerStop(e);
    }

    public void playerMove(KeyEvent e){
        int code = e.getKeyCode();

        // A key to walk left
        if (code == KeyEvent.VK_A){
            toast.setToastLeftImage();
            toast.startWalking(-walkingSpeed);
        }
        // D key to walk right
        else if (code == KeyEvent.VK_D){
            toast.setToastRightImage();
            toast.startWalking(walkingSpeed);
        }
        // W key to jump
        else if (code == KeyEvent.VK_W){
            toast.jump(jumpingSpeed);
        }
    }

    public void playerStop(KeyEvent e){
        int code = e.getKeyCode();

        // A key released to stop walking
        if (code == KeyEvent.VK_A){
            toast.stopWalking();
        }
        // D key released to stop walking
        else if (code == KeyEvent.VK_D){
            toast.stopWalking();
        }
    }

    // Update player movements
    public void updateToast(Toast toast){
        this.toast = toast;
    }
}
