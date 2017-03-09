; redcode
; name :  Handsome Jack
; author :  Team 12
; assert    CORESIZE == 8000 && MAXLENGTH >= 100
SLT  -3,  1
LDP  1, <4
SPL  0, >-3
SNE  -3,  0
DJN #0,  0
SPL }-6,  8
JMN  -9,  -1
JMN  -9,  -9
SLT  2, <6
SNE }-4, $3
end