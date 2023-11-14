package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.data.storage.entity.Note
import com.example.myapplication.R
import com.example.myapplication.presentation.mainScreen.MainActivity
import com.example.myapplication.presentation.mainScreen.MainViewModel
import com.example.myapplication.presentation.themeScreen.ThemeFragmentViewModel
import org.koin.java.KoinJavaComponent.inject


class NoteAdapter(
    private val context: Context,
    private val mainActivity: MainActivity
) : RecyclerView.Adapter<NoteAdapter.MyHolder>() {

    private val mainViewModel : MainViewModel by inject(MainViewModel::class.java)
    var notes = ArrayList<Note>()

    class MyHolder(
        private val itemView: View,
        private val context: Context,
        private val mainActivity: MainActivity,
        private val mainViewModel: MainViewModel
        ) : RecyclerView.ViewHolder(itemView) {

        private val tfvm : ThemeFragmentViewModel by inject(ThemeFragmentViewModel::class.java)

        val title = itemView.findViewById<TextView>(R.id.title)
        val description = itemView.findViewById<TextView>(R.id.description)
        val noteLinearLayout = itemView.findViewById<LinearLayout>(R.id.noteLinearLayout)

        fun setData(note: Note) {
            title.text = note.title
            description.text = note.description
            itemView.setOnClickListener {
                mainViewModel.getNote(note.id)
            }
        }

        fun changeBackgroundNote(){
            tfvm.getChangedTheme().observe(mainActivity, Observer {
                if(it != null) {
                    noteLinearLayout.setBackgroundColor(
                        context.getResources().getColor(it.backgroundNoteColor)
                    )
                }
            })
        }

        fun setCurrentNoteBackground(){
            noteLinearLayout.setBackgroundColor(
                context.getResources().getColor(tfvm.getCurrentTheme().backgroundNoteColor)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(
            inflater.inflate(R.layout.note, parent, false),
            context,
            mainActivity,
            mainViewModel)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(notes[position])
        holder.changeBackgroundNote()
        holder.setCurrentNoteBackground()
    }

    fun updateAdapter(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    fun removeNote(position:Int){
        val idNoteForDelete = notes[position].id
        mainViewModel.deleteNote(idNoteForDelete)
        notifyItemRangeChanged(0,notes.size)
        notifyItemRemoved(position)
    }
}

