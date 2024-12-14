package com.example.app2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // *** Highlighted Code Starts Here ***

        // Get references to the UI components
        val fullNameEditText = findViewById<EditText>(R.id.editTextFullName)
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val genderRadioGroup = findViewById<RadioGroup>(R.id.radioGroupGender)
        val countrySpinner = findViewById<Spinner>(R.id.spinnerCountry)
        val cityAutoComplete = findViewById<AutoCompleteTextView>(R.id.autoCompleteCity)
        val checkBoxTerms = findViewById<CheckBox>(R.id.checkBoxTerms)
        val submitButton = findViewById<Button>(R.id.buttonSubmit)

        // Sample list for countries and city autocomplete
        val countries = arrayOf("India", "USA", "UK", "Canada", "Australia")
        val cities = arrayOf("New York", "Mumbai", "London", "Sydney", "Toronto")

        // Set up the Spinner for countries
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = adapter

        // Set up the AutoCompleteTextView for cities
        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cities)
        cityAutoComplete.setAdapter(cityAdapter)

        // Handle the Submit Button click
        submitButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Get the selected gender
            val selectedGenderId = genderRadioGroup.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedGenderId)
                selectedRadioButton.text.toString()
            } else {
                "Not Selected"
            }

            val country = countrySpinner.selectedItem.toString()
            val city = cityAutoComplete.text.toString()

            // Check if the terms checkbox is checked
            if (!checkBoxTerms.isChecked) {
                Toast.makeText(this, "Please agree to terms and conditions", Toast.LENGTH_LONG).show()
            } else {
                // Pass data to the second activity
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("FULL_NAME", fullName)
                intent.putExtra("EMAIL", email)
                intent.putExtra("GENDER", gender)
                intent.putExtra("COUNTRY", country)
                intent.putExtra("CITY", city)
                startActivity(intent)
            }
        }
        // *** Highlighted Code Ends Here ***
    }
}

class SecondActivity {

}
