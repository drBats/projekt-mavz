package com.example.jan.gps_koordinate;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class NujnaPomocFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_nujna_pomoc, container, false);

        String[] array = {"Zlomi", "Oživljanje", "Krvavitve"};

        final ListView seznam = (ListView)rootView.findViewById(R.id.listview);

        ListAdapter adapter =  new ArrayAdapter<String>(seznam.getContext(), android.R.layout.simple_list_item_1, array);

        seznam.setAdapter(adapter);


        seznam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), NavodilaActivity.class);

                switch (i){
                    case 0:
                        intent.putExtra("id", 2);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("id", 1);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("id", 3);
                        startActivity(intent);
                        break;
                }
            }
        });

        return rootView;
    }



}
