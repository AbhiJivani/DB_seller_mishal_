package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db_seller.Main_Page;
import com.example.db_seller.R;
import com.example.db_seller.Splash_Screen;
import com.example.db_seller.ViewProAdapter;

import DataBase.Instence_class;
import DataBase.TransferDataFragment;
import DataBase.ViewProductClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inventory_Fragment extends Fragment
{
    ViewProAdapter adapter;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.inventory_fragment,container,false);

        Instence_class.Callapi().viewproduct(Splash_Screen.preferences.getInt("sellerid",0)).enqueue(new Callback<ViewProductClass>() {
            @Override
            public void onResponse(Call<ViewProductClass> call, Response<ViewProductClass> response) {
                adapter=new ViewProAdapter(Inventory_Fragment.this,response.body().getProductdata(), new TransferDataFragment() {
                    @Override
                    public void getDataFromFragment(int id, String name, String price, String stock, String category,String imgData) {
                        Add_Product_Fragment add_product_fragment=new Add_Product_Fragment();
                        Bundle args = new Bundle();
                        args.putString("id", String.valueOf(id));
                        args.putString("name", name);
                        args.putString("price", price);
                        args.putString("stock", stock);
                        args.putString("category", category);
                        args.putString("img",imgData);
                        add_product_fragment.setArguments(args);


                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.framlayout, add_product_fragment);
                        transaction.commit();


                    }
                });
                recyclerView=view.findViewById(R.id.recycleview);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<ViewProductClass> call, Throwable t) {

            }
        });

        return view;
    }
}
