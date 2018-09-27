package com.example.purva.vaccineupdate1.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.purva.vaccineupdate1.Model.VaccineTimeTable;
import com.example.purva.vaccineupdate1.R;

import java.util.List;

import static android.content.ContentValues.TAG;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder>{

    private List<VaccineTimeTable> vaccineList;
    private LayoutInflater inflater;
    Context context;

    public recyclerAdapter(Context context, List<VaccineTimeTable> myVaccineList)
    {
        this.context = context;
        this.vaccineList = myVaccineList;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       Log.d(TAG,"onCreateViewHolder");
        View view = inflater.inflate(R.layout.list_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG,"onCreateViewHolder "+position);

        VaccineTimeTable currentObj = vaccineList.get(position);
        holder.getData(currentObj,position);
    }

    @Override
    public int getItemCount() {
        return vaccineList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView title,t;
        ImageView done;
        int position;
        VaccineTimeTable current;
        ViewGroup vg;
        View v;


        public MyViewHolder(View itemView){
            super(itemView);
            t = itemView.findViewById(R.id.t);
            title = (TextView) itemView.findViewById(R.id.VaccineName);
            done = (ImageView) itemView.findViewById(R.id.doneVaccine);
            vg = (ViewGroup) itemView.findViewById(R.id.card_container);
          
                    }

        public void getData(VaccineTimeTable currentObj, int position) {
            this.title.setText(currentObj.getTitle());
            this.t.setText(currentObj.getDescription());
            this.done.setImageResource(currentObj.getImage());
            this.position=position;
            this.current=currentObj;

            //final Animation a = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.mytransition);
            title.setOnClickListener((new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(vg);
                        if(t.getVisibility()==View.GONE){
                            t.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            t.setVisibility(View.GONE);
                        }
                    }
                }
            }));

        }
    }

}
