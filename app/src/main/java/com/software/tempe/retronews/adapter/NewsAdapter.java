package com.software.tempe.retronews.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.software.tempe.retronews.R;
import com.software.tempe.retronews.model.Articles;
import com.software.tempe.retronews.utility.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewAdapter> {

    private List<Articles> articlesList = new ArrayList<>();
    private Context context;
    private OnItemClickListener onitemClickListener;

    public NewsAdapter(List<Articles> articlesList, Context context) {
        this.articlesList = articlesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, viewGroup, false);
        return new ViewAdapter(view, onitemClickListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewAdapter viewAdapter, int i) {
        final ViewAdapter adapter = viewAdapter;
        final Articles articles = articlesList.get(i);

        Picasso.get()
                .load(articles.getUrlToImage())
                .placeholder(R.color.loading_white_plain)
                .fit()
                .into(viewAdapter.newsImgView, new Callback() {
                    @Override
                    public void onSuccess() {
                        adapter.loading_image.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        adapter.loading_image.setVisibility(View.GONE);
                        viewAdapter.newsImgView.setImageResource(R.mipmap.ic_launcher);
                    }
                });

        viewAdapter.authorTxtView.setText(articles.getAuthor());
        viewAdapter.newsDescTxtView.setText(articles.getDescription());
        viewAdapter.newSourceTxtView.setText(articles.getSource().getName());
        viewAdapter.newsTitleTxtView.setText(articles.getTitle());
        viewAdapter.newsDateTxtView.setText(Utils.DateFormat(articles.getPublishedAt()));
        viewAdapter.newsTimeTxtView.setText(" \u2022 " + Utils.DateToTimeFormat(articles.getPublishedAt()));

        viewAdapter.item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "message: " + articles.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView newsImgView;
        FrameLayout item_list;

        // ImageView bottom_shadow;
        ProgressBar loading_image;
        TextView authorTxtView;
        FrameLayout date_layout;
        ImageView dateImgView;
        TextView newsDateTxtView;
        TextView newsTitleTxtView;
        TextView newsDescTxtView;
        TextView newSourceTxtView;
        TextView newsTimeTxtView;
        OnItemClickListener onItemClickListener;

        public ViewAdapter(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);

            newsImgView = itemView.findViewById(R.id.newsImgView);
            // bottom_shadow = itemView.findViewById(R.id.bottom_shadow);
            loading_image = itemView.findViewById(R.id.loading_image);
            authorTxtView = itemView.findViewById(R.id.authorTxtView);
            date_layout = itemView.findViewById(R.id.date_layout);
            dateImgView = itemView.findViewById(R.id.dateImgView);
            newsImgView = itemView.findViewById(R.id.newsImgView);
            newsDateTxtView = itemView.findViewById(R.id.newsDateTxtView);
            newsTitleTxtView = itemView.findViewById(R.id.newsTitleTxtView);
            newsDescTxtView = itemView.findViewById(R.id.newsDescTxtView);
            newSourceTxtView = itemView.findViewById(R.id.newsSourceTxtView);
            newsTimeTxtView = itemView.findViewById(R.id.newsTimeTxtView);

            item_list = itemView.findViewById(R.id.item_list);

            // this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
