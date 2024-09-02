package com.example.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Custom_Adapter extends BaseAdapter {
    Context context;
    private int res;
    ArrayList<user> list;

    public Custom_Adapter(Context context, ArrayList<user> list, int res) {
        this.context = context;
        this.list = list;
        this.res = res;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(res, null, false);
        }

        TextView name_text = v.findViewById(R.id.name);
        TextView number_text = v.findViewById(R.id.number);
        ImageView img_btn = v.findViewById(R.id.imageButton);
        ImageView edit_btn = v.findViewById(R.id.editButton);  // Add reference to the edit button
        CircleImageView photoImageView = v.findViewById(R.id.photoImageView); // Add reference to the photo ImageView

        // Set contact details
        name_text.setText(list.get(i).getName());
        number_text.setText(list.get(i).getNumber());

        // Load photo if available
        String photoPath = list.get(i).getPhotoPath();
        if (photoPath != null) {
            photoImageView.setImageURI(Uri.parse(photoPath));
        } else {
            photoImageView.setImageResource(R.drawable.image);  // Default photo if no photo
        }

        // Set click listener for the call button
        img_btn.setOnClickListener(view1 -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + list.get(i).getNumber()));
            context.startActivity(callIntent);
        });

        // Set click listener for the edit button
        edit_btn.setOnClickListener(view1 -> {
            Intent editIntent = new Intent(context, edit_contact.class);
            editIntent.putExtra("CONTACT_ID", list.get(i).getId());  // Pass the contact ID
            context.startActivity(editIntent);
        });

        return v;
    }
}
