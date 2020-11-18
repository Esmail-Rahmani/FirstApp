package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cvmaaker.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;

import model.Achievement;

public class AchievementRecyclerViewAdapter extends RecyclerView.Adapter<AchievementRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Achievement> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public AchievementRecyclerViewAdapter(Context context, List<Achievement> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public AchievementRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.achievement_list_row, viewGroup, false);


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        Achievement item = itemList.get(position); // object Item

        viewHolder.title.setText(MessageFormat.format("Title: {0}", item.getAchievementTitle()));
        viewHolder.description.setText(MessageFormat.format("Description: {0}", item.getAchievementDescription()));
        viewHolder.dateAdded.setText(MessageFormat.format("date: {0}", String.valueOf(item.getAchievementDate())));



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView description;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;

        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            title = itemView.findViewById(R.id.achievement_title_list);
            description = itemView.findViewById(R.id.achievement_description_list);
            dateAdded = itemView.findViewById(R.id.achievement_date_list);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position;
            position = getAdapterPosition();
            Achievement item = itemList.get(position);

            switch (v.getId()) {
                case R.id.editButton:
                    //edit item
                    editItem(item);
                    break;
                case R.id.deleteButton:
                    //delete item
                    deleteItem(item.getId());
                    break;
            }

        }

        private void deleteItem(final int id) {

            builder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_popup, null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();


            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


        }

        private void editItem(final Achievement newItem) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.acievement_popup, null);

            Button saveButton;
            final EditText title;
            final EditText description;
            final EditText date;


            title = view.findViewById(R.id.achievement_title);
            description = view.findViewById(R.id.achievement_description);
            date = view.findViewById(R.id.date_of_achievement);
            saveButton = view.findViewById(R.id.saveButton);
            saveButton.setText(R.string.update_text1);

            title.setText(newItem.getAchievementTitle());
            description.setText(String.valueOf(newItem.getAchievementDescription()));
            date.setText(newItem.getAchievementDate());

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //update items
                    newItem.setAchievementTitle(title.getText().toString());
                    newItem.setAchievementDescription(description.getText().toString());
                    newItem.setAchievementDate(date.getText().toString());

                    if (!title.getText().toString().isEmpty()
                            && !description.getText().toString().isEmpty()
                            && !date.getText().toString().isEmpty()) {

                        notifyItemChanged(getAdapterPosition(),newItem); //important!


                    }else {
                        Snackbar.make(view, "Fields Empty",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    dialog.dismiss();

                }
            });
        }


    }


}
