package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParticleTest {
    @Nested
    @DisplayName("fromString()")
    class FromString {

        @Test
        @DisplayName("string")
        void string() {
            Particle particle = Particle.fromString(" str");
            assertEquals(new Particle(Particle.Type.STRING, " str"), particle);
        }

        @Test
        @DisplayName("root")
        void root() {
            Particle particle = Particle.fromString("Pr");
            assertEquals(new Particle(Particle.Type.ROOT, "Pr"), particle);
        }

        @Test
        @DisplayName("participle")
        void participle() {
            Particle particle = Particle.fromString("PPP");
            assertEquals(new Particle(Particle.Type.PARTICIPLE, "PPP"), particle);
        }

    }

    @Nested
    @DisplayName("make()")
    class Make {

        VerbInfo info;

        @BeforeEach
        void init() {
            info = new VerbInfo(
                    "voc", "vocav",
                    "vocatum", "vocati", "vocans", "vocantes", "vocaturus", "vocaturi",
                    true, true
            );
        }

        @Test
        @DisplayName("string")
        void string() {
            assertEquals("str", new Particle(Particle.Type.STRING, "str").make(info));
        }

        @Test
        @DisplayName("root")
        void root() {
            assertEquals("voc", new Particle(Particle.Type.ROOT, "Pr").make(info));
        }

        @Test
        @DisplayName("participle")
        void participle() {
            assertEquals("vocatum", new Particle(Particle.Type.PARTICIPLE, "PPP").make(info));
        }

    }
}