# CS 21 S2 18.1
# Antonio Cavan -- 10/20/18
# cs21me12.asm -- Recursion

.macro	print_int(%n)
		add		$a0,	$zero,	%n
		addi	$v0,	$zero,	1
		syscall
.end_macro

.text

main:		

			li,		$v0, 5
			syscall
			
			move	$a0, $v0
			jal		F		#all retunrn values should be in $v0
			move	$k0, $v0
			
			print_int($k0)

			li		$v0, 10
			syscall

#fib(n){
#	if(n == 0)
#		return 0;
#	if(n == 1)
#		return 1;
#	else 
#		return (f(n-1)+f(n-2));
#}

F:			subi	 	$sp, $sp, 48
			sw		$t1, 0($sp)
			sw		$t2, 4($sp)
			sw		$t3, 8($sp)
			sw		$t4, 12($sp)
			sw		$t5, 16($sp)
			sw		$t6, 20($sp)
			sw		$t7, 24($sp)
			sw		$t8, 28($sp)
			sw		$t9, 32($sp)
			sw		$s0, 36($sp)
			sw		$s1, 40($sp)
			sw		$ra,	44($sp)

			move	$t1, $a0		#$a0 = input from user
			blez	$t1, RET0
			
			
			#n = t1
			#a = n - 3, a == t2
			subi 	$t2, $t1, 3
			
			#b = f(a), b == t3
			move	$a0, $t2
			jal		F
			move	$t3, $v0
			
			#c = b - 1	t4 == c
			subi	$t4, $t3, 1

			#d = n - 1, d == t5
			subi	$t5, $t1, 1

			#e = f(d), e == t6
			move	$a0, $t5
			jal 	F
			move	$t6, $v0

			#f = n - 2, f == t7
			subi	$t7, $t1, 2

			#g = f(f), g == t8
			move	$a0, $t7
			jal		F
			move	$t8, $v0

			#h = e + g, t9 == h
			add 	$t9, $t6, $t8
			
			#i = f(h)
			move	$a0, $t9
			jal		F
			move	$s0, $v0

			#j = i + c
			add 	$s1, $s0, $t4

			move	$v0, $s1
			j 		End



RET0:		li		$v0, 0
			j		End
			
End:		
			
			lw		$t1, 0($sp)
			lw		$t2, 4($sp)
			lw		$t3, 8($sp)
			lw		$t4, 12($sp)
			lw		$t5, 16($sp)
			lw		$t6, 20($sp)
			lw		$t7, 24($sp)
			lw		$t8, 28($sp)
			lw		$t9, 32($sp)
			lw		$s0, 36($sp)
			lw		$s1, 40($sp)
			lw		$ra,	44($sp)
			addi	$sp, $sp, 48

			jr		$ra		