package com.cursoandroid.pucfrotasdeescolar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MotoristaAdapter extends RecyclerView.Adapter<MotoristaAdapter.MyViewHolder> {
    private List<Motorista> mDataset;
    OnClickListener<Motorista> onClickListener;

    public void setList(List<Motorista> motoristas) {
        mDataset.addAll(motoristas);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView motoristaNome;
        public TextView motoristaEmail;
        public TextView motoristaInfo;

        public MyViewHolder(CardView v) {
            super(v);
            motoristaNome = v.findViewById(R.id.motorista_nome);
            motoristaEmail = v.findViewById(R.id.motorista_email);
            motoristaInfo = v.findViewById(R.id.motorista_info_atendimento);
        }
    }

    public MotoristaAdapter(OnClickListener<Motorista> clickListener) {
        mDataset = new ArrayList<>();
        onClickListener = clickListener;
    }

    @NonNull
    @Override
    public MotoristaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                            int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_motorista, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.motoristaNome.setText(mDataset.get(position).getNome());
        holder.motoristaEmail.setText(mDataset.get(position).getEmail());
        holder.motoristaInfo.setText(mDataset.get(position).getInstituicoesAtendidas());
        holder.itemView.setTag(mDataset.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick((Motorista) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}