package com.example.purva.vaccineupdate1.Adapter;

import com.example.purva.vaccineupdate1.Model.VaccineTimeTable;

interface MyViewHolder {
    void getData(VaccineTimeTable currentObj, int position);

    void setListeners();

    void onClick();
}
