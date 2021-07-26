package com.quantum.mystreamingapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quantum.mystreamingapp.Address;
import com.quantum.mystreamingapp.Models.AddressModel;
import com.quantum.mystreamingapp.MyAddress;
import com.quantum.mystreamingapp.R;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressModel> addressModelList;

    public AddressAdapter(List<AddressModel> addressModelList) {
        this.addressModelList = addressModelList;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.address_recycler_item, parent, false );
        return new ViewHolder( view );    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {

        String name=addressModelList.get( position ).getNmae();
        String phone=addressModelList.get( position ).getPhone();
        String details=addressModelList.get( position ).getDetails();
        String type=addressModelList.get( position ).getType();
        String altrrnamePhn=addressModelList.get( position ).getAltrtnatePhn();
        int size=addressModelList.get( position ).getSize();




        holder.setData( name,details,phone,type,position,altrrnamePhn,size);

    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,details,phone,type;
        private ImageButton delete,edit;
        private LinearLayout item_layout;



        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            name=itemView.findViewById( R.id.address_recycler_name);

            details=itemView.findViewById( R.id.address_recycler_address_details);
            phone=itemView.findViewById( R.id.address_recycler_phone_no);
            type=itemView.findViewById( R.id.address_recycler_address_type);
            delete=itemView.findViewById( R.id.address_recycler_delete_btn);
            edit=itemView.findViewById( R.id.address_recycler_edit_btn);
            item_layout=itemView.findViewById( R.id.address_recycler_item_LL );


        }


        private void setData(final String Name, final String Details, final String Phone, final String Type, final int positon, final String AltPhone, final int Size)
        {
            name.setText( Name );
            details.setText( Details );
            phone.setText( Phone );
            type. setText(Type );


            item_layout.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {


                    MyAddress.loadingDialog.show();
                    Map<String, Object> UserData = new HashMap<>( );
                    UserData.put( "fullname", Name );
                    UserData.put( "phone", Phone );
                    UserData.put( "address_details", Details  );
                    UserData.put( "address_type", Type  );
                    UserData.put( "altPhone", AltPhone );
                    UserData.put("previous_position",positon+1);

                    FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                            .update( UserData ).addOnCompleteListener(
                            new OnCompleteListener<Void>( ) {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful( )) {
                                        Toast.makeText( itemView.getContext(), "selected Address no:"+ String.valueOf( positon+1 ), Toast.LENGTH_SHORT ).show( );
                                        MyAddress.selectedAdd.setText( "selected Address no:"+ String.valueOf( positon+1 ) );
                                        MyAddress.selected_position=positon+1;

                                        MyAddress.loadingDialog.dismiss();

                                    }
                                }
                            }
                    );



                }
            } );

            edit.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent( itemView.getContext(), Address.class );
                    intent.putExtra( "layout_code",1 );
                    intent.putExtra( "position",addressModelList.size()- positon-1);
                    itemView.getContext().startActivity( intent );
                }
            } );

            delete.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {


                    if(positon+1== MyAddress.selected_position){
                        Toast.makeText( itemView.getContext( ), "Can't delete selected address", Toast.LENGTH_SHORT ).show( );

                    }else {
                        MyAddress.loadingDialog.show();
                        if(positon+1< MyAddress.selected_position){
                            Map<String, Object> UserData = new HashMap<>( );
                            UserData.put("previous_position", MyAddress.selected_position-1);
                            MyAddress.loadingDialog.show();

                            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                                    .update( UserData ).addOnCompleteListener(
                                    new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful( )) {
                                                MyAddress.loadingDialog.dismiss();

                                            }
                                        }
                                    }
                            );
                        }

                        Map<String, Object> UserData = new HashMap<>( );
                        UserData.put( "is_visible", false );
                        int index =MyAddress.size-Size;
                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_ADDRESS" ).collection( "address_list" ).document( "address_" + index ).update( UserData ).
                                addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful( )) {
                                            MyAddress.loadingDialog.dismiss();
                                            Toast.makeText( itemView.getContext( ), " Address removed ", Toast.LENGTH_SHORT ).show( );
                                            itemView.getContext( ).startActivity( new Intent( itemView.getContext( ), MyAddress.class ) );

                                        }
                                    }
                                } );
                    }
                }
            } );




        }


    }
}
