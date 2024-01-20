package com.dinesh.android.java.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.dinesh.android.R;

public class ThirdFragment extends Fragment {
    private final String TAG = "log_" + getClass().getName().split(getClass().getName().split("\\.")[2] + ".")[1];

    TextView textView;
    EditText et_ThirdFragment;
    Button btnSecondFragment, btnFirstFragment;

    String result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third,container,false);

        textView = view.findViewById(R.id.textView);
        et_ThirdFragment = view.findViewById(R.id.et_ThirdFragment);
        btnSecondFragment = view.findViewById(R.id.btnSecondFragment);
        btnFirstFragment = view.findViewById(R.id.btnFirstFragment);

        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();

        btnSecondFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = et_ThirdFragment.getText().toString();
                Log.e(TAG, "onClick: SecondFragment");
                Toast.makeText(getActivity(), "Hello SecondFragment", Toast.LENGTH_SHORT).show();
                fragmentTransaction.replace(R.id.fragment, new SecondFragment());
                fragmentTransaction.commit();
            }
        });

        btnFirstFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = et_ThirdFragment.getText().toString();
                Log.e(TAG, "onClick: ThirdFragment");
                Toast.makeText(getActivity(), "Hello FirstFragment", Toast.LENGTH_SHORT).show();
                fragmentTransaction.replace(R.id.fragment, new FirstFragment());
                fragmentTransaction.commit();
            }
        });

        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                result = bundle.getString("bundleKey");
                et_ThirdFragment.setText(result);
                Log.i(TAG, "onFragmentResult: " + result);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: " );
        Bundle bundle = new Bundle();
        bundle.putString("bundleKey", result);
        getParentFragmentManager().setFragmentResult("requestKey", bundle);
    }

}