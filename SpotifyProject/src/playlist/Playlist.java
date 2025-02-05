package playlist;

import song.Song;

import java.util.ArrayList;

public record Playlist(String name, String owner, int duration, int numberOfSongs, ArrayList<Song> songs, int amountOfPlayes) { }
