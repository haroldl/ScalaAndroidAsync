package com.example.scalaasync

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.TextView

import ButtonHelpers._

class AsyncActivity extends Activity {

  var started: Boolean = false

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_async)

    val textView = findViewById(R.id.async_textView).asInstanceOf[TextView]
    val button = findViewById(R.id.async_button).asInstanceOf[Button]

    object asyncTask extends Async {
      def inBackground() {
        (1 to 100) foreach { i =>
          Thread.sleep(500)
          uiThread { () =>
            textView setText s"Status: $i"
          }
        }
      }
    }

    button setOnClickListener { view: View =>
      if (!started) {
        started = true
        asyncTask.execute()
      }
    }
  }

}