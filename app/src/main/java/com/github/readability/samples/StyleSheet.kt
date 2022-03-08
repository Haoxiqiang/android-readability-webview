package com.github.readability.samples

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.github.readability.webview.ReaderJSInterface
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONObject

fun DialogFragment.bind(bundle: Bundle.() -> Unit): DialogFragment {
    var args: Bundle? = arguments
    if (args == null) {
        args = Bundle()
        arguments = args
    }
    bundle.invoke(args)
    return this
}

class StyleSheet : BottomSheetDialogFragment() {

    var styleTheme: ((JSONObject) -> Unit)? = null
    var styleFontSize: ((JSONObject) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sheet_view_style, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.dark).setOnClickListener {
            themeClick("dark")
        }
        view.findViewById<View>(R.id.light).setOnClickListener {
            themeClick("light")
        }
        view.findViewById<View>(R.id.sepia).setOnClickListener {
            themeClick("sepia")
        }
        view.findViewById<View>(R.id.font_size_decrease).setOnClickListener {
            fontSizeClick(-1)
        }
        view.findViewById<View>(R.id.font_size_increase).setOnClickListener {
            fontSizeClick(1)
        }

        view.findViewById<View>(R.id.sans_serif).setOnClickListener {
            fontTypeClick("sans_serif")
        }
        view.findViewById<View>(R.id.serif).setOnClickListener {
            fontTypeClick("serif")
        }

        updateFontSizeText()
    }

    // just for test. it's async method.
    @SuppressLint("SetTextI18n")
    private fun updateFontSizeText() {
        view?.postDelayed({
            val fontSize = view?.findViewById<TextView>(R.id.font_size)
            fontSize?.text = "${ReaderJSInterface.theme.optString("fontSize")}px"
        }, 300)
    }

    private fun themeClick(t: String) {
        val json = JSONObject()
        json.put(ReaderJSInterface.ACTION_MESSAGE_KEY, ReaderJSInterface.ACTION_SET_COLOR_SCHEME)
        json.put(ReaderJSInterface.ACTION_VALUE_COLOR_SCHEME, t)
        styleTheme?.invoke(json)
    }

    private fun fontSizeClick(size: Int) {
        val json = JSONObject()
        json.put(ReaderJSInterface.ACTION_MESSAGE_KEY, ReaderJSInterface.ACTION_CHANGE_FONT_SIZE)
        json.put(ReaderJSInterface.ACTION_VALUE_FONT_SIZE, size)
        styleTheme?.invoke(json)
        updateFontSizeText()
    }

    private fun fontTypeClick(type: String) {
        val json = JSONObject()
        json.put(ReaderJSInterface.ACTION_MESSAGE_KEY, ReaderJSInterface.ACTION_SET_FONT_TYPE)
        json.put(ReaderJSInterface.ACTION_VALUE_FONT_TYPE, type)
        styleTheme?.invoke(json)
    }
}
