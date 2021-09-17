package test.qustodio.hackathonkidsexprerience.ui.main

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import test.qustodio.hackathonkidsexprerience.R
import test.qustodio.hackathonkidsexprerience.databinding.FragmentColorsGameBinding
import test.qustodio.hackathonkidsexprerience.databinding.FragmentMathGameBinding
import kotlin.random.Random


/**
 * A placeholder fragment containing a simple view.
 */

class MathGameFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMathGameBinding? = null
    var result = 0
    var score = 0

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

        _binding = FragmentMathGameBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Find all views and set Tag to all draggable views
        val mathProblem = binding.mathProblem

        val button1 = binding.button1
        val button2 = binding.button2
        val button3 = binding.button3
        val button4 = binding.button4
        newMathProblem()
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
    }

    private fun newMathProblem() {
        binding.scoreText.text = getString(R.string.score, score)
        val sum1 = Random.nextInt(100)
        val sum2 = Random.nextInt(100)
        val operationPlus = Random.nextBoolean()
        result = if (operationPlus) {
            binding.mathProblem.text = getString(R.string.math_problem, sum1, "+", sum2)
            sum1+sum2
        } else {
            binding.mathProblem.text = getString(R.string.math_problem, sum1, "-", sum2)
            sum1-sum2
        }

        val resultPosition = Random.nextInt(4)

        binding.button1.text = (result + 1).toString()
        binding.button2.text = (result - 1).toString()
        binding.button3.text = (result + 2).toString()
        binding.button4.text = (result - 2).toString()

        when {
            resultPosition == 0 -> binding.button1.text = result.toString()
            resultPosition == 1 -> binding.button2.text = result.toString()
            resultPosition == 2 -> binding.button3.text = result.toString()
            else -> binding.button4.text = result.toString()
        }
    }


    override fun onClick(v: View?) {
        if ((v as Button).text == result.toString()) {
            activity?.let {
                MaterialAlertDialogBuilder(it)
                    .setTitle("GREAT!")
                    .setMessage("Correct answer")
                    .setPositiveButton("New challenge") { _, _ ->
                        score+=1
                        newMathProblem()
                    }
                    .show()
            }
        } else {
            activity?.let {
                MaterialAlertDialogBuilder(it)
                    .setTitle("OoooooOops!")
                    .setMessage("Wrong answer")
                    .setPositiveButton("Try again") { _, _ ->
                        score-=1
                        binding.scoreText.text = getString(R.string.score, score)
                    }
                    .show()
            }
        }
    }

    companion object {

        /**
         * Returns a new instance of this fragment.
         */
        @JvmStatic
        fun newInstance(): MathGameFragment {
            return MathGameFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

