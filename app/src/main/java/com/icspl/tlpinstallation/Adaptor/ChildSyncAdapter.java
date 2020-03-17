package com.icspl.tlpinstallation.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.tlpinstallation.Model.ChildSyncModel;
import com.icspl.tlpinstallation.Model.NewSyncModel;
import com.icspl.tlpinstallation.R;

import java.util.List;

public class ChildSyncAdapter extends RecyclerView.Adapter<ChildSyncAdapter.Holder>
{
    Context context;
    List<ChildSyncModel> array;

    public ChildSyncAdapter(Context context, List<ChildSyncModel> array) {
        this.context = context;
        this.array = array;
    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view= LayoutInflater.from(context).inflate(R.layout.child_pendingsync_layout,parent,false);
        return new ChildSyncAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildSyncAdapter.Holder holder, int position)
    {
        holder.chainage.setText(array.get(position).getChainage());
        holder.location.setText(array.get(position).getLocation());
        holder.tlpno.setText(array.get(position).getTlp_no());
        holder.cabletype.setText(array.get(position).getTlp_type());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView submitdate,tlpno,chainage,location,cabletype;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tlpno=itemView.findViewById(R.id.tlp_no_child);
            chainage=itemView.findViewById(R.id.chainage_child);
            location=itemView.findViewById(R.id.location_child);
            cabletype=itemView.findViewById(R.id.cable_type_child);
        }
    }
}
