package com.example.myshoppingapp.activities;

import static android.app.ProgressDialog.show;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshoppingapp.R;
import com.example.myshoppingapp.models.CustomeAdapter;
import com.example.myshoppingapp.models.DataModel;
import com.example.myshoppingapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.myshoppingapp.models.myData;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public ArrayList<DataModel> dataSet;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomeAdapter adapter;

    public User currentUser;
    public TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


    }

    public void creatDataView(View view)
    {


// Access the View from the Fragment's layout

        recyclerView =  view.findViewById(R.id.resView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (dataSet == null) {
            dataSet = new ArrayList<>();
            for ( int i =0 ; i < myData.nameArray.length ; i++) {
                dataSet.add(new DataModel(
                        myData.nameArray[i],
                        myData.priceArray[i],
                        myData.drawableArray[i],
                        myData.id_[i],
                        0
                ));
            }
        }


        adapter = new CustomeAdapter(dataSet, new CustomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DataModel dataModel) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_item_details);

                // Initialize the views in the custom dialog layout
                ImageView dialogImageView = dialog.findViewById(R.id.dialogImageView);
                TextView dialogTextViewName = dialog.findViewById(R.id.dialogTextViewName);
                TextView dialogTextViewVersion = dialog.findViewById(R.id.dialogTextViewVersion);

                // Set the data from the clicked item to the dialog views
                dialogImageView.setImageResource(dataModel.getImage());
                dialogTextViewName.setText(dataModel.getName());
                dialogTextViewVersion.setText(dataModel.getPrice());

                // Display the custom dialog
                dialog.show();
            }
        });
        recyclerView.setAdapter(adapter);

        Button button = view.findViewById(R.id.searchButt);

        // Set onClickListener to the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = ((EditText) findViewById(R.id.editTextText)).getText().toString().trim();
                adapter.filter(searchQuery);
            }
        });
    }
    public void addData()
    {
        EditText userEmail = this.findViewById(R.id.emailInput);
        EditText userPass = this.findViewById(R.id.passwordInput);
        EditText userName = this.findViewById(R.id.nameinput);
        EditText userPhone = this.findViewById(R.id.phone_input);
        EditText userAge = this.findViewById(R.id.age_input);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("users").child(userName.getText().toString());

        User user1 = new User(userEmail.getText().toString(),userPass.getText().toString(),userPhone.getText().toString(),userAge.getText().toString(),userName.getText().toString());



        String email=userEmail.getText().toString();

        String password=userPass.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign i n success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            DatabaseReference myRef = database.getReference("users").child(firebaseUser.getUid());
                            myRef.setValue(user1);
                            Toast.makeText(MainActivity.this, "User created successfully!.",
                                    Toast.LENGTH_SHORT).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void readData()
    {
        EditText user = findViewById(R.id.nameinput);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(user.getText().toString());

        myRef.addValueEventListener(new ValueEventListener() {
            // Read from the database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
                Toast.makeText(MainActivity.this, value.getEmail(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void onSignIn(View v)
    {
        EditText userEmail = this.findViewById(R.id.emailInput);
        EditText userPass = this.findViewById(R.id.passwordInput);


        String email = userEmail.getText().toString();
        String password = userPass.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            fetchUserDetails(user);
                            //updateUI(user);
                            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_shopFragment);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }


    private void fetchUserDetails(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
            //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();//a@a.com
            //String email = FirebaseDatabase.getInstance().getReference("users").child(userId).child("email");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("FirebaseDebug", "onDataChange invoked.");
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        currentUser = user;
                        usernameTextView.setText(currentUser.getName());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("FirebaseDebug", "onCancelled invoked.");
                    // Handle possible errors
                }
            });
        }
    }
    public void addProduct(View v) {
        ViewGroup parentLayout = (ViewGroup)v.getParent().getParent().getParent().getParent();
        TextView productText = parentLayout.findViewById(R.id.productName);
        String currentProduct= productText.getText().toString();
        
        for (DataModel product :dataSet
             ) {
            if (product.getName() == currentProduct) {
                product.setAmount(product.getAmount()+1);
                TextView myTextView = parentLayout.findViewById(R.id.textAmount);
                Integer amount= product.getAmount();                                             // Set the new text you want to display
                myTextView.setText(amount.toString());
                currentUser.setFinalAmount(currentUser.getFinalAmount()+Integer.parseInt(product.getPrice()));


            }

        }

    }


    public void removeProduct(View v) {
        ViewGroup parentLayout = (ViewGroup)v.getParent().getParent().getParent().getParent();
        TextView productText = parentLayout.findViewById(R.id.productName);
        String currentProduct= productText.getText().toString();
        
        for (DataModel product :dataSet
        ) {
            if (product.getName() == currentProduct && product.getAmount()>0) {
                product.setAmount(product.getAmount()-1);
                TextView myTextView = parentLayout.findViewById(R.id.textAmount);
                Integer amount= product.getAmount();      // Set the new text you want to display
                if (amount == 0) {
                    myTextView.setText("");
                }
                else {
                    myTextView.setText(amount.toString());
                }
                currentUser.setFinalAmount(currentUser.getFinalAmount()-Integer.parseInt(product.getPrice()));

            }

        }

    }
    public void initViewHolder(View viewHolder)
    {
        TextView productText = viewHolder.findViewById(R.id.productName);
        String currentProduct= productText.getText().toString();
        for (DataModel product :dataSet
        ) {
            if (product.getAmount() != 0) {
                TextView myTextView = viewHolder.findViewById(R.id.textAmount);
                Integer amount= product.getAmount();                                             // Set the new text you want to display
                myTextView.setText(amount.toString());
            }
        }
    }
}