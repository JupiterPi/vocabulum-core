package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatternTest {
    @Test
    void fromString() {
        Pattern pattern = Pattern.fromString("PPP+Pr+ str");
        assertEquals(new Pattern(
                new Particle(Particle.Type.PARTICIPLE, "PPP"),
                new Particle(Particle.Type.ROOT, "Pr"),
                new Particle(Particle.Type.STRING, " str")
        ), pattern);
    }

    @Nested
    @DisplayName("exists()")
    class Exists {

        @Test
        void exists() {
            Pattern pattern = Pattern.fromString("str");
            assertTrue(pattern.exists());
        }

        @Test
        void doesntExist() {
            Pattern pattern = Pattern.fromString("-");
            assertFalse(pattern.exists());
        }

    }

    @Test
    void make() {
        Pattern pattern = new Pattern(
                new Particle(Particle.Type.PARTICIPLE, "PPP"),
                new Particle(Particle.Type.ROOT, "Pr"),
                new Particle(Particle.Type.STRING, " str")
        );
        VerbInfo info = new VerbInfo("voc", "vocav", "vocatum", "vocati", "vocans", "vocantes", "vocaturus", "vocaturi");
        assertEquals("vocatumvoc str", pattern.make(info));
    }
}