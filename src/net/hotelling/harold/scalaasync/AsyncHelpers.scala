package net.hotelling.harold.scalaasync

import android.os.AsyncTask

/**
 * Create an AsyncTask that will execute the code block in the background.
 * You can call uiThread within that code to execute parts of the code on
 * the main UI thread as needed.
 */
abstract class Async extends AsyncTask[AnyRef, AnyRef, AnyRef] {

  /**
   * Override with your code to execute in the background.
   */
  def inBackground()

  /**
   * Execute the code block on the main UI thread.
   */
  def uiThread[T](code: () => Unit) {
    // Enqueue the code and use publishProgress to trigger it on the UI thread.
    queue add code
    publishProgress(null)
  }

  private val queue = new java.util.concurrent.LinkedBlockingQueue[Function0[_]]()

  override def doInBackground(params: AnyRef*): AnyRef = {
    inBackground()
    null
  }

  override def onProgressUpdate(progress: AnyRef*) {
    // Ignore the progress params: the code to execute is in the queue.
    queue.poll.apply
  }

  override def onPostExecute(result: AnyRef) {
    while (!queue.isEmpty) queue.poll.apply
  }
}
