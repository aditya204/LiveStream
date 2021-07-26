package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quantum.mystreamingapp.Adapters.AddProductCategoryAdapter;
import com.quantum.mystreamingapp.Adapters.AddProductDetailsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import top.defaults.colorpicker.ColorPickerPopup;

import static com.quantum.mystreamingapp.R.color.gray_e3;
import static com.quantum.mystreamingapp.R.color.green_200;
import static com.quantum.mystreamingapp.R.color.white;

public class AddProductDetails extends AppCompatActivity {

    ImageButton addproductImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    AddProductDetailsAdapter addProductDetailsAdapter;
    private RecyclerView productImageRecycler;
    EditText name_et,price_et,cut_price_et,offer_et,description_et,stock_et,tags_et;
    TextView condition_txt_new,condition_txt_used,s_size,m_size,l_size,xl_size,xxl_size,color_1,color_2,color_3;
    boolean is_s=false,is_m=false,is_l=false,is_xl=false,is_xxl=false;
    boolean is_new=false,is_old=false;



    private int mDefaultColor_1;
    private int mDefaultColor_2;
    private int mDefaultColor_3;

    TextView category_txt,sub_category_txt;

    Button live_btn;
    String product_id="";
    String display_pic_url="";
    String category="";
    String sub_category="";

    boolean is_timmer=false;

