package com.rana.abjadity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChildsAdapter extends RecyclerView.Adapter<ChildsAdapter.ViewHolder> {
    private static final String TAG = "ChildsAdapter";


    private Context context;
    private ArrayList<Child> children;

    public ChildsAdapter(Context c, ArrayList<Child> children) {
        this.context=c;
        this.children=children;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.child_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Child child =children.get(position);
        holder.childName.setText(child.getName());
        holder.childLevel.setText("المستوى "+child.getLevel());
        Picasso.get().load(R.mipmap.child_blue).into(holder.childIcon);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ChildProfileActivity.class);
                i.putExtra("childId", child.getId());
                i.putExtra("parentId", child.getParentId());
                context.startActivity(i);

//                Log.e(TAG,child.getName()+"---"+child.getParentId()+"---"+child.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return children.size();
    }


    //layout class
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView childName,childLevel;
        ImageView childIcon;
        LinearLayout card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            childName=itemView.findViewById(R.id.childName);
            childLevel=itemView.findViewById(R.id.childLevel);
            childIcon=itemView.findViewById(R.id.childIcon);
            card=itemView.findViewById(R.id.card);



        }
    }
}
