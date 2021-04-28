package config;

import java.util.Set;

public interface ConfigProvider {
    Set<Character> getDesiredChars();
    String getOriginalSequence();
}
