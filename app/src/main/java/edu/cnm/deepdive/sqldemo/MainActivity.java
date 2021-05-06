package edu.cnm.deepdive.sqldemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

  Button bt_add, bt_viewAll, bt_delete, bt_find;
  EditText et_name, et_age, et_find;
  Switch sw_activeCustomer;
  ListView lv_customerList;
  ArrayAdapter customerArrayAdapter;
  DataBaseHelper dataBaseHelper;


  @SuppressLint("WrongViewCast")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    bt_add = findViewById(R.id.bt_add);
    bt_viewAll = findViewById(R.id.bt_viewAll);
    bt_delete = findViewById(R.id.bt_delete);
    bt_find = findViewById(R.id.bt_find);
    et_name = findViewById(R.id.et_name);
    et_age = findViewById(R.id.et_age);
    et_find = findViewById(R.id.et_find);
    sw_activeCustomer = findViewById(R.id.sw_active);
    lv_customerList = findViewById(R.id.lv_customerList);

    dataBaseHelper = new DataBaseHelper(MainActivity.this);
    showCustomersOnListView(dataBaseHelper);

    bt_add.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        CustomerModel customerModel;
        try {
          customerModel = new CustomerModel(-1, et_name.getText().toString(),
              Integer.parseInt(et_age.getText().toString()), sw_activeCustomer.isChecked());
          Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
          Toast.makeText(MainActivity.this, "error creating customer", Toast.LENGTH_SHORT).show();
          customerModel = new CustomerModel(-1, "error", 0, false);


        }
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        boolean success = dataBaseHelper.addOne(customerModel);
        Toast.makeText(MainActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();
        showCustomersOnListView(dataBaseHelper);

      }
    });

    bt_viewAll.setOnClickListener((v) -> {
      DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

      showCustomersOnListView(dataBaseHelper);

      //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_LONG).show();

    });

    bt_delete.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(MainActivity.this, "Click on the item you want to delete", Toast.LENGTH_LONG)
            .show();


      }
    });


    bt_find.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

    lv_customerList.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
        dataBaseHelper.deleteOne(clickedCustomer);
        showCustomersOnListView(dataBaseHelper);
        Toast.makeText(MainActivity.this, "Deleted: " + clickedCustomer.toString(),
            Toast.LENGTH_SHORT).show();

      }
    });
  }


  private void showCustomersOnListView(DataBaseHelper dataBaseHelper) {
    customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,
        android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
    lv_customerList.setAdapter(customerArrayAdapter);
  }
}