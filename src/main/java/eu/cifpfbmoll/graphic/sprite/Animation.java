package eu.cifpfbmoll.graphic.sprite;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Animations are arrays of frames, which are images, and those simulate movement (or not)
 */
public class Animation {

    private int cont; //Counter to check when the frame has to change
    private int delay; //How many repaints of the main program has to wait until the next frame
    private int currentFrame;
    private boolean stopped; //If its stopped, it won't do anything until it starts again.

    private List<Frame> frames = new ArrayList<>(); //List of frames/images

    /**
     * Checks that the delay is enough and then fills the frame list with the images provided. And Initializes.
     * @param frames
     * @param delay
     */
    public Animation(BufferedImage[] frames, int delay) {
        if (delay <= 0){
            this.delay = 1;
        }else {
            this.delay = delay;
        }

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i]);
        }

        //Initializes the variables
        this.cont = 0;
        this.delay = delay;
        this.currentFrame = 0;
        this.stopped = true;
    }

    /**
     * Starts the animation
     */
    public void start() {
        if (stopped && frames.size() != 0) {
            stopped = false;
        }
    }

    /**
     * Stops the animation
     */
    public void stop() {
        if (frames.size() != 0) {
            stopped = true;
        }
    }

    /**
     * Starts the animation but to the frame 0
     */
    public void restart() {
        if (frames.size() != 0) {
            currentFrame = 0;
            this.start();
        }
    }

    /**
     * Adds frame to the list and resets the animation.
     * @param frame
     */
    private void addFrame(BufferedImage frame) {
            frames.add(new Frame(frame));
            currentFrame = 0;
    }

    /**
     * This returns the image of the animation, its what's used in the paint method.
     * @return
     */
    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }

    /**
     * This goes to the refresh method in the main program, it will keep the animation going.
     */
    public void update() {
        if (!stopped) {
            cont++;
            if (cont > delay) {
                cont = 0;
                currentFrame += 1;
                if (currentFrame > this.frames.size() - 1) {
                    currentFrame = 0;
                }
            }
        }
    }

}