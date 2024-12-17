screen EQU $0400

start:
   jsr setFibNum
   rts

setFibNum:
   ldx #screen
loop:
   ldd var1
   jsr displayNum
   ;add var1 and var2, store in var1
   ldd VAR2
   addd VAR1
   std VAR1
   ;swap var1 and var2
   ldy var1
   ldd var2
   sty var2
   std var1
   cmpd #8
   bls loop
   rts

displayNum: 
   addb #48
   stb 0,x+
   rts


VAR1: 
   .byte 00,00

VAR2:
   .byte 00,01