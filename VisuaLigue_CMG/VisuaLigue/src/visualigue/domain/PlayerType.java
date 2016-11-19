
package visualigue.domain;



public class PlayerType {
    private String name = "Joueur";
    private int color = 0xFFFFFF;
    
    public PlayerType(String name, int hex_color)
    {
        this.name = name;
        this.color = hex_color;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getColor()
    {
        return this.color;
    }
    
    public void setType(int hex_color)
    {
        this.color = hex_color;
    }
}
