package ca.ulaval.glo2004.visualigue.domain.xmladapters;

import ca.ulaval.glo2004.visualigue.utils.ColorUtils;
import java.awt.Color;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlColorAdapter extends XmlAdapter<String, Color> {

    @Override
    public String marshal(Color color) throws Exception {
        return ColorUtils.ToHex(color);
    }

    @Override
    public Color unmarshal(String stringColor) throws Exception {
        return Color.decode(stringColor);
    }

}
