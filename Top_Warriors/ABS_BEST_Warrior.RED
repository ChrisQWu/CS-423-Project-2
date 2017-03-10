; redcode
; name :  Handsome Jack
; author :  Team 12
; assert    CORESIZE == 8000 && MAXLENGTH >= 100
JMN  1, @-4
JMP #-1, >-4
ADD *8,  -3
DAT  5,  7
ADD  -4,  -3
JMN  -2,  -8
SUB  1, #6
end