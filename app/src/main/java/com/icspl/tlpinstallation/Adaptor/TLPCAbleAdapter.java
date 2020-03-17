package com.icspl.tlpinstallation.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.tlpinstallation.Model.TLPCableModel;
import com.icspl.tlpinstallation.R;

import java.util.List;

public class TLPCAbleAdapter extends RecyclerView.Adapter<TLPCAbleAdapter.Holder>
{
Context context;
List<TLPCableModel> tlpCableModelList;

    public TLPCAbleAdapter(Context context, List<TLPCableModel> tlpCableModelList) {
        this.context = context;
        this.tlpCableModelList = tlpCableModelList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.raw_cablelayout,parent,false);
       return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.sp_cable_type.setTag(tlpCableModelList.get(position).getTlpcabletyp());
        holder.cableno.setText(tlpCableModelList.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        return tlpCableModelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public EditText cableno;
        public Spinner sp_cable_type;
        public Holder(@NonNull View itemView) {
            super(itemView);
            cableno=itemView.findViewById(R.id.et_cableno);
            sp_cable_type=itemView.findViewById(R.id.sp_for_typecableinno);
        }
    }
}
