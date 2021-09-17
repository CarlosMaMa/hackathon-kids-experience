package test.qustodio.hackathonkidsexprerience.ui.main

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.DragShadowBuilder
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import test.qustodio.hackathonkidsexprerience.databinding.FragmentColorsGameBinding


/**
 * A placeholder fragment containing a simple view.
 */

class ColorsGameFragment : Fragment(), View.OnDragListener, View.OnLongClickListener {

    private var _binding: FragmentColorsGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentColorsGameBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Find all views and set Tag to all draggable views
        val red = binding.red
        red.tag = "RED"
        red.setOnLongClickListener(this)
        val blue = binding.blue
        blue.tag = "BLUE"
        blue.setOnLongClickListener(this)
        val green = binding.green
        green.tag = "GREEN"
        green.setOnLongClickListener(this)
        //Set Drag Event Listeners for defined layouts
        binding.layout1.setOnDragListener(this)
        binding.layout2.setOnDragListener(this)
        binding.layout3.setOnDragListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onLongClick(v: View): Boolean {
        // Create a new ClipData.Item from the ImageView object's tag
        val item = ClipData.Item(v.tag as CharSequence)
        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val data = ClipData(v.tag.toString(), mimeTypes, item)
        // Instantiates the drag shadow builder.
        val dragshadow = DragShadowBuilder(v)
        // Starts the drag
        v.startDragAndDrop(data // data to be dragged
            , dragshadow // drag shadow builder
            , v // local data about the drag and drop operation
            , 0 // flags (not currently used, set to 0)
        )
        return true
    }

    override fun onDrag(v: View, event: DragEvent): Boolean {
        // Defines a variable to store the action type for the incoming event
        val action = event.action
        when (action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                // Determines if this View can accept the dragged data
                return if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept data.
                    // v.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                    // Invalidate the view to force a redraw in the new tint
                    //  v.invalidate();
                    // returns true to indicate that the View can accept the dragged data.
                    true
                } else false
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                // Applies a GRAY or any color tint to the View. Return true; the return value is ignored.
                v.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
                // Invalidate the view to force a redraw in the new tint
                v.invalidate()
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION ->                 // Ignore the event
                return true
            DragEvent.ACTION_DRAG_EXITED -> {
                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                // view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                //It will clear a color filter .
                v.background.clearColorFilter()
                // Invalidate the view to force a redraw in the new tint
                v.invalidate()
                return true
            }
            DragEvent.ACTION_DROP -> {
                // Gets the item containing the dragged data
                val item = event.clipData.getItemAt(0)
                // Gets the text data from the item.
                val dragData = item.text.toString()
                // Displays a message containing the dragged data.
                Toast.makeText(context, "Dragged data is $dragData", Toast.LENGTH_SHORT).show()
                // Turns off any color tints
                v.background.clearColorFilter()
                // Invalidates the view to force a redraw
                v.invalidate()
                val vw = event.localState as View
                val owner = vw.parent as ViewGroup
                owner.removeView(vw) //remove the dragged view
                //caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                val container = v as LinearLayout
                container.addView(vw) //Add the dragged view
                vw.visibility = View.VISIBLE //finally set Visibility to VISIBLE
                // Returns true. DragEvent.getResult() will return true.
                return true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                // Turns off any color tinting
                v.background.clearColorFilter()
                // Invalidates the view to force a redraw
                v.invalidate()
                // Does a getResult(), and displays what happened.
                if (event.result) Toast.makeText(context, "The drop was handled.", Toast.LENGTH_SHORT)
                    .show() else Toast.makeText(context, "The drop didn't work.", Toast.LENGTH_SHORT)
                    .show()
                // returns true; the value is ignored.
                return true
            }
            else -> Log.e("DragDrop Example", "Unknown action type received by OnDragListener.")
        }
        return false
    }


    companion object {

        /**
         * Returns a new instance of this fragment.
         */
        @JvmStatic
        fun newInstance(): ColorsGameFragment {
            return ColorsGameFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

