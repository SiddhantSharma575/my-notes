package com.example.mynotes.ui

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDatabase
import com.example.mynotes.ui.helpers.toast
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


class AddNoteFragment : BaseFragment() {

    private var note: Note? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)



        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            et_title.setText(note?.title)
            ettext.setText(note?.note)

        }


       btnSave.setOnClickListener { view ->

           val note_Title = et_title.text.toString()
           val note_Body = ettext.text.toString()

           if(note_Title.isEmpty()){
               et_title.error = "Title Required"
               et_title.requestFocus()
               return@setOnClickListener
           }


           if(note_Body.isEmpty()){
               et_title.error = "Title Required"
               et_title.requestFocus()
               return@setOnClickListener
           }

           launch {
               context?.let {
                   val mnote = Note(note_Title,note_Body)
                   if (note == null){
                       NoteDatabase(it).getNoteDao().addNote(mnote)
                       it.toast("Note Saved")
                   }else{
                       mnote.id =  note!!.id
                       NoteDatabase(it).getNoteDao().updateNote(mnote)
                       it.toast("Note updated Successfully")
                   }


                   val action = AddNoteFragmentDirections.actionSaveNote()
                   Navigation.findNavController(view).navigate(action)
               }
           }
       }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    private fun deleteNote(){
        AlertDialog.Builder(context).apply {
            setTitle("Are You Sure??")
            setMessage("You Cannot Undo this operation")
            setPositiveButton("Yes") {_,_ ->
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(requireView()).navigate(action)
                }
            }
            setNegativeButton("No") {_,_ ->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete -> {
                if(note != null) deleteNote() else context?.toast("Cannot Delete")
            }
        }

        return super.onOptionsItemSelected(item)
    }

}