package config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Config {
    private String desiredCharacters;
    private String sentence;
}
