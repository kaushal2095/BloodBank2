package com.example.bloodbank2;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bloodbank2.activities.Detail;
import com.example.bloodbank2.container.DonarData;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListItem extends Fragment {

    DonarData donarData=new DonarData();

    public ListItem() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() !=null){
            donarData.id=getArguments().getInt("Id");
            donarData.full_name=getArguments().getString("Name");
            donarData.city=getArguments().getString("City");
            donarData.area=getArguments().getString("Area");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView name  = (TextView) view.findViewById(R.id.name);
        TextView location  = (TextView) view.findViewById(R.id.location);

        name.setText(donarData.full_name);
        location.setText(donarData.city+", "+donarData.area);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getActivity(), Detail.class);
                i.putExtra("Id",donarData.id);
                startActivity(i);
            }
        });

    }
}
