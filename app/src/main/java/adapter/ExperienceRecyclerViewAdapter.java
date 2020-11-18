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

import java.util.List;

import model.Experience;

public class ExperienceRecyclerViewAdapter extends RecyclerView.Adapter<ExperienceRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Experience> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public ExperienceRecyclerViewAdapter(Context context, List<Experience> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ExperienceRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.experience_list_row, viewGroup, false);


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        Experience item = itemList.get(position); // object Item

        viewHolder.title.setText( item.getCompanyName());
        viewHolder.instituteName.setText( item.getDesignation());
        viewHolder.startDate.setText( item.getStartDate());
        viewHolder.endDate.setText(item.getEndDate());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView instituteName;
        public TextView startDate;
        public TextView endDate;
        public Button editButton;
        public Button deleteButton;

        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            title = itemView.findViewById(R.id.experience_title_list);
            instituteName = itemView.findViewById(R.id.experience_institute_name_list);
            startDate = itemView.findViewById(R.id.experience_start_date_list);
            endDate = itemView.findViewById(R.id.experience_end_date_list);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position;
            position = getAdapterPosition();
            Experience item = itemList.get(position);

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

        private void editItem(final Experience newItem) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.experience_popup, null);

            Button saveButton;
            final EditText title;
            final EditText instituteName;
            final EditText sDate;
            final EditText eDate;



            title = view.findViewById(R.id.experience_title);
            instituteName = view.findViewById(R.id.institute_name_experience);
            sDate = view.findViewById(R.id.start_date_of_experience);
            eDate = view.findViewById(R.id.end_date_of_experience);

            saveButton = view.findViewById(R.id.saveButton);
            saveButton.setText(R.string.update_text1);

            title.setText(newItem.getCompanyName());
            instituteName.setText(String.valueOf(newItem.getDesignation()));
            sDate.setText(newItem.getStartDate());
            eDate.setText(newItem.getEndDate());


            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //update items
                    newItem.setCompanyName(title.getText().toString());
                    newItem.setDesignation(instituteName.getText().toString());
                    newItem.setStartDate(sDate.getText().toString());

                    if (!title.getText().toString().isEmpty()
                            && !instituteName.getText().toString().isEmpty()
                            && !sDate.getText().toString().isEmpty()
                            && !eDate.getText().toString().isEmpty()) {

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
