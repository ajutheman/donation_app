package com.example.donationapp.DHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.donationapp.databinding.FragmentAddReviwsBinding;
import com.example.donationapp.databinding.FragmentHomeBinding;


public class ViewReviewsa extends Fragment {

    private FragmentAddReviwsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddReviwsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

}