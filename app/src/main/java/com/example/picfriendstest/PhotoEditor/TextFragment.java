package com.example.picfriendstest.PhotoEditor;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picfriendstest.Adapter.ColorAdapter;
import com.example.picfriendstest.Adapter.FontAdapter;
import com.example.picfriendstest.Interface.TextFragmentListener;
import com.example.picfriendstest.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TextFragment extends BottomSheetDialogFragment implements ColorAdapter.ColorAdapterListener, FontAdapter.FontAdapterClickListener {

    int colorSelected = Color.parseColor("#000000"); // Default color of text
    EditText edit_add_text;
    RecyclerView recycler_color, recycler_font;
    Button btn_done;

    TextFragmentListener listener;

    Typeface typefaceSelected = Typeface.DEFAULT;

    public void setListener(TextFragmentListener listener) {
        this.listener = listener;
    }

    static TextFragment instance;

    public static TextFragment getInstance() {
        if (instance == null){
            instance = new TextFragment();
        }
        return instance;
    }

    @Override
    public void onColorSelected(int color) {
        colorSelected = color; // set color when uer selected

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_text, container, false);

        edit_add_text = (EditText) itemView.findViewById(R.id.edit_add_text);
        btn_done = (Button) itemView.findViewById(R.id.btn_done);
        recycler_color = (RecyclerView) itemView.findViewById(R.id.recycler_color);
        recycler_color.setHasFixedSize(true);
        recycler_color.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_font = (RecyclerView) itemView.findViewById(R.id.recycler_font);
        recycler_font.setHasFixedSize(true);
        recycler_font.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        ColorAdapter colorAdapter = new ColorAdapter(getContext(),  this );
        recycler_color.setAdapter(colorAdapter);

        FontAdapter fontAdapter = new FontAdapter(getContext(), this);
        recycler_font.setAdapter(fontAdapter);

        //Event
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddTextButtonClick(typefaceSelected, edit_add_text.getText().toString(), colorSelected);
            }
        });

        return itemView;
    }

    @Override
    public void onFontSelected(String fontName) {
        typefaceSelected = Typeface.createFromAsset(getContext().getAssets(), new StringBuilder("fonts/")
                .append(fontName).toString());
    }
}
