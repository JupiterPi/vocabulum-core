package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Conjugation schema Pattern:
 * <p>
 * - every form consists of parts
 * <p>
 * - parts in FormInfos are separated by "+"
 * <p>
 * - parts can be:
 * <p>
 *   - just text
 *   <p>
 *   - "Pr", "Pf" -> will be replaced with that tense's root
 *   <p>
 *   - "PPP", "PPA", "PFA" -> will be replaced with that noun-like form (in Nom. Sg. m.)
 *   <p>
 * - exceptions:
 * <p>
 *   - "-" -> form does not exist
 *   <p>
 *  (- "." -> get form info from parent)
 */
public class Pattern extends ArrayList<Particle> {
    private boolean exists = true;

    public Pattern(Particle... particles) {
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
            String particleStr = particle.make(info);
            if (particleStr == null) return null;
            str += particleStr;
        }
        return str;
    }

    @Override
    public String toString() {
        return "{" + this.stream().map(Particle::toString).collect(Collectors.joining(",")) + "}";
    }
}