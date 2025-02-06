package file.writer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import user.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserWriter implements Writer<User> {
    @Override
    public void writeToFile(User toAdd) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<User>>() {

        }.getType();
        List<User> userList = new ArrayList<>();

        try (FileReader reader = new FileReader("src/data/users.json")) {
            userList = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userList.add(toAdd);

        try (FileWriter writer = new FileWriter("src/data/users.json")) {
            gson.toJson(userList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
