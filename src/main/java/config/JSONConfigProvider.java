package config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class JSONConfigProvider implements ConfigProvider{
    private final Config config;
    public JSONConfigProvider(File file) throws FileNotFoundException
    {
        config = new Gson().fromJson(new JsonReader(new FileReader(file)), Config.class);
    }
    @Override
    public Set<Character> getDesiredChars() {
        return Arrays.stream(config.getDesiredCharacters()
                .split(","))
                .flatMapToInt(String::chars)
                .mapToObj(c->(char) c)
                .collect(Collectors.toSet());
    }

    @Override
    public String getOriginalSequence() {
        return config.getSentence();
    }
}
