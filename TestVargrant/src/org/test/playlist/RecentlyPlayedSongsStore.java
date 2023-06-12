package org.test.playlist;

import java.util.LinkedHashMap;
import java.util.Map;

public class RecentlyPlayedSongsStore {
    private LinkedHashMap<String, String> songUserPairs;
    private int capacity;

    public RecentlyPlayedSongsStore(int capacity) {
        this.capacity = capacity;
        this.songUserPairs = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > RecentlyPlayedSongsStore.this.capacity;
            }
        };
    }

    public void addSong(String song, String user) {
        songUserPairs.put(song, user);
    }

    public String[] getRecentlyPlayedSongs(String user) {
        return songUserPairs.entrySet().stream()
                .filter(entry -> entry.getValue().equals(user))
                .map(Map.Entry::getKey)
                .toArray(String[]::new);
    }

    public static void main(String[] args) {
        // Create an instance of RecentlyPlayedSongsStore with capacity 3
        RecentlyPlayedSongsStore store = new RecentlyPlayedSongsStore(3);

        // Add songs played by a user
        store.addSong("S1", "User1");
        store.addSong("S2", "User1");
        store.addSong("S3", "User1");

        // Verify the initial playlist
        String[] initialPlaylist = store.getRecentlyPlayedSongs("User1");
        assert initialPlaylist.length == 3;
        assert initialPlaylist[0].equals("S1");
        assert initialPlaylist[1].equals("S2");
        assert initialPlaylist[2].equals("S3");

        // Add a new song, S4
        store.addSong("S4", "User1");

        // Verify the updated playlist after adding S4
        String[] updatedPlaylist1 = store.getRecentlyPlayedSongs("User1");
        assert updatedPlaylist1.length == 3;
        assert updatedPlaylist1[0].equals("S2");
        assert updatedPlaylist1[1].equals("S3");
        assert updatedPlaylist1[2].equals("S4");

        // Add another song, S2
        store.addSong("S2", "User1");

        // Verify the updated playlist after adding S2
        String[] updatedPlaylist2 = store.getRecentlyPlayedSongs("User1");
        assert updatedPlaylist2.length == 3;
        assert updatedPlaylist2[0].equals("S3");
        assert updatedPlaylist2[1].equals("S4");
        assert updatedPlaylist2[2].equals("S2");

        // Add one more song, S1
        store.addSong("S1", "User1");

        // Verify the updated playlist after adding S1
        String[] updatedPlaylist3 = store.getRecentlyPlayedSongs("User1");
        assert updatedPlaylist3.length == 3;
        assert updatedPlaylist3[0].equals("S4");
        assert updatedPlaylist3[1].equals("S2");
        assert updatedPlaylist3[2].equals("S1");

        System.out.println("All assertions passed!");
    }
}