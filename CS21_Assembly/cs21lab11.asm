# CS 21 S2 18.1
# Antonio Cavan -- 10/13/18
# cs21lab11.asm

.macro	exit
		addi	$v0,	$zero,	10
		syscall
.end_macro

.macro	print_int(%n)
		add		$a0,	$zero,	%n
		addi	$v0,	$zero,	1
		syscall
.end_macro

.macro	read_int(%reg)
		addi 	$v0,	$zero,	5
		syscall
		add		%reg, 	$zero,	$v0
.end_macro

.macro	print_char(%n)
		add 	$a0,	$zero,	%n
		addi	$v0,	$zero,	11
		syscall
.end_macro
.macro print_string(%data)
		la		$a0,	%data
		li		$v0,	4
		syscall
.end_macro

.eqv		x	$s0
.eqv		y	$s1
.eqv		l	$s2
.eqv		m	$s3
.eqv		str	$t0
.eqv		input	$t1
.eqv		term	$t3
.eqv		first_occur	$t4

.text

main:	la		$a0, haystack
		la		$a1, needle
	
		jal 	STRSTR

		# Should print "azy azz 12345 dogs!"
		move	$a0, $v0
		li		$v0, 4
		syscall
		j	exit
		
error:	print_string(errormsg)	
exit:		li		$v0, 10
		syscall

STRSTR:
			la		str, 		($a1)
			la		input,	($a0)
			
traverse:		lb		$s4,		(str)
			lb		$s5,		(input)
			beq		$s4,		$s5,		compare
			addi	input,	input,		1
			lb		$s7,		1(input)
			beqz	$s7,		error
			j		traverse
			
compare:	move 	$s6,		input				# $s6 as marker
			addi	input,	input,		1		#incremeent
			addi	str,		str,			1		#increment
			lb		$s4,		(str)					#
			lb		$s5,		(input)
			lb		$s7,		1(str)	
			bne		$s4,		$s5,		traverse
			beqz	$s7,		back
			j		compare
			
back:		move	$v0,		$s6
			jr	$ra

.data

terminator:	.byte '\0'
needle:		.asciiz "wala"
haystack:	.asciiz "The quick brown lazy azz 12345 dogs!"
errormsg:	.asciiz "null"