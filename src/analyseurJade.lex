%%
%unicode

%xstate ENTIER_LU
%xstate FOIS_LU
%xstate PAS

NOMBRE_ENTIER=[[:digit:]]*
ESPACES=[[:space:]]*
DIRECTIONS="nord"|"sud"|"est"|"ouest"
%%
   
<YYINITIAL> "nord" {}
<YYINITIAL> "sud" {}
<YYINITIAL> "est" {}
<YYINITIAL> "ouest" {}
<YYINITIAL> "quitter" {}
<YYINITIAL> "baisser" {}
<YYINITIAL> "lever" {}
<YYINITIAL> "\n" {}
<YYINITIAL> {NOMBRE_ENTIER} {yybegin(ENTIER_LU); }
<YYINITIAL> "pas"{ESPACES} {yybegin(PAS); }
<YYINITIAL> "origine("{ESPACES}{NOMBRE_ENTIER}{ESPACES}","{ESPACES}{NOMBRE_ENTIER}{ESPACES}")" {}

<ENTIER_LU> {ESPACES}"fois"{ESPACES} {yybegin(FOIS_LU);}
<ENTIER_LU> "\n" {}
<FOIS_LU> {DIRECTIONS} {yybegin(YYINITIAL) ;}
<FOIS_LU> "\n" {}

<PAS> {NOMBRE_ENTIER} {yybegin(YYINITIAL) ;}
<PAS> "\n" {}

