package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, "contactsManager", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = """
            CREATE TABLE contacts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                phone_number TEXT
            )
        """

        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS contacts")

        onCreate(db)
    }

    // INSERT
    fun addContact(contact: Contact) {

        val db = writableDatabase

        val values = ContentValues()

        values.put("name", contact.name)
        values.put("phone_number", contact.phoneNumber)

        db.insert("contacts", null, values)

        db.close()
    }

    // RETRIEVE ALL CONTACTS
    fun getAllContacts(): ArrayList<Contact> {

        val contactList = ArrayList<Contact>()

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM contacts",
            null
        )

        if (cursor.moveToFirst()) {

            do {

                val contact = Contact()

                contact.id = cursor.getInt(0)
                contact.name = cursor.getString(1)
                contact.phoneNumber = cursor.getString(2)

                contactList.add(contact)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return contactList
    }

    // UPDATE
    fun updateContact(contact: Contact): Int {

        val db = writableDatabase

        val values = ContentValues()

        values.put("name", contact.name)
        values.put("phone_number", contact.phoneNumber)

        val result = db.update(
            "contacts",
            values,
            "id = ?",
            arrayOf(contact.id.toString())
        )

        db.close()

        return result
    }

    // DELETE
    fun deleteContact(id: Int): Int {

        val db = writableDatabase

        val result = db.delete(
            "contacts",
            "id = ?",
            arrayOf(id.toString())
        )

        db.close()

        return result
    }

    // COUNT
    fun getContactsCount(): Int {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM contacts",
            null
        )

        val count = cursor.count

        cursor.close()
        db.close()

        return count
    }
}