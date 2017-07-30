package shanks.com.fitness.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import shanks.com.fitness.R;
import shanks.com.fitness.Utils.Session;
import shanks.com.fitness.Utils.Utils;
import shanks.com.fitness.model.Foods;
import shanks.com.fitness.model.MealSchedulerModel;
import shanks.com.fitness.model.Nutrients;

public class dietAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private LayoutInflater inflater;
    ArrayList<Foods> myFoods;
    Session session;
    String name;
    MealSchedulerModel model;



    public dietAdapter(Context context, ArrayList<Foods> myFoods,String name) {
        super();
        this.context = context;
        model = new MealSchedulerModel();
        session = Session.getSession(context);
        this.myFoods = myFoods;
        this.name = name;
        inflater= LayoutInflater.from(context);
        for(int i=0; i<myFoods.size(); i++)
        {
            Log.d("ADAPTER FOODS","DATA OF FOODS "+myFoods.get(i).getName());
        }
    }


    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.items_mydiet_chart, parent,false);

        MyHolder holder=new MyHolder(view,context);
        return holder;
    }
    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Foods current = myFoods.get(position);

        myHolder.Fname.setText(current.getName());
        myHolder.Fcal.setText(current.getWeight());

        //GET IMAGE FROM DB
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return myFoods.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView Fname,Fcal;
        ImageView removebtn;

        // create constructor to get widget reference
        public MyHolder(View itemView, final Context context) {
            super(itemView);
            final Context context1 = context;
            Fname = (TextView) itemView.findViewById(R.id.foodname);
            Fcal = (TextView) itemView.findViewById(R.id.foodcalorie);
            removebtn = (ImageView) itemView.findViewById(R.id.removebtn);
            removebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myFoods.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),myFoods.size());

                    if(myFoods!=null){
                        if(name.equalsIgnoreCase(Utils.breakfast)){
                            session.setBreakfastDiet(getString(myFoods));
                        } else if(name.equalsIgnoreCase(Utils.lunch)){
                            session.setLunchDiet(getString(myFoods));
                        } else {
                            session.setDinnerDiet(getString(myFoods));
                        }
                    }

                }
            });

        }
    }
    private String getString(ArrayList<Foods> obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
   }

