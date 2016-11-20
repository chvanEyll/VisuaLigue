/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

public class Obstacle {
    private String name;
    private String builtInImagePathName = "";

    public Obstacle(String name, String builtInImagePathName)
    {
        this.name = name;
        this.builtInImagePathName = builtInImagePathName;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getBuiltInImagePathName()
    {
        return this.builtInImagePathName;
    }
    
    public void setBuiltInImagePathName(String builtInImagePathName)
    {
        this.builtInImagePathName = builtInImagePathName;
    }
}