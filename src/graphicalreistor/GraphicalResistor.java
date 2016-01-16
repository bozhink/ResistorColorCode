package graphicalreistor;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

/**
 * @author bozhin
 */
public class GraphicalResistor extends MIDlet implements CommandListener
{
    private ResistorCanvas resistor = new ResistorCanvas();

    private String [] colorCommand = {"BLACK", "BROWN", "RED", "ORANGE", 
        "YELLOW", "GREEN", "BLUE", "VIOLET", "GREY", "WHITE", "GOLD", "SILVER"};
    
    private Command exitCommand = new Command("Exit", Command.EXIT, 0);
    private Command commandBlack = new Command(colorCommand[0], colorCommand[0], Command.SCREEN, 0);
    private Command commandBrown = new Command(colorCommand[1], colorCommand[1], Command.SCREEN, 0);
    private Command commandRed = new Command(colorCommand[2], colorCommand[2], Command.SCREEN, 0);
    private Command commandOrange = new Command(colorCommand[3], colorCommand[3], Command.SCREEN, 0);
    private Command commandYellow = new Command(colorCommand[4], colorCommand[4], Command.SCREEN, 0);
    private Command commandGreen = new Command(colorCommand[5], colorCommand[5], Command.SCREEN, 0);
    private Command commandBlue = new Command(colorCommand[6], colorCommand[6], Command.SCREEN, 0);
    private Command commandViolet = new Command(colorCommand[7], colorCommand[7], Command.SCREEN, 0);
    private Command commandGrey = new Command(colorCommand[8], colorCommand[8], Command.SCREEN, 0);
    private Command commandWhite = new Command(colorCommand[9], colorCommand[9], Command.SCREEN, 0);
    private Command commandGold = new Command(colorCommand[10], colorCommand[10], Command.SCREEN, 0);
    private Command commandSilver = new Command(colorCommand[11], colorCommand[11], Command.SCREEN, 0);
    private Command cancelCommand = new Command("Cancel", Command.SCREEN, 0);
    private Command backCommand = new Command("Back", Command.SCREEN, 0);

    public void startApp() {
        resistor.addCommand(exitCommand);
        resistor.addCommand(backCommand);
        resistor.addCommand(commandBlack);
        resistor.addCommand(commandBrown);
        resistor.addCommand(commandRed);
        resistor.addCommand(commandOrange);
        resistor.addCommand(commandYellow);
        resistor.addCommand(commandGreen);
        resistor.addCommand(commandBlue);
        resistor.addCommand(commandViolet);
        resistor.addCommand(commandGrey);
        resistor.addCommand(commandWhite);
        resistor.addCommand(commandGold);
        resistor.addCommand(commandSilver);
        resistor.addCommand(cancelCommand);
        resistor.setCommandListener(this);
        Display.getDisplay(this).setCurrent(resistor);
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if (displayable == resistor) {
            if (command == cancelCommand) {
                CancelCommand();
            }
            else if (command == backCommand) {
                BackCommand();
            }
            else if (command == exitCommand) {
                destroyApp(true);
                notifyDestroyed();
            }
            else if (command == commandBlack) {
                ExecCommand(0); // Black color selected
            }
            else if (command == commandWhite) {
                ExecCommand(9); // White color selected
            }
            else if (command == commandGold) {
                ExecCommand(10); // Gold color selected
            }
            else if (command == commandSilver) {
                ExecCommand(11); // Silver color selected
            }
            else if (command == commandBrown) {
                ExecCommand(1); // Brown color selected
            }
            else if (command == commandRed) {
                ExecCommand(2); // Red color selected
            }
            else if (command == commandOrange) {
                ExecCommand(3); // Orange color selected
            }
            else if (command == commandYellow) {
                ExecCommand(4); // Yellow color selected
            }
            else if (command == commandGreen) {
                ExecCommand(5); // Green color selected
            }
            else if (command == commandBlue) {
                ExecCommand(6); // Blue color selected
            }
            else if (command == commandViolet) {
                ExecCommand(7); // Violet color selected
            }
            else if (command == commandGrey) {
                ExecCommand(8); // Grey color selected
            }
        }
    }
    
    private void BackCommand() {
        // Clears last band
        if (resistor.currentBand > resistor.maxNumberOfBands) {
            resistor.currentBand = resistor.maxNumberOfBands;
        } else if (resistor.currentBand < 1) {
            CancelCommand();
            return;
        }
        resistor.bands[--resistor.currentBand] = 12;
        resistor.setWarning = false;
        resistor.repaint();
    }

    private void CancelCommand() {
        // Clears all content
        for (int i = 0; i < resistor.maxNumberOfBands; i++) {
            resistor.bands[i] = 12;
        }
        resistor.setWarning = false;
        resistor.currentBand = 0;
        resistor.repaint();
    }
    
    private void ExecCommand(int colorNumber) {
        if (resistor.currentBand >= resistor.maxNumberOfBands) {
            resistor.setWarning = true;
            resistor.repaint();
        } else {
            resistor.bands[resistor.currentBand] = colorNumber;
            resistor.repaint();
        }
        resistor.currentBand++;
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
