package com.example.android.newsappstage2_rebuild;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NewsViewHolder>{


    public static List<News> mNewsList;
    private Activity mActivity;

    // News Adapter
    public RecyclerViewAdapter(List<News> newsList, Activity activity) {
        this.mNewsList = newsList;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_card_view,parent, false);
        return new NewsViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        final News news = mNewsList.get(position);

        holder.titleNews.setText(news.getNewsTitle());
        holder.categoryNews.setText(news.getNewsCategory());
        holder.authorNews.setText(news.getNewsAuthor());
        holder.dateNews.setText(news.getNewsDate());

        String categoryNews = news.getNewsCategory();


       /* String categoryNews = news.getNewsCategory();

        if (categoryNews == "world")(
               list_item.setCardBackgroundColor(R.color.world));


        //CardView card = card.setCardBackgroundColor(color);

*/

        Glide.with (mActivity)
                .load(news.getThumbnailUrl())
                .into(holder.imageThumbnail);

        String clickedNews = news.getNewsUrl(position);
        final Uri newsUri = Uri.parse(clickedNews);


        holder.list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                mActivity.startActivity(websiteIntent);
            }


        });

    }


    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageThumbnail;
        TextView titleNews;
        TextView categoryNews;
        TextView authorNews;
        TextView dateNews;
        CardView list_item;

        public NewsViewHolder(View itemView) {
            super(itemView);

            imageThumbnail = itemView.findViewById(R.id.imageView);
            titleNews = itemView.findViewById(R.id.newsTitle);
            categoryNews = itemView.findViewById(R.id.newsCategory);
            authorNews = itemView.findViewById(R.id.newsAuthor);
            dateNews = itemView.findViewById(R.id.newsDate);

            list_item = itemView.findViewById(R.id.list_item);
        }

    }

}
