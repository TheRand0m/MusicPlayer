package app.musicplayer.model;

import app.musicplayer.util.Resources;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

/**
 * Model class for an Artist
 *
 */
public final class Artist implements Comparable<Artist> {

    private String title;
    private ArrayList<Album> albums;
    private Image artistImage;
    private SimpleObjectProperty<Image> artistImageProperty;

    /**
     * Constructor for the Artist class.
     * Creates an artist object and obtains the artist artwork.
     *
     * @param title Artist name
     * @param albums List of artist albums
     */
    public Artist(String title, ArrayList<Album> albums) {
        this.title = title;
        this.albums = albums;
        this.artistImageProperty = new SimpleObjectProperty<>(getArtistImage());
    }

    /**
     * Gets the artist title.
     * @return artist title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets array list of artist albums
     * @return artist albums
     */
    public ArrayList<Album> getAlbums() {
        return new ArrayList<>(this.albums);
    }

    public ObjectProperty<Image> artistImageProperty() {
        return this.artistImageProperty;
    }

    /**
     * Gets images for artists
     * @return artist image
     */
    public Image getArtistImage() {
        if (artistImage == null) {
            try {
                File file = new File(Resources.JAR + "/img/" + this.title + ".jpg");
                artistImage = new Image(file.toURI().toURL().toString());
                if (artistImage.isError()) {
                    file.delete();
                    artistImage = new Image(Resources.IMG + "artistsIcon.png");
                }
            } catch (Exception ex) {
                File file = new File(Resources.JAR + "/img/" + this.title + ".jpg");
                file.delete();
                artistImage = new Image(Resources.IMG + "artistsIcon.png");
            }
        }

        return artistImage;
    }

    @Override
    public int compareTo(Artist other) {
        String first = removeArticle(this.title);
        String second = removeArticle(other.title);

        return first.compareTo(second);
    }

    private String removeArticle(String title) {

        String arr[] = title.split(" ", 2);

        if (arr.length < 2) {
            return title;
        } else {

            String firstWord = arr[0];
            String theRest = arr[1];

            switch (firstWord) {
                case "A":
                case "An":
                case "The":
                    return theRest;
                default:
                    return title;
            }
        }
    }
}