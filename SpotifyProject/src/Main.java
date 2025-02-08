import playlist.Playlist;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Playlist playlist = new Playlist("My Playlist", "Me", 0, 0, new ArrayList<>(), 0);
        System.out.println(playlist.toString());
    }
}