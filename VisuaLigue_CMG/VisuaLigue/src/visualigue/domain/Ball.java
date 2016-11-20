/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

/**
 *
 * @author maxime
 */
public class Ball {
    private String name = "Ball";
    private String builtInImagePathName = "../image/generic-ball-icon.png";
    
    public Ball()
    {
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getBuiltInImagePathName()
    {
        return this.builtInImagePathName;
    }
    
    public void setBuiltInImagePathName(String imagePathName)
    {
        this.builtInImagePathName = imagePathName;
    }
}
