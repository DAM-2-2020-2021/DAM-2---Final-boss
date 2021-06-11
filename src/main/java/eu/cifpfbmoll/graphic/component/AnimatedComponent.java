package eu.cifpfbmoll.graphic.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatedComponent extends JComponent {
    // CONS

    // VARS
    private JComponent component;
    private AnimatedComponentType animatedComponentType;
    private float intensityValue;

    public AnimatedComponent(JComponent component, AnimatedComponentType animatedComponentType){
        this.setLayout(new FlowLayout());
        component.setOpaque(false);
        this.add(component);
        this.component = component;
        this.animatedComponentType = animatedComponentType;
        this.setOpaque(false);
        this.intensityValue = 0f;
        timer.start();
    }

    public AnimatedComponent(JComponent component, AnimatedComponentType animatedComponentType, float intensityValue){
        this.setLayout(new FlowLayout());
        component.setOpaque(false);
        this.add(component);
        this.component = component;
        this.animatedComponentType = animatedComponentType;
        this.setOpaque(false);
        this.intensityValue = intensityValue;
        timer.start();
    }

    Timer timer = new Timer (50, new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            intensityValue = animatedComponentType.act(intensityValue, timer);
        }
    });

    @Override
    public void paintComponent(Graphics g){
        animatedComponentType.paintComponent(g, this.getWidth(), this.getHeight(), this.intensityValue);
    }

    public enum AnimatedComponentType {
        FADE_IN {
            public void paintComponent(Graphics g, int width, int height, float value){
                Graphics2D graphics2d = (Graphics2D) g;
                graphics2d.setComposite(AlphaComposite.SrcOver.derive(value));
            }

            @Override
            public float act(float intensityValue, Timer timer){
                if (intensityValue > 0.95){
                    timer.stop();
                }else{
                    intensityValue += 0.05;
                }
                return intensityValue;
            }

            public float act(float intensityValue, int timer) {
                return 0;
            }
        },
        FADE_OUT {
            public void paintComponent(Graphics g, int width, int height, float value){
                Graphics2D graphics2d = (Graphics2D) g;
                graphics2d.setComposite(AlphaComposite.SrcOver.derive(value));
            }

            @Override
            public float act(float intensityValue, Timer timer) {
                if (intensityValue < 0.05){
                    timer.stop();
                }else{
                    intensityValue -= 0.05;
                }
                return intensityValue;
            }
        },
        SLIDE_IN {
            public void paintComponent(Graphics g, int width, int height, float value){
                Graphics2D graphics2d = (Graphics2D) g;
                graphics2d.setComposite(AlphaComposite.SrcOver.derive(value));
            }

            @Override
            public float act(float intensityValue, Timer timer) {
                return 0;
            }
        },
        SLIDE_IN_RIGHT {
            public void paintComponent(Graphics g, int width, int height, float value){
                Graphics2D graphics2d = (Graphics2D) g;
                graphics2d.setComposite(AlphaComposite.SrcOver.derive(value));
            }

            @Override
            public float act(float intensityValue, Timer timer) {
                return 0;
            }
        },
        SLIDE_IN_LEFT {
            public void paintComponent(Graphics g, int width, int height, float value){
                Graphics2D graphics2d = (Graphics2D) g;
                graphics2d.setComposite(AlphaComposite.SrcOver.derive(value));
            }

            @Override
            public float act(float intensityValue, Timer timer) {
                return 0;
            }
        };

        /**
         * Constructor
         */
        AnimatedComponentType(){

        }

        public abstract void paintComponent(Graphics g, int width, int height, float value);
        public abstract float act(float intensityValue, Timer timer);
    }
}
