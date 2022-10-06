import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

class Handler implements URLHandler {
    ArrayList<String> contents = new ArrayList<String>();
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String returner = "All Uploaded Strings";
            for(String s: contents) {
                returner += "\n" + s;
            }
            return returner;
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("&");
                String returner = "Added:";
                for(String s: parameters) {
                    contents.add(s);
                    returner+="\n" + s;
                }
                return returner;
            } else if(url.getPath().contains("/search")) {
                String parameters = url.getQuery();
                System.out.println("yes");
                String returner = "Results for: " + parameters;
                for(String s: contents) {
                    if(s.contains(parameters)) {
                        returner+="\n" + s;
                    }
                    
                }
                return returner;
            }
            return "404 Not Found!";
        }
    }
}