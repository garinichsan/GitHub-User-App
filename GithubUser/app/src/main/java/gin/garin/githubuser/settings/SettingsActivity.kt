package gin.garin.githubuser.settings

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Switch
import androidx.core.app.NotificationCompat
import androidx.core.view.get
import gin.garin.githubuser.R
import gin.garin.githubuser.ReminderReceiver
import gin.garin.githubuser.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var reminderReceiver: ReminderReceiver

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "githubuser channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardLanguageSetting.setOnClickListener(this)

        reminderReceiver = ReminderReceiver()
        binding.notificationSwitch.isChecked = reminderReceiver.activeAlarm(this)
    }

    override fun onClick(v: View?) {
        if (v == binding.cardLanguageSetting) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
    }

    fun setNotification(view: View) {

        val status = binding.notificationSwitch.isChecked

        if(status) {
            reminderReceiver.setRepeatingAlarm(this,"09:00",resources.getString(R.string.reminder_text))
        } else {
            reminderReceiver.cancelAlarm(this)
        }
    }

    fun showNotif(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.garin.gin/githubuser"))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_notifications))
            .setContentTitle(resources.getString(R.string.app_name))
            .setContentText(resources.getString(R.string.reminder_text))
            .setSubText(resources.getString(R.string.reminder))
            .setAutoCancel(true)

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_NAME
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()

        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }
}