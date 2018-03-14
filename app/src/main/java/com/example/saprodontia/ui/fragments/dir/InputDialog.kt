package com.xt.directoryfragment

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.input_dialog.view.*
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import com.example.saprodontia.R


/**
 * Created by steve on 18-1-28.
 */
class InputDialog(ctx: Context) : Dialog(ctx,R.style.dialog_noframe){

    var title : String? = null
    var onclick : (() -> Unit)? = null
    var contentText : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = View.inflate(context, R.layout.input_dialog,null)
        setContentView(view)

        val lp = window.attributes;
        lp.height = dip2px(context,200f)
        lp.width = (context.resources.displayMetrics.widthPixels*0.8).toInt()
//        lp.gravity = Gravity.LEFT or Gravity.TOP
//        lp.verticalMargin = 200f
//        lp.horizontalMargin = (context.resources.displayMetrics.widthPixels*0.1).toFloat()

        lp.windowAnimations=R.style.dialog_enter_anim
        
        lp.dimAmount = 0.5f

        window.attributes = lp
        setCanceledOnTouchOutside(true);

        view.title.text=title
        view.ensure.setOnClickListener{
            onclick?.invoke()
        }

        view.content.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                contentText = s.toString()
            }
        })

        view.postDelayed({showInputMethod()},300)


    }

    class InputDialogBuilder(private val ctx:Context,private  val build : InputDialog.()->Unit){

        fun build() : InputDialog{
            val dialog = InputDialog(ctx)
            dialog.build()
            return dialog
        }
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    private fun showInputMethod() {
        //自动弹出键盘
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        //强制隐藏Android输入法窗口
        // inputManager.hideSoftInputFromWindow(edit.getWindowToken(),0);
    }

}