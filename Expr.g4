/* Expr.g4 extended  */
grammar Expr;
// parser rules
prog : (assn ';' NEWLINE? | expr ';' NEWLINE?)*;
expr : expr op=(MUL|DIV) expr # MulDiv
     | expr op=(ADD|SUB) expr # AddSub
     | num		   # IntReal
     | ID 		   # Id
     | '(' expr ')'	   # Bracket
     ;
assn : ID op=EQ val=(INT|REAL)
     ;

num  : INT
     | REAL
     ;
// lexer rules
NEWLINE: [\r\n]+ ;
INT: [-+]?[0-9]+ ;
REAL: [-+]?[0-9]+'.'[0-9]* ;
ID: [a-zA-Z]+ ;
WS: [ \t\r\n]+ -> skip ;

MUL: '*' ;
DIV: '/' ;
ADD: '+' ;
SUB: '-' ;
EQ : '=' ;

