package julius.barde.com.dreamlisterapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE = 5;
    DreamItem myDreamItem = new DreamItem();
    RecyclerView recyclerView;
    Adapter myAdapter;
    List<DreamItem> AllDreamItem ;
    myDBHandler myDB= new myDBHandler(this);
    int idALLOC = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

findViews();




}

    private void findViews() {
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView);
       myAdapter = new Adapter(this,myDB.getAllDreamItems());
        recyclerView.setAdapter(myAdapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);


    }

    public void addItem(View view){

        Intent myIntent = new Intent(this,Add_Dream.class);
        startActivityForResult (myIntent,REQ_CODE);

    }


    public void menuButton(View view) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        PopupMenu popup = new PopupMenu(MainActivity.this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_first, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.edit:
                    editItem();
                    break;
                case R.id.delete:
                    deleteItem();
                    break;
            }
            return false;
        }
    }
    public void editItem(){

        Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();

    }
    public void deleteItem(){
        Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {

            String myName= data.getStringExtra("NAME");
            String myPrice= data.getStringExtra("PRICE");
            String myDescription= data.getStringExtra("DESCRIPTION");
            Bitmap myImageID = data.getParcelableExtra("IMAGEID");
            myDreamItem.setName(myName);
            myDreamItem.setPrice((myPrice));
            myDreamItem.setDescription(myDescription);
            myDreamItem.setImageID(myImageID);


            Log.d("Insert: ", "Inserting ..");
            AddDream(myDreamItem);


        }

    }

    public void AddDream(DreamItem myDreamItem){

        boolean insertData = myDB.addItem(myDreamItem.getName(),myDreamItem.getDescription(),myDreamItem.getPrice(),myDreamItem.getImageID());
        findViews();
        if (insertData) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, myDreamItem.getName()+myDreamItem.getPrice()+myDreamItem.getDescription()+myDreamItem.getImageID(), Toast.LENGTH_SHORT).show();
        }


    }
}



