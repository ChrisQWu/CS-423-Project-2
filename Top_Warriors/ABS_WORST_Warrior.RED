; redcode
; name :  Handsome Jack
; author :  Team 12
; assert    CORESIZE == 8000 && MAXLENGTH >= 100
DAT {0,  4
ADD *4,  -4
JMN  2,  -1
JMZ  -5, #-6
MOV {2, $4
MOV  -5, #3
end