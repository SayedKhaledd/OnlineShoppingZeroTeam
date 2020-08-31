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

public class FinalOrderAdapter extends RecyclerView.Adapter<FinalOrderAdapter.MyViewHolder> {
    List<Product> products;
    Context context;

    public FinalOrderAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.final_order_item,parent,false);

        return new FinalOrderAdapter.MyViewHolder(view);    }

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
        LinearLayout linearLayout;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linearLayout);

            description=itemView.findViewById(R.id.description);
            productName=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            totalprice=itemView.findViewById(R.id.totalprice);

            quantity=itemView.findViewById(R.id.quantity);

            imageView=itemView.findViewById(R.id.image);
            relativeLayout=itemView.findViewById(R.id.relative_layout);


        }
    }
}
