/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maxime
 */
public class Jeu {
    private String name = "Default Strat";
    private Sport sport;
    private List<PlayFrame> playFrames= new ArrayList();
    
    public Jeu(String name)
    {
        this.name = name;
    }
    
    public Jeu(String name, Sport sport)
    {
        this.name = name;
        this.sport = sport;
    }
    
    public Sport getSport()
    {
        return this.sport;
    }
    
    public boolean hasSportAssociated()
    {
        return sport != null;
    }
    
    public void newFrame(int frameNb) {
        
        PlayFrame frame = new PlayFrame(frameNb);
        playFrames.add(frame);  
        
    }
    
    public PlayFrame getFrame(int frameNb) {
        
        return playFrames.get(frameNb);
        
    }
}
