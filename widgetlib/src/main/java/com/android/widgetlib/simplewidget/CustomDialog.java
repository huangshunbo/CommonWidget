package com.android.widgetlib.simplewidget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import commonwidget.android.com.widgetlib.R;

public class CustomDialog extends Dialog{
    private CustomDialog(@NonNull Context context) {
        super(context);
    }

    private CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder
    {
        Context context;
        String title;
        String message;
        String btCancelTxt;
        String btSubmitTxt;
        View.OnClickListener btCancelClickListener;
        View.OnClickListener btSubmitClickListener;
        int btCancelTxtColor = 0;
        int btSubmitTxtColor = 0;
        int btCancelBackground = 0;
        int btSubmitBackground = 0;
        public Builder(Context context){
            this.context = context;
        }
        public Builder setTitle(String title){
            this.title = title;
            return this;
        }
        public Builder setMessage(String message){
            this.message = message;
            return this;
        }
        public Builder setCancelButton(String btCancelTxt,View.OnClickListener listener){
            this.btCancelTxt = btCancelTxt;
            this.btCancelClickListener = listener;
            return this;
        }
        public Builder setCancelButtonTextColor(int color){
            this.btCancelTxtColor = color;
            return this;
        }
        public Builder setCancelButtonTextBackgrounud(int drawable){
            this.btCancelBackground = drawable;
            return this;
        }
        public Builder setSubmitButton(String btSubmitTxt,View.OnClickListener listener){
            this.btSubmitTxt =btSubmitTxt;
            this.btSubmitClickListener = listener;
            return this;
        }
        public Builder setSubmitButtonTextColor(int color){
            this.btSubmitTxtColor = color;
            return this;
        }
        public Builder setSubmitButtonTextBackground(int drawable){
            this.btSubmitBackground = drawable;
            return this;
        }
        public CustomDialog create(){
            final CustomDialog customDialog = new CustomDialog(context);
            View view = View.inflate(context, R.layout.dialog_custom_layout,null);
            TextView tvTitle = view.findViewById(R.id.custom_dialog_title);
            TextView tvMessage = view.findViewById(R.id.custom_dialog_message);
            View line = view.findViewById(R.id.custom_dialog_line);
            TextView btCancel = view.findViewById(R.id.custom_dialog_cancel);
            TextView btSubmit = view.findViewById(R.id.custom_dialog_submit);
            if(TextUtils.isEmpty(title)){
                tvTitle.setVisibility(View.GONE);
            }else {
                tvTitle.setText(title);
            }
            if(TextUtils.isEmpty(message)){
                tvMessage.setVisibility(View.GONE);
            }else {
                tvMessage.setText(message);
            }
            if(TextUtils.isEmpty(btCancelTxt)){
                btCancel.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
            }else {
                btCancel.setText(btCancelTxt);
            }
            if(TextUtils.isEmpty(btSubmitTxt)){
                btSubmit.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
            }else {
                btSubmit.setText(btSubmitTxt);
            }
            if(btCancelTxtColor != 0){
                btCancel.setTextColor(btCancelTxtColor);
            }
            if(btCancelBackground != 0){
                btCancel.setBackgroundResource(btCancelBackground);
            }
            if(btSubmitTxtColor != 0){
                btSubmit.setTextColor(btSubmitTxtColor);
            }
            if(btSubmitBackground != 0){
                btSubmit.setBackgroundResource(btSubmitBackground);
            }
            if(btCancelClickListener != null){
                btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                        btCancelClickListener.onClick(v);
                    }
                });
            }
            if(btSubmitClickListener != null){
                btSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                        btSubmitClickListener.onClick(v);
                    }
                });
            }
            customDialog.setContentView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return customDialog;
        }
    }
}
