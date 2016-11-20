/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualigue.domain;

/**
 *
 * @author Guillaume
 */
public class PlayingSurface {

    private Double width = 100.0;
    private Double length = 200.0;
    //private PlayingSurfaceUnit units = PlayingSurfaceUnit.METER;
    private String units;
    private String customImageUUID;
    private String builtInImagePathName = System.getProperty("user.dir")+"/src/image/soccer-field.jpg";

    public PlayingSurface() {
        //Required for JAXB instanciation.
    }

    public PlayingSurface(Double width, Double length, String units) {
        this.width = width;
        this.length = length;
        this.units = units;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getCustomImageUUID() {
        return customImageUUID;
    }

    public void setCustomImageUUID(String customImageUUID) {
        this.customImageUUID = customImageUUID;
    }

    public Boolean hasCustomImage() {
        return customImageUUID != null;
    }

    public String getBuiltInImagePathName() {
        return builtInImagePathName;
    }

    public void setBuiltInImagePathName(String builtInImagePathName) {
        this.builtInImagePathName = builtInImagePathName;
    }
}

