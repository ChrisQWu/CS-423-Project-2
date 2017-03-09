; redcode
; name :  competitor 1
; author :  Team 12
; assert    CORESIZE == 8000 && MAXLENGTH >= 100
DAT  -1, @-3
MOV }0, #-1
DAT  -1, @-3
LDP  5,  -3
SNE  1,  -5
SNE  -3, $-3
JMP  4,  0
end