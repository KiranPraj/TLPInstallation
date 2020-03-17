package com.icspl.tlpinstallation.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icspl.tlpinstallation.Model.NewSyncModel;
import com.icspl.tlpinstallation.Model.ParentSyncModel;
import com.icspl.tlpinstallation.Model.SyncModel;
import com.icspl.tlpinstallation.R;

import java.util.List;

public class SyncAdaptor extends RecyclerView.Adapter<SyncAdaptor.Holder> {
    Context context;
    List<ParentSyncModel> array;
    private LinearLayoutManager linearLayoutManager;
    ChildSyncAdapter childSyncAdapter;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public SyncAdaptor(Context context, List<ParentSyncModel> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public SyncAdaptor.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_sync_data,parent,false);
        return new Holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SyncAdaptor.Holder holder, int position) {
     ParentSyncModel parentSyncModel;
        parentSyncModel=array.get(position);
        holder.submitdate.setText(parentSyncModel.getTlp_submitdate());
       linearLayoutManager=new LinearLayoutManager(context);
       holder.recyclerView.setLayoutManager(linearLayoutManager);
        childSyncAdapter=new ChildSyncAdapter(context,parentSyncModel.getTlplist());
        holder.recyclerView.setAdapter(childSyncAdapter);



    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView submitdate;
        RecyclerView recyclerView;
        Button sync;
        public Holder(@NonNull View itemView) {
            super(itemView);
            submitdate=itemView.findViewById(R.id.dateOfTlp);
           recyclerView=itemView.findViewById(R.id.rv_child);
            sync=itemView.findViewById(R.id.sync);
        }
    }
}
