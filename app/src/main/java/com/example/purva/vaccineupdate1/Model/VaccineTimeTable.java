package com.example.purva.vaccineupdate1.Model;

import com.example.purva.vaccineupdate1.R;

import java.util.ArrayList;

public class VaccineTimeTable {


    private String title;
    private int image;
    boolean flag;
    private String description;

/*
    public static Date valueOf (String s)
    {
       Date date = new Date();
        return date;
    }*/
public void setFlag(boolean flag1)
{
    this.flag=flag1;
}

    public boolean getFlag() {
        return flag;
    }

    public void setTitle(String title) {
        this.title = title;
    }
//getter and setter
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description=description;
    }
    public void setImage(int image)
    {
        this.image = image;
    }

    public int getImage()
    {
        return image;
    }
    public String getTitle()
    {
        return title;
    }

    public static ArrayList<VaccineTimeTable> getdata(){

        ArrayList<VaccineTimeTable> datalist = new ArrayList<>();
        for(int i=0;i<10;i++)
        {

            VaccineTimeTable table = new VaccineTimeTable();
            table.setFlag(true);
            table.setTitle("Vaccine "+ i);
            table.setDescription("Description "+i);
            datalist.add(table);
            int images = getImages(table.getFlag());
            table.setImage(images);
        }
        return datalist;
    }


    public static int getImages(boolean flag)
    {
        int imagesUndo = R.drawable.baseline_undo_black_18dp;
        int imagesDone = R.drawable.baseline_done_black_18;
        if(!flag)
        {
            return imagesDone;
        }
        else
        {
            return imagesUndo;
        }


    }
}