    LinearLayout time_date;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_product_details );

        addproductImage=findViewById( R.id.addImageBtn );
        mStorageRef= FirebaseStorage.getInstance().getReference("USER_PRODUCT_IMAGE");
        productImageRecycler=findViewById( R.id.add_product_details_image_recycler );
        live_btn=findViewById( R.id.live_btn );

        name_et=findViewById( R.id.name_et );
        price_et=findViewById( R.id.price_et );
        cut_price_et=findViewById( R.id.cut_price_et );
        offer_et=findViewById( R.id.offer_et );
        description_et=findViewById( R.id.description_et );
        stock_et=findViewById( R.id.stock_et );
        tags_et=findViewById( R.id.tags_et );
        condition_txt_used=findViewById( R.id.condition_txt_used );
        condition_txt_new=findViewById( R.id.condition_txt_new );
        s_size=findViewById( R.id.s_size );
        m_size=findViewById( R.id.m_size );
        l_size=findViewById( R.id.l_size );
        xl_size=findViewById( R.id.xl_size );
        xxl_size=findViewById( R.id.xxl_size );
        color_1=findViewById( R.id.color_1 );
        color_2=findViewById( R.id.color_2 );
        color_3=findViewById( R.id.color_3 );
        time_date=findViewById( R.id.time_date );

        category_txt=findViewById( R.id.category_txt );
        sub_category_txt=findViewById( R.id.sub_category_txt);


        display_pic_url  =getIntent().getStringExtra( "live_display_image" );
        category=getIntent().getStringExtra( "category_name" );
        sub_category =getIntent().getStringExtra( "sub_category_name" );


        category_txt.setText( category );
        sub_category_txt.setText( sub_category );

        DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);




        if(category.equals( "Live Countdown" )){
            is_timmer=true;
            offer_et.setVisibility( View.INVISIBLE );
            cut_price_et.setVisibility(View.INVISIBLE );
            price_et.setHint( "Base Price in RM" );
            time_date.setVisibility( View.VISIBLE );

        }


        live_btn.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                String name=name_et.getText().toString();
                String description=description_et.getText().toString();
                String price=price_et.getText().toString();
                String stock=stock_et.getText().toString();
                String tags=tags_et.getText().toString();


                if(name.isEmpty()||description.isEmpty()||price.isEmpty()||tags.isEmpty()||DBquaries.product_images.size()==0||stock.isEmpty()){
                    Toast.makeText( AddProductDetails.this, "Fill All Fields", Toast.LENGTH_SHORT ).show( );

                }else {
                    DocumentReference ref = FirebaseFirestore.getInstance().collection("PRODUCTS").document();
                    product_id = ref.getId();
                    String[] tag =tags.toLowerCase().trim().split( "," );

                    ArrayList<String> tags_arr =new ArrayList<>(  );
                    tags_arr.add( name );
                    tags_arr.add( price );
                    tags_arr.add( offer_et.getText().toString() );
                    tags_arr.addAll( Arrays.asList( tag ) );

                    Map<String,Object> AddProductDetails=new HashMap<>(  );
                    for(int i=0;i<DBquaries.product_images.size();i++){
                        AddProductDetails.put( "image_0"+i,DBquaries.product_images.get( i ) );
                    }

                    calendar = new GregorianCalendar(datePicker.getYear(),
                            datePicker.getMonth(),
                            datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(),
                            timePicker.getCurrentMinute());
                    AddProductDetails.put( "live_id","" );
                    AddProductDetails.put( "name",name );
                    AddProductDetails.put( "price",price );
                    AddProductDetails.put( "rating","-" );
                    AddProductDetails.put( "review_count","0" );
                    AddProductDetails.put( "in_stock",Integer.parseInt( stock_et.getText().toString() ) );

                    if(cut_price_et.getText().toString().isEmpty()){
                        AddProductDetails.put( "cut_price",price_et.getText().toString() );

                    }else {
                        AddProductDetails.put( "cut_price",cut_price_et.getText().toString() );

                    }
                    AddProductDetails.put( "no_of_image",DBquaries.product_images.size() );
                    AddProductDetails.put( "offer",offer_et.getText().toString() );
                    AddProductDetails.put( "description",description );
                    AddProductDetails.put( "relevant_tag",tags );
                    AddProductDetails.put( "is_s",is_s );
                    AddProductDetails.put( "is_m",is_m );
                    AddProductDetails.put( "is_l",is_l );
                    AddProductDetails.put( "is_xl",is_xl );
                    AddProductDetails.put( "is_xxl", is_xxl );
                    AddProductDetails.put( "is_new", is_new );
                    AddProductDetails.put( "is_old", is_old );
                    AddProductDetails.put( "color_1",mDefaultColor_1 );
                    AddProductDetails.put( "color_2",mDefaultColor_2 );
                    AddProductDetails.put( "color_3",mDefaultColor_3 );
                    AddProductDetails.put( "time",calendar.getTime() );
                    AddProductDetails.put( "winner_id","" );
                    AddProductDetails.put( "winner_name","" );
                    AddProductDetails.put( "bid_price",price );
                    AddProductDetails.put( "is_timmer",is_timmer );
                    AddProductDetails.put( "display_pic",display_pic_url );
                    AddProductDetails.put( "tags",tags_arr );
                    AddProductDetails.put( "owner_id",FirebaseAuth.getInstance().getCurrentUser().getUid() );



                    category=category.toLowerCase().replaceAll( "\\s", "_" );

                    FirebaseFirestore.getInstance().collection("PRODUCTS").document(product_id).set( AddProductDetails )
                            .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Intent intent=new Intent( AddProductDetails.this,MainActivity.class );
                                        intent.putExtra( "product_id",product_id );
                                        intent.putExtra( "display_picture",display_pic_url );
                                        intent.putExtra( "category",category );
                                        intent.putExtra( "sub_category",sub_category);
                                        DBquaries.dealsUploadNotification( display_pic_url,product_id,is_timmer,sub_category,calendar.getTime().toString() );
                                        startActivity( intent );


                                    }

                                }
                            } );









                }


            }
        } );

        color_1.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(AddProductDetails.this).initialColor(
                        Color.RED) // set initial color
                        // of the color
                        // picker dialog
                        .enableBrightness(
                                true) // enable color brightness
                        // slider or not
                        .enableAlpha(
                                true) // enable color alpha
                        // changer on slider or
                        // not
                        .okTitle(
                                "Choose") // this is top right
                        // Choose button
                        .cancelTitle(
                                "Cancel") // this is top left
                        // Cancel button which
                        // closes the
                        .showIndicator(
                                true) // this is the small box
                        // which shows the chosen
                        // color by user at the
                        // bottom of the cancel
                        // button
                        .showValue(
                                true) // this is the value which
                        // shows the selected
                        // color hex code
                        // the above all values can be made
                        // false to disable them on the
                        // color picker dialog.
                        .build()
                        .show(
                                v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        // set the color
                                        // which is returned
                                        // by the color
                                        // picker
                                        mDefaultColor_1 = color;

                                        // now as soon as
                                        // the dialog closes
                                        // set the preview
                                        // box to returned
                                        // color
                                        color_1.setText( "" );
                                        color_1.setBackgroundColor(mDefaultColor_1);
                                    }


                                });
            }
        } );
        color_2.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(AddProductDetails.this).initialColor(
                        Color.RED) // set initial color
                        // of the color
                        // picker dialog
                        .enableBrightness(
                                true) // enable color brightness
                        // slider or not
                        .enableAlpha(
                                true) // enable color alpha
                        // changer on slider or
                        // not
                        .okTitle(
                                "Choose") // this is top right
                        // Choose button
                        .cancelTitle(
                                "Cancel") // this is top left
                        // Cancel button which
                        // closes the
                        .showIndicator(
                                true) // this is the small box
                        // which shows the chosen
                        // color by user at the
                        // bottom of the cancel
                        // button
                        .showValue(
                                true) // this is the value which
                        // shows the selected
                        // color hex code
                        // the above all values can be made
                        // false to disable them on the
                        // color picker dialog.
                        .build()
                        .show(
                                v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        // set the color
                                        // which is returned
                                        // by the color
                                        // picker
                                        mDefaultColor_2 = color;

                                        // now as soon as
                                        // the dialog closes
                                        // set the preview
                                        // box to returned
                                        // color
                                        color_2.setText( "" );
                                        color_2.setBackgroundColor(mDefaultColor_2
                                        );
                                    }


                                });
            }
        } );
        color_3.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                new ColorPickerPopup.Builder(AddProductDetails.this).initialColor(
                        Color.RED) // set initial color
                        // of the color
                        // picker dialog
                        .enableBrightness(
                                true) // enable color brightness
                        // slider or not
                        .enableAlpha(
                                true) // enable color alpha
                        // changer on slider or
                        // not
                        .okTitle(
                                "Choose") // this is top right
                        // Choose button
                        .cancelTitle(
                                "Cancel") // this is top left
                        // Cancel button which
                        // closes the
                        .showIndicator(
                                true) // this is the small box
                        // which shows the chosen
                        // color by user at the
                        // bottom of the cancel
                        // button
                        .showValue(
                                true) // this is the value which
                        // shows the selected
                        // color hex code
                        // the above all values can be made
                        // false to disable them on the
                        // color picker dialog.
                        .build()
                        .show(
                                v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        // set the color
                                        // which is returned
                                        // by the color
                                        // picker
                                        mDefaultColor_3 = color;

                                        // now as soon as
                                        // the dialog closes
                                        // set the preview
                                        // box to returned
                                        // color
                                        color_3.setText( "" );
                                        color_3.setBackgroundColor(mDefaultColor_3);
                                    }


                                });
            }
        } );



        s_size.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(is_s){
                    s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    is_s=false;

                }else {
                    s_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                    is_s=true;
                }
            }
        } );
        m_size.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(is_m){
                    m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    is_m=false;

                }else {
                    m_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                    is_m=true;
                }
            }
        } );
        l_size.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(is_l){
                    l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    is_l=false;

                }else {
                    l_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                    is_l=true;
                }
            }
        } );
        xl_size.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(is_xl){
                    xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    is_xl=false;

                }else {
                    xl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                    is_xl=true;
                }
            }
        } );
        xxl_size.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                if(is_xxl){
                    xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    is_xxl=false;

                }else {
                    xxl_size.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                    is_xxl=true;
                }
            }
        } );


        condition_txt_new.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                    condition_txt_new.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                    condition_txt_used.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                    is_new=true;
                    is_old=false;

            }
        } );

        condition_txt_used.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                condition_txt_used.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), green_200) );
                condition_txt_new.setBackgroundTintList( ContextCompat.getColorStateList(getApplicationContext(), gray_e3) );
                is_new=false;
                is_old=true;

            }
        } );











        addProductDetailsAdapter = new AddProductDetailsAdapter(DBquaries.product_images );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this );
        linearLayoutManager.setOrientation( RecyclerView.HORIZONTAL );
        productImageRecycler.setLayoutManager( linearLayoutManager );
        productImageRecycler.setAdapter( addProductDetailsAdapter );
        addProductDetailsAdapter.notifyDataSetChanged();


        addproductImage.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        } );


    }
    private void openFileChooser() {

        Intent intent = new Intent( );
        intent.setType( "image/*" );
        intent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( intent, PICK_IMAGE_REQUEST );


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if(requestCode==PICK_IMAGE_REQUEST && resultCode== RESULT_OK
                && data!= null && data.getData() != null   ){


            mImageUri= data.getData();

            uploadFile();

        }
    } private String getFileExtention(Uri uri){
        ContentResolver cR= getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( cR.getType( uri ) );
    }

    public void uploadFile(){

        if(mImageUri!=null){
            final StorageReference fileref= mStorageRef.child( FirebaseAuth.getInstance().getCurrentUser().getUid()).child( System.currentTimeMillis()+"."+getFileExtention( mImageUri ) );

            fileref.putFile( mImageUri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>( ) {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>( ) {
                        @Override
                        public void onSuccess(final Uri uri) {
                            DBquaries.product_images.add( uri.toString() );

                            addProductDetailsAdapter.notifyDataSetChanged();
                            productImageRecycler.smoothScrollToPosition(  DBquaries.product_images.size()-1 );




                        }
                    } );




                }
            } ).addOnFailureListener( new OnFailureListener( ) {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( AddProductDetails.this, e.getMessage(), Toast.LENGTH_SHORT ).show( );
                }
            } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>( ) {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {



                }
            } );
        }else {
            Toast.makeText( this, "no item selected", Toast.LENGTH_SHORT ).show( );

        }



    }

}