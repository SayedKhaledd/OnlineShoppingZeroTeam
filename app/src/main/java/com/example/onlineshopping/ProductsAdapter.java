package com.example.onlineshopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.Model.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
List<Product> products;
Context context;
ProductOnClickListener productOnClickListener;

    public ProductsAdapter(List<Product> products, Context context, ProductOnClickListener productOnClickListener) {
        this.products = products;
        this.context = context;
        this.productOnClickListener=productOnClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
Product product=products.get(position);
holder.name.setText(product.getProductName());
holder.imageView.setImageResource(product.getImage());
holder.price.setText(product.getPrice()+"");
holder.description.setText(product.getDescription());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {


        TextView name, description, price;
        Button add,rate;
        ImageView imageView;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            add = itemView.findViewById(R.id.add);
            imageView = itemView.findViewById(R.id.image);
  add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

          if(getAdapterPosition()!=-1){
              Product product=products.get(getAdapterPosition());
              productOnClickListener.productAddOnClickListener(product);

          }
      }
  });

        }



    }
}