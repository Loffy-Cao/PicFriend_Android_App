package com.example.picfriendstest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picfriendstest.R;

import java.util.ArrayList;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewholder> {


    Context context;
    List<Integer> colorList;
    ColorAdapterListener listener;

    public ColorAdapter(Context context,  ColorAdapterListener listener) {
        this.context = context;
        this.colorList = getColorList();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ColorViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false);
        return new ColorViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewholder holder, int position) {
        holder.color_section.setCardBackgroundColor(colorList.get(position));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class ColorViewholder extends RecyclerView.ViewHolder{

        public CardView color_section;

        public ColorViewholder(View itemView){
            super(itemView);
            color_section = (CardView) itemView.findViewById(R.id.color_section);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onColorSelected(colorList.get(getAdapterPosition()));
                }
            });
        }

    }

    private List<Integer> getColorList() {
        List<Integer> colorList = new ArrayList<>();

        colorList.add(Color.parseColor("#822111"));
        colorList.add(Color.parseColor("#AC2B16"));
        colorList.add(Color.parseColor("#CC3A21"));
        colorList.add(Color.parseColor("#E66550"));
        colorList.add(Color.parseColor("#EFA093"));
        colorList.add(Color.parseColor("#F6C5BE"));

        colorList.add(Color.parseColor("#A46A21"));
        colorList.add(Color.parseColor("#CF8933"));
        colorList.add(Color.parseColor("#EAA041"));
        colorList.add(Color.parseColor("#FFBC6B"));
        colorList.add(Color.parseColor("#FFD6A2"));
        colorList.add(Color.parseColor("#FFE6C7"));

        colorList.add(Color.parseColor("#AA8831"));
        colorList.add(Color.parseColor("#D5AE49"));
        colorList.add(Color.parseColor("#F2C960"));
        colorList.add(Color.parseColor("#FCDA83"));
        colorList.add(Color.parseColor("#FCE8B3"));
        colorList.add(Color.parseColor("#FEF1D1"));

        colorList.add(Color.parseColor("#076239"));
        colorList.add(Color.parseColor("#0B804B"));
        colorList.add(Color.parseColor("#149E60"));
        colorList.add(Color.parseColor("#44B984"));
        colorList.add(Color.parseColor("#89D3B2"));
        colorList.add(Color.parseColor("#B9E4D0"));

        colorList.add(Color.parseColor("#1A764D"));
        colorList.add(Color.parseColor("#2A9C68"));
        colorList.add(Color.parseColor("#3DC789"));
        colorList.add(Color.parseColor("#68DFA9"));
        colorList.add(Color.parseColor("#A0EAC9"));
        colorList.add(Color.parseColor("#C6F3DE"));

        colorList.add(Color.parseColor("#1C4587"));
        colorList.add(Color.parseColor("#285BAC"));
        colorList.add(Color.parseColor("#C6F3DE"));
        colorList.add(Color.parseColor("#3C78D8"));
        colorList.add(Color.parseColor("#6D9EEB"));
        colorList.add(Color.parseColor("#A4C2F4"));
        colorList.add(Color.parseColor("#C9DAF8"));

        colorList.add(Color.parseColor("#41236D"));
        colorList.add(Color.parseColor("#653E9B"));
        colorList.add(Color.parseColor("#8E63CE"));
        colorList.add(Color.parseColor("#B694E8"));
        colorList.add(Color.parseColor("#D0BCF1"));
        colorList.add(Color.parseColor("#E4D7F5"));

        colorList.add(Color.parseColor("#83334C"));
        colorList.add(Color.parseColor("#B65775"));
        colorList.add(Color.parseColor("#E07798"));
        colorList.add(Color.parseColor("#B694E8"));
        colorList.add(Color.parseColor("#F7A7C0"));
        colorList.add(Color.parseColor("#FBC8D9"));

        colorList.add(Color.parseColor("#FCDEE8"));
        colorList.add(Color.parseColor("#000000"));
        colorList.add(Color.parseColor("#434343"));
        colorList.add(Color.parseColor("#666666"));
        colorList.add(Color.parseColor("#999999"));
        colorList.add(Color.parseColor("#CCCCCC"));

        return colorList;
    }

    public interface ColorAdapterListener{
        void onColorSelected(int color);
    }
}
