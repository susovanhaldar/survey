package com.example.firebaseapplication1.activities.surveys.customSurvey

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.firebaseapplication1.R
import com.example.firebaseapplication1.data.model.*

import com.example.firebaseapplication1.databinding.ActivityCustomSurveyBinding
import com.example.firebaseapplication1.util.AndroidPermissions
import com.example.firebaseapplication1.util.EXTRA_SURVEY
import com.example.firebaseapplication1.util.INPUT_TYPES
import com.example.firebaseapplication1.util.getViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.quickbirdstudios.surveykit.*
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.InstructionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep
import com.quickbirdstudios.surveykit.survey.SurveyView
import com.quickbirdstudios.surveykit.backend.views.main_parts.*
import com.quickbirdstudios.surveykit.backend.views.step.StepView
import com.quickbirdstudios.surveykit.result.question_results.*
import com.quickbirdstudios.surveykit.steps.Step
import kotlinx.android.synthetic.main.fill_question_text.view.*
import kotlinx.android.synthetic.main.fill_question_mcq.view.*

import kotlinx.android.synthetic.main.fill_question_text.view.tv_question

private const val REQUEST_CODE_PERMISSIONS = 1444

class CustomSurveyActivity : AppCompatActivity() {
    private val questions_id = ArrayList<Pair<String,String>>()
    private val customSurveyViewModel : CustomSurveyViewModel by viewModels<CustomSurveyViewModel> {
        getViewModelFactory()
    }
    //permissions and location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var androidPermissions: AndroidPermissions
    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    private lateinit var binding: ActivityCustomSurveyBinding
    private lateinit var mDocumentId : String
    private var mSurvey : Survey? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.hasExtra(EXTRA_SURVEY)){
            mSurvey = intent.getParcelableExtra(EXTRA_SURVEY) as? Survey
            mSurvey?.let {
                Log.e(TAG, "onCreate: ${it.name},${it.description}\n${it.toString()}", )

                customSurveyViewModel.getQuestions(questions = it.questions)
                setObserver()
            }
        }
        androidPermissions = AndroidPermissions(this, null)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)



        //checkPermissions()
        //setupActionBar()
    }


    private fun handleSurveySubmit(a : ArrayList<AnswerType>) {
        val response : Response = Response(survey_id = mSurvey!!.id, user_id = "random", question = mSurvey!!.questions, answers = a  )
        customSurveyViewModel.submitSurvey(response)
        Log.e(TAG, "handleSurveySubmit: \n${mSurvey!!.questions}\n$a", )
    }

    private fun setObserver() {
        customSurveyViewModel.questionsLiveData.observe(this){
            //Log.e(TAG, "setObserver: ${it.toString()}", )

            questions_id.clear()
            val steps = ArrayList<Step>()
            steps.add(InstructionStep(
                title = "${mSurvey?.name}",
                text = "${mSurvey?.description}",
                buttonText = this.resources.getString(R.string.intro_start)
            ))
            it.forEachIndexed{ _, question ->
                when(question.question_type){
                    INPUT_TYPES[0] -> {
                        steps.add(
                            QuestionStep(
                                title = question.text,
                                text = "",
                                answerFormat = AnswerFormat.TextAnswerFormat(maxLines = 5)
                            )
                        )
                    }
                    INPUT_TYPES[1] -> {
                        steps.add(
                            QuestionStep(
                                title = question.text,
                                text = "",
                                answerFormat = AnswerFormat.IntegerAnswerFormat(
                                    defaultValue = 25,
                                    hint = this.resources.getString(R.string.how_old_hint)
                                )
                            )
                        )
                    }
                    INPUT_TYPES[2] -> {
                        steps.add(
                            QuestionStep(
                                title = question.text,
                                text = "",
                                answerFormat = AnswerFormat.TextAnswerFormat(maxLines = 5)
                            )
                        )
                    }
                    INPUT_TYPES[3] -> {
                        val choices = ArrayList<TextChoice>()
                        question.answer_choice.forEach{ ac ->
                            choices.add(TextChoice(ac.text))
                        }
                        steps.add(
                            QuestionStep(
                                title = question.text,
                                text = "",
                                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                    textChoices = choices
                                )
                            )
                        )
                    }
                    INPUT_TYPES[4] -> {
                        val choices = ArrayList<TextChoice>()
                        question.answer_choice.forEach{ ac ->
                            choices.add(TextChoice(ac.text))
                        }
                        steps.add(
                            QuestionStep(
                                title = question.text,
                                text = "",
                                answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                                    textChoices = choices
                                )
                            )
                        )
                    }
                }
            }
            steps.add(
                CompletionStep(
                    title = this.resources.getString(R.string.finish_question_title),
                    text = this.resources.getString(R.string.finish_question_text),
                    buttonText = this.resources.getString(R.string.finish_question_submit)
                )
            )
            val surveyView : SurveyView = binding.surveyView
            setupSurvey(surveyView,steps)



           /* it.forEachIndexed{ index, question ->
                questions_id.add(Pair(question.id,question.question_type))
                val inf : LayoutInflater = LayoutInflater.from(this)

                when(question.question_type){
                    INPUT_TYPES[0] -> {
                        val view : View = inf.inflate(R.layout.fill_question_text,null, false)
                        view.tv_question.text = question.text


                        view.tag = 0
                        *//*view.et_answer.inputType = InputType.TYPE_CLASS_TEXT*//*

                        binding.llQuestions.addView(view)
                    }
                    INPUT_TYPES[1] -> {
                        val view : View = inf.inflate(R.layout.fill_question_text, null, false)
                        view.tv_question.text = question.text
                        view.et_answer.inputType = InputType.TYPE_CLASS_NUMBER
                        view.tag = 1

                                *//*view.et_answer.inputType = InputType.TYPE_CLASS_NUMBER*//*
                        binding.llQuestions.addView(view)
                    }
                    INPUT_TYPES[2] -> {
                        val view : View = inf.inflate(R.layout.fill_question_text, null, false)
                        view.tv_question.text = question.text
                        view.et_answer.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                        view.tag=2
                        *//*view.et_answer.inputType = InputType.TYPE_CLASS_NUMBER*//*
                        binding.llQuestions.addView(view)
                    }
                    INPUT_TYPES[3] -> {
                        val view : View = inf.inflate(R.layout.fill_question_mcq, null, false)
                        view.tv_question.text = question.text
                        view.tag=3

                        question.answer_choice.forEachIndexed{ index, answerChoice ->
                            val rb = RadioButton(this)
                            rb.setPadding(5,5,5,5)

                            rb.text = answerChoice.text
                            rb.id = index+10
                            rb.inputType = InputType.TYPE_CLASS_TEXT
                            view.rb_options.addView(rb)
                        }
                        binding.llQuestions.addView(view)
                    }
                    else -> {
                        Log.e(TAG, "setObserver: question type not matched", )
                    }
                }


            }*/

        }
