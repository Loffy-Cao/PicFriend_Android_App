package com.example.picfriendstest.PhotoEditor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picfriendstest.Adapter.ColorAdapter;
import com.example.picfriendstest.Interface.BrushFragmentListener;
import com.example.picfriendstest.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BrushFragment extends BottomSheetDialogFragment implements ColorAdapter.ColorAdapterListener {

    SeekBar seekBar_brush_size, seekBar_opacity_size;
    RecyclerView recycler_color;
    ToggleButton btn_brush_state;
    ColorAdapter colorAdapter;

    BrushFragmentListener listener;

    static BrushFragment instance;

    public static BrushFragment getInstance(){
        if (instance == null){
            instance = new BrushFragment();
        }
        return instance;
    }


    public void setListener(BrushFragmentListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View itemView = inflater.inflate(R.layout.fragment_brush, container, false);

       seekBar_brush_size = (SeekBar) itemView.findViewById(R.id.seekBar_brush_size);
       seekBar_opacity_size = (SeekBar) itemView.findViewById(R.id.seekBar_brush_opacity);
       btn_brush_state = (ToggleButton) itemView.findViewById(R.id.btn_brush_state);
       recycler_color = (RecyclerView) itemView.findViewById(R.id.recycler_coler);
       recycler_color.setHasFixedSize(true);
       recycler_color.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

       colorAdapter = new ColorAdapter(getContext(),  this );
       recycler_color.setAdapter(colorAdapter);

       //Event
        seekBar_opacity_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listener.onBrushOpacityChangedListener(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar_brush_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listener.onBrushSizeChangedListener(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btn_brush_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onBrushStateChangedListener(isChecked);
            }
        });


       return itemView;
    }


    @Override
    public void onColorSelected(int color) {
        listener.onBrushColorChangedListener(color);

    }
}
