package and.okm.currency.rate.utils

import and.okm.currency.rate.R
import and.okm.currency.rate.data.constants.Messages
import android.app.AlertDialog
import android.content.Context
import java.net.UnknownHostException

class ErrorHandler {

    companion object {

        fun getMessageByThrowable(cause: Throwable): String {
            val message = if (cause is UnknownHostException) {
                Messages.CHECK_CONNECTION
            } else {
                ""
            }
            return message
        }

        fun showAlertDialog(
            context: Context,
            message: String,
            onPositiveButtonClicked: () -> Unit,
        ) {
            val title = message.ifBlank {
                Messages.SOMETHING_WRONG
            }

            val subtitle = if (message.isNotBlank()) {
                ""
            } else {
                Messages.CONTACT_SERVICE
            }

            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(subtitle)
                .setPositiveButton(R.string.retry) { _, _ ->
                    onPositiveButtonClicked.invoke()
                }
                .setCancelable(false)
                .show()
        }

    }
}