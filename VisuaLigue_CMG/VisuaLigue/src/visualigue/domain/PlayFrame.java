/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import java.util.List;
import visualigue.utils.Vector2d;
import java.util.ArrayList;

/**
 *
 * @author maxime
 */
public class PlayFrame {
    private List<Vector2d> Positions_joueurs = new ArrayList();
    private List<Vector2d> Positions_adversaires = new ArrayList();
    private List<Vector2d> Obstacles = new ArrayList();
    private int Id;
    
    public PlayFrame(int p_Id) {Id=p_Id;}
    
    public PlayFrame(int p_Id, List<Vector2d> p_Positions_joueurs, List<Vector2d> p_Positions_adversaires, List<Vector2d> p_Obstacles) {
        Id = p_Id;
        Positions_joueurs = p_Positions_joueurs;
        Positions_adversaires = p_Positions_adversaires;
        Obstacles = p_Obstacles;
    
    }
    
    public List<Vector2d> getJoueursPos() {
        
        return Positions_joueurs;
        
    }
    
    public List<Vector2d> getAdversairesPos() {
        
        return Positions_adversaires;
        
    }
    
    public List<Vector2d> getObstaclesPos() {
        
        return Obstacles;
        
    }
    
    public void addJoueur(Vector2d joueurPos) {
        
        Positions_joueurs.add(joueurPos);
        
    }
    
    public void addAdversaire(Vector2d advPos) {
        
        Positions_adversaires.add(advPos);
        
    }
     
    public void addObstacle(Vector2d obstaclePos) {
        
        Obstacles.add(obstaclePos);
        
    }
    
    public void setJoueurPos(int idx, Vector2d joueurPos) {
        
        Positions_joueurs.set(idx,joueurPos);
        
    }
    
    public void setAdversairePos(int idx, Vector2d advPos) {
        
        Positions_adversaires.set(idx,advPos);
        
    }
     
    public void setObstaclePos(int idx, Vector2d obstaclePos) {
        
        Obstacles.set(idx,obstaclePos);
        
    }
    
    public void insertFramePositions(PlayFrame oldFrame) {
        
        List<Vector2d> oldJoueursPos = oldFrame.getJoueursPos();
        List<Vector2d> oldAdversairesPos = oldFrame.getAdversairesPos();
        List<Vector2d> oldObstaclesPos = oldFrame.getObstaclesPos();
        
        for (int i =0;i<oldJoueursPos.size();i++){
        
            this.addJoueur(oldJoueursPos.get(i));
            
        }
        
        for (int i =0;i<oldAdversairesPos.size();i++){
        
            this.addAdversaire(oldAdversairesPos.get(i));
            
        }
        
        for (int i =0;i<oldObstaclesPos.size();i++){
        
            this.addObstacle(oldObstaclesPos.get(i));
            
        }
        
    }
}
