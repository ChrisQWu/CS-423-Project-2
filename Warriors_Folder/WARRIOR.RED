; redcode
; name :  Handsome Jack
; author :  Team 12
; assert    CORESIZE == 8000 && MAXLENGTH >= 100
SPL #9,  4
JMN $8,  -1
CMP #2, <-3
SLT  0,  -3
CMP  -3,  -10
DJN #4,  1
MUL {-3,  0
LDP  8, @8
MOD  1, $-5
end