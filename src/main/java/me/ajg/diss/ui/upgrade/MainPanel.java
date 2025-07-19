package me.ajg.diss.ui.upgrade;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    
    private Config config;
    private SequencerSelector sequencerSelector;
    private SynthesisChooser synthesisChooser;
    private EncoderChooser encoderChooser;
    
    public MainPanel(Config config) {
        this.config = config;
        setBackground(Color.DARK_GRAY);
        this.setOpaque(true);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        sequencerSelector = new SequencerSelector(this.config);
        synthesisChooser = new SynthesisChooser(this.config);
        encoderChooser = new EncoderChooser(this.config);
        
        add(sequencerSelector);
        //+20 prevents cropping of objects
        this.setPreferredSize(new Dimension(sequencerSelector.getPreferredSize().width +20, HomePage2.HEIGHT ));
        add(encoderChooser);
        
        synthesisChooser.setPreferredSize(new Dimension(encoderChooser.getPreferredSize().width, synthesisChooser.getPreferredSize().height));
        add(synthesisChooser);
    }
}
