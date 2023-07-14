package fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import DataBase.Instence_class;
import DataBase.Model_Class;
import DataBase.PModel;

import com.bumptech.glide.Glide;
import com.example.db_seller.R;
import com.example.db_seller.Splash_Screen;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import DataBase.TransferDataFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Product_Fragment extends Fragment
{

    ImageView imageView;
    EditText fname,fstock,fprice,fcategory;
    TextView submitbutton;

    int SELECT_PICTURE = 200;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = LayoutInflater.from(getContext()).inflate(R.layout.add_product_fragment,container,false);

       imageView = view.findViewById(R.id.addimage);
       fname=view.findViewById(R.id.namefield);
       fstock=view.findViewById(R.id.stockfield);
       fprice=view.findViewById(R.id.pricefield);
       fcategory=view.findViewById(R.id.categoryfield);
       submitbutton = view.findViewById(R.id.submitbutton);

       if(getArguments()!=null) {

            fname.setText("" + getArguments().getString("name"));
            fstock.setText("" + getArguments().getString("stock"));
            fprice.setText("" + getArguments().getString("price"));
            fcategory.setText("" + getArguments().getString("category"));
            Glide.with(Add_Product_Fragment.this)
                    .load("https://dipkakadiya.000webhostapp.com/MySite/" + getArguments().getString("img"))
                    .into(imageView);
           Log.d("NNN", "onCreateView: Name="+getArguments().getString("name"));
        }
        //Log.d("TTT", "getDataFromFragment: fName="+name);


       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               imageChooser();
           }
       });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                // In case you want to compress your image, here it's at 40%
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String imagedata = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    imagedata = Base64.getEncoder().encodeToString(byteArray);
                }

                addfragment(new Home_Fragment());

                if(Splash_Screen.preferences.getString("from",null).equals("add")) {
                    Instence_class.Callapi().addproduct(Splash_Screen.preferences.getInt("sellerid", 0), fname.getText().toString(), fstock.getText(), fprice.getText(), fcategory.getText().toString(), imagedata).enqueue(new Callback<PModel>() {
                        @Override
                        public void onResponse(Call<PModel> call, Response<PModel> response) {
                            if (response.body().getConnection() == 1) {
                                if (response.body().getProductaddd() == 1) {
                                    //Toast.makeText(getContext(), "Product Add Sucessfully", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "Failed to Add Product", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PModel> call, Throwable t) {

                        }
                    });
                }
                if(Splash_Screen.preferences.getString("from",null).equals("update")){
                    Toast.makeText(getContext(), "Update pref", Toast.LENGTH_LONG).show();

                    fname.setText(""+Splash_Screen.preferences.getString("pname",null));

                    Instence_class.Callapi().updateproduct(getArguments().getString("pname",null),getArguments().getString("pprice",null),getArguments().getString("pstock",null),getArguments().getString("pcategory",null),getArguments().getString("pid",null)).enqueue(new Callback<Model_Class>() {
                        @Override
                        public void onResponse(Call<Model_Class> call, Response<Model_Class> response) {
                            if(response.body().getConnection()==1){
                                if(response.body().getResult()==1){
                                    Log.d("RRR", "onResponse: update result = "+response.body().getResult());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Model_Class> call, Throwable t) {

                        }
                    });

                }
            }
        });

        return view;
    }

    private void addfragment(Fragment fragment)
    {
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.framlayout,fragment);
        transaction.commit();
    }


    void imageChooser() {
/////////////111111111111
//        // create an instance of the
//        // intent of the type image
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//
//        // pass the constant to compare it
//        // with the returned requestCode
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);


        ////////////////22222

        CropImage.activity()
                .start(getContext(), this);


    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == RESULT_OK) {
//
//            // compare the resultCode with the
//            // SELECT_PICTURE constant
//            if (requestCode == SELECT_PICTURE) {
//                // Get the url of the image from data
//                Uri selectedImageUri = data.getData();
//                if (null != selectedImageUri) {
//                    // update the preview image in the layout
//                    imageView.setImageURI(selectedImageUri);
//                }
//            }
//        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
