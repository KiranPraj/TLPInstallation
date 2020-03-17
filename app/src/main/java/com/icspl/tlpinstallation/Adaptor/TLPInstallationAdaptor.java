package com.icspl.tlpinstallation.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.tlpinstallation.Model.TLPInstallationModel;
import com.icspl.tlpinstallation.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

public class TLPInstallationAdaptor extends RecyclerView.Adapter<TLPInstallationAdaptor.Holder> {
    Context context;
    List<TLPInstallationModel> array;
    private Photoclickhandler photoclickhandler;
    private int lastSelectedPosition = -1;
    public TLPInstallationAdaptor(Context context, List<TLPInstallationModel> array, Photoclickhandler photoclickhandler) {
        this.context = context;
        this.array = array;
        this.photoclickhandler = photoclickhandler;
    }
    public interface Photoclickhandler {
        void photoclickcallback(View v, Button rvbtn_cam);
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_tlpinstallation,parent,false);
        return new Holder(view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.tlpno.setText(array.get(position).getTlpno());
        holder.chainage.setText(array.get(position).getChainage());
        holder.location.setText(array.get(position).getLocation());

        holder.sp_for_typecable.setTag((array.get(position).getValueoftlpcable()));

        if(!array.get(position).getImage().equals("NA"))
        {
            holder.Photo.setBackgroundColor(R.color.colorPrimary);
            holder.Photo.setTag(array.get(position).getImage());
        }
        holder.Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoclickhandler.photoclickcallback(v,holder.Photo);
            }
        });

      /*  holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
             holder.rb = (RadioButton) radioGroup.findViewById(i);
                Toast.makeText(context," On checked change "+holder.rb.getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });*/


        // added
   /*   if ((array.get(position).getTypeofcabel().equals("YES")))
        {
            holder.linearLayoutCompat.setVisibility(View.VISIBLE);
        }
      else
      {
          holder.linearLayoutCompat.setVisibility(View.GONE);
      }
        holder.radioGroup.setTag(array.get(position).getTypeofcabel());

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
              if (holder.yes!=null)

                if (null != rb && checkedId > -1) {
                   holder.rb_text= rb.getText().toString();
                }

            }
        });
        if (holder.rb_text=="YES")
        {
           holder. linearLayoutCompat.setVisibility(View.VISIBLE);
        }
        else
        {
            holder. linearLayoutCompat.setVisibility(View.GONE);
        }*/

    }






    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
       public EditText tlpno,chainage,location;
      public RadioGroup radioGroup;
      public RadioButton yes,no;
      public   RadioButton rb;
        public Button Photo;
        String rb_text;
     public LinearLayoutCompat linearLayoutCompat;
      public Spinner sp_for_typecable;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tlpno=itemView.findViewById(R.id.ettlpno);
            chainage=itemView.findViewById(R.id.chainage);
            location=itemView.findViewById(R.id.location);
            Photo=itemView.findViewById(R.id.photo);
          //  radioGroup=itemView.findViewById(R.id.cable_for_tlp_radiogrp);
          //  yes=itemView.findViewById(R.id.yes_cable);
          //  no=itemView.findViewById(R.id.no_cable);
         //   linearLayoutCompat=itemView.findViewById(R.id.layout_linear_cabletype);
            sp_for_typecable=itemView.findViewById(R.id.sp_for_typecable);
        }
    }
}
