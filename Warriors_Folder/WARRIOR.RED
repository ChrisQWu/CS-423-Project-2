; redcode
; name :  Handsome Jack
; author :  Team 12
; assert    CORESIZE == 8000 && MAXLENGTH >= 100
JMZ  -6, @-4
SEQ  1,  -1
LDP  0,  1
STP $-1,  -5
STP $4, #-2
MOD  -1,  -4
DJN  5,  -2
DAT  -7,  -1
end