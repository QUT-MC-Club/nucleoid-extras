package xyz.nucleoid.extras.model;

import net.minecraft.util.Identifier;
import xyz.nucleoid.extras.NucleoidExtras;

public class NEModels {
    public static final Identifier CONTROLLER = of("controller");
    public static final Identifier DARULES = of("darules");

    private static Identifier of(String path) {
        return NucleoidExtras.identifier(path);
    }
}
