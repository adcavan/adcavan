# CS 21 WFVSWX -- S2 AY 17-18
# Antonio Cavan -- 03/09/18
# cs21me7.asm

.macro exit
	li 	$v0, 10
	syscall
.end_macro

.macro print(%input)
	move 	$a0, %input
	li	$v0, 1
	syscall
.end_macro

.macro square(%reg1)
	mul	$t2, %reg1, %reg1	#squared value stored at $t2 
.end_macro

.macro cube(%reg1)
	la	$a3, (%reg1)	# register $a3 used as a temporary holder of %reg1 value
	square (%reg1)
	mul	$t3, $t2, $a3	#cubed value stored at $t3
	move	$a3, $zero
.end_macro

.macro input
	li	$v0, 5
	syscall
.end_macro

.text
main:
	
	input		#a
	move $s0, $2
	input		#b
	move $s1, $2
	input		#c
	move $s2, $2
	input		#d
	move $s3, $2
	
	input		#x
	move $k0, $2

	cube($k0)
	square($k0)
	
	mul $t3, $t3, $s0	#First term
	mul $t2, $t2, $s1	#second term
	mul $t4, $k0, $s2	#third term
	
	add $t5, $t2, $t3	#first + second term
	add $t5, $t5, $t4	#(first +second) + third term
	add $t5, $t5, $s3	#(first +second + third) + fourth term
	
	move $k1, $t5		
	exit
.data







