package test.qustodio.hackathonkidsexprerience.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import test.qustodio.hackathonkidsexprerience.R
import test.qustodio.hackathonkidsexprerience.databinding.FragmentShapesGameBinding
import kotlin.random.Random

/**
 * A placeholder fragment containing a simple view.
 */
class ShapesGameFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentShapesGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val shapeList: List<Int> = listOf(R.drawable.triangle,
        R.drawable.square,
        R.drawable.circle,
        R.drawable.oval,
        R.drawable.rectangle)

    private val shapeListNames: List<String> = listOf("triangle",
        "square",
        "circle",
        "oval",
        "rectangle")

    private var currentChallenge = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentShapesGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button1 = binding.kidConfirm
        button1.setOnClickListener(this)

        newShapeProblem()
    }

    private fun newShapeProblem() {
        binding.kidAnswer.text?.clear()
        currentChallenge = Random.nextInt(shapeList.size)
        binding.shapeImage.setImageResource(shapeList[currentChallenge])
    }


    override fun onClick(v: View?) {
        if (shapeListNames[currentChallenge].equals(binding.kidAnswer.text.toString(), true)) {
            activity?.let {
                MaterialAlertDialogBuilder(it)
                    .setTitle("GREAT!")
                    .setMessage("Correct answer")
                    .setPositiveButton("New challenge") { _, _ ->
                        newShapeProblem()
                    }
                    .show()
            }
        } else {
            activity?.let {
                MaterialAlertDialogBuilder(it)
                    .setTitle("OoooooOops!")
                    .setMessage("Wrong answer, the correct answer is: " + shapeListNames[currentChallenge])
                    .setPositiveButton("Try again") { _, _ ->
                        //Nothing to do
                    }
                    .show()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(): ShapesGameFragment {
            return ShapesGameFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}