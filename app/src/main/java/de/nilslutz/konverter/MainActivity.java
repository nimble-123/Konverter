package de.nilslutz.konverter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    private static final String DEBUG_TAG = "nlsltz";

    private EditText mEditTextInputValue;
    private TextView mTextViewOutputValue;
    private Spinner mSpinnerNumberSystem;
    private Spinner mSpinnerTargetSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextInputValue   = (EditText) findViewById(R.id.editTextInputValue);
        mSpinnerNumberSystem  = (Spinner) findViewById(R.id.spinnerNumberSystem);
        mTextViewOutputValue  = (TextView) findViewById(R.id.textViewOutputValue);
        mSpinnerTargetSystem  = (Spinner) findViewById(R.id.spinnerTargetSystem);

        mSpinnerNumberSystem.setOnItemSelectedListener(this);
        mSpinnerTargetSystem.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(DEBUG_TAG, "SpinnerItem " + parent.getItemAtPosition(position).toString() + " selected");
        String sInputValue   = mEditTextInputValue.getText().toString();
        String sNumberSystem = mSpinnerNumberSystem.getSelectedItem().toString();
        String sTargetSystem = mSpinnerTargetSystem.getSelectedItem().toString();

        // case distinction with start and target system
        String sResult = null;
        try {
            sResult = this.convert(sInputValue, sNumberSystem, sTargetSystem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTextViewOutputValue.setText(sResult);

        if (sNumberSystem.equals("Decimal")) {
            mEditTextInputValue.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        } else {
            mEditTextInputValue.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String convert(String sInputValue, String sNumberSystem, String sTargetSystem) throws Exception {
        String sResult = "";
        switch (sNumberSystem) {
            case "Decimal":
                switch (sTargetSystem) {
                    case "Decimal":
                        sResult = sInputValue;
                        break;
                    case "Binary":
                        sResult = Integer.toBinaryString(Integer.parseInt(sInputValue));
                        break;
                    case "Octal":
                        sResult = "0o" + Integer.toOctalString(Integer.parseInt(sInputValue));
                        break;
                    case "Hexal":
                        sResult = "0x" + Integer.toHexString(Integer.parseInt(sInputValue)).toUpperCase();
                        break;
                    default:
                }
                break;
            case "Binary":
                switch (sTargetSystem) {
                    case "Decimal":
                        sResult = String.valueOf(Integer.parseInt(sInputValue, 2));
                        break;
                    case "Binary":
                        sResult = sInputValue;
                        break;
                    case "Octal":
                        sResult = "0o" + Integer.toOctalString(Integer.parseInt(sInputValue, 2));
                        break;
                    case "Hexal":
                        sResult = "0x" + Integer.toHexString(Integer.parseInt(sInputValue, 2)).toUpperCase();
                        break;
                    default:
                }
                break;
            case "Octal":
                switch (sTargetSystem) {
                    case "Decimal":
                        sResult = String.valueOf(Integer.parseInt(sInputValue, 8));
                        break;
                    case "Binary":
                        sResult = Integer.toBinaryString(Integer.parseInt(sInputValue, 8));
                        break;
                    case "Octal":
                        sResult = "0o" + sInputValue;
                        break;
                    case "Hexal":
                        sResult = "0x" + Integer.toHexString(Integer.parseInt(sInputValue, 8)).toUpperCase();
                        break;
                    default:
                }
                break;
            case "Hexal":
                switch (sTargetSystem) {
                    case "Decimal":
                        sResult = String.valueOf(Integer.parseInt(sInputValue, 16));
                        break;
                    case "Binary":
                        sResult = Integer.toBinaryString(Integer.parseInt(sInputValue, 16));
                        break;
                    case "Octal":
                        sResult = "0o" + Integer.toOctalString(Integer.parseInt(sInputValue, 16));
                        break;
                    case "Hexal":
                        sResult = "0x" + sInputValue.toUpperCase();
                        break;
                    default:
                }
                break;
            default:
        }

        return sResult;
    }
}
