package br.com.drtis.students.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.drtis.students.dao.StudentDAO;

/**
 * Created by webmaster on 01/02/17.
 */

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        String format = (String) intent.getSerializableExtra("format");

        byte[] pdu = (byte[]) pdus[0];

        SmsMessage message = SmsMessage.createFromPdu(pdu, format);
        String messageNumber = message.getDisplayOriginatingAddress();
        StudentDAO dao = new StudentDAO(context);

        if(dao.isStudent(messageNumber))
            Toast.makeText(context, "sms arrived from: " + messageNumber, Toast.LENGTH_SHORT).show();
    }
}
