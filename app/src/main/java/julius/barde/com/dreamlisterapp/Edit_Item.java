package julius.barde.com.dreamlisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Edit_Item extends AppCompatActivity {
    Uri myUri;
    TextView price_tv;
    TextView description_tv;
    TextView name_tv;
    EditText price,description,name;
    AlertDialog priceDialog,descriptionDialog,nameDialog;
    ImageView itemPhoto;
    private static final int SELECT_PHOTO = 25;
    private static final int CAPTURE_PHOTO = 26;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

         findViews();
        priceDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    price_tv.setText(price.getText());

                    }
                });
        price_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                price.setText(price_tv.getText());
                priceDialog.show();
            }
        });

        descriptionDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                description_tv.setText(description.getText());

            }
        });
       description_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setText(description_tv.getText());
                descriptionDialog.show();
            }
        });
        nameDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name_tv.setText(name.getText());

            }
        });
        name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(name_tv.getText());
                nameDialog.show();
            }
        });



    }
    public void menu_button(View view) {
        Toast.makeText(this, "yoyo", Toast.LENGTH_SHORT).show();
        PopupMenu popup = new PopupMenu(Edit_Item.this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.options, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {

            myUri = data.getData();


            itemPhoto.setImageURI(myUri);


        }
       else if (requestCode == CAPTURE_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {

            if (requestCode == CAPTURE_PHOTO && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                itemPhoto.setImageBitmap(imageBitmap);
            }


        }
    }
    public void findViews(){


        itemPhoto = (ImageView) findViewById(R.id.itemPhoto);
        price_tv = (TextView) findViewById(R.id.price_tvv);
        description_tv = (TextView) findViewById(R.id.description_tv);
        name_tv = (TextView) findViewById(R.id.name_tvv);
        price = new EditText(this);
        name = new EditText(this);
        description = new EditText(this);
        priceDialog = new AlertDialog.Builder(this).create();
        descriptionDialog = new AlertDialog.Builder(this).create();
        nameDialog = new AlertDialog.Builder(this).create();
        priceDialog.setTitle("Edit the Price");
        priceDialog.setView(price);
        descriptionDialog.setTitle("Edit the Description");
        descriptionDialog.setView(description);
        nameDialog.setTitle("Edit the name");
        nameDialog.setView(name);

    }
}
