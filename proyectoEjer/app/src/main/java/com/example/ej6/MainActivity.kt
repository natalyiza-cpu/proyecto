package com.example.ej6

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var edtNombre: EditText
    private lateinit var btnAgregar: Button
    private lateinit var txtMostrarResultado: TextView
    private lateinit var db: FirebaseFirestore // Instancia de Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los elementos del layout
        edtNombre = findViewById(R.id.EditTxtNombre)
        btnAgregar = findViewById(R.id.btnAgregar)
        txtMostrarResultado = findViewById(R.id.txtMostrarResultado)
        db = FirebaseFirestore.getInstance()

        btnAgregar.setOnClickListener {
            val nombre = edtNombre.text.toString().trim()
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Debes ingresar un nombre obligatoriamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = hashMapOf(
                "nombre" to nombre,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("usuarios")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Guardado con ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
                    edtNombre.text.clear()
                    txtMostrarResultado.text = "Último guardado: $nombre"
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
