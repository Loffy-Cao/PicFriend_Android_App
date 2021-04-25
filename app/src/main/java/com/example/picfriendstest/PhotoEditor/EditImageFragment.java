package com.example.picfriendstest.PhotoEditor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.picfriendstest.Interface.EditImageFragmentListener;
import com.example.picfriendstest.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditImageFragment extends BottomSheetDialogFragment implements SeekBar.OnSeekBarChangeListener {

    private EditImageFragmentListener listener;
    SeekBar seekbar_brightness, seekbar_constraint, seekbar_saturation;


    public void setListener(EditImageFragmentListener listener) {
        this.listener = listener;
    }

    static EditImageFragment instance;

    public static EditImageFragment getInstance() {
        if (instance == null){
            instance = new EditImageFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_edit_image, container, false);

        seekbar_brightness = (SeekBar)itemView.findViewById(R.id.seekBar_brightness);
        seekbar_constraint = (SeekBar)itemView.findViewById(R.id.seekBar_constraint);
        seekbar_saturation = (SeekBar)itemView.findViewById(R.id.seekBar_saturation);

        seekbar_brightness.setMax(200);
        seekbar_brightness.setProgress(100);

        seekbar_constraint.setMax(20);
        seekbar_constraint.setProgress(0);

        seekbar_saturation.setMax(30);
        seekbar_saturation.setProgress(10);

        seekbar_saturation.setOnSeekBarChangeListener(this);
        seekbar_constraint.setOnSeekBarChangeListener(this);
        seekbar_brightness.setOnSeekBarChangeListener(this);

        return itemView;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (listener != null){
            if (seekBar.getId() == R.id.seekBar_brightness)
            {
                listener.onBrightnessChanged(progress-100);

            }else if (seekBar.getId() == R.id.seekBar_constraint){

                progress+=10;
                float value = .10f*progress;
                listener.onConstraintChanged(value);

            }else if (seekBar.getId() == R.id.seekBar_saturation){
                float value = .10f*progress;
                listener.onSaturationChanged(value);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (listener != null){
            listener.onEditStarted();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (listener != null){
            listener.onEditCompleted();
        }
    }

    public void resetControls(){
        seekbar_constraint.setProgress(0);
        seekbar_brightness.setProgress(100);
        seekbar_saturation.setProgress(10);
    }
}
