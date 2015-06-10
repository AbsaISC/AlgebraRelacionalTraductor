/***********************Codigo de Usuario************************/
package traductor;
import java_cup.runtime.*;

%% // inicio de opciones

%class AnalizadorLexico
%public
%full
%unicode
%line
%column
%cup

%{
	private Symbol symbol(int type){
		return new Symbol(type,yyline,yycolumn);
	}
	private Symbol symbol(int type,Object values){
		return new Symbol(type,yyline,yycolumn,values);
	}
%}

ALPHA=[A-Za-z]
DIGITO=[0-9]
ALPHANUMERIC={ALPHA}|DIGITO}

NUMERO=({DIGITO})+ | ({DIGITO})+.({DIGITO})+
IDENTIFICADOR={ALPHA}({ALPHANUMERIC} | [_])*

TABLA= "T."{IDENTIFICADOR}
SALTO=\n|\r|\r\n
ESPACIO=[ \t\f]
CADENA = \" ({NUMERO} | {ALPHANUMERIC}| _|-|.)*\"

%%//fin de opciones


<YYINITIAL>{
	"Π"		{return symbol(sym.PROY);}
	"σ"		{return symbol(sym.SELECT);}
	"∩"		{return symbol(sym.INTERCECTION);}
	"∪"		{return symbol(sym.UNION);}
	"*"		{return symbol(sym.CRUZ);}
	"-"		{return symbol(sym.DIFERENCIA);}
	"JOIN"	{return symbol(sym.JOIN);}
	","		{return symbol(sym.COMA);}
	"."		{return symbol(sym.PUNTO);}
	";"		{return symbol(sym.PyC);}
	"("		{return symbol(sym.PAR_I);}
	")"		{return symbol(sym.PAR_D);}
//	"["		{return symbol(sym.COR_I);}
//	"]"		{return symbol(sym.COR_D);}
	"&"		{return symbol(sym.AND);}
	"|"		{return symbol(sym.OR);}
	"~"		{return symbol(sym.NOT);}
	">"		{return symbol(sym.MAYOR);}
	">="	{return symbol(sym.MAYORIGUAL);}
	"<"		{return symbol(sym.MENOR);}
	"<="	{return symbol(sym.MENORIGUAL);}
	"="		{return symbol(sym.IGUAL);}
	"<>"	{return symbol(sym.DIFERENTE);}
	"LIKE"	{return symbol(sym.COMO);}
	{TABLA}	{return symbol(sym.TABLA,yytext());}
	{IDENTIFICADOR}	{return symbol(sym.IDENTIFICADOR,yytext());}
	{SALTO}			{}
	{NUMERO}		{return symbol(sym.NUMERO,yytext());}
	{CADENA}		{return symbol(sym.CADENA,yytext());}
	{ESPACIO}	{}
}

[^]	{throw new Error("token ilegal -> "+yytext());}