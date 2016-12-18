/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

import visualigue.utils.Vector2d;

/**
 *
 * @author grasshop
 */
public class Command {
    
    public String commandType;
    public String objectType;
    public int frame;
    public int index;
    public Vector2d oldPos;
    public Vector2d newPos;
    
    public Command(String p_commandType, String p_ObjectType, int p_frame, int p_index) {

        this.commandType = p_commandType;
        this.objectType = p_ObjectType;
        this.frame = p_frame;
        this.index = p_index;

    }
    
    public Command(String p_commandType, String p_ObjectType, int p_frame, int p_index, Vector2d p_oldPos, Vector2d p_newPos) {

        this.commandType = p_commandType;
        this.objectType = p_ObjectType;
        this.frame = p_frame;
        this.index = p_index;
        this.oldPos = p_oldPos;
        this.newPos = p_newPos;

    }
}
