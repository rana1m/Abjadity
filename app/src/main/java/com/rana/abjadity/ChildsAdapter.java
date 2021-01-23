package com.rana.abjadity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChildsAdapter extends RecyclerView.Adapter<ChildsAdapter.ViewHolder> {

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

    }

    @Override
    public int getItemCount() {
        return children.size();
    }


    //layout class
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView childName,childLevel;
        ImageView childIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            childName=itemView.findViewById(R.id.childName);
            childLevel=itemView.findViewById(R.id.childLevel);
            childIcon=itemView.findViewById(R.id.childIcon);


        }
    }
}
