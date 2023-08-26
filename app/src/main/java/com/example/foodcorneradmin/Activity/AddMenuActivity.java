package com.example.foodcorneradmin.Activity;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodcorneradmin.Models.MenuModel;
import com.example.foodcorneradmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class AddMenuActivity extends AppCompatActivity {
    FirebaseStorage storage;
    StorageReference storageRef;
    private Uri selectedImageUri;

    EditText itemName, itemPrice, itemDescription;
    ImageView itemImage,addItemImage;
    AppCompatButton addItemButton;
    ProgressBar progressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);


        db = FirebaseDatabase.getInstance().getReference().child("Data").child("MenuItems");
        progressBar=findViewById(R.id.progressBar);
        itemName = findViewById(R.id.addMenuActivityItemName);
        itemPrice = findViewById(R.id.addMenuActivityItemPrice);
        itemDescription = findViewById(R.id.addMenuActivityItemDescription);
        itemImage = findViewById(R.id.addMenuActivityItemImage);
        addItemImage = findViewById(R.id.addMenuActivityAddImageButton);
        addItemButton = findViewById(R.id.addMenuActivityAddItemButton);  // Add this line
        selectedImage();

        // Set up click listener for "Add Item" button
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addItemButton.isEnabled()) {
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                addItemButton.setEnabled(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                addMenuItemToFireStore();
            }
        });
    }

    private void selectedImage() {
        addItemImage = findViewById(R.id.addMenuActivityAddImageButton);
        addItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            itemImage.setImageURI(selectedImageUri);
        }
    }

    private void addMenuItemToFireStore() {
        // Check if any of the fields are empty
        String itemNameStr = itemName.getText().toString();
        String itemPriceStr = itemPrice.getText().toString();
        String itemDescriptionStr = itemDescription.getText().toString();
        if (itemNameStr.isEmpty() || itemPriceStr.isEmpty() || itemDescriptionStr.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            addItemButton.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        // Initialize Firebase Storage reference
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        // Generate a random UUID for the menu item
        String menuItemId=db.push().getKey();

        // Create a reference to the image in Firebase Storage
        StorageReference imageRef = storageRef.child("menu_item_images").child(menuItemId);

        // Upload the image to Firebase Storage
        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL of the uploaded image
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Create a new MenuModel instance with the image URL
                        MenuModel menuModel = new MenuModel(menuItemId, itemNameStr, itemPriceStr, uri.toString(), itemDescriptionStr);

                        // Add the menu item to Firestore
                        db.child(menuItemId).setValue(menuModel)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(AddMenuActivity.this, "Menu item added successfully", Toast.LENGTH_SHORT).show();
                                    // Clear input fields or perform other actions after adding
                                    itemName.setText("");
                                    itemPrice.setText("");
                                    itemDescription.setText("");
                                    itemImage.setImageURI(null);
                                    selectedImageUri = null;
                                    addItemButton.setEnabled(true);
                                    progressBar.setVisibility(View.INVISIBLE);

                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(AddMenuActivity.this, "Error adding menu item", Toast.LENGTH_SHORT).show();
                                });
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddMenuActivity.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                });
    }

}
