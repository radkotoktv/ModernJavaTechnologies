package file.reader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import exception.FileReaderException;
import user.User;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UsersReader implements Reader {
    @Override
    public ArrayList<User> readFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("src/data/users.json")) {
            return gson.fromJson(reader, new TypeToken<ArrayList<User>>() {

            }.getType());
        } catch (IOException e) {
            throw new FileReaderException("Error reading from file in UsersReader");

        }
    }
}
