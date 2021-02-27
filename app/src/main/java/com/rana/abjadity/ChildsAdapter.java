package com.rana.abjadity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChildsAdapter extends RecyclerView.Adapter<ChildsAdapter.ViewHolder> {
    private static final String TAG = "ChildsAdapter";

    private Context context;
    private ArrayList<Child> children;
    StorageReference storageReference ;

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

        storageReference.child("characters/char"+child.getCharacter()+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.childIcon);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ChildProfileActivity.class);
                i.putExtra("childId", child.getId());
                i.putExtra("parentId", child.getParentId());
                i.putExtra("childPosition",position);
                notifyDataSetChanged();
                notifyItemChanged(position);
                context.startActivity(i);
            }
        });

    }

    public void removeItem(int position){
        children.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        notifyItemRangeChanged(position, children.size());
    }

    public void addItem(Child c){
        children.add(c);
        notifyDataSetChanged();
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
            storageReference = FirebaseStorage.getInstance().getReference();


        }
    }
}
