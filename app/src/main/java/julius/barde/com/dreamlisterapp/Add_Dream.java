package julius.barde.com.dreamlisterapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import static java.lang.Double.parseDouble;

public class Add_Dream extends AppCompatActivity {
    private static final String DREAM_LIST_HOLDER = "yoyo";
    private static final String KEY_PRICE = "550";
    ImageView itemPhoto;


    Bitmap imageBitmap;


    EditText price_et,name_et,description_et;
    public DreamItem myDreamItem = new DreamItem();
    private static final int SELECT_PHOTO = 25;
    private static final int CAPTURE_PHOTO = 26 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__dream);
        itemPhoto = (ImageView) findViewById(R.id.dreamImage);
        price_et = (EditText) findViewById(R.id.price_et);
        name_et = (EditText) findViewById(R.id.itemName_et);
        description_et = (EditText) findViewById(R.id.description_et);


    }

    private void setValues() {
        myDreamItem.setName(name_et.getText().toString());
        myDreamItem.setPrice(price_et.getText().toString());
        myDreamItem.setDescription(description_et.getText().toString());
        myDreamItem.setImageID(imageBitmap);


    }





    public void menu_button(View view) {

        PopupMenu popup = new PopupMenu(Add_Dream.this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.options, popup.getMenu());
        popup.setOnMenuItemClickListener(new Add_Dream.MyMenuItemClickListener());
        popup.show();
    }

    public void Add_Item(View view) {
        itemPhoto.setImageResource(R.drawable.blank);
        setValues();



        Intent myIntent = new Intent();
        myIntent.putExtra("NAME",myDreamItem.getName() );
        myIntent.putExtra("PRICE",myDreamItem.getPrice());
        myIntent.putExtra("DESCRIPTION",myDreamItem.getDescription());
        myIntent.putExtra("IMAGEID",myDreamItem.imageID);

        setResult(Activity.RESULT_OK,myIntent);
        finish();




    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.upload:
                    ChoosePhoto();
                    break;
                case R.id.take:
                    TakePhoto();

                    break;
            }
            return false;
        }
    }

    public void ChoosePhoto() {
        Intent myIntent = new Intent(Intent.ACTION_PICK);
        myIntent.setType("image/*");
        startActivityForResult(myIntent, SELECT_PHOTO);


    }
    public void TakePhoto() {
        Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (myIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(myIntent, CAPTURE_PHOTO);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            itemPhoto.setImageBitmap(imageBitmap);

        }
        else if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Toast.makeText(this, "Joke rani sir", Toast.LENGTH_SHORT).show();


        }


        }

}
