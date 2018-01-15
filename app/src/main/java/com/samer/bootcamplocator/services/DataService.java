package com.samer.bootcamplocator.services;

import com.samer.bootcamplocator.model.Data;

import java.util.ArrayList;

/**
 * Created by Samer AlShurafa on 1/14/2018.
 */

public class DataService {

    private static final DataService instance = new DataService();


    public static DataService getInstance() {
        return instance;
    }

    private DataService() {

    }


    public ArrayList<Data> getBootcamoLocationsWithin10MilesOfZip(int zipCode) {
        ArrayList<Data> list = new ArrayList<>();

        list.add(new Data(35.279f,-120.663f,"Downtown",
                "762 Higuera Street, San Luis Obispo, CA 93401","slo"));
        list.add(new Data(35.302f,-120.658f,"On The Campus",
                "1 Grand Ave, San Luis Obispo, CA 93401","slo2"));
        list.add(new Data(35.267f,-120.652f,"East Side Tower",
                "2494 Victoria Ave, San Luis Obispo, CA 93401","slo3"));
        list.add(new Data(35.280f,-120.664f,"Downtown 2",
                "762 Higuera Street, San Luis Obispo, CA 93401","slo"));
        list.add(new Data(35.303f,-120.659f,"On The Campus 2",
                "1 Grand Ave, San Luis Obispo, CA 93401","slo2"));
        list.add(new Data(35.268f,-120.653f,"East Side Tower 2",
                "2494 Victoria Ave, San Luis Obispo, CA 93401","slo3"));

        return list;
    }


}
