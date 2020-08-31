package com.example.onlineshopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.Model.Product;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
    List<Product> products;
    Context context;
    OrdersOnclickListener orderOnClickListener;

    public OrdersAdapter(List<Product> products, Context context,OrdersOnclickListener ordersOnclickListener) {
        this.products = products;
        this.context = context;
        this.orderOnClickListener=ordersOnclickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);

        return new OrdersAdapter.MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product=products.get(position);
        holder.productName.setText(product.getProductName());
        holder.price.setText("Price:"+product.getPrice()+"");
        holder.totalprice.setText("Total price:"+product.getTotalPrice()+"");

        holder.quantity.setText("Quantity:"+product.getQuantity()+"");
        holder.imageView.setImageResource(product.getImage());
        holder.description.setText(product.getDescription()+"");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView productName,price,quantity,description,totalprice;
        ImageView imageView;
        LinearLayout linearLayout, linearLayout2, linearLayout3;
        RelativeLayout relativeLayout;
        Button minus,plus, delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linearLayout);
            linearLayout2=itemView.findViewById(R.id.linearLayout2);
            linearLayout3=itemView.findViewById(R.id.linearLayout3);
description=itemView.findViewById(R.id.description);
            productName=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            totalprice=itemView.findViewById(R.id.totalprice);

            quantity=itemView.findViewById(R.id.quantity);
            minus=itemView.findViewById(R.id.minus);
            plus=itemView.findViewById(R.id.plus);
            delete=itemView.findViewById(R.id.delete);
            imageView=itemView.findViewById(R.id.image);
            relativeLayout=itemView.findViewById(R.id.relative_layout);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition()!=-1){
                        Product product=products.get(getAdapterPosition());
                        orderOnClickListener.plusOnClickListener(product);

                    }
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition()!=-1){
                        Product product=products.get(getAdapterPosition());
                        orderOnClickListener.minusOnClickListener(product);

                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition()!=-1){
                        Product product=products.get(getAdapterPosition());
                        orderOnClickListener.deleteOnClickListener(product);

                    }
                }
            });

        }
    }
}
