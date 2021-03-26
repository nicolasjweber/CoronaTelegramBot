package de.weber;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Webpage {

    private final URL url;

    public Webpage(String link) throws MalformedURLException {
        this.url = new URL(link);
    }

    public String getFreiburgCases() throws IOException {
        URLConnection connection = this.url.openConnection();
        InputStream stream = connection.getInputStream();

        Scanner scanner = new Scanner(stream);

        String output = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("SK Freiburg im Breisgau</a>")) {
                String[] parts = line.split("SK Freiburg im Breisgau</a>");
                String[] parts2 = parts[1].split("</li>");
                output = parts2[0].replace("(","").replace(")","");
                break;
            }
        }

        return output;
    }

    public String getRPCases() throws IOException {
        URLConnection connection = this.url.openConnection();
        InputStream stream = connection.getInputStream();

        Scanner scanner = new Scanner(stream);
        String output = "";

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("Rhein-Pfalz-Kreis")) {
                String[] parts = line.split("<p>");
                String[] parts2 = parts[119].split("</p>");
                output = parts2[0];
                break;
            }
        }

        return output;
    }

    public String getWormsCases() throws IOException {
        URLConnection connection = this.url.openConnection();
        InputStream stream = connection.getInputStream();

        Scanner scanner = new Scanner(stream);
        String output = "";

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("Worms")) {
                String[] parts = line.split("<p>");
                String[] parts2 = parts[221].split("</p>");
                output = parts2[0];
                break;
            }
        }

        return output;
    }

}
