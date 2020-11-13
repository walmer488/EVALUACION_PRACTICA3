package com.example.crudmysql_eva3.ui.ListaCategorias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.crudmysql_eva3.R;

public class list_reciclerview extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root  = inflater.inflate(R.layout.fragment_list_reciclerview, container, false);

        return root;
    }

}