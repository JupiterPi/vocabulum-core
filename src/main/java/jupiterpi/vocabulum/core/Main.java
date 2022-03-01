package jupiterpi.vocabulum.core;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.portions.Portion;
import jupiterpi.vocabulum.core.portions.PortionManager;
import jupiterpi.vocabulum.core.vocabularies.declinated.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionClasses;

import java.util.Map;

public class Main {
    public static I18n i18n = I18n.de;
    public static PortionManager portionManager;

    public static void main(String[] args) throws LoadingDataException {
        System.out.println("----- Vocabulum Core -----");

        DeclensionClasses.loadDeclensionSchemas();
        portionManager = new PortionManager();
        Map<String, Portion> portions = portionManager.getPortions();
        for (String key : portions.keySet()) {
            Portion portion = portions.get(key);
            System.out.println(portion);
        }

        //Terminal terminal = new Terminal();
        //terminal.run();
    }
}
