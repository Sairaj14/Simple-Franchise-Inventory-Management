package com.example.hp.franchisehelper;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
interface FirebaseCallback {
    void onCallback(String value,String date,String Sname);
}
public class Report extends Fragment {

    String mainReportDate, mainReportId;


    public Report(){

        // String id=getArguments().getString("id");
        //String date=getArguments().getString("date");
        //Toast.makeText(getContext(),id,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    String key;
    ConstraintLayout cl;
    RecyclerView recyclerView;
    DatabaseReference dbRef, tp;
    ArrayList<Profile> list;
    String mainReportId1;
    MyAdapter adapter;
    static int height;
    int i=0,itemCount;
    Button button,downloadPdf;
    private Bitmap bitmap;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_report, container, false);
        recyclerView=view.findViewById(R.id.reportData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        button=view.findViewById(R.id.getReport);
        downloadPdf=view.findViewById(R.id.download);
        cl=view.findViewById(R.id.layout);



        downloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.measure(
                        View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

                bitmap = loadBitmapFromView(cl,itemCount,adapter);
                createPdf();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                readData(new FirebaseCallback() {
                    @Override
                    public void onCallback(String value, String date, String Sname) {
                        //  Toast.makeText(getContext(),"Id"+date,Toast.LENGTH_LONG).show();

                        dataShow(value,date);


                    }
                });




            }
        });


        return  view;
    }





    public void readData(final FirebaseCallback firebaseCallback)
    {
        final String Sname=getArguments().getString("sname");
        final  String date=getArguments().getString("date");

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("shopList");
        databaseReference.child(Sname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null)
                {
                    Toast.makeText(getContext(),"Entry not done............",Toast.LENGTH_LONG).show();
                }


                else
                {
                    key=dataSnapshot.getValue().toString();
                    mainReportId1=key;
                    //  Toast.makeText(getContext(),key,Toast.LENGTH_LONG).show();
                    firebaseCallback.onCallback(key,date,Sname);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    public void dataShow(final String mainReportId, final String mainReportDate)
    {

        dbRef = FirebaseDatabase.getInstance().getReference("shop");

        dbRef.keepSynced(true);
        DatabaseReference tp=dbRef.child(mainReportId).child(mainReportDate);
        tp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Profile pro;
                    pro = data.getValue(Profile.class);
                    list.add(pro);
                }
                if(list==null)
                {
                    Toast.makeText(getContext(),"Sorry no entry has been done ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    adapter=new MyAdapter(getContext(),list);

                    recyclerView.setAdapter(adapter);
                    itemCount=adapter.getItemCount();
                    Toast.makeText(getContext(),"Count : "+itemCount,Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }



    public static Bitmap loadBitmapFromView(View v ,int itemCounts,MyAdapter adap) {

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        int iHeight=0;
        LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
        Paint paint=new Paint();
        for(int i = 0; i<itemCounts; i++)
        {
            MyAdapter.MyViewHolder holder=adap.createViewHolder((ViewGroup) v,adap.getItemViewType(i));
            adap.onBindViewHolder(holder, i);
            holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
            holder.itemView.setDrawingCacheEnabled(true);
            holder.itemView.buildDrawingCache();
            Bitmap drawingCache = holder.itemView.getDrawingCache();
            if (drawingCache != null) {

                bitmaCache.put(String.valueOf(i), drawingCache);
            }
//               holder.itemView.setDrawingCacheEnabled(false);
//                holder.itemView.destroyDrawingCache();
            height += holder.itemView.getMeasuredHeight();
        }


        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        for (int i = 0; i < itemCounts; i++) {
            Bitmap bitmap = bitmaCache.get(String.valueOf(i));
            c.drawBitmap(bitmap, 0f, iHeight, paint);
            iHeight += bitmap.getHeight();
            bitmap.recycle();
        }
        return b;
    }
    private void createPdf(){
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            Paint paint = new Paint();
            canvas.drawPaint(paint);
            bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            document.finishPage(page);

            // write the document content
            int i=0;
            String targetPdf = Environment.getExternalStorageDirectory().getAbsolutePath();

            File filePath;
            String Sname=getArguments().getString("sname");
            String date1=getArguments().getString("date");
            filePath = new File(targetPdf,Sname+date1+".pdf");
            try {
                document.writeTo(new FileOutputStream(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            // close the document
            document.close();
        }



        Toast.makeText(getContext(), "PDF is created!!!", Toast.LENGTH_SHORT).show();

        // openGeneratedPDF();

    }


    private void openGeneratedPDF(){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"sarang"+i+".pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(getContext(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
}
