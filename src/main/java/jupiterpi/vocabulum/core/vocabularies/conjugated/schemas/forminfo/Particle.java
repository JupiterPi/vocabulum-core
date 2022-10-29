package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo;

import java.util.Objects;

public class Particle {
    private Type type;
    private String content;

    public enum Type {
        STRING, ROOT, PARTICIPLE
    }

    public static final String ROOT_PRESENT = "Pr";
    public static final String ROOT_PERFECT = "Pf";

    public static final String PARTICIPLE_PPP_SG = "PPP";
    public static final String PARTICIPLE_PPP_PL = "PPPs";
    public static final String PARTICIPLE_PPA_SG = "PPA";
    public static final String PARTICIPLE_PPA_PL = "PPAs";
    public static final String PARTICIPLE_PFA_SG = "PFA";
    public static final String PARTICIPLE_PFA_PL = "PFAs";

    /* constructors */

    public Particle(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public static Particle fromString(String str) {
        return new Particle(switch (str) {
            case ROOT_PRESENT, ROOT_PERFECT -> Type.ROOT;
            case PARTICIPLE_PPP_SG, PARTICIPLE_PPP_PL, PARTICIPLE_PPA_SG, PARTICIPLE_PPA_PL, PARTICIPLE_PFA_SG, PARTICIPLE_PFA_PL -> Type.PARTICIPLE;
            default -> Type.STRING;
        }, str);
    }

    /* getters */

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    /* make */

    public String make(VerbInfo info) {
        return switch (type) {
            case STRING -> content;
            case ROOT -> switch (content) {
                case ROOT_PRESENT -> info.getPresentRoot();
                case ROOT_PERFECT -> info.perfectExists() ? info.getPerfectRoot() : null;
                default -> null;
            };
            case PARTICIPLE -> info.pppExists() ? switch (content) {
                case PARTICIPLE_PPP_SG -> info.getPpp_sg();
                case PARTICIPLE_PPP_PL -> info.getPpp_pl();
                case PARTICIPLE_PPA_SG -> info.getPpa_sg();
                case PARTICIPLE_PPA_PL -> info.getPpa_pl();
                case PARTICIPLE_PFA_SG -> info.getPfa_sg();
                case PARTICIPLE_PFA_PL -> info.getPfa_pl();
                default -> null;
            } : null;
        };
    }

    /* equals */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return type == particle.type && Objects.equals(content, particle.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, content);
    }

    /* to string */

    @Override
    public String toString() {
        return type + " \"" + content + "\"";
    }
}