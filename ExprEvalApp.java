import java.io.File;
import java.util.Scanner;
import org.antlr.v4.runtime.ANTLRInputStream;
import java.io.IOException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class ExprEvalApp {
   public static void main(String[] args) throws IOException {
      System.out.println("** Expression Eval w/ antlr-listener **");

      File file = new File(args[0]);
      Scanner sc = new Scanner(file);
      
      // Walk parse-tree and attach our listener
      ParseTreeWalker walker = new ParseTreeWalker();
      EvalListener listener = new EvalListener();   
      while(sc.hasNext()) {
         String s = sc.nextLine();
         
         ANTLRInputStream input = new ANTLRInputStream(s);
         // Get lexer
         ExprLexer lexer = new ExprLexer(input);
         // Get a list of matched tokens
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         // Pass tokens to parser
         ExprParser parser = new ExprParser(tokens);

         // walk from the root of parse tree
         walker.walk(listener, parser.prog());
         if(listener.mode == 0) {
            listener.resetMode();
            System.out.println(listener.getResult());
         }
      }
   }
} 

