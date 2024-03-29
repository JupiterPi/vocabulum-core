package jupiterpi.vocabulum.core.util;

import java.util.ArrayList;
import java.util.List;

public class StringSet extends ArrayList<String> {
    public StringSet() {}

    public StringSet(String... items) {
        super(List.of(items));
    }

    public static StringSet getCharacters(String str) {
        return new StringSet(str.split(""));
    }

    public boolean contains(String str) {
        for (String s : this) {
            if (s.equals(str)) return true;
        }
        return false;
    }

    public int indexOf(String str) {
        for (int i = 0; i < this.size(); i++) {
            String s = this.get(i);
            if (s.equals(str)) return i;
        }
        return -1;
    }
}