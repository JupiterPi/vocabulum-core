package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.mail.Part;

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

    @Nested
    @DisplayName("getSuffix()")
    class GetSuffix {

        @Test
        @DisplayName("empty")
        void empty() {
            Pattern pattern = new Pattern();
            assertNull(pattern.getSuffix());
        }

        @Test
        @DisplayName("only suffix")
        void onlySuffix() {
            Pattern pattern = new Pattern(
                    new Particle(Particle.Type.STRING, "sfx")
            );
            assertEquals("sfx", pattern.getSuffix());
        }

        @Test
        @DisplayName("something before suffix")
        void somethingBeforeSuffix() {
            Pattern pattern = new Pattern(
                    new Particle(Particle.Type.ROOT, "Pr"),
                    new Particle(Particle.Type.STRING, "sfx")
            );
            assertEquals("sfx", pattern.getSuffix());
        }

        @Test
        @DisplayName("no suffix")
        void noSuffix() {
            Pattern pattern = new Pattern(
                    new Particle(Particle.Type.STRING, "sfx"),
                    new Particle(Particle.Type.ROOT, "Pr")
            );
            assertNull(pattern.getSuffix());
        }

    }

    @DisplayName("make()")
    class Make {

        @Test
        @DisplayName("simple")
        void simple() {
            Pattern pattern = new Pattern(
                    new Particle(Particle.Type.PARTICIPLE, "PPP"),
                    new Particle(Particle.Type.ROOT, "Pr"),
                    new Particle(Particle.Type.STRING, " str")
            );
            VerbInfo info = new VerbInfo(
                    "voc", "vocav",
                    "vocatum", "vocati", "vocans", "vocantes", "vocaturus", "vocaturi",
                    true, true
            );
            assertEquals("vocatumvoc str", pattern.make(info));
        }

        @Test
        @DisplayName("perfect/ppp don't exist")
        void perfectPppDontExist() {
            Pattern pattern = new Pattern(
                    new Particle(Particle.Type.ROOT, "Pf"),
                    new Particle(Particle.Type.STRING, "str")
            );
            VerbInfo info = new VerbInfo(
                    "voc", "-",
                    "-", "-", "-", "-", "-", "-",
                    false, false
            );
            assertNull(pattern.make(info));
        }

    }
}