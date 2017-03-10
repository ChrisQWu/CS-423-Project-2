; redcode
; name :  Handsome Jack
; author :  Team 12
; assert    CORESIZE == 8000 && MAXLENGTH >= 100
SPL #9,  4
MUL  9,  1
DJN *-1,  -1
JMP  2,  -3
DJN  -5,  5
JMZ #-2, @-6
JMN }-2, $-1
JMZ }4,  0
LDP  8, @8
end