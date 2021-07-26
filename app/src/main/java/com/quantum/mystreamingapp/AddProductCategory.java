package com.quantum.mystreamingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quantum.mystreamingapp.Adapters.AddProductCategoryAdapter;
import com.quantum.mystreamingapp.Adapters.grocery_cart_product_Adapter;
import com.quantum.mystreamingapp.Models.AddProductCategoryModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProductCategory extends AppCompatActivity {

    ImageButton addimge;
    private ImageView displayImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    public static String imageUrl="";
    public static String live_category="";
    RecyclerView add_product_category_recycler;
    AddProductCategoryAdapter addProductCategoryAdapter;
    List<AddProductCategoryModel> addProductCategoryModelList;
    private Dialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_product_category );

        mStorageRef= FirebaseStorage.getInstance().getReference("USER_VIDEO_DISPLAY_IMAGE");
        displayImage=findViewById( R.id.display_image );
        add_product_category_recycler=findViewById( R.id.add_product_category_recycler );

        addimge=findViewById( R.id.addImageBtn );

        live_category=getIntent().getStringExtra( "category_name" );

        loadingDialog = new Dialog( AddProductCategory.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow( ).setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );


        addProductCategoryModelList=new ArrayList<>(  );
        addProductCategoryModelList.add( new AddProductCategoryModel( "Pet Supplies" ) );
        addProductCategoryModelList.add( new AddProductCategoryModel( "Furniture" ) );
        addProductCategoryModelList.add( new AddProductCategoryModel( "Toys & Games" ) );
        addProductCategoryModelList.add( new AddProductCategoryModel( "Electronic Supplies" ) );
        addProductCategoryModelList.add( new AddProductCategoryModel( "Auto Accessories" ) );
        addProductCategoryModelList.add( new AddProductCategoryModel( "Babies & Kids" ) );
        addProductCategoryModelList.add( new AddProductCategoryModel( "Community" ));
        addProductCategoryModelList.add( new AddProductCategoryModel( "Gaming" ) );
        addProductCategoryModelList.add( new AddProductCategoryModel( "Clothes" ) );
        addProductCategoryModelList.add( new AddProductCategoryModel( "Design & Craft" ) );


        addProductCategoryAdapter = new AddProductCategoryAdapter(addProductCategoryModelList );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        add_product_category_recycler.setLayoutManager( linearLayoutManager );
        add_product_category_recycler.setAdapter( addProductCategoryAdapter );
        addProductCategoryAdapter.notifyDataSetChanged();










        addimge.setOnClickListener( new View.OnClickListener( ) {
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
            displayImage.setVisibility( View.VISIBLE );
            displayImage.setImageURI( mImageUri );
            uploadFile();
            addimge.setVisibility( View.GONE );

        }
    } private String getFileExtention(Uri uri){
        ContentResolver cR= getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( cR.getType( uri ) );
    }

    public void uploadFile(){

        if(mImageUri!=null){
            loadingDialog.show();
            final StorageReference fileref= mStorageRef.child( FirebaseAuth.getInstance().getCurrentUser().getUid()).child( System.currentTimeMillis()+"."+getFileExtention( mImageUri ) );

            fileref.putFile( mImageUri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>( ) {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>( ) {
                        @Override
                        public void onSuccess(final Uri uri) {

                            imageUrl=uri.toString();
                            loadingDialog.dismiss();
                        }
                    } );




                }
            } ).addOnFailureListener( new OnFailureListener( ) {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( AddProductCategory.this, e.getMessage(), Toast.LENGTH_SHORT ).show( );
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