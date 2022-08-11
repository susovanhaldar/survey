package com.example.firebaseapplication1.activities.surveys.create

import android.app.DatePickerDialog
import android.content.ContentValues.TAG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.firebaseapplication1.R
import com.example.firebaseapplication1.data.model.Question
import com.example.firebaseapplication1.data.model.Survey
import com.example.firebaseapplication1.databinding.ActivityCreateSurveyBinding
import com.example.firebaseapplication1.util.INPUT_TYPES
import com.example.firebaseapplication1.util.TYPE
import com.example.firebaseapplication1.util.createQuestion

import com.example.firebaseapplication1.util.getViewModelFactory
import kotlinx.android.synthetic.main.activity_create_survey.*
import kotlinx.android.synthetic.main.add_question_text.view.*
import kotlinx.android.synthetic.main.layout_question_mcq.*
import kotlinx.android.synthetic.main.layout_question_mcq.view.*
import kotlinx.android.synthetic.main.layout_question_mcq.view.tv_question
import kotlinx.android.synthetic.main.layout_question_text.view.*
import kotlinx.android.synthetic.main.radio_button_option.view.*
import java.util.*

import kotlin.collections.ArrayList


class CreateSurveyActivity : AppCompatActivity(),View.OnClickListener {

    private val createSurveyViewModel : CreateSurveyViewModel by viewModels<CreateSurveyViewModel>{
        getViewModelFactory()
    }
    private lateinit var binding : ActivityCreateSurveyBinding
    private lateinit var c : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etStartDate.setOnClickListener(this)
        binding.etClosingDate.setOnClickListener(this)
        binding.btnExampleSurvey.setOnClickListener(this)

        c = Calendar.getInstance()
        setupActionBar()
    }

    private fun showQuestionTypeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select type")
        builder.setSingleChoiceItems(INPUT_TYPES,-1){ dialogInterface, i ->

            dialogInterface.dismiss()
            addQuestionLayout(i)
            binding.svContainer.fullScroll(View.FOCUS_DOWN)
        }
        builder.setNeutralButton("Cancel") { dialog, which ->

            dialog.cancel()
        }
        val mDialog = builder.create()
        mDialog.show()
    }

    private fun addSurvey() {
        val questionArray = ArrayList<String>()
        val child = binding.llQuestions.childCount
        for(i in 0 until child){
            val view = binding.llQuestions.getChildAt(i)
            val type = view.tag
            Log.e(TAG, "addSurvey: $type", )
            val question = createQuestion(view)
            questionArray.add(question.id)
            createSurveyViewModel.insertQuestion(question)
        }
        val survey = Survey(name = binding.etSurveyName.text.toString(), description = binding.etSurveyDescription.text.toString(), questions = questionArray)
        createSurveyViewModel.createSurvey(survey)
    }

    private fun addQuestionLayout(index : Int) {
        val view : View
        Log.e(TAG, "addQuestionLayout: index = $index", )
        when(index){
            0,1,2-> {
                view = layoutInflater.inflate(R.layout.layout_question_text,null, false)
                view.tag = index
                view.btn_delete_question.setOnClickListener{
                    binding.llQuestions.removeView(view)
                }
            }
            3,4->{
                view =  layoutInflater.inflate(R.layout.layout_question_mcq,null, false)
                view.btn_add_option.setOnClickListener {
                    val etView : View = layoutInflater.inflate(R.layout.radio_button_option,null, false)
                    etView.btn_delete_option.setOnClickListener{
                        ll_options_extra.removeView(etView)
                    }
                    etView.tag = "1"
                    view.ll_options_extra.addView(etView)
                }
                view.tag = index
                view.btn_delete_question_mcq.setOnClickListener {
                    ll_questions.removeView(view)
                }
            }
            else ->{
                view  = layoutInflater.inflate(R.layout.layout_question_mcq,null, false)
                view.tag = -1
            }
        }
        view.tv_question.text = "Question(${INPUT_TYPES[index]})"

        binding.llQuestions.addView(view)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.etStartDate -> {
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    binding.etStartDate.setText("$dayOfMonth $monthOfYear, $year")
                }, year, month, day)
                dpd.show()
            }
            binding.etClosingDate ->{
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    binding.etClosingDate.setText("$dayOfMonth $monthOfYear, $year")
                }, year, month, day)
                dpd.show()
            }
            binding.btnExampleSurvey -> {
                addSurvey()
            }

        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.create_survey_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.add_question -> {
            // User chose the "Settings" item, show the app settings UI...

            Log.e(TAG, "onOptionsItemSelected: clicked on user", )
            showQuestionTypeDialog()
            binding.svContainer.fullScroll(View.FOCUS_DOWN)
            true
        }
        R.id.create_survey -> {
            Log.e(TAG, "onOptionsItemSelected: create clicked", )
            addSurvey()
            true
        }


        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
    private fun setupActionBar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "create survey"
    }


}