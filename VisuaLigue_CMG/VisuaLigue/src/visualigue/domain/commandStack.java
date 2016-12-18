/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import java.util.Stack;
import javafx.util.Pair;
import visualigue.utils.Vector2d;

/**
 *
 * @author guillaume
 */

public class commandStack {
    
    private Stack<Command> maPile = new Stack();
    private Stack<Command> pileDeRedo = new Stack();
    
    public void addAjout(String ObjectType,int frame,int index) {
        
        Command maCommande = new Command("Add",ObjectType,frame,index);
        maPile.push(maCommande);
        
    }
    
    public void addDeplacement(String ObjectType,int frame, int index, Vector2d oldPos, Vector2d newPos) {
        
        Command maCommande = new Command("Move",ObjectType,frame, index, oldPos, newPos);
        maPile.push(maCommande);
        
    }
    
    public Command getLastCommand() {
        
        if (maPile.size() != 0) {
            
            return pileDeRedo.push(maPile.pop());
            //return (Command) maPile.pop();
        
        } else {
            
            return null;
            
        }
    }
    
    public Command getLastRedoCommand() {
        
        if (pileDeRedo.size() != 0) {
            
            return maPile.push(pileDeRedo.pop());
        
        } else {
            
            return null;
            
        }
    }
    
}
