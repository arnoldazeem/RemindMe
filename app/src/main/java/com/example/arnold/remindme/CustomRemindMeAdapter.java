package com.example.arnold.remindme;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRemindMeAdapter extends RecyclerView.Adapter<CustomRemindMeAdapter.ViewHolder> {

    //private ArrayAdapter<RowItem> data;
    private Context context;
    List<RemindmeItem> data;
    Typeface font3;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomRemindMeAdapter(Context context, List<RemindmeItem> data) {
        this.context = context;
        this.data = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomRemindMeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminderme_row, parent, false);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public View view;

        public ViewHolder(View v) {
            super(v);

            //makes the recycleview clickable
            v.setClickable(true);

            v.setOnClickListener(this);

            view = v;

        }

        @Override
        public void onClick(View v) {

        }
    }

    // Replace the contents of a view (invoked by the layout manager)
// Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

       // font3 = Typeface.createFromAsset(context.getAssets(),
       //         "fonts/robome.ttf");


        TextView main = (TextView) holder.view.findViewById(R.id.text1);


        main.setText(data.get(position).getTitle());
       // texthymn.setText(data.get(position).getHymnum());

        //text.setTypeface(font3);
        //texthymn.setTypeface(font3);


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }

}

