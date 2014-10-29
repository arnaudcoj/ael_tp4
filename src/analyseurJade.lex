%%
%unicode

%xstate ENTIER_LU
%xstate FOIS_LU
%xstate PAS
%xstate ORIGINE_1
%xstate ORIGINE_2
%xstate REPETER_LU
%xstate REPETER_ENTIER

NOMBRE_ENTIER=[[:digit:]]+
ESPACES=" "*
DIRECTIONS="nord"|"sud"|"est"|"ouest"
%%
   
<YYINITIAL> "repeter"{ESPACES} {yybegin(REPETER_LU); return new ULMotClef(Token.repeter);}
<YYINITIAL> {ESPACES}"fin repeter" {return new ULMotClef(Token.fin);}
<YYINITIAL> {ESPACES}"fin definir" {return new ULMotClef(Token.fin);}
<YYINITIAL> "nord" {return new ULMotClef(Token.nord);}
<YYINITIAL> "sud" {return new ULMotClef(Token.sud);}
<YYINITIAL> "est" {return new ULMotClef(Token.est);}
<YYINITIAL> "ouest" {return new ULMotClef(Token.ouest);}
<YYINITIAL> "quitter" {return new ULMotClef(Token.eof);}
<YYINITIAL> "baisser" {return new ULMotClef(Token.baisser);}
<YYINITIAL> "lever" {return new ULMotClef(Token.lever);}
<YYINITIAL> "\n" {}
<YYINITIAL> {NOMBRE_ENTIER} {yybegin(ENTIER_LU); return new ULEntier(Integer.parseInt(yytext()));}
<YYINITIAL> "pas"{ESPACES} {yybegin(PAS); return new ULMotClef(Token.pas);}
<YYINITIAL> "origine(" {yybegin(ORIGINE_1); return new ULMotClef(Token.origine);}
<YYINITIAL> [^] {return new ULMotClef(Token.erreur);}

<REPETER_LU> {NOMBRE_ENTIER} {yybegin(REPETER_ENTIER); return new ULEntier(Integer.parseInt(yytext()));}
<REPETER_LU> "\n" {}

<REPETER_ENTIER> {ESPACES}"fois"{ESPACES} {yybegin(YYINITIAL); return new ULMotClef(Token.fois);}
<REPETER_ENTIER> "\n" {}

<ORIGINE_1> {NOMBRE_ENTIER}","{NOMBRE_ENTIER} {
  yybegin(ORIGINE_2);
  String[] strArray = yytext().split(",");
  return new ULPoint(Integer.parseInt(strArray[0]),Integer.parseInt(strArray[1]));
}
<ORIGINE_1> "\n" {}
<ORIGINE_1> [^] {return new ULMotClef(Token.erreur);}

<ORIGINE_2> ")" {yybegin(YYINITIAL);}
<ORIGINE_2> "\n" {}
<ORIGINE_2> [^] {return new ULMotClef(Token.erreur);}


<ENTIER_LU> {ESPACES}"fois"{ESPACES} {yybegin(FOIS_LU); return new ULMotClef(Token.fois);}
<ENTIER_LU> "\n" {}
<ENTIER_LU> [^] {return new ULMotClef(Token.erreur);}


<FOIS_LU> "nord" {yybegin(YYINITIAL) ; return new ULMotClef(Token.nord);}
<FOIS_LU> "sud" {yybegin(YYINITIAL) ; return new ULMotClef(Token.sud);}
<FOIS_LU> "est" {yybegin(YYINITIAL) ; return new ULMotClef(Token.est);}
<FOIS_LU> "ouest" {yybegin(YYINITIAL) ; return new ULMotClef(Token.ouest);}
<FOIS_LU> "\n" {}
<FOIS_LU> [^] {return new ULMotClef(Token.erreur);}


<PAS> {NOMBRE_ENTIER} {yybegin(YYINITIAL) ; return new ULEntier(Integer.parseInt(yytext()));}
<PAS> "\n" {}
<PAS> [^] {return new ULMotClef(Token.erreur);}
