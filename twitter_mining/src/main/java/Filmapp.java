import org.apache.commons.lang3.StringEscapeUtils;
import twitter4j.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Mine Twitter data for opinions about movies
 * Semester Project for CSCI6250, Spring 2020
 * @author Daniel Murphy, University of New Orleans
 */
public class Filmapp {

    public static int id = 993;

    public static void main(String args[]) throws TwitterException, InterruptedException {

        // add all the movies you want to search to this list
        ArrayList<String> movies = new ArrayList<String>();
        movies.add("theinfiltrators");
        movies.add("workingmanmovie");
        movies.add("saintfrancesmovie");
        movies.add("bluestory");
        movies.add("dreamkatcher");
        movies.add("beanpole");
        movies.add("extraction");
        movies.add("therythmsection");
        movies.add("parasitemovie");
        movies.add("thelastfullmeasure");
        movies.add("parasite");
        movies.add("bloodshotmovie");
        movies.add("catsmovie");
        movies.add("cats");
        movies.add("thehunt");
        movies.add("emmafilm");
        movies.add("elhoyo");
        movies.add("thebanker");
        movies.add("spiesindisguise");
        movies.add("1917");
        movies.add("darkwaters");
        movies.add("uncutgems");
        movies.add("sonicthehedgehogmovie");
        movies.add("badboysforlife");
        movies.add("bloodshot2020");
        movies.add("birdsofprey");
        movies.add("avengersendgame");
        movies.add("jumanji");
        movies.add("thegentlemen");
        movies.add("joker");
        movies.add("frozen2");
        movies.add("riseofskywalker");
        movies.add("underwatermovie");
        movies.add("theinvisibleman");
        movies.add("code8movie");

        Iterator<String> mi = movies.iterator();


        // For each movie, grab as many tweets as you can without going over
        // twitter's limit, then wait 16 minutes to start again
        // repeat until done
        while(mi.hasNext()){
            String name = mi.next();
            System.out.println("Searching:\t " + name);
            searchTwitter(name);
            System.out.println("sleeping...");
            TimeUnit.MINUTES.sleep(16);
            System.out.println("... waking up!");
        }

    }



    public static void searchTwitter(String filename) throws TwitterException {
        String hashtag = "#" + filename;
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query(hashtag);
        query.count(25);
        QueryResult result;
        String xml = "<data>\n";

        do {
            result = twitter.search(query);
            for (Status status : result.getTweets()) {
                if(status.getLang().equals("en")){
                    int t_id = id;
                    id++;

                    // get engagement scores
                    int followers = status.getUser().getFollowersCount();
                    int likes = status.getFavoriteCount();
                    int retweets = status.getRetweetCount();

                    // create xml
                    String text = status.getText().trim().replaceAll(" +", " ");
                    text = text.trim().replaceAll("\\n+", " ");
                    text = StringEscapeUtils.escapeXml(text);
                    String t_xml =  "<tweet>\n" +
                            "   <id>" + t_id + "</id>\n" +
                            "   <followers>" + followers + "</followers>\n" +
                            "   <likes>" + likes + "</likes>\n" +
                            "   <retweets>" + retweets + "</retweets>\n" +
                            "   <text>" + text + "</text>\n" +
                            "</tweet>\n";

                    xml += t_xml;
                }

            }
        } while ((query = result.nextQuery()) != null && checkLimit(twitter));

        xml += "</data>";

        writeFile(xml, filename);


    }

    /**
     * Checks in with twitter server to make sure this account hasn't exceeded its query limit
     * @param twitter
     * @return
     */
    public static boolean checkLimit(Twitter twitter){
        Map<String ,RateLimitStatus> rateLimitStatus = null;
        try {
            rateLimitStatus = twitter.getRateLimitStatus();
            int remaining = rateLimitStatus.get("/search/tweets").getRemaining();
            if (remaining <= 2){
                return false;
            }
            else{
                return true;
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Writes xml based tweet data to disk
     * @param string
     * @param name
     */
    public static void writeFile(String string, String name){
        try {
            FileWriter writer = new FileWriter("output/" + name+".xml");
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(string);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
