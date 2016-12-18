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
    
    public int addJoueur(Vector2d joueurPos) {
        
        Positions_joueurs.add(joueurPos);
        return Positions_joueurs.size();
        
    }
    
    public int addAdversaire(Vector2d advPos) {
        
        Positions_adversaires.add(advPos);
        return Positions_adversaires.size();
        
    }
     
    public int addObstacle(Vector2d obstaclePos) {
        
        Obstacles.add(obstaclePos);
        return Obstacles.size();
        
    }
    
    public Vector2d setJoueurPos(int idx, Vector2d joueurPos) {
        
        Vector2d oldPos = Positions_joueurs.get(idx);
        Positions_joueurs.set(idx,joueurPos);
        return oldPos;
        
    }
    
    public Vector2d setAdversairePos(int idx, Vector2d advPos) {
        
        Vector2d oldPos = Positions_adversaires.get(idx);
        Positions_adversaires.set(idx,advPos);
        return oldPos;
        
    }
     
    public Vector2d setObstaclePos(int idx, Vector2d obstaclePos) {
        
        Vector2d oldPos = Obstacles.get(idx);
        Obstacles.set(idx,obstaclePos);
        return oldPos;
        
    }
    
    public void deleteAtIndex(String ObjectType, int index) {
        
        List<Vector2d> maListe = new ArrayList();
        if (ObjectType == "Joueurs") {
            
            maListe = Positions_joueurs;
            
        } else if (ObjectType == "Adversaires") {
            
            maListe = Positions_adversaires;
            
        } else if (ObjectType == "Obstacles") {
            
            maListe = Obstacles;
            
        }
        
        // delete index
        maListe.remove(index-1);
        
    }
    
    public void deleteAnyPos(float x, float y) {
        
        deleteFromList(x,y,Positions_joueurs);
        deleteFromList(x,y,Positions_adversaires);
        deleteFromList(x,y,Obstacles);
        
    }
    
    public void deleteFromList(float x, float y, List<Vector2d> maListe) {
        
        for (int i=0; i<maListe.size(); i++) {
            
            if (maListe.get(i).x == x) {
                
                if (maListe.get(i).y == y) {
                    
                    maListe.remove(i);
                    
                }
            }
            
        }
        
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
