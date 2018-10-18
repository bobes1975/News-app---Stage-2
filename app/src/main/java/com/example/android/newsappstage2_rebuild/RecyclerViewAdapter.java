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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NewsViewHolder> {


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_card_view, parent, false);
        return new NewsViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        final News news = mNewsList.get(position);

        holder.titleNews.setText(news.getNewsTitle());
        holder.categoryNews.setText(news.getNewsCategory());
        holder.authorNews.setText(news.getNewsAuthor());

        // Reformat and display news date
        SimpleDateFormat dateFormatJSON = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("EE dd MMM yyyy", Locale.ENGLISH);
        String newDate = null;
        String oldDate = news.getNewsDate();

        try {
            newDate = dateFormat2.format(date);
            Toast.makeText(mActivity, newDate, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.dateNews.setText(newDate);

        //change color of the empty textview to highlight category

        if (news.getNewsCategory().equals((mActivity.getString(R.string.world)))) {
            holder.colorCategory.setBackgroundResource(R.color.world);
        }

        if (news.getNewsCategory().equals((mActivity.getString(R.string.us)))) {
            holder.colorCategory.setBackgroundResource(R.color.us);
        }

        if (news.getNewsCategory().equals((mActivity.getString(R.string.uk)))) {
            holder.colorCategory.setBackgroundResource(R.color.uk);
        }

        if (news.getNewsCategory().equals((mActivity.getString(R.string.business)))) {
            holder.colorCategory.setBackgroundResource(R.color.business);
        }

        if (news.getNewsCategory().equals((mActivity.getString(R.string.science)))) {
            holder.colorCategory.setBackgroundResource(R.color.science);
        }

        if (news.getNewsCategory().equals((mActivity.getString(R.string.society)))) {
            holder.colorCategory.setBackgroundResource(R.color.society);
        }

        if (news.getNewsCategory().equals((mActivity.getString(R.string.sport)))) {
            holder.colorCategory.setBackgroundResource(R.color.sport);
        }

        if (news.getNewsCategory().equals((mActivity.getString(R.string.art)))) {
            holder.colorCategory.setBackgroundResource(R.color.art);
        }

        if (news.getNewsCategory().equals((mActivity.getString(R.string.technology)))) {
            holder.colorCategory.setBackgroundResource(R.color.technology);
        }

        Glide.with(mActivity)
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
        TextView colorCategory;

        public NewsViewHolder(View itemView) {
            super(itemView);

            imageThumbnail = itemView.findViewById(R.id.imageView);
            titleNews = itemView.findViewById(R.id.newsTitle);
            categoryNews = itemView.findViewById(R.id.newsCategory);
            authorNews = itemView.findViewById(R.id.newsAuthor);
            dateNews = itemView.findViewById(R.id.newsDate);

            list_item = itemView.findViewById(R.id.list_item);

            colorCategory = itemView.findViewById(R.id.categoryColor);
        }
    }
}
