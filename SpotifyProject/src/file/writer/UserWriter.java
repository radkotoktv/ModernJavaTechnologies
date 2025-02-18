package file.writer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exception.FileReaderException;
import exception.FileWriterException;
import user.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class UserWriter extends Writer<User> {
    private static volatile UserWriter instance;

    private UserWriter(String filePath) {
        super(filePath);
    }

    public static UserWriter getInstance(String filePath) {
        UserWriter result = instance;
        if (result != null) {
            return result;
        }

        synchronized (UserWriter.class) {
            if (instance == null) {
                instance = new UserWriter(filePath);
            }
            return instance;
        }
    }

    @Override
    public void writeToFile(User toAdd) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<User>>() {

        }.getType();
        List<User> userList;

        try (FileReader reader = new FileReader(filePath)) {
            userList = gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new FileReaderException("Error reading from file in UserWriter", e);
        }
        userList.add(toAdd);

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(userList, writer);
        } catch (IOException e) {
            throw new FileWriterException("Error writing to file in UserWriter", e);
        }
    }
}
