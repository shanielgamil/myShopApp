package com.example.myshoppingapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myshoppingapp.R;
import com.example.myshoppingapp.activities.MainActivity;
import com.example.myshoppingapp.models.User;

import java.io.Console;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity main = (MainActivity) getActivity();
        View view= inflater.inflate(R.layout.fragment_shop, container, false);
        main.usernameTextView = view.findViewById(R.id.userName);

        if(main.currentUser != null)
        {
            main.usernameTextView.setText(main.currentUser.getName());
        }



        TextView viewById = view.findViewById(R.id.userName);



        // Inflate the layout for this fragment
        //View view =inflater.inflate(R.layout.fragment_register, container, false);
        Button toCart = view.findViewById(R.id.goToCartButton);

        toCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_shopFragment_to_cartFragment);

            }
        });

        main.creatDataView(view);


        return view;

    }
}