package laborai.demo;

import laborai.gui.swing.Lab4Window;
import java.util.Locale;

/*
 * Darbo atlikimo tvarka - čia yra pradinė klasė.
 */
public class VykdymoModulis {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        AutoTestai.atvaizdzioTestas();
        Lab4Window.createAndShowGUI();
    }
}
