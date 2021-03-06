package com.example.employeelist.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.employeelist.R;
import com.example.employeelist.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ItemHolder> {

    private Context context;
    private List<Employee> list = new ArrayList<>();
    private int[] images = {R.drawable.ic_profile1, R.drawable.ic_profile2, R.drawable.ic_profile3, R.drawable.ic_profile4, R.drawable.ic_profile5, R.drawable.ic_profile6};
    private Random random = new Random();

    public EmployeeAdapter(Context context) {
        this.context = context;
    }

    public void addList(List<Employee> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public Employee removeItem(int position) {
        Employee item = null;
        try {
            item = list.get(position);
            list.remove(position);
            notifyItemRemoved(position);
        } catch (Exception e) {
        }
        return item;
    }

    public void clearItem() {
        try {
            list.clear();
            notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    @Override
    public EmployeeAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        final Employee employee = list.get(position);
        if (employee.getProfile_image() == null) {
            holder.mImgView.setImageResource(images[random.nextInt(images.length)]);
        }
        holder.mTxtName.setText(employee.getEmployee_name());
        holder.mTxtId.setText(String.valueOf(employee.getId()));
        holder.mTxtSalary.setText((String.valueOf(employee.getEmployee_salary())));
        holder.mTxtAge.setText(String.valueOf(employee.getEmployee_age()));
        holder.mItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Id: " + employee.getId() +
                        " Name: " + employee.getEmployee_name() +
                        " Age: " + employee.getEmployee_age() +
                        " Salary: " + employee.getEmployee_salary());
                shareIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(shareIntent, "Share Employee Details"));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mItem;
        private CircleImageView mImgView;
        private TextView mTxtName, mTxtId, mTxtSalary, mTxtAge;

        ItemHolder(View itemView) {
            super(itemView);
            mItem = itemView.findViewById(R.id.item);
            mImgView = itemView.findViewById(R.id.imgProfile);
            mTxtName = itemView.findViewById(R.id.txtName);
            mTxtId = itemView.findViewById(R.id.txtId);
            mTxtSalary = itemView.findViewById(R.id.txtSalary);
            mTxtAge = itemView.findViewById(R.id.txtAge);
        }
    }
}
