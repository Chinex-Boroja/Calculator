//Define package
package calculator;

//Define imports
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import java.util.ArrayList;

//Start Controller class
public class Controller {

    //Declare necessary variables
    private double result;
    private ArrayList<String> holder = new ArrayList<>();
    private boolean newCalculation = false;
    private double previousAnswer = 0.0;

    //Access FXML elements
    @FXML
    private TextField input;

    @FXML
    private TextField resultBox;

    //FXML initialize method
    @FXML
    public void initialize() {
        input.setEditable(false);
        resultBox.setEditable(false);
    }

    //Versatile method that handles button events
    public void getInput(ActionEvent evt) {

        //Check if user is performing a new calculation (default value is false)
        if (newCalculation) {
            input.setText("");
            resultBox.setText("");
            holder.clear();
        }

        //Set newCalculation to false
        newCalculation = false;

        //Get the source of the button event
        Button button = (Button) evt.getSource();
        //Get text of the source button
        final String buttonText = button.getText();

        //Check if the button text is clear
        if (buttonText.equals("C")) {

            input.setText("");
            resultBox.setText("");
            holder.clear();
            return;
        }

        //Check if the button text is a number
        if (buttonText.matches("-?\\d+(\\.\\d+)?") || buttonText.contains(".")) {


            if (holder.size() > 0) {
                String previousElement = holder.get(holder.size() - 1);


                if (previousElement.matches("-?\\d+(\\.\\d+)?") || previousElement.contains(".")) {

                    holder.set(holder.size() - 1, previousElement + buttonText);
                    input.appendText(buttonText);
                    return;
                }
            }

            input.appendText(buttonText);
            holder.add(buttonText);
            return;

        }

        //Check if the button text is an operator
        if (buttonText.matches("[-+*÷^%.√]")) {

            if (holder.size() > 0) {
                String previousElement = holder.get(holder.size() - 1);

                if (previousElement.matches("[-+*÷^%.√]")) {

                    return;
                }
            }


            if (holder.size() == 0 && !buttonText.equals("√")) {
                input.appendText("Ans(" + previousAnswer + ")");
                holder.add(String.valueOf(previousAnswer));
            }
            input.appendText(buttonText);
            holder.add(buttonText);
            return;
        }

        //Check if button text is looking for the previous answer
        if (buttonText.equals("Ans")) {

            input.appendText(buttonText + "(" + previousAnswer + ")");
            holder.add(String.valueOf(previousAnswer));
            return;
        }

        //Check if buttonText is ready to perform a calculation
        if (buttonText.equals("=")) {

            if (holder.size() >= 3 || holder.contains("√")) {

                calculate();

                resultBox.setText(String.valueOf(result));
                newCalculation = true;

                previousAnswer = result;
            }

        }

    }

    //Method to perform the necessary calculations in order to obtain the answer
    private void calculate() {

        try {
            while (holder.size() > 1) {

                if (holder.indexOf("√") > -1) {

                    int index = holder.indexOf("√");

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = Math.sqrt(right);

                    holder.set(index, String.valueOf(result));

                    holder.remove(index + 1);

                }

                if (holder.indexOf("^") > -1) {

                    int index = holder.indexOf("^");
                    double left = 1, right = 1;

                    if (holder.get(index - 1).matches("-?\\d+(\\.\\d+)?")) {
                        left = Double.parseDouble(holder.get(index - 1));
                    }

                    if (holder.get(index + 1).matches("-?\\d+(\\.\\d+)?")) {
                        right = Double.parseDouble(holder.get(index + 1));
                    }


                    double result = Math.pow(left, right);

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }


                if (holder.indexOf("*") > -1) {
                    int index = holder.indexOf("*");

                    double left = Double.parseDouble(holder.get(index - 1));

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = left * right;

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }

                if (holder.indexOf("÷") > -1) {
                    int index = holder.indexOf("÷");

                    double left = Double.parseDouble(holder.get(index - 1));

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = left / right;

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }

                if (holder.indexOf("%") > -1) {
                    int index = holder.indexOf("%");

                    double left = Double.parseDouble(holder.get(index - 1));

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = left % right;

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }

                if (holder.indexOf("+") > -1) {
                    int index = holder.indexOf("+");

                    double left = Double.parseDouble(holder.get(index - 1));

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = left + right;

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }

                if (holder.indexOf("-") > -1) {
                    int index = holder.indexOf("-");

                    double left = Double.parseDouble(holder.get(index - 1));

                    double right = Double.parseDouble(holder.get(index + 1));

                    double result = left - right;

                    holder.set(index - 1, String.valueOf(result));

                    holder.remove(index);
                    holder.remove(index);

                }

            }

            result = Double.parseDouble(holder.get(0));

        } catch (RuntimeException e) {

            resultBox.setText("Calculation Error");

        }

    }
}
