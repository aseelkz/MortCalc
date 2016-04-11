package a.mortcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {


    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private TextView loanAmtVw;
    private TextView loanAmtCalc;
    private TextView purchasePriceVw;
    private EditText purchasePriceTx;
    private TextView downPaymentVw;
    private EditText downPaymentTx;
    private TextView intRateVw;
    private EditText intRateTx;
    private TextView seekBarVw;
    private TextView customYrsVw;
    private SeekBar seekBar;
    private TextView tenYrLoanVw;
    private TextView tenYrLoanCalc;
    private TextView twentyYrLoanVw;
    private TextView twentyYrLoanCalc;
    private TextView thirtyYrLoanVw;
    private TextView thirtyYrLoanCalc;
    private TextView customLoanVw;
    private TextView customLoanCalc;
    private double loanAmt=0.0;
    private double purchasePrice=0.0;
    private double intRate = 0.0;
    private double downPayment= 0.0;
    private int customYrs = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //references to textviews
        loanAmtVw = (TextView) findViewById(R.id.loanAmtVw);
        loanAmtCalc = (TextView) findViewById(R.id.loanAmtCalc);
        purchasePriceVw = (TextView)findViewById(R.id.purchasePriceVw);
        downPaymentVw = (TextView) findViewById(R.id.downPaymentVw);
        intRateVw = (TextView) findViewById(R.id.intRateVw);
        seekBarVw = (TextView) findViewById(R.id.seekBarVw);
        customYrsVw = (TextView) findViewById(R.id.customYrsVw);
        tenYrLoanVw = (TextView) findViewById(R.id.tenYrLoanVw);
        twentyYrLoanVw = (TextView) findViewById(R.id.twentyYrLoanVw);
        thirtyYrLoanVw = (TextView) findViewById(R.id.thirtyYrLoanVw);
        customLoanVw = (TextView) findViewById(R.id.customLoanVw);
        tenYrLoanCalc = (TextView) findViewById(R.id.tenYrLoanCalc);
        twentyYrLoanCalc = (TextView) findViewById(R.id.twentyYrLoanCalc);
        thirtyYrLoanCalc = (TextView) findViewById(R.id.thirtyYrLoanCalc);
        customLoanCalc = (TextView) findViewById(R.id.customLoanCalc);

        //updateGui on LoanAmt byt Price, intRate, customIntrate
        updateDefault();
        updateCustom();

        //text listiners

        EditText purchasePriceTx = (EditText) findViewById(R.id.purchasePrice);
        purchasePriceTx.addTextChangedListener(purchasePriceTxListener);

        EditText downPaymentTx = (EditText) findViewById(R.id.downPaymenTx);
        downPaymentTx.addTextChangedListener(downPaymentTxListener);

        EditText intRateTx = (EditText) findViewById(R.id.intRateTx);
        intRateTx.addTextChangedListener(intRateTxListener);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarListener);
        seekBar.setMax(50);


    } // end method onCreate

    private void updateDefault()
    {
        loanAmt = purchasePrice-downPayment;
        loanAmtCalc.setText(currencyFormat.format(loanAmt));

        double tenyrAmt = (loanAmt+((loanAmt*intRate)*10))/120;
        double twentyyrAmt = ((loanAmt+(loanAmt*intRate)*20))/240;
        double thirtyyrAmt = ((loanAmt+(loanAmt*intRate)*30))/360;
        tenYrLoanCalc.setText(currencyFormat.format(tenyrAmt));
        twentyYrLoanCalc.setText(currencyFormat.format(twentyyrAmt));
        thirtyYrLoanCalc.setText(currencyFormat.format(thirtyyrAmt));

    }

    private void updateCustom()
    {
        loanAmt = purchasePrice-downPayment;
        loanAmtCalc.setText(currencyFormat.format(loanAmt));
        double customyrAmt =(loanAmt+(loanAmt*intRate*customYrs))/(12*customYrs);;
        customLoanCalc.setText(currencyFormat.format(customyrAmt));
        customYrsVw.setText(String.valueOf(customYrs));

    }
    private SeekBar.OnSeekBarChangeListener seekBarListener =
            new SeekBar.OnSeekBarChangeListener()
            {
                // update customPercent, then call updateCustom
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser)
                {
                    // sets customPercent to position of the SeekBar's thumb
                    customYrs = progress;
                    updateCustom(); // update the custom tip TextViews
                } // end method onProgressChanged

                @Override
                public void onStartTrackingTouch(SeekBar seekBar)
                {
                } // end method onStartTrackingTouch

                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {
                } // end method onStopTrackingTouch
            };

    private TextWatcher purchasePriceTxListener = new TextWatcher()
    {
        // called when the user enters a number
        private boolean wasEdited = false;
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count)
        {

        } // end method onTextChanged

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        } // end method afterTextChanged

        @Override
        public void afterTextChanged(Editable s)
        {
            if (s.length()==0) {
                wasEdited = false;
                return;
            }

            String inputStr = s.toString();
            //purchasePriceTx.setText(inputStr);
            purchasePrice = Double.parseDouble(inputStr);
            wasEdited=true;
            updateDefault();
            updateCustom();

        } // end method beforeTextChanged
    };

    private TextWatcher downPaymentTxListener = new TextWatcher()
    {
        // called when the user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count)
        {
            // convert amountEditText's text to a double
            try
            {
                downPayment = Double.parseDouble(s.toString());
            } // end try
            catch (NumberFormatException e)
            {
                downPayment = 0.0; // default if an exception occurs
            } // end catch

            // display currency formatted bill amount
            //downPaymentTx.setText(currencyFormat.format(downPayment));
            updateDefault(); // update the 15% tip TextViews
            updateCustom(); // update the custom tip TextViews
        } // end method onTextChanged

        @Override
        public void afterTextChanged(Editable s)
        {
        } // end method afterTextChanged

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after)
        {
        } // end method beforeTextChanged
    };


    private TextWatcher intRateTxListener = new TextWatcher()
    {
        // called when the user enters a number
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count)
        {
            // convert amountEditText's text to a double
                try {
                    intRate = Double.parseDouble(s.toString()) / 100.0;
                }
                catch(NumberFormatException e){
                    intRate = 0.0;

                }

            //intRateTx.setText(currencyFormat.format(intRate));
            updateDefault(); // update the 15% tip TextViews
            updateCustom(); // update the custom tip TextViews
        } // end method onTextChanged

        @Override
        public void afterTextChanged(Editable s)
        {
        } // end method afterTextChanged

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after)
        {
        } // end method beforeTextChanged
    };

}
