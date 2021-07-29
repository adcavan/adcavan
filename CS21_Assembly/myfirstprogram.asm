# CS 21 WFVSWX -- S2 AY 17-18
# Antonio Cavan -- 03/09/18
# myfirstprogram.asm

.macro exit
	li	$v0, 10
	syscall
.end_macro

.text

main:	li	$t0, 3	#load 3 to t0
	li	$t1, 5	#load 5 to t1
	add	$t2, $t1, $t0	#adds  t1 t0 and stores to t2
	subu 	$t3, $t1, $t0
	#mul 	$t4, $t1, $t0
	mult	$t3, $t0
	#multu	$t1, $t0
	
	exit
.data 