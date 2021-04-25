package com.example.picfriendstest.PhotoEditor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.picfriendstest.R;

public class CameraFragment extends Fragment {
    private static final String TAG = "CameraFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        Log.d(TAG, "onCreateView: Camera Fragment Start");
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        Button btnPhotoEdit = (Button) view.findViewById(R.id.btnPhotoEdit);
        btnPhotoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to the photo editing page");
                Intent intent = new Intent(getActivity(), PhotoEditActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }

}
