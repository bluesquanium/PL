import java.lang.*;
import java.util.*;
import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class EvalListener extends ExprBaseListener {
   // hash-map for variables' integer value for assignment
   Map<String, Double> vars = new HashMap<String, Double>(); 

   // stack for expression tree evaluation
   Stack<Double> evalStack = new Stack<Double>();
   // for assn
   String s;
   int mode = 1; // 0 : expr, 1 : assn

   @Override public void exitAddSub(ExprParser.AddSubContext ctx) {
      mode = 0;
      // ex) num1 + num2  ||  num1 - num2
      double num2 = evalStack.pop(), num1 = evalStack.pop();
      
      if(ctx.op.getType() == ExprParser.ADD) {
         evalStack.push(num1+num2);
      }
      else { // case of Sub
         evalStack.push(num1-num2);
      }
   }

   @Override public void exitMulDiv(ExprParser.MulDivContext ctx) {
      mode = 0;
      //For TEST
      //System.out.println(ctx.getText());

      // ex) num1 * num2  ||  num1 / num2
      double num2 = evalStack.pop(), num1 = evalStack.pop();
      
      if(ctx.op.getType() == ExprParser.MUL) {
         evalStack.push(num1*num2);
      }
      else { // case of Div
         evalStack.push(num1/num2);
      }
   }

   @Override public void exitBracket(ExprParser.BracketContext ctx) { }

   @Override public void exitAssn(ExprParser.AssnContext ctx) {
      mode = 1;
      s = ctx.ID().getText();
      vars.put(s, Double.parseDouble(ctx.val.getText()));
      //For TEST
      //System.out.println(ctx.ID().getText());
      //System.out.println(vars.get(s));
   }

   @Override public void exitIntReal(ExprParser.IntRealContext ctx) {
      evalStack.push(Double.parseDouble(ctx.getText()));
   }

   @Override public void exitId(ExprParser.IdContext ctx) {
      evalStack.push(vars.get(ctx.getText()));
   }

   
   public double getResult() {
      if(evalStack.empty()) { // prevent Error. "-1" means, it has some problems.
         return -1;
      }
      else {
         return evalStack.pop();
      }
   }

   public void resetMode() {
      mode = 1;
   }
}

