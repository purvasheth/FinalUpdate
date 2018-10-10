package com.example.purva.vaccineupdate1.Model;


import com.example.purva.vaccineupdate1.R;

public class VaccineTimeTable {

    private String cost;
    private String vac_name;
    private String after;
    private boolean flag;
    private int image;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getVac_name() {
        return vac_name;
    }

    public void setVac_name(String vac_name) {
        this.vac_name = vac_name;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getImage() {
        int image = R.drawable.baseline_done_black_18;
        return image;
    }

    public int getImageUndo() {
        int image = R.drawable.baseline_undo_black_18dp;
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
