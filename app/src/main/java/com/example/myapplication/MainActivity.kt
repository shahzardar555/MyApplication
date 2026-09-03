package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val idInput = findViewById<EditText>(R.id.idInput)

        val insertButton = findViewById<Button>(R.id.insertButton)
        val retrieveButton = findViewById<Button>(R.id.retrieveButton)
        val updateButton = findViewById<Button>(R.id.updateButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)

        val resultText = findViewById<TextView>(R.id.resultText)

        val database = DatabaseHandler(this)

        // INSERT
        insertButton.setOnClickListener {

            val name = nameInput.text.toString()
            val phone = phoneInput.text.toString()

            if (name.isEmpty() || phone.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter name and phone number",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val contact = Contact()

                contact.name = name
                contact.phoneNumber = phone

                database.addContact(contact)

                Toast.makeText(
                    this,
                    "Contact Added",
                    Toast.LENGTH_SHORT
                ).show()

                nameInput.text.clear()
                phoneInput.text.clear()
            }
        }

        // RETRIEVE
        retrieveButton.setOnClickListener {

            val contacts = database.getAllContacts()

            if (contacts.isEmpty()) {

                resultText.text = "No contacts found"

            } else {

                var result = ""

                for (contact in contacts) {

                    result = result +
                            "ID: ${contact.id}\n" +
                            "Name: ${contact.name}\n" +
                            "Phone: ${contact.phoneNumber}\n\n"
                }

                resultText.text = result
            }
        }

        // UPDATE
        updateButton.setOnClickListener {

            val idText = idInput.text.toString()
            val name = nameInput.text.toString()
            val phone = phoneInput.text.toString()

            if (idText.isEmpty() || name.isEmpty() || phone.isEmpty()) {

                Toast.makeText(
                    this,
                    "Enter ID, name and phone number",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val contact = Contact()

                contact.id = idText.toInt()
                contact.name = name
                contact.phoneNumber = phone

                val result = database.updateContact(contact)

                if (result > 0) {

                    Toast.makeText(
                        this,
                        "Contact Updated",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Toast.makeText(
                        this,
                        "Contact Not Found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // DELETE
        deleteButton.setOnClickListener {

            val idText = idInput.text.toString()

            if (idText.isEmpty()) {

                Toast.makeText(
                    this,
                    "Enter ID",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val result = database.deleteContact(idText.toInt())

                if (result > 0) {

                    Toast.makeText(
                        this,
                        "Contact Deleted",
                        Toast.LENGTH_SHORT
                    ).show()

                    idInput.text.clear()

                } else {

                    Toast.makeText(
                        this,
                        "Contact Not Found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}