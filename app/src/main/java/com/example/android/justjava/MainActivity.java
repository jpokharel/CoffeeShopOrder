/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        boolean whippedCream = addWhippedCream();
        boolean chocolate = addChocolate();
        String customerName = getCustomerName();
        int price = calculatePrice(whippedCream, chocolate);

        String priceMessage = createOrderSummary(price, customerName, whippedCream, chocolate);
        emailOrderSummary(priceMessage,customerName);
       // displayMessage(priceMessage);
    }

    /**
     * Get the name of the customer from Edit Text View
     */
    private String getCustomerName() {
        EditText editText = (EditText) findViewById(R.id.name_edit_text);
        return editText.getText().toString();
    }

    /**
     * This method gets the checkbox state for adding whipped cream
     */
    private boolean addWhippedCream() {
        CheckBox chkBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        return chkBox.isChecked();
    }

    /**
     * This method gets the checkbox state for adding chocolate
     */
    private boolean addChocolate() {
        CheckBox chkBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        return chkBox.isChecked();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method is called when the quantity is to be increased.
     *
     * @param view
     */
    public void increment(View view) {
        quantity = quantity + 1;
        if (quantity > 100) {
            getToastMessage(quantity);
            quantity = 100;
        }
        display(quantity);
    }

    /**
     * This method is called when the quantity is to be decreased.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        if (quantity < 1) {
            getToastMessage(quantity);
            quantity = 1;
        }
        display(quantity);
    }

    /**
     * This method displays toast message to user when quantity is not in the range of 1-100.
     */
    private void getToastMessage(int quantity) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        if (quantity < 1) {
            Toast.makeText(context, R.string.text_less_than_one_item, duration).show();
        } else if (quantity > 100) {
            Toast.makeText(context, R.string.text_more_than_100_items, duration).show();
        }
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        int price = 5;
        if (whippedCream) {
            price++;
        }
        if (chocolate) {
            price += 2;
        }
        return price * quantity;
    }

    /**
     * Creates an intent to send the order summary through the email.
     */
    private void emailOrderSummary(String orderSummary,String customerName){
        String[] email={"mango@tango.org"};
        String emailSubject=getString(R.string.order_summary_text) + customerName;

        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:"))
                //.setType("*/*")
                .putExtra(Intent.EXTRA_EMAIL,email)
                .putExtra(Intent.EXTRA_SUBJECT,emailSubject)
                .putExtra(Intent.EXTRA_TEXT,orderSummary);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Creates the order summary
     */
    private String createOrderSummary(int price, String customerName, boolean whippedCream, boolean chocolate) {
        return getString(R.string.oder_name_text) + customerName + getString(R.string.order_whipped_text) + whippedCream + getString(R.string.order_chocolate_text)
                + chocolate + getString(R.string.order_quantity_text) + quantity + getString(R.string.order_total_text) + price + getString(R.string.order_thank_you_text);
    }
}