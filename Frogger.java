import java.awt.EventQueue;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import FixedGameElements.Board;

public class Frogger extends JFrame {

    public Frogger() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
               
        setResizable(false);
        pack();
        
        setTitle("Frogger");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playMusic("Media/8bit_song_free_use.wav");
    }
//methode pour mettre la musique
    public static void playMusic(String file_path){
        AudioInputStream song;
        try {
            song = AudioSystem.getAudioInputStream(new File(file_path));
            Clip clip = AudioSystem.getClip();
            clip.open(song);
            clip.start();
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // set the percent (between 0.0 and 1.0)
            double percent = 0.1;   
            float dB = (float) (Math.log(percent) / Math.log(10.0) * 20.0);
            volume.setValue(dB);
        } catch (Exception e) {
            //TODO: handle exception
            JOptionPane.showMessageDialog(null,"error song");
        }
    }
    

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new Frogger();
            ex.setVisible(true);
        });
    }
}
