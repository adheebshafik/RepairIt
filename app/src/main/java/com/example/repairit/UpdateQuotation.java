package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.bumptech.glide.Glide;
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

public class UpdateQuotation extends AppCompatActivity {

    private EditText vNo;
    private EditText vModel;
    private Button chooseImageBtn;
    private ImageView imageViewDamage;
    private EditText jobAppointed;
    private Button update, back;

    StorageReference storageRef  = FirebaseStorage.getInstance().getReferenceFromUrl("gs://repairit-bfc37.appspot.com/");
//    StorageReference storageRef  = FirebaseStorage.getInstance().getReferenceFromUrl("gs://repairit-bfc37.appspot.com/");
    Uri imguri;

//    private ProgressDialog progressDialog = new ProgressDialog(this);
//    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quotation);


        vNo = findViewById(R.id.editQuoVNoET);
        vModel = findViewById(R.id.editQuotationVModelET);
        chooseImageBtn = findViewById(R.id.editchooseFileAddQuotation);
        imageViewDamage = findViewById(R.id.imageView8edit);
        jobAppointed = findViewById(R.id.editQuojobAppointedET);
        update = findViewById(R.id.editQuoAddBtn);
        back = findViewById(R.id.editQuoBackButton);

        Intent intent = getIntent();
        String quoID = intent.getStringExtra(Quotation.QUOTATIONID);
        String vcehNo = intent.getStringExtra(Quotation.VEHICLE_NO);
        String vcehModel = intent.getStringExtra(Quotation.VEHICLE_MODEL);
        final String vImgUrl = intent.getStringExtra(Quotation.PHOTO);
        String vchJob = intent.getStringExtra(Quotation.APP_JOB);

        vNo.setText(vcehNo);
        vModel.setText(vcehModel);
        Glide.with(getApplicationContext()).load(vImgUrl).into(imageViewDamage);
        jobAppointed.setText(vchJob);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UpdateQuotation.this, Quotation.class);
                startActivity(intent);
                finish();
            }
        });

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //GET TEXT FROM FIELDS
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

//                    progressDialog.setMessage("Updating Quotation");
//                    progressDialog.show();
//                    ProgressThread thread = new ProgressThread();
//                    thread.start();

//                    if(uploadTask != null && uploadTask.isInProgress())
//                        Toast.makeText(UpdateQuotation.this, "Upload is in progress", Toast.LENGTH_LONG).show();


                    Intent intent = getIntent();
                    String quoID = intent.getStringExtra(Quotation.QUOTATIONID);
                    String urll = intent.getStringExtra(Quotation.PHOTO);

                    updateHelper();
//                    progressDialog.dismiss();

                }

            }

        });

    }

//    private class ProgressThread extends Thread {
//
//        public void run() {
//
//            for(int count = 0;  count <= 100; count++) {
//                try {
//                    Thread.sleep(100);
//                    progressDialog.setProgress(count);;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//    }

    private boolean updateHelper() {


        if(imguri != null) {


            StorageReference childRef = storageRef.child("Images/" + System.currentTimeMillis() + "." + getExtension(imguri));

            UploadTask uploadTask= childRef.putFile(imguri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String vehicleNo = vNo.getText().toString();
                    String vehicleModel = vModel.getText().toString();
                    String vehicleJob = jobAppointed.getText().toString();


                    Intent intent = getIntent();
                    String quoID = intent.getStringExtra(Quotation.QUOTATIONID);
                    updateImage(quoID, vehicleNo, vehicleModel, taskSnapshot.getDownloadUrl().toString(), vehicleJob  );
                }
            });


        } else {
            Toast.makeText(UpdateQuotation.this, "Select an image", Toast.LENGTH_SHORT).show();
        }

        return true;

    }

    private boolean updateImage(final String quotationId, String vehicleNo, String vehicleModel, String imageUrl, String vehicleJob) {



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        DatabaseReference quoReference = FirebaseDatabase.getInstance().getReference("quotations").child(userId).child(quotationId);
        QuotationObject quotationObject = new QuotationObject(quotationId, vehicleNo, vehicleModel, imageUrl, vehicleJob);
        quoReference.setValue(quotationObject);
//        progressDialog.dismiss();
        Toast.makeText(this, "Quotation Updated", Toast.LENGTH_LONG).show();
        startActivity(new Intent(UpdateQuotation.this, Quotation.class));
        finish();

        return true;
    }


    private boolean updateQuotation(final String quotationId, String vehicleNo, String vehicleModel, String vehicleJob) {

        DatabaseReference quoReference = FirebaseDatabase.getInstance().getReference("quotations").child(quotationId);
        QuotationObject quotationObject = new QuotationObject(quotationId, vehicleNo, vehicleModel, vehicleJob);
        quoReference.setValue(quotationObject);
        Toast.makeText(this, "Quotation Updated", Toast.LENGTH_LONG).show();
        startActivity(new Intent(UpdateQuotation.this, Quotation.class));

        return true;

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

}
