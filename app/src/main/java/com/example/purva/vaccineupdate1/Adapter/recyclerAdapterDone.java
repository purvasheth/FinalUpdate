package com.example.purva.vaccineupdate1.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.purva.vaccineupdate1.Model.VaccineTimeTable;
import com.example.purva.vaccineupdate1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.ContentValues.TAG;

public class recyclerAdapterDone extends RecyclerView.Adapter<recyclerAdapterDone.MyViewHolder>{

    private List<VaccineTimeTable> vaccineList;
    private LayoutInflater inflater;
    Context context;
    int mExpandedPosition = RecyclerView.NO_POSITION;
    private RecyclerView recyclerViewDone = null;

    public recyclerAdapterDone(Context context, List<VaccineTimeTable> myVaccineList)
    {
        this.context = context;
        this.vaccineList = myVaccineList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerViewDone = recyclerView;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder");
        View view = inflater.inflate(R.layout.list_item_done,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Log.d(TAG,"onCreateViewHolder "+position);

        final boolean isExpanded = position==mExpandedPosition;

        VaccineTimeTable currentObj = vaccineList.get(position);
        holder.getData(currentObj,position);

        holder.description.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        //     holder.vac_name.setSelected(true);
        holder.vac_name.setActivated(isExpanded);
        holder.vac_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mExpandedPosition = isExpanded ? RecyclerView.NO_POSITION:position;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(recyclerViewDone);
                }
                notifyItemChanged(position);
            }
        });

        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return vaccineList.size();
    }

    public void removeItem(int position){
        vaccineList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,vaccineList.size());
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView vac_name,cost,after;
        RelativeLayout description;
        ImageView done;
        int position;
        VaccineTimeTable current;


        public MyViewHolder(View itemView){
            super(itemView);
            description = (RelativeLayout) itemView.findViewById(R.id.description);
            after = (TextView) itemView.findViewById(R.id.after);
            cost = (TextView) itemView.findViewById(R.id.cost);
            vac_name = (TextView) itemView.findViewById(R.id.VaccineName);
            done = (ImageView) itemView.findViewById(R.id.undoVaccine);
        }

        public void getData(VaccineTimeTable currentObj, int position) {
            this.vac_name.setText(currentObj.getVac_name());
            this.after.setText(currentObj.getAfter());
            this.cost.setText(currentObj.getCost());
            this.done.setImageResource(currentObj.getImageUndo());
            this.position=position;
            this.current=currentObj;

        }

        public void setListeners()
        {
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    final DatabaseReference databaseReference = firebaseDatabase.getReference("users");

                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            DataSnapshot vaccineSnapshot = dataSnapshot.child(FirebaseAuth.getInstance().getUid()).child("vaccineList");
                                            for (DataSnapshot ds : vaccineSnapshot.getChildren()) {
                                                if (ds.getValue(VaccineTimeTable.class).getVac_name() == vaccineList.get(position).getVac_name()) {
                                                    databaseReference.child(FirebaseAuth.getInstance().getUid()).child("vaccineList").child(ds.getKey()).child("flag").setValue(false);
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }


                                    });
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                  //  removeItem(position);
                }
            });
        }

    }

}
