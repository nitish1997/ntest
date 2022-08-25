package com.the.demosimple;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
public class MainActivity extends AppCompatActivity {
    RecyclerView rcyPetList;
    ArrayList<PetsModel> petList;
    Calendar calendar1;
    SimpleDateFormat formatter1;
    String startTime="09:00";
    String endTime="18:00";
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcyPetList=findViewById(R.id.rcyPetList);
        calendar1 = Calendar.getInstance();
        formatter1 = new SimpleDateFormat("HH:mm");
        String currentDate = formatter1.format(calendar1.getTime());
        Log.e("current time",currentDate);
        getData(currentDate);
    }
    private void getData(String currentDate) {
        try {
            Date date1 = formatter1.parse(startTime);
            Date date2 = formatter1.parse(endTime);
            Date date3 = formatter1.parse(currentDate);
            if (between(date3,date1,date2)){
                petList= new ArrayList<>();
                InputStream inputStream = getResources().openRawResource(R.raw.raw_txt);
                try{
                    byte[] buffer=new byte[inputStream.available()];
                    while (inputStream.read(buffer) != -1){
                        LoadData(new String(buffer));
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                dialouge();
             }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            Log.e("Error", ""+e);
        }
    }
    private static boolean between(Date date, Date dateStart, Date dateEnd) {
        if (date != null && dateStart != null && dateEnd != null) {
            return (dateEqualOrAfter(date, dateStart) && dateEqualOrBefore(date, dateEnd));
        }
        return false;
    }
    private static boolean dateEqualOrAfter(Date dateInQuestion, Date date2)
    {
        if (dateInQuestion.equals(date2))
            return true;
        return (dateInQuestion.after(date2));
    }
    private static boolean dateEqualOrBefore(Date dateInQuestion, Date date2)
    {
        if (dateInQuestion.equals(date2))
            return true;
        return (dateInQuestion.before(date2));

    }
    private void LoadData(String s) throws JSONException {
        Log.e("data",s);
        JSONObject jsonObject=new JSONObject(s);
        JSONArray jsonArray=jsonObject.getJSONArray("pets");
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject1=jsonArray.getJSONObject(i);
            String curl=jsonObject1.getString("content_url");
            String image_url=jsonObject1.getString("image_url");
            String title=jsonObject1.getString("title");
            String date_added=jsonObject1.getString("date_added");
           petList.add(new PetsModel(title, image_url, date_added,  curl));
        }

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        rcyPetList.setLayoutManager(linearLayoutManager);
        PetListAdapter petListAdapter = new PetListAdapter(this,petList);
        rcyPetList.setAdapter(petListAdapter);
    }

    public class PetListAdapter extends RecyclerView.Adapter<PetListAdapter.MyViewHolder> {
        private final Context context;
        private final ArrayList<PetsModel> petList;

        public PetListAdapter(Context context, ArrayList<PetsModel> petList) {
            this.context = context;
            this.petList = petList;
        }

        @NonNull
        @Override
        public PetListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view= LayoutInflater.from(context).inflate(R.layout.layout_pet_item,parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PetListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.tvTitle.setText(petList.get(position).getName());
            holder.cardView.setOnClickListener(view -> {
                Intent intent=new Intent(MainActivity.this,Second.class);
                intent.putExtra("url",petList.get(position).getContet_url());
                startActivity(intent);
            });
            Glide.with(context).load(petList.get(position).getImage()).apply(RequestOptions.centerCropTransform()).into(holder.imgPet);
        }

        @Override
        public int getItemCount() {
            return petList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tvTitle;
            ImageView imgPet;
            CardView cardView;
            public MyViewHolder(View itemView){
                super(itemView);
                cardView=itemView.findViewById(R.id.card);
                tvTitle=itemView.findViewById(R.id.title);
                imgPet=itemView.findViewById(R.id.image);
            }
        }
    }
    public void dialouge()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set a title for alert dialog
        builder.setTitle("Alert");
        builder.setCancelable(false);
        // Ask the final question
        builder.setMessage("Your working hour not matched?");

        // Set the alert dialog yes button click listener
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent startMain = new Intent(Intent.ACTION_MAIN);
//                startMain.addCategory(Intent.CATEGORY_HOME);
//                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(startMain);
//            }
//        });
//
//        // Set the alert dialog no button click listener
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
////                // Do something when No button clicked
////                Toast.makeText(getApplicationContext(),
////                        "No Button Clicked",Toast.LENGTH_SHORT).show();();
//            }
//        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }
}