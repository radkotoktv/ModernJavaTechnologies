package file.reader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import exception.FileReaderException;
import user.User;

import java.io.FileReader;
import java.io.IOException;

import java.util.List;

public final class UsersReader extends Reader {
    private static volatile UsersReader instance;

    private UsersReader(String filePath) {
        super(filePath);
    }

    public static UsersReader getInstance(String filePath) {
        UsersReader result = instance;
        if (result != null) {
            return instance;
        }

        synchronized (UsersReader.class) {
            if (instance == null) {
                instance = new UsersReader(filePath);
            }
            return instance;
        }
    }

    @Override
    public List<User> readFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, new TypeToken<List<User>>() {

            }.getType());
        } catch (IOException e) {
            throw new FileReaderException("Error reading from file in UsersReader", e);

        }
    }
}
