package com.example.scalaasync

import android.view.View
import android.view.View.OnClickListener

object ButtonHelpers {

  implicit def onClick(handler: View => _): OnClickListener =
    new OnClickListener() {
	  override def onClick(source: View) { handler(source) }
    }

}
