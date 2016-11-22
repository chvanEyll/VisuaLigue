/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.utils;

import static java.lang.Math.abs;

/**
 *
 * @author maxime and charles
 */
public class Vector2d {
    
    float range = 10; // constant
    public float x, y;
    
    public Vector2d() {
        x = y = 0;
    }
    
    public Vector2d(float p_x, float p_y) {
        x = p_x;
        y = p_y;
    }
    
    public boolean tooCloseTo(Vector2d other) {
        
        if (abs(this.x-other.x)<range) {
            
            if (abs(this.x-other.x)<range) {
            
                return true;
            
            }
            
        }
        
        return false;
        
    }
}
