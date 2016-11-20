/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import java.util.List;
import visualigue.utils.Vector2d;

/**
 *
 * @author maxime
 */
public class PlayFrame {
    public List<Vector2d> Positions_joueurs;
    public List<Vector2d> Positions_adversaires;
    public List<Vector2d> Obstacles;
    
    public PlayFrame() {}
    
    public PlayFrame(List<Vector2d> p_Positions_joueurs, List<Vector2d> p_Positions_adversaires, List<Vector2d> p_Obstacles) {
    
        Positions_joueurs = p_Positions_joueurs;
        Positions_adversaires = p_Positions_adversaires;
        Obstacles = p_Obstacles;
    
    }
}
