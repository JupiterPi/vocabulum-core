package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo;

import java.util.ArrayList;
import java.util.List;

// Conjugation schema Pattern:
//
// - every form consists of parts
// - parts in FormInfos are separated by "+"
// - parts can be:
//   - just text
//   - "Pr", "Pf" -> will be replaced with that tense's root
//   - "PPP", "PPA", "PFA" -> will be replaced with that noun-like form (in Nom. Sg. m.)
// - exceptions:
//   - "-" -> form does not exist
//  (- "." -> get form info from parent)

public class Pattern extends ArrayList<Particle> {
    private boolean exists = true;

    public Pattern (Particle... particles) {
        super(List.of(particles));
    }

    public Pattern(boolean exists) {
        this.exists = exists;
    }

    public static final String FORM_DOES_NOT_EXIST_SYMBOL = "-";

    public static Pattern fromString(String str) {
        if (str.equals(FORM_DOES_NOT_EXIST_SYMBOL)) {
            return new Pattern(false);
        }

        Pattern pattern = new Pattern();
        for (String particleStr : str.split("\\+")) {
            Particle particle = Particle.fromString(particleStr);
            pattern.add(particle);
        }
        return pattern;
    }

    public boolean exists() {
        return exists;
    }

    public String getSuffix() {
        if (size() == 0) return null;
        Particle last = get(size() - 1);
        if (last.getType() == Particle.Type.STRING) {
            return last.getContent();
        } else {
            return null;
        }
    }

    public String make(VerbInfo info) {
        if (!exists()) {
            return null;
        }
        String str = "";
        for (Particle particle : this) {
            str += particle.make(info);
        }
        return str;
    }
}