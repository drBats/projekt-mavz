package com.example.jan.gps_koordinate;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;


public class RezultatiSeznamFragment extends Fragment implements AdapterView.OnItemClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rezultati_seznam, container, false);

        String[] files = getActivity().fileList();
        /*ArrayList<String> fileNames = new ArrayList<>();
        for(File file : files){
            fileNames.add(file.getName());
        }*/

        ListView listView = rootView.findViewById(R.id.seznam_rezultatov);
        RezultatiArrayAdapter adapter = new RezultatiArrayAdapter(getActivity(), R.layout.item_rezultat, files);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String fileName = (String) view.getTag();

        RezultatiPodrobnostiFragment fragment = new RezultatiPodrobnostiFragment();

        Bundle bundle = new Bundle();
        bundle.putString("fileName", fileName);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
