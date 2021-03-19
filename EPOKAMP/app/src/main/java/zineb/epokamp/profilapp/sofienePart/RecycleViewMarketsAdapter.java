package zineb.epokamp.profilapp.sofienePart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import zineb.epokamp.profilapp.R;

public class RecycleViewMarketsAdapter extends  RecyclerView.Adapter<RecycleViewMarketsAdapter.ViewHolder> {
    private ArrayList<String> marketLogoUrlArray;
    private ArrayList<String> marketNameArray;
    private Context mContext;

    public RecycleViewMarketsAdapter(ArrayList<String> marketLogoUrlArray, ArrayList<String> marketNameArray, Context mContext) {
        this.marketLogoUrlArray = marketLogoUrlArray;
        this.marketNameArray = marketNameArray;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_offres_market, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Glide.with(mContext).asBitmap().load(marketLogoUrlArray.get(position)).into(holder.marketLogo);
        holder.marketName.setText(marketNameArray.get(position));
        int imageId = mContext.getResources().getIdentifier(marketLogoUrlArray.get(position), "drawable", mContext.getPackageName());
        holder.marketLogo.setImageResource(imageId);


        // !!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!

        /*
        holder.marketLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("hey", marketNameArray.get(position));
                Market market = new Market(marketNameArray.get(position), marketLogoUrlArray.get(position));
                FragmentTransaction fr = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.container, new ListOfMarketPromotionsFragment(market));
                fr.addToBackStack(null).commit();


            }
        });*/

    }


    @Override
    public int getItemCount() {
        return marketNameArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView marketLogo;
        TextView marketName;

        public ViewHolder(View itemView) {
            super(itemView);
            marketLogo = (ImageView) itemView.findViewById(R.id.marketLogo);
            marketName = (TextView) itemView.findViewById(R.id.marketName);

        }
    }
}
