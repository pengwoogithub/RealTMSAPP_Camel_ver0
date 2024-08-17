package com.example.realtmsapp_camel.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.realtmsapp_camel.DatabaseHelper;
import com.example.realtmsapp_camel.R;
import com.example.realtmsapp_camel.Trial;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class SettingsFragment extends Fragment {

    private Button exportButton,clearAllButton, secretbutton;;
    private DatabaseHelper databaseHelper;

    private static final int PERMISSION_REQUEST_CODE = 100;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        clearAllButton = view.findViewById(R.id.Clear);
        exportButton = view.findViewById(R.id.Export);
        databaseHelper = new DatabaseHelper(getActivity());

        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearAllConfirmationDialog();
            }
        });
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportDatabaseToExcel();
            }
        });

        return view;
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportDatabaseToExcel();
            } else {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //method to export the database to xls file
    private void exportDatabaseToExcel() {
        try {
            // Create a workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Trial Data");

            // Fetch data from the database
            List<Trial> trialsList = databaseHelper.getAllTrials();

            // Add headers
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Process");
            headerRow.createCell(1).setCellValue("Person");
            headerRow.createCell(2).setCellValue("Model");
            for (int i = 1; i <= 10; i++) {
                headerRow.createCell(i + 2).setCellValue("Trial" + i);
            }

            // Add data rows
            int rowIndex = 1;
            for (Trial trial : trialsList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(trial.getProcess());
                row.createCell(1).setCellValue(trial.getPerson());
                row.createCell(2).setCellValue(trial.getModel());
                List<String> trialValues = Arrays.asList(trial.getTrialValues());
                for (int i = 0; i < trialValues.size(); i++) {
                    row.createCell(i + 3).setCellValue(trialValues.get(i));
                }
            }

            // Save to a file in the Documents directory
            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "MyAppExports");
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }
            File file = new File(directory, "TrialData.xlsx");
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            Toast.makeText(getContext(), "Export successful! File saved at: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Export failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    //Method for confirming deletion of all data
    private void showClearAllConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Clear All Data")
                .setMessage("Are you sure you want to clear all data? This action cannot be undone.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearAllData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clearAllData() {
        boolean isCleared = databaseHelper.clearAllData();
        if (isCleared) {
            Toast.makeText(getActivity(), "All data cleared successfully", Toast.LENGTH_SHORT).show();
            // Optionally refresh any displayed data or UI
        } else {
            Toast.makeText(getActivity(), "Failed to clear data", Toast.LENGTH_SHORT).show();
        }
    }
}