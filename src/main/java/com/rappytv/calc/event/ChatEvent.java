package com.rappytv.calc.event;

import com.rappytv.calc.Calculator;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.utils.ModColor;
import scala.actors.Eval;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ChatEvent implements MessageSendEvent {

    @Override
    public boolean onSend(String s) {
        String[] args = s.split(" ");

        if(args[0].equalsIgnoreCase("/" + Calculator.cmd)) {
            if(args.length < 2) {
                Calculator.get().getApi().displayMessageInChat("\u00A78\u00bb\n" + Calculator.prefix + ModColor.RED + "Usage: /" + Calculator.cmd + " <Operation>\n\u00A78\u00bb");
                return true;
            }
            StringBuilder operation = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                operation.append(args[i]);
            }

            try {
                Calculator.get().getApi().displayMessageInChat("\u00A78\u00bb\n" + Calculator.prefix + ModColor.GREEN + operation + " = " + formatNumber(calculation(operation.toString())) + "\n\u00A78\u00bb");
            } catch (Exception e) {
                Calculator.get().getApi().displayMessageInChat("\u00A78\u00bb\n" + Calculator.prefix + ModColor.RED + "Invalid operation!" + "\n\u00A78\u00bb");
            }
            return true;
        } else return false;
    }

    public static float calculation(String operation) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Object num = null;
        
        try {
            num = engine.eval(operation);
        } catch (Exception e) {
            Calculator.get().getApi().displayMessageInChat("\u00A78\u00bb\n" + Calculator.prefix + ModColor.RED + "Invalid operation!" + "\n\u00A78\u00bb");
            return 0;
        }

        if(num instanceof Float) {
            return (float) num;
        } else if(num instanceof Integer) {
            return (int) num;
        } else if(num instanceof Double) {
            double doubleNumber = (double) num;
            return (float) doubleNumber;
        } else {
            return 0;
        }
    }

    public static String formatNumber(float num) {
        return num % 1 == 0 ? Float.toString(num).substring(0, Float.toString(num).length() - 2) : Float.toString(num);
    }
}
