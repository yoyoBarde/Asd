package julius.barde.com.dreamlisterapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JBluee on 10/7/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    List<DreamItem> Item;
    LayoutInflater inflater;

    public Adapter(Context context, List<DreamItem> Item) {
        this.context = context;
        this.Item = Item;
        inflater = LayoutInflater.from(context);

    }

    

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.activity_list__item, parent, false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int position1 = position;
        holder.name.setText(Item.get(position).name);
        //holder.image.setImageResource(Item.get(position).imageID);
        holder.price.setText(String.valueOf(Item.get(position).price));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "onClick called on  position" + position1, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Item.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name_tv);
            price = (TextView) itemView.findViewById(R.id.price_tv);
            image = (ImageView) itemView.findViewById(R.id.img_row);
        }
    }
}
