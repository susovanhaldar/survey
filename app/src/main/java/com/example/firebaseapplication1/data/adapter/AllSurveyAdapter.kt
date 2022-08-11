package com.example.firebaseapplication1.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapplication1.R
import com.example.firebaseapplication1.data.model.Survey
import com.example.firebaseapplication1.util.setDate
import kotlinx.android.synthetic.main.layout_survey.view.*

open class AllSurveyAdapter(
    private val context: Context,
    private var surveyList : ArrayList<Survey>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_survey,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val survey = surveyList[position]
        if(holder is MyViewHolder){
            holder.itemView.tv_survey_name.text = survey.name
            holder.itemView.tv_survey_description.text = survey.description
            holder.itemView.tv_num_questions.text = "total "+survey.questions.size.toString()+" questions"
            setDate(holder.itemView.tv_start_date,survey.opening_time)
            setDate(holder.itemView.tv_end_date,survey.Closing_time)

            holder.itemView.btn_fill_survey.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, survey)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Survey)
    }
}
