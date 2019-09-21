package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AddQuotation extends AppCompatActivity {


    private EditText vNo;
    private EditText vModel;
    private Button chooseImageBtn;
    private ImageView imageViewDamage;
    private EditText jobAppointed;
    private Button add, back;

    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    Uri imguri;

    private ProgressDialog progressDialog;
    private StorageTask uploadTask;

    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quotation);


        progressDialog = new ProgressDialog(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("quotations");

        vNo = findViewById(R.id.addQuoVNoET);
        vModel = findViewById(R.id.addQuotationVModelET);
        chooseImageBtn = findViewById(R.id.chooseFileAddQuotation);
        imageViewDamage = findViewById(R.id.imageView8);
        jobAppointed = findViewById(R.id.addQuojobAppointedET);
        back = findViewById(R.id.newQuoBackButton);
        add = findViewById(R.id.newQuoAddBtn);

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddQuotation.this, Quotation.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String vehicleNo = vNo.getText().toString();
                String vehicleModel = vModel.getText().toString();
                String vehicleJob = jobAppointed.getText().toString();

                if(vehicleNo.isEmpty()) {
                    vNo.setError("Enter Vehicle No");
                } else if(vehicleModel.isEmpty())
                    vModel.setError("Enter Model");
                else if(vehicleJob.isEmpty())
                    jobAppointed.setError("Enter Job");
                else if(imageViewDamage.getDrawable() == null)
                    jobAppointed.setError("Insert Image");
                else if(!vehicleNo.isEmpty() && !vehicleModel.isEmpty() && !vehicleJob.isEmpty() && imageViewDamage.getDrawable() !=null) {

                    progressDialog.setMessage("Adding Quotation");
                    progressDialog.show();

                    if(uploadTask != null && uploadTask.isInProgress())
                        Toast.makeText(AddQuotation.this, "Upload is in progress", Toast.LENGTH_LONG).show();

                    quotationUploader();

                }

            }
        });

    }

    public void displayNotification(View view) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(AddQuotation.this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_sms);
        builder.setContentTitle("Repairit ALERT");
        builder.setContentText("Quotation will sent by SMS within 24 hours after evaluation");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(AddQuotation.this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }

    private String getExtension(Uri uri) {

        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private void fileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            imageViewDamage.setImageURI(imguri);
        }

    }

    private void quotationUploader() {

        if( imguri != null ) {

            StorageReference reference = mStorageRef.child("Images/" + System.currentTimeMillis() + "." + getExtension(imguri));

            uploadTask = reference.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String vehicleNo = vNo.getText().toString();
                            String vehicleModel = vModel.getText().toString();
                            String vehicleJob = jobAppointed.getText().toString();
							
							FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            final String userId = user.getUid();


                            // Get a URL to the uploaded content
                            String quoId = mDatabaseRef.push().getKey();
                            QuotationObject quotationObject = new QuotationObject(quoId, vehicleNo, vehicleModel, taskSnapshot.getDownloadUrl().toString(), vehicleJob);
                            mDatabaseRef.child(userId).child(quoId).setValue(quotationObject);
                            progressDialog.dismiss();
                            Toast.makeText(AddQuotation.this, "Quotation Added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddQuotation.this, Quotation.class));
                            finish();

                        }
                    });

        } else {
            Toast.makeText(AddQuotation.this, "Please Upload an Image", Toast.LENGTH_LONG).show();
        }

    }

}
