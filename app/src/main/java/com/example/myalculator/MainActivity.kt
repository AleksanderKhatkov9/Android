package com.example.myalculator


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Double.valueOf


class MainActivity : AppCompatActivity() {

    var resultField: TextView? = null
    var numberField: EditText? = null
    var operationField: TextView? = null
    var operand: Double? = null
    var lastOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // получаем все поля по id из activity_main.xml
        resultField = findViewById<TextView>(R.id.resultField) as TextView
        numberField = findViewById<EditText>(R.id.numberField) as EditText
        operationField = findViewById<TextView>(R.id.operationField) as TextView
    }

    // сохранение состояния
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("OPERATION", lastOperation);
        if (operand != null)
            outState.putDouble("OPERAND", operand!!);
        super.onSaveInstanceState(outState)
    }

    // получение ранее сохраненного состояния
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastOperation = savedInstanceState.getString("OPERATION")!!;
        operand = savedInstanceState.getDouble("OPERAND");
        resultField?.setText(operand.toString());
        operationField?.setText(lastOperation);
    }

    // обработка нажатия на числовую кнопку
    fun onNumberClick(view: View) {
        val button: Button = view as Button
        numberField?.append(button.getText());

        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    // обработка нажатия на кнопку операции
    fun onOperationClick(view: View) {
        val button = view as Button
        val op = button.text.toString()
        var number = numberField!!.text.toString()

        // если введенно что-нибудь
        if (number.length > 0) {
            number = number.replace(',', '.');
            performOperation(valueOf(number), op);
            numberField!!.setText("");
        }
        lastOperation = op;
        operationField?.setText(lastOperation);
    }

    fun performOperation(number: Double, operation: String) {
        // если операнд ранее не был установлен (при вводе самой первой операции)


        if (operand == null) {
            operand = number;
        } else {
            if (lastOperation.equals("=")) {
                lastOperation = operation;
            }

            when (lastOperation) {
                "=" -> {
                    operand = number;
                }
                "/" -> {
                    operand = number / number;
                }
                "+" -> {
                    operand = number + number;
                }
                "-" -> {
                    operand = number - number;
                }
                "*" ->
                    operand = number * number;

            }
        }
        resultField?.setText(operand.toString().replace('.', ','));
        numberField?.setText("");
    }
}