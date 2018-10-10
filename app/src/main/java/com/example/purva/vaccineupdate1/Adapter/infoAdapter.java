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
import android.widget.TextView;

import com.example.purva.vaccineupdate1.Model.PointsToRemember;

import com.example.purva.vaccineupdate1.R;

import java.util.List;

import static android.content.ContentValues.TAG;


public class infoAdapter extends RecyclerView.Adapter<infoAdapter.MyViewHolder> {

    private List<PointsToRemember> pointsList;
    private LayoutInflater inflater;
    Context context;
    int mExpandedPosition =RecyclerView.NO_POSITION;
    private RecyclerView recyclerView = null;

    public infoAdapter(Context context, List<PointsToRemember> myPointsList)
    {
        this.context = context;
        this.pointsList = myPointsList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder");
        View view = inflater.inflate(R.layout.info_items,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Log.d(TAG,"onCreateViewHolder "+position);

        final boolean isExpanded = position==mExpandedPosition;
        PointsToRemember currentObj = pointsList.get(position);
        holder.getData(currentObj,position);

        holder.t.setVisibility(isExpanded? View.VISIBLE:View.GONE);
        holder.title.setVisibility(isExpanded? View.GONE:View.VISIBLE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mExpandedPosition = isExpanded ? RecyclerView.NO_POSITION:position;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                    TransitionManager.beginDelayedTransition(recyclerView);
                }
                //    notifyDataSetChanged();
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pointsList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView title,t;
        int position;
        PointsToRemember current;


        public MyViewHolder(View itemView){
            super(itemView);
            t = (TextView) itemView.findViewById(R.id.pointExpand);
            title = (TextView) itemView.findViewById(R.id.points);

        }

        public void getData(PointsToRemember currentObj, int position) {
            this.title.setText(currentObj.getPoint());
            this.t.setText(currentObj.getPointExpanded());
            this.position=position;
            this.current=currentObj;

            //final Animation a = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.mytransition);


        }
    }
}
