package iut.lp2017.acpi.tps_first;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by necesanym on 11/01/17.
 */
public class DataHandler {

    static List<String> imageList;
    static List<String> nameList;
    private static List<String> firstNameList;
    private static DataHandler instance;

    public static DataHandler getInstance() {
        if(instance == null)
            instance = new DataHandler();
        return instance;
    }

    public static List<String> getImageList() {
        return imageList;
    }

    public static void setImageList(List<String> imageList) {
        DataHandler.imageList = imageList;
    }

    public static List<String> getNameList() {
        return nameList;
    }

    public static void setNameList(List<String> nameList) {
        DataHandler.nameList = nameList;
    }

    public static List<String> getFirstNameList() {
        return firstNameList;
    }

    public static void setFirstNameList(List<String> firstNameList) {
        DataHandler.firstNameList = firstNameList;
    }

    public DataHandler(List<String> imageList, List<String> nameList, List<String> prenomlist){
        this.imageList = imageList;
        this.nameList = nameList;
        this.firstNameList = prenomlist;
    }

    public DataHandler(){
        this.imageList = new ArrayList<String>();
        this.nameList =  new ArrayList<String>();
        this.firstNameList =  new ArrayList<String>();
    }


}
