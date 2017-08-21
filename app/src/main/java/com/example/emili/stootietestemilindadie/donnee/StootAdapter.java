package com.example.emili.stootietestemilindadie.donnee;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.emili.stootietestemilindadie.R;

import java.util.List;

/**
 * Created by emili on 09/08/2017.
 */


public class StootAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Stoot> stoots;
    Context context;
    LayoutInflater inflater;
    int taille;


    private RecyclerItemClickListener clickListener;


    public StootAdapter(Context context, List<Stoot> stoots, RecyclerItemClickListener clickListener){
        this.context = context;
        this.stoots = stoots;
        inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;


    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.stoot, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        MyHolder myHolder = (MyHolder) holder;
        Stoot stoot = stoots.get(position);

        myHolder.nom.setText(stoot.getNom());
        myHolder.prenom.setText(stoot.getPrenom());
        myHolder.adresse.setText(stoot.getAdresse());
        myHolder.prix.setText(String.valueOf(stoot.getPrix()+ " euro"));

        myHolder.distance.setText(String.valueOf(String.format("%.1f", stoot.getDistance()) +" km"));
        myHolder.jour.setText(stoot.getDuration());
        ((MyHolder) holder).bind(stoot, clickListener);

        if(!stoot.getUrlPhotoProfil().isEmpty()){
            //new DownloadImageAsyncTask(myHolder.userImage).execute(stoot.getUrlPhotoProfil());
            Glide.with(context)
                    .load(stoot.getUrlPhotoProfil())
                    .override(100, 100)
                    .placeholder(R.drawable.anonyme)
                    .into(myHolder.userImage);
        }
    }

    @Override
    public int getItemCount() {

        taille = stoots.size();
        return taille;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView jour;
        ImageView userImage;
        TextView prenom;
        TextView nom;
        TextView adresse;
        TextView prix;
        TextView distance;
        View view1;

        public MyHolder(View itemView) {
            super(itemView);
            userImage = (ImageView) itemView.findViewById(R.id.photoUser);
            prenom = (TextView) itemView.findViewById(R.id.prenom);
            nom = (TextView) itemView.findViewById(R.id.nom);
            adresse = (TextView) itemView.findViewById(R.id.adresse);
            prix = (TextView) itemView.findViewById(R.id.prix);
            distance = (TextView) itemView.findViewById(R.id.km);
            jour = (TextView) itemView.findViewById(R.id.jour);
            view1 = (View) itemView.findViewById(R.id.view1);

        }

        public void bind(final Stoot stoot, final RecyclerItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.OnClickListener(stoot, getLayoutPosition());
                }
            });
        }
    }


    /*
    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        ImageView image;

        public DownloadImageAsyncTask(ImageView image) {
            this.image = image;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];

            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result);
            image.setVisibility(View.VISIBLE);
        }
    }
    */

    public interface RecyclerItemClickListener{

        void OnClickListener(Stoot stoot, int position);
    }

}