/*        customSurveyViewModel.locationLiveData.observe(this){
            binding.tvLatitude.text = it.first.toString()
            binding.tvLongitude.text=it.second.toString()

        }*/
    }


    private fun checkPermissions() {
        val nonGranted = androidPermissions.checkNonGrantedPermissions(*requiredPermissions)

        if (nonGranted.isNotEmpty()) {
            androidPermissions.requestPermissions(REQUEST_CODE_PERMISSIONS, *nonGranted)
        } else {
            onPermissionsGranted()
        }
    }

    private fun onPermissionsDeclined() {
        finish()
    }

    @SuppressLint("MissingPermission")
    private fun onPermissionsGranted() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it?.let {
                Log.e(TAG, "onPermissionsGranted: Location success", )
                customSurveyViewModel.onLocationAvailable(it.latitude, it.longitude)
            }
        }.addOnFailureListener{
            Log.e(TAG, "onPermissionsGranted: failed", )
        }
    }
    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.custom_survey_filling_menu,menu)
        return true
    }*/

   /* override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
*//*        R.id.add_question -> {
            // User chose the "Settings" item, show the app settings UI...

            Log.e(TAG, "onOptionsItemSelected: clicked on user", )
            true
        }*//*
        R.id.create_survey -> {
            Log.e(TAG, "onOptionsItemSelected: create clicked", )
            true
        }


        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }*/
    /*private fun setupActionBar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }*/
    private fun setupSurvey(surveyView: SurveyView, steps : ArrayList<Step>) {

/*
        val steps = listOf(
            InstructionStep(
                title = this.resources.getString(R.string.intro_title),
                text = this.resources.getString(R.string.intro_text),
                buttonText = this.resources.getString(R.string.intro_start)
            ),
            QuestionStep(
                title = this.resources.getString(R.string.about_you_question_title),
                text = this.resources.getString(R.string.about_you_question_text),
                answerFormat = AnswerFormat.TextAnswerFormat(maxLines = 5)
            ),
            QuestionStep(
                title = this.resources.getString(R.string.how_old_title),
                text = this.resources.getString(R.string.how_old_text),
                answerFormat = AnswerFormat.IntegerAnswerFormat(
                    defaultValue = 25,
                    hint = this.resources.getString(R.string.how_old_hint)
                )
            ),
            QuestionStep(
                title = this.resources.getString(R.string.how_fat_question_title),
                text = this.resources.getString(R.string.how_fat_question_text),
                answerFormat = AnswerFormat.ScaleAnswerFormat(
                    minimumValue = 1,
                    maximumValue = 5,
                    minimumValueDescription = this.resources.getString(R.string.how_fat_min),
                    maximumValueDescription = this.resources.getString(R.string.how_fat_max),
                    step = 1f,
                    defaultValue = 3
                )
            ),
            QuestionStep(
                title = this.resources.getString(R.string.allergies_question_title),
                text = this.resources.getString(R.string.allergies_question_text),
                answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                    textChoices = listOf(
                        TextChoice(this.resources.getString(R.string.allergies_back_penicillin)),
                        TextChoice(this.resources.getString(R.string.allergies_latex)),
                        TextChoice(this.resources.getString(R.string.allergies_pet)),
                        TextChoice(this.resources.getString(R.string.allergies_pollen))
                    )
                )
            ),
            QuestionStep(
                title = this.resources.getString(R.string.quit_or_continue_question_title),
                text = this.resources.getString(R.string.quit_or_continue_question_text),
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                    textChoices = listOf(
                        TextChoice(this.resources.getString(R.string.yes)),
                        TextChoice(this.resources.getString(R.string.no))
                    )
                )
            ),

            QuestionStep(
                title = this.resources.getString(R.string.boolean_example_title),
                text = this.resources.getString(R.string.boolean_example_text),
                answerFormat = AnswerFormat.BooleanAnswerFormat(
                    positiveAnswerText = this.resources.getString(R.string.how_fat_min),
                    negativeAnswerText = this.resources.getString(R.string.how_fat_max),
                    defaultValue = AnswerFormat.BooleanAnswerFormat.Result.NegativeAnswer
                )
            ),
            QuestionStep(
                title = this.resources.getString(R.string.value_picker_example_title),
                text = this.resources.getString(R.string.value_picker_example_text),
                answerFormat = AnswerFormat.ValuePickerAnswerFormat(
                    choices = (0..10).toList().map { it.toString() },
                    defaultValue = 5.toString()
                )
            ),
            QuestionStep(
                title = this.resources.getString(R.string.date_picker_title),
                text = this.resources.getString(R.string.date_picker_text),
                answerFormat = AnswerFormat.DateAnswerFormat()
            ),
            QuestionStep(
                title = this.resources.getString(R.string.time_picker_title),
                text = this.resources.getString(R.string.time_picker_text),
                answerFormat = AnswerFormat.TimeAnswerFormat()
            ),
            QuestionStep(
                title = this.resources.getString(R.string.email_question_title),
                text = this.resources.getString(R.string.email_question_text),
                answerFormat = AnswerFormat.EmailAnswerFormat()
            ),
            QuestionStep(
                title = this.resources.getString(R.string.image_selector_question_title),
                text = this.resources.getString(R.string.image_selector_question_text),
                answerFormat = AnswerFormat.ImageSelectorFormat(
                    numberOfColumns = 5,
                    defaultSelectedImagesIndices = listOf(1, 3),
                    imageChoiceList = listOf(
                        ImageChoice(R.drawable.color1),
                        ImageChoice(R.drawable.color2)
                    )
                )
            ),
            CompletionStep(
                title = this.resources.getString(R.string.finish_question_title),
                text = this.resources.getString(R.string.finish_question_text),
                buttonText = this.resources.getString(R.string.finish_question_submit)
            )
        )*/
        val task = NavigableOrderedTask(steps = steps)
        surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
            val a = ArrayList<AnswerType>()
            if (reason == FinishReason.Completed) {
                taskResult.results.forEachIndexed { index, stepResult ->
                    val result = stepResult.results.firstOrNull()
                    Log.e("ASDF", "answer ${stepResult.results.firstOrNull()}")
                    when(result){
                        is IntroQuestionResult -> {

                        }
                        is FinishQuestionResult -> {

                        }
                        is SingleChoiceQuestionResult -> {
                            Log.e(TAG, "setupSurvey: ${result.answer?.value}", )
                            a.add(MCQAnswerType(answer = result.answer!!.value))
                        }
                        is MultipleChoiceQuestionResult -> {
                            val choices = ArrayList<String>()
                            result.answer.forEach {
                                choices.add(it.value)
                            }
                            a.add(MSQAnswerType(answer = choices))
                            
                        }
                        is IntegerQuestionResult -> {
                            Log.e(TAG, "setupSurvey: ${result.answer}", )
                            a.add(IntegerAnswerType(answer = result.answer!!))
                        }
                        is TextQuestionResult -> {
                            Log.e(TAG, "setupSurvey: ${result.answer!!}", )
                            a.add(TextAnswerType(answer = result.answer!!))
                        }
                        else -> {
                            
                        }
                    }

                }
                handleSurveySubmit(a)
                finish()

            }
            if(reason==FinishReason.Discarded){
                Log.e(TAG, "setupSurvey: discarded", )
                finish()

            }
        }

        val configuration = SurveyTheme(
            themeColorDark = ContextCompat.getColor(this, R.color.cyan_dark),
            themeColor = ContextCompat.getColor(this, R.color.cyan_normal),
            textColor = ContextCompat.getColor(this, R.color.cyan_text)
        )
        surveyView.start(task, configuration)



    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            binding.surveyView.backPressed()
            true
        } else false
    }
}