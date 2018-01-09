package com.example.jan.gps_koordinate;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SimulacijeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_simulacije, container, false);

        Button btnDefibrilator = (Button)rootView.findViewById(R.id.button_defibrilator);
        Button btnOzivljanje = (Button)rootView.findViewById(R.id.button_ozivljanje);

        btnDefibrilator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DefibrilatorActivity.class);
                startActivity(i);
            }
        });

        btnOzivljanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), OzivljanjeActivity.class);
                startActivity(i);
            }
        });

        return rootView;
    }
}
