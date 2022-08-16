package com.rappytv.calc.event;

import com.rappytv.calc.Calculator;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.utils.ModColor;

public class ChatEvent implements MessageSendEvent {

    @Override
    public boolean onSend(String s) {
        String[] args = s.split(" ");

        if(args[0].equalsIgnoreCase("/" + Calculator.cmd)) {
            if(args.length != 4) {
                Calculator.get().getApi().displayMessageInChat(Calculator.prefix + ModColor.RED + "Usage: /" + Calculator.cmd + " <Number> <Operator> <Number>");
                return true;
            }
            String operation = args[2];
            float number1;
            float number2;

            try {
                number1 = Float.parseFloat(args[1]);
                number2 = Float.parseFloat(args[3]);
            } catch (NumberFormatException e) {
                Calculator.get().getApi().displayMessageInChat(Calculator.prefix + ModColor.RED + "On of your provided numbers is invalid!");
                return true;
            }
            if(!operation.equalsIgnoreCase("+") && !operation.equalsIgnoreCase("-") && !operation.equalsIgnoreCase("*") && !operation.equalsIgnoreCase("/")) {
                Calculator.get().getApi().displayMessageInChat(Calculator.prefix + ModColor.RED + "Invalid Operator! Valid operators are +, -, *, /");
                return true;
            }
            Calculator.get().getApi().displayMessageInChat(Calculator.prefix + ModColor.GREEN + formatNumber(number1) + " " + operation + " " + formatNumber(number2) + " = " + formatNumber(calculation(operation, number1, number2)));
            return true;
        } else return false;
    }

    public static float calculation(String operation, float num1, float num2) {
        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            default:
                return 0;
        }
    }

    public static String formatNumber(float num) {
        return num % 1 == 0 ? Float.toString(num).substring(0, Float.toString(num).length() - 2) : Float.toString(num);
    }
}
