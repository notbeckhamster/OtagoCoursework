;
;  Enter your 6809 assembly language program here
;  Press the Assemble button below
;
;  No errors? Press Run in the panel to the left
;  The CPU will stop on breakpoints (in red)
;  Press Run to continue
;

screen EQU $0400

start:
   ldy #screen
loop:
   ;print the value woohoo
   ldd var1
   jsr printD
   lda #44
   sta ,y+
   ;swap var1 and var2
   ldd var1
   ldx var2
   std var2
   stx var1
   ;add
   ldd var1
   addd var2
   std var2
   ; check if 25th fib num
   cmpd #$B520
   bls loop
   ; remove commas woohoo
   lda #0
   sta -1,y
   rts


var1: 
   .byte 00,00

var2:
    .byte 00,01

printD:
   pshu d
   jsr printA
   pulu d
   exg a,b
   jsr printA
   rts

printA:
   pshu a
   lsra
   lsra
   lsra
   lsra
   jsr changeCodesA
   pulu a
   anda #%00001111
   jsr changeCodesA
   rts

changeCodesA:
   cmpa #9
   bls add48
   bhi add1

print:
   sta ,y+
   rts

add48:
   adda #48
   jmp print
 
add1:
   suba #9
   jmp print