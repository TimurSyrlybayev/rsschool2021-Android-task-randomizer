package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var listener: GenerateButtonClickedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as GenerateButtonClickedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"
        val min = view.findViewById<EditText>(R.id.min_value)
        val max = view.findViewById<EditText>(R.id.max_value)

        generateButton?.setOnClickListener {
            val minimumNumber = min.text.toString()
            val maximumNumber = max.text.toString()
            if (minimumNumber == "" || maximumNumber == "") {
                Toast.makeText(
                    getActivity(),
                    "Please insert numbers in both fields.",
                    LENGTH_SHORT
                ).show()
            } else if (minimumNumber.toIntOrNull() !is Int) {
                Toast.makeText(
                    getActivity(),
                    "Minimum number should be in the range between 0 and 2,147,483,646.",
                    LENGTH_SHORT
                ).show()
            } else if (minimumNumber.toInt() < 0 || minimumNumber.toInt() > 2_147_483_646) {
                Toast.makeText(
                    getActivity(),
                    "Minimum number should be in the range between 0 and 2,147,483,646.",
                    LENGTH_SHORT
                ).show()
            } else if (maximumNumber.toIntOrNull() !is Int) {
                Toast.makeText(
                    getActivity(),
                    "Maximum number should be in the range between 0 and 2,147,483,646.",
                    LENGTH_SHORT
                ).show()
            } else if (maximumNumber.toInt() < 0 || maximumNumber.toInt() > 2_147_483_646) {
                Toast.makeText(
                    getActivity(),
                    "Maximum number should be in the range between 0 and 2,147,483,646.",
                    LENGTH_SHORT
                ).show()
            } else if(minimumNumber > maximumNumber) {
                Toast.makeText(
                    getActivity(),
                    "Minimum number should be lesser than maximum number",
                    LENGTH_SHORT
                ).show()
            } else {
                listener?.changeFunction(minimumNumber.toInt(), maximumNumber.toInt())
            }
        }
    }

    companion object {

        @JvmStatic /* аннотация @JvmStatic нужна для того, чтобы в java-файле MainActivity данный
        метод newInstance был статическим, и его можно было вызвать не создавая экземпляр класса.*/
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"

    }

    interface GenerateButtonClickedListener {
        fun changeFunction(min: Int, max: Int): Unit {}
    }
}