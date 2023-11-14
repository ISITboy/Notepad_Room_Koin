package com.example.myapplication.presentation.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.NoteAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.addNoteScreen.AddNoteFragment
import com.example.myapplication.presentation.themeScreen.ThemeFragment
import com.example.myapplication.presentation.themeScreen.ThemeFragmentViewModel

import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val vm : MainViewModel by inject()
    private val noteAdapter by lazy { NoteAdapter(this,this)}
    lateinit var binding : ActivityMainBinding
    private val tfvm : ThemeFragmentViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initItemTouchHelper()
        initSearchView()
        initCustomToolBar()
        initBackgroundNotepadColor()

        vm.getAllNote()

        vm.getNotes().observe(this, Observer {
            showMessage(it.isEmpty())
            noteAdapter.updateAdapter(it)
        })
        vm.getSelectedNote().observe(this,Observer{
            if(it != null) AddNoteFragment().show(supportFragmentManager, AddNoteFragment().tag)
        })
        tfvm.getChangedTheme().observe(this, Observer {
            if(it != null) {
                supportActionBar?.setBackgroundDrawable(
                    getColor(it.toolbarColor).toDrawable())
                binding.rootConstraintlayout.setBackgroundColor(
                    getResources().getColor(it.backgroundNotepadColor)
                )
            }
        })

    }

    private fun showMessage(emptyList:Boolean){
        if (emptyList){
            binding.message.visibility = View.VISIBLE
        } else binding.message.visibility = View.GONE
    }
    fun addNoteOnClick(view: View) {
        vm.selectedNote.value = null
        AddNoteFragment().show(supportFragmentManager, AddNoteFragment().tag)
    }

    private fun getSwapMg(): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.
        SimpleCallback(0, ItemTouchHelper.RIGHT /*or ItemTouchHelper.LEFT*/ ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteAdapter.removeNote(viewHolder.adapterPosition)
                showMessage("Note deleted")
            }

        })
    }
    private fun showMessage(message:String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete-> {
                dialogForDelete()
            }
            R.id.theme-> ThemeFragment().show(supportFragmentManager, ThemeFragment().tag)
        }
        return true
    }

    private fun dialogForDelete(){
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Do you want all notes delete?")
            .setMessage("Data is deleted forever!!")
            .setPositiveButton("Delete"){dialog,id->
                vm.deleteAllNotes()
                showMessage("All_Delete")
                dialog.cancel()
            }
            .setNegativeButton("Back"){ dialog,id->
                dialog.cancel()
            }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun initCustomToolBar(){
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setBackgroundDrawable(
            getColor(vm.getCurrentToolbarColor()).toDrawable()
        )
    }

    private fun initBackgroundNotepadColor(){
        binding.rootConstraintlayout.setBackgroundColor(
            getResources().getColor(vm.getCurrentBackgroundNotepadColor())
        )
    }
    private fun initItemTouchHelper()= with(binding){
        val swapHelper = getSwapMg()
        swapHelper.attachToRecyclerView(recyclerView)
    }
    fun initSearchView(){
        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(searchedTitle: String?): Boolean {
                vm.findNoteByTitle(searchedTitle!!)
                vm.getNotesSortedByTitle().observe(this@MainActivity, Observer {
                    noteAdapter.updateAdapter(it)
                })
                return true
            }
        })
    }
    private fun initRecyclerView()=with(binding){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }
    }
}