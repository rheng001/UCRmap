package ucr.ucrmap;

/**
 * Created by jorge on 2/25/18.
 */

public class Student {

    String name;
    String id;
    String gender;
    String num;


    public Student(String name, String id, String gender,String num) {
        this.name = name;
        this.id = id;
        this.gender = gender;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
