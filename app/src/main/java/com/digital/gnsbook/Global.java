package com.digital.gnsbook;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import com.digital.gnsbook.Activity.MainActivity;
import com.httpgnsbook.gnsbook.R;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Global {
    public static String A_status = null;
    public static String Banner = null;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static int Cappinglimt = 0;
    public static String City = null;
    public static int Company_Admin_Id = 0;
    public static String Company_Id = null;
    public static String Company_Logo = null;
    public static String Company_Name = null;
    public static String Company_Type = null;
    public static String Company_Cate = null;
    public static String DP = null;
    public static String Email = null;
    public static final int READ_TIMEOUT = 15000;
    public static String agentid = null;
    public static String customerid = null;
    public static String mobile = null;
    public static String name = null;
    public static int notificationcount = 0;
    public static int premium_status = 0;
    public static String refferalid = null;
    public static String userid = null;
    public static String verify_email = "0";
    public static String verify_sms = "0";
    public static String verify_wallet = "0";
    public static String sender_id=null;

    /* renamed from: com.digital.gnsbook.Global$1 */
    static class C04591 implements OnClickListener {
        C04591() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }


    public static void diloge(final Context context ,String Title ,String Message){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setCancelable(false);
        builder.setTitle(Title)
                .setMessage(Message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public static String CurrencyFormat(String str) {
        return new DecimalFormat("#,###.").format(Double.parseDouble(str));
    }

    public static void actionbar(Activity activity , ActionBar abar , String title){
        View viewActionBar = activity.getLayoutInflater().inflate(R.layout.actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams( //Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText(title);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setHomeButtonEnabled(true);
    }


    public static void failedDilogue(final Context context ,String result) {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.failediloge);
        Button button = dialog.findViewById(R.id.btnfailed);
        TextView textView = dialog.findViewById(R.id.failedreson);
        textView.setText(result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    public static void successDilogue(final Context context ,String result) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.success_dilogue);
        Button button = dialog.findViewById(R.id.btnsucces);
        TextView textView = dialog.findViewById(R.id.successtext);
        textView.setText(result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public static String getColoredSpanned(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<font color=");
        stringBuilder.append(str2);
        stringBuilder.append(">");
        stringBuilder.append(str);
        stringBuilder.append("</font>");
        return stringBuilder.toString();
    }

    public static Bitmap uriToBitmap(Uri selectedFileUri , Context context) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

            parcelFileDescriptor.close();

            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setFlags(1);
        canvas.drawRoundRect(new RectF(new Rect(0, 0, width, height)), (float) (width / 2), (float) (height / 2), paint);
        Paint paint2 = new Paint();
        paint2.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint2);
        return createBitmap;
    }

    public static String encodeTobase64(Bitmap bitmap) {
        if (bitmap == null) {
            return "";
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
        return Base64.encodeToString(((ByteArrayOutputStream) byteArrayOutputStream).toByteArray(), 2);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    public static String Date(String time){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        String formatedDate = null;
        // String date = "2017-02-08 00:00:00.0";
        try {
            Date dateNew = format1.parse(time);
            formatedDate = format2.format(dateNew);
            System.out.println(formatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;
    }

    public static String getDate(long time , String formate) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format(formate, cal).toString();
        return date;
    }

    public static String Time(String str) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm a");
            Date parse = simpleDateFormat.parse(str);
            System.out.println(parse);
            return simpleDateFormat2.format(parse);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }


    public static String getDaysAgo(String str, String str2) {
        Date parse;
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            parse = null;
        }
        long time = (new Date().getTime() - parse.getTime()) / 86400000;
        if (time == 0) {
            StringBuilder  st = new StringBuilder();
            st.append("Today at ");
            st.append(Global.Time(str2));
            return str.toString();
        } else if (time != 1) {
            return Global.Date(str);
        } else {
            StringBuilder st = new StringBuilder();
            st.append("Yesterday at ");
            st.append(Global.Time(str2));
            return str.toString();
        }
    }


    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;






    public static Date getNewDate(long time) {
        Date date = null;
        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(time);
        String strdate = DateFormat.format("dd-MMM-yy hh.mm.ss aa", cal).toString();

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");
        try {
            date = format.parse(strdate);
            System.out.println(date);


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss");
            String dateTime = dateFormat.format(date);
            System.out.println("Current Date Time : " + dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return date;
    }}
