package com.example.listatarefas

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var txtTarefa: EditText
    private lateinit var btAdd: Button
    private lateinit var listView: ListView
    private lateinit var tarefas: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Aplicando padding de acordo com os insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializando as views
        txtTarefa = findViewById(R.id.txt_tarefa)
        btAdd = findViewById(R.id.bt_add)
        listView = findViewById(R.id.list_view)

        // Inicializando o ArrayList e o ArrayAdapter
        tarefas = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tarefas)
        listView.adapter = adapter

        // Configurando o botão "Adicionar"
        btAdd.setOnClickListener {
            val tarefa = txtTarefa.text.toString()

            if (tarefa.isNotEmpty()) {
                tarefas.add(tarefa) // Adiciona a nova tarefa ao ArrayList
                adapter.notifyDataSetChanged() // Atualiza a ListView
                txtTarefa.setText("") // Limpa o campo de texto após adicionar
            }
        }
    }
}
