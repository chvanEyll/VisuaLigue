package ca.ulaval.glo2004.visualigue.domain.xmladapters;

import javafx.scene.paint.Color;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlColorAdapter extends XmlAdapter<String, Color> {

    @Override
    public String marshal(Color color) throws Exception {
        return color.toString();
    }

    @Override
    public Color unmarshal(String stringColor) throws Exception {
        return Color.valueOf(stringColor);
    }

}
