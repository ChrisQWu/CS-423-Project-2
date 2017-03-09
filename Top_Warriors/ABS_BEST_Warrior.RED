; redcode
; name :  Handsome Jack
; author :  Team 12
; assert    CORESIZE == 8000 && MAXLENGTH >= 100
MOV  0, @-1
JMP  1,  -2
JMN *-3, #-2
JMN  2,  5
JMN $-3, <-3
end