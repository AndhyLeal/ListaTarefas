package com.example.listatarefas

import android.R.id
import android.content.Context
import android.icu.util.LocaleData
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var txtTarefa: EditText
    private lateinit var btAdd: Button
    private lateinit var listView: ListView
    private lateinit var btRemove: Button
    private lateinit var tarefas: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedItemIndex: Int = -1 // Para armazenar o índice do item selecionado


    private val PREFS_NAME = "TarefasPrefs"
    private val KEY_TAREFAS = "tarefas"
    private val KEY_CONTADOR = "contador"


    private var contador = 0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
      //  dataTaf.text = "19/10/2024"
        // Aplicando padding de acordo com os insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dataTaf = findViewById<TextView>(R.id.txt_date)
        val dataAtual: LocalDate = LocalDate.now()
        dataTaf.text = dataAtual.toString()
        dataTaf.visibility = View.VISIBLE
        // Inicializando as views
        txtTarefa = findViewById(R.id.txt_tarefa)
        btAdd = findViewById(R.id.bt_add)
        btRemove = findViewById(R.id.bt_remover)
        listView = findViewById(R.id.list_view)

        // Inicializando o ArrayList e o ArrayAdapter
        tarefas = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tarefas)
        listView.adapter = adapter

        // Carregar tarefas do SharedPreferences1
        loadTarefas()
        loadContador() // Carrega o contador


        // Configurando o botão "Adicionar"
        btAdd.setOnClickListener {
            val tarefa = txtTarefa.text.toString()

            if (tarefa.isNotEmpty()) {
                tarefas.add(" ID: $contador | $tarefa" ) // Adiciona a nova tarefa ao ArrayList
                adapter.notifyDataSetChanged() // Atualiza a ListView
                txtTarefa.setText("") // Limpa o campo de texto após adicionar
                saveTarefas() // Salva a lista atualizada
            }
        }

        /* Configurando o botão "Remover"
        btRemove.setOnClickListener{
             val idRemover = txtTarefa.text.toString()
             val removerTaf = tarefas.find { it.contains("ID: $idRemover" ) }
             tarefas.remove(removerTaf)
             txtTarefa.setText("")
            adapter.notifyDataSetChanged() // Atualiza a ListView
            saveTarefas() // Salva a lista atualizada
            saveContador() //Salva o contador atualizado
        }*/

        // Configurando o clique nos itens da lista
        listView.setOnItemClickListener { _, _, position, _ ->
            selectedItemIndex = position // Armazena o índice do item selecionado
            tarefas[position] // Exibe o item selecionado no campo de texto
        }

        // Configurando o botão "Remover"
        btRemove.setOnClickListener {
            if (selectedItemIndex != -1) {
                tarefas.removeAt(selectedItemIndex) // Remove o item selecionado
                adapter.notifyDataSetChanged() // Atualiza a ListView
                saveTarefas() // Salva a lista atualizada
                selectedItemIndex = -1 // Reseta o índice do item selecionado
            }
        }

    }

    private fun saveTarefas() {
        contador++
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_TAREFAS, tarefas.joinToString(";")) // Salva as tarefas separadas por ponto e vírgula
        editor.apply()
    }

    private fun loadTarefas() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val tarefasString = sharedPreferences.getString(KEY_TAREFAS, "")
        if (!tarefasString.isNullOrEmpty()) {
            tarefas.clear()
            tarefas.addAll(tarefasString.split(";")) // Divide a string e adiciona as tarefas na lista
            adapter.notifyDataSetChanged() // Atualiza a ListView
        }
    }

    private fun saveContador() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CONTADOR, contador) // Salva o contador
        editor.apply()
    }

    private fun loadContador() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        contador = sharedPreferences.getInt(KEY_CONTADOR, 0) // Carrega o contador (0 se não existir)
    }


}
