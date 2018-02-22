package ucr.ucrmap;

/**
 * Created by Richard on 9/4/2017.
 */

public class recycler_information {

    //Class Recycler View Data
    String classNameData;
    String buildingDescriptionData;
    String roomDescriptionData;
    String startTimeData;
    String endTimeData;

    //Friend Recycler View Data
    String friendNameData;
    String friendGenderData;
    int friendIDData;
    int friendIconId;

    //Personal Event Recycler View Data
    String eventTitleData;
    String eventDateData;
    String eventTimeData;



    // UCR Event Recycler View Data
    String eventTitle;
    String eventBuilding;
    String eventRoom;
  //  String eventDay;


    // UCR EVENT DAY
    String eventDay;



    //Places RecyclerView
    String placeNameData;
    int placeIconId;

    int layoutType;
    ////////////////////////////////////////////done initialization


    //default
    public recycler_information()
    {

    }

    public int getLayoutType()
    {
        return layoutType;
    }

    //classroom constructor
    public recycler_information(String classNameData, String buildingDescriptionData, String roomDescriptionData, String startTimeData, String endTimeData, int layoutType)
    {
        this.classNameData = classNameData;
        this.buildingDescriptionData = buildingDescriptionData;
        this.roomDescriptionData = roomDescriptionData;
        this.startTimeData = startTimeData;
        this.endTimeData = endTimeData;
        this.layoutType = layoutType;

    }

    //classroom methods
    public String getClassNameData() {
        return classNameData;
    }

    public String getBuildingDescriptionData() {
        return buildingDescriptionData;
    }
    public String getStartTimeData() {
        return startTimeData;
    }
    public String getEndTimeData() {
        return endTimeData;
    }
    /////////////////////

    //friend
    public recycler_information(String friendNameData, int friendIDData, String friendGenderData, int friendIconId, int layoutType)
    {
        this.friendNameData = friendNameData;
        this.friendIDData = friendIDData;
        this.friendGenderData = friendGenderData;
        this.friendIconId = friendIconId;
        this.layoutType = layoutType;
    }

    public String getFriendNameData() {
        return friendNameData;
    }
    public int getFriendIDData(){
        return friendIDData;
    }

    public String getFriendGenderData() {
        return friendGenderData;
    }
    public int getFriendIconId() {
        return friendIconId;
    }
    //////////////////////////////////

    //event
    public recycler_information(String eventTitleData, String eventDateData, String eventTimeData, int layoutType)
    {
        this.eventTitleData = eventTitleData;
        this.eventDateData = eventDateData;
        this.eventTimeData = eventTimeData;
        this.layoutType = layoutType;
    }
    public String getEventTitleData() {
        return eventTitleData;
    }
    public String getEventDateData() {
        return eventDateData;
    }
    public String getEventTimeData() {
        return eventTimeData;
    }
    /////////////////////////////////////////


    // UCR EVENTS
    public recycler_information(int layoutType,String eventTitle, String eventBuilding, String eventRoom)
    {
        //this.eventDay = eventDay;
        this.eventTitle = eventTitle;
        this.eventBuilding = eventBuilding;
        this.eventRoom = eventRoom;
        this.layoutType = layoutType;
    }
    public String getEventTitle() {
        return eventTitle;
    }
    public String getEventBuilding() {
        return eventBuilding;
    }
    public String getEventRoom() {
        return eventRoom;
    }



    public recycler_information(int layoutType, String eventDay)
    {
        this.layoutType = layoutType;
        this.eventDay = eventDay;
    }
    public String getEventDay() {
        return eventDay;
    }

    //places
    public recycler_information(String placeNameData, int placeIconId, int layoutType)
    {
        this.placeNameData = placeNameData;
        this.placeIconId = placeIconId;
        this.layoutType = layoutType;
    }
    public String getPlaceNameData() {
        return placeNameData;
    }

    public int getPlaceIconId() {
        return placeIconId;
    }
    ///////////////////////////////

    @Override
    public String toString() {
        return ", Event Day " + this.getEventDay();
        //+
              //  ", Event Building " + this.getEventBuilding()+
               // ", Event Title " + this.getEventTitle();
    }

}
