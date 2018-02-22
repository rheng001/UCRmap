package ucr.ucrmap;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class web_Crawl_Events extends AppCompatActivity {

    List<recycler_information> ucrEvents;
    List<recycler_information> ucrDays;


    Map<Integer,String> event_map=new HashMap<Integer,String>();
    private static final String TAG ="web_Crawl_Events";

    public web_Crawl_Events()
    {
        ucrEvents = new ArrayList<>();
        ucrDays = new ArrayList<>();
        new doit().execute();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ucrEvents = new ArrayList<>();


    }


    public class doit extends AsyncTask<Void,ArrayList<String>,ArrayList<String>> { //Returning string

        String title, date;
        Document document,document1;
        int count;
        ArrayList<String> Dates = new ArrayList<String>();
        ArrayList<String> Title = new ArrayList<String>();
        ArrayList<String> Building = new ArrayList<String>();
        ArrayList<String> Room = new ArrayList<String>();
        Elements eventcontent, eventlist,testlinks,testloc1;
        Element testtable,testloc;
        int counter= 0;
        int counter_row= 0;
        String link1,new_loc,event_info,room;

        @Override
        protected ArrayList<String> doInBackground(Void... arg0) { //Want string returned

            try {
                document = Jsoup.connect("http://events.ucr.edu/cgi-bin/display.cgi").get();
                title = document.title();
                eventcontent = document.getElementsByClass("eventcontent");
                eventlist = document.select("div#eventlist");
                Node n;
                for (Element link : eventcontent.select("h3")) {
                    date = link.ownText();
                    Dates.add(date);
                    Log.i("day: ", date);
                    ucrDays.add(new recycler_information(3,date));
                    testtable = document.select("div#eventlist > table").get(counter);
                    for (Element rows : testtable.select("tr")) {

                        event_info = rows.text().toString().replace("</td>","");
                        Log.i("event info: ", rows.text());
                        testloc = testtable.select("a").get(counter_row);
                        String link1 = testloc.attr("abs:href");
                        Log.i("link: ",link1);



                        document1 = Jsoup.connect(link1).get();
                        testloc1 = document1.getElementsByClass("eventcontent");
                        Element link2 = testloc1.select("span").first(); // gets location
                        n =  link2.nextSibling();
                        if(n.toString().contains("<a href"))
                        {
                            link2 = testloc1.select("a").first();
                            if(link2.text().contains("Additional"))
                            {
                                link2 = testloc1.select("a").get(1);
                            }
                            new_loc = link2.text().toString().replace(" Building","");
                            new_loc.replace(":","");
                            new_loc = new_loc.toUpperCase();
                            Log.i("else: ",new_loc);
                            switch(new_loc){
                                case "PHYSICS": //adds a space when you put replace
                                    new_loc = "PHY";
                                    break;
                                case "CHASS INTERDISCIPLINARY BLDG SOUTH":
                                    new_loc = "INTS";
                                    break;
                                case "CHUNG HALL":
                                    new_loc = "CHUNG";
                                    break;
                                case "UNIVERSITY THEATRE":
                                    new_loc = "UV_THEATRE";
                                    break;
                                default:
                                    break;
                            }
                            Log.i("location: ",new_loc);
                            n = link2.nextSibling();
                            room = n.toString();
                            Log.i("room number: ", room);
                        }
                        else
                        {
                            new_loc = n.toString().replace("Building","");
                            new_loc.replace(":","");
                            new_loc = new_loc.toUpperCase();
                            switch(new_loc){
                                case "PHYSICS":
                                    new_loc = "PHY";
                                    break;
                                case "CHASS INTERDISCIPLINARY BLDG SOUTH":
                                    new_loc = "INTS";
                                    break;
                                case "CHUNG HALL":
                                    new_loc = "CHUNG";
                                    break;
                                case "UNIVERSITY THEATRE":
                                    new_loc = "UV_THEATRE";
                                    break;
                                default:
                                    break;
                            }
                            Log.i("location: ", new_loc);
                        }
                        counter_row = counter_row + 1;

                    }

                    counter_row = 0;
                    counter = counter + 1;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return Dates;
        }

        protected void onPostExecute(ArrayList<String> str) {
            super.onPostExecute(str);
            for (int i = 0; i < Dates.size(); i++) {
                System.out.println(Dates.get(i));
            }

        }

    }
}
