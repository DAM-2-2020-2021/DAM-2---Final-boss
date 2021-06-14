package eu.cifpfbmoll.sound;

import javax.sound.sampled.*;
import java.io.File;

public class Sound extends Thread{

    private String filename;
    private boolean finished;
    Clip menuSound;

    public Sound(String fname){
        filename = fname;
        finished = false;
    }


    public static void alert(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/Alert/Alert.wav");
        s1.start();
    }

    public static void notice(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/Aviso/aviso.wav");
        s1.start();
    }

    public static void blackHole(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/blackHole/teleport.wav");
        s1.start();
    }

    // DISPAROS
    public static void shootPowerup(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/Disparo/disparoPowerup.wav");
        s1.start();
    }
    public static void shooting(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/Disparo/Disparo.wav");
        s1.start();
    }

    public static void destroyMeteorite(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/Meteorito/DestruccionMeteorito.wav");
        s1.start();
    }

    public static void shipDeath(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/Muerte/Muerte.wav");
        s1.start();
    }

    public static void shipCollision(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/Muerte/muerteColision.wav");
        s1.start();
    }

    public static void lastMinute(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/Musica/Final/lastMinute.wav");
        s1.start();
    }

    public static void shield(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/PowerUp/escudo.wav");
        s1.start();
    }

    public static void getWeaponPowerUp(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/PowerUp/PowerUpArmaAviso.wav");
        s1.start();
    }

    public static void fast(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Partida/PowerUp/velocidadPowerup.wav");
        s1.start();
    }

    public static void soundInteractueMenu(){
        Sound s1 = new Sound("src/main/resources/Sonidos/Menu/cambioMenu.wav");
        s1.start();
    }


    public void run() {

        try {
            File file = new File(filename);
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            Thread.sleep(100);
            while (clip.isRunning()) { Thread.sleep(100); }

            clip.close();
        }
        catch (Exception e) { }
        finished = true;
    }

    public static Clip clipSoundMenu(int volumen){
        File file = new File("src/main/resources/Sonidos/Menu/musicaMenu.wav");
        Clip clip = null;
        try{
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(stream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-volumen+.0f);
            clip.loop(99);
            return clip;
        } catch (Exception e){ }
        return clip;
    }

    public static Clip clipSoundGame(int volumen){
        int random = 1 + (int) (Math.random()*7);
        File file = new File("src/main/resources/Sonidos/Partida/Musica/Partida/"+ random + ".wav");
        Clip clip = null;
        try{
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(stream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-volumen+.0f);
            clip.loop(99);
            return clip;
        } catch (Exception e){ }
        return clip;
    }
    public static Clip clipSoundLastMinute(int volumen){
        int random = 1 + (int) (Math.random()*2);
        File file = new File("src/main/resources/Sonidos/Partida/Musica/Final/final"+random+".wav");
        Clip clip = null;
        try{
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(stream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-volumen+.0f);
            clip.loop(99);
            return clip;
        } catch (Exception e){ }
        return clip;
    }

}
