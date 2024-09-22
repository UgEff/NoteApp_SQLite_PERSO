package fr.esilv.notesqlite
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter (private var notes: List<Note>, context: Context): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    private val db: NoteDatabaseHelper = NoteDatabaseHelper(context)

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updataButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deletteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view) 
    }

    override fun getItemCount(): Int = notes.size
    
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        Log.d("ADAPTER", "Affichage de la note : Title: ${note.title}, Content: ${note.content}")
        // Réinitialisation
        holder.titleTextView.text = ""
        holder.contentTextView.text = ""

        // Mise à jour avec les nouvelles données
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        // Mise à jour du bouton
        holder.updataButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("noteId", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // delete button
        holder.deletteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshDate(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Note supprimée", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshDate(newNotes: List<Note>){
        notes = newNotes
        Log.d("ADAPTER", "Mise à jour de l'Adapter avec ${newNotes.size} notes")
        notifyDataSetChanged()
    }
}