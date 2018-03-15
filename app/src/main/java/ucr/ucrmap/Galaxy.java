package ucr.ucrmap;

/**
 * Created by Oclemy on 2017 for ProgrammingWizards TV Channel and http://www.camposha.info.
 - Our data object
 - We pass it data via constructor.
 - We retrieve its data via getters.
 */

public class Galaxy {

    private String name, description, time, link;
    private Category category;

    public Galaxy()
    {

    }

    public Galaxy(String name, String description, String time, /*String link,*/ Category category) {
        this.name = name;
        this.description = description;
        this.time = time;
        //this.link = link;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getLink() { return link; }



    public int getCategoryId() {
        return category.getId();
    }

    public String getCategoryName() {
        return category.getName();
    }
}
