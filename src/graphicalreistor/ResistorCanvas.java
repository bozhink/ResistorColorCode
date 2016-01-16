package graphicalreistor;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author bozhin
 */
public class ResistorCanvas extends Canvas
{
//    private int Black = 0x000000;
//    private int Brown = 0x9B4700;
//    private int Red = 0xFF0000;
//    private int Orange = 0xFF8000;
//    private int Yellow = 0xFFFF00;
//    private int Green = 0x00FF00;
//    private int Blue = 0x0000FF;
//    private int Violet = 0x800080;
//    private int Gray = 0x808080;
//    private int White = 0xD0D0D0;
//    private int Gold = 0xC0B300;
//    private int Silver = 0xBEBEBE;
    
    private int [] Colors = {0x000000, 0x9B4700, 0xFF0000, 0xFF8000,
        0xFFFF00, 0x00FF00, 0x0000FF, 0x800080, 0x808080, 0xD0D0D0,
        0xC0B300, 0xBEBEBE, 0xF5AA74};

    final int maxNumberOfBands = 5;
    public int [] bands = {12, 12, 12, 12, 12};
    public int currentBand = 0;
    public boolean setWarning=false;
    
    
    public void paint (Graphics g) {
        int width = getWidth();
        int height = getHeight();
        // width = w1 + 7*w1/3 + w1 + 2*condWidth = 5*w1
        int w1 = 3*((2*((width/5)/2))/3);
        int w2 = 7 * w1 / 3;
        int bandWidth = w1 / 3;
        
        int h1 = 2*w1, h2 = 5*h1/6;
        
        int condWidth = bandWidth, condHeight = bandWidth;
        
        int x = 0;
        int y = 5, y1 = y, y2 = y + (h1-h2)/2;
        // Clear canvas
        g.setGrayScale(255);
        g.fillRect(0, 0, width-1, height-1);
        // Painting the resistor
        g.setColor(Colors[0]);
        y += h1/2 - condHeight/2;
        g.fillRect(0, y, condWidth, condHeight);

        g.setColor(Colors[12]);
        x += condWidth;
        g.fillRoundRect(x, y1, w1, h1, bandWidth, bandWidth);
        x += w1;
        g.fillRect(x, y2, w2, h2);
        x += w2;
        g.fillRoundRect(x, y1, w1, h1, bandWidth, bandWidth);

        g.setColor(Colors[0]);
        x += w1;
        g.fillRect(x, y, condWidth, condHeight);

        // Bands
        g.setColor(Colors[bands[0]]);
        x = condWidth;
        g.fillRect(x + w1/3, y1, bandWidth, h1);

        g.setColor(Colors[bands[1]]);
        x += w1;
        g.fillRect(x, y2, bandWidth, h2);

        g.setColor(Colors[bands[2]]);
        x += 2*bandWidth;
        g.fillRect(x, y2, bandWidth, h2);

        g.setColor(Colors[bands[3]]);
        x += 2*bandWidth;
        g.fillRect(x, y2, bandWidth, h2);

        g.setColor(Colors[bands[4]]);
        x += 2*bandWidth;
        g.fillRect(x, y2, bandWidth, h2);

        // Value
        y = h1 + 10;
        g.setColor(Colors[0]);
        g.drawString(EvaluateColorCode(), 2, y, 0);
        // Warning message
        if (setWarning) {
            y += 15;
            g.setColor(Colors[2]);
            g.drawString("Enter at most 5 bands", 2, y, 0);
        }
        
    }
    
    private String [] ohm = {" Ω"," kΩ", " MΩ", " GΩ"};
    private String [] tolerances = {null, " ± 1 % (F)", " ± 2 % (G)", null, null, " ± 0.5 % (D)", 
        " ± 0.25 % (C)", " ± 0.10 % (B)", " ± 0.05 %", null, " ± 5 % (J)", " ± 10 % (K)", " ± 20 %"};
    private int [] digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0};
    private int [] exponents = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2};

    private String invalidCode = "Invalid tolerance.";
    
    private String EvaluateColorCode() {
        String code="";
        int value, exponent;
        int val, dec, exp=0;
        String tolerance;
        
        switch (currentBand)
        {
            case 0:
                value = 0;
                exponent = 0;
                tolerance = "";
                break;
            case 1:
                value = digits[bands[0]];
                exponent = 0;
                tolerance = tolerances[12];
                break;
            case 2:
                value = digits[bands[0]]*10 + digits[bands[1]];
                exponent = 0;
                tolerance = tolerances[12];
                break;
            case 3:
                value = digits[bands[0]]*10 + digits[bands[1]];
                exponent = exponents[bands[2]];
                tolerance = tolerances[12];
                break;
            case 4:
                value = digits[bands[0]]*10 + digits[bands[1]];
                exponent = exponents[bands[2]];
                tolerance = tolerances[bands[3]];
                break;
            default:
                value = digits[bands[0]]*100 +
                        digits[bands[1]]*10 +
                        digits[bands[2]];
                exponent = exponents[bands[3]];
                tolerance = tolerances[bands[4]];
        }
        if (tolerance==null) {
            return invalidCode;
        }
        if (exponent < 0) {
            switch (exponent) {
                case -1:
                    val = value / 10;
                    dec = value % 10;
                    break;
                case -2:
                    val = value / 100;
                    dec = value % 100;
                    break;
                default:
                    return invalidCode;
            }
        } else {
            exp = exponent / 3;
            for (int i = 0; i < exponent % 3; i++) {
                value *= 10;
            }
            val = value;
            dec = 0;
            if (value > 999) {
                val = value / 1000;
                dec = (value % 1000);
                dec = (dec % 10)==0 ? dec/10 : dec;
                dec = (dec % 10)==0 ? dec/10 : dec;
                exp++;
            }
        }
        if (dec == 0) {
            code += val + ohm[exp] + tolerance;
        } else {
            code += val + "." + dec + ohm[exp] + tolerance;
        }
        return code;
    }
}
