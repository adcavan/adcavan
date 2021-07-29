.macro	exit
		addi	$v0, $zero, 10
		syscall
.end_macro

.macro	print_int(%n)
		add		$a0, $zero, %n
		addi	$v0, $zero, 1
		syscall
.end_macro

.macro	read_int(%reg)
		addi 	$v0, $zero, 5
		syscall
		add		%reg, $zero, $v0
.end_macro

.macro	print_char(%n)
		add 	$a0, $zero, %n
		addi	$v0, $zero, 11
		syscall
.end_macro

.macro multiply(%reg, %n)
		addi    	$at, $zero, %n
		mul     	%reg, %reg, $at
.end_macro

.macro	branch_gte(%var1, %var2, %label)
		sub 	$k0, %var1, %var2				#var1 - var2: 
		bgez	$k0, %label						#if var1 - var2 >= 0, var1 is positive, goto label
.end_macro
.macro	branch_lt(%var1, %var2, %label)
		sub 	$k0, %var1, %var2				#var1 - var2
		bltz		$k0, %label						#if var1 - var2 <= 0, var1 is negative, goto label
.end_macro

.macro	branch_gt(%var1, %var2, %label)
		sub 	$k0, %var1, %var2				#k0 = 1: if var1 is less than var2. k0 = 0 if var1 is greater than var2
		bgtz	$k0, %label						#goto label if var1 > var2
.end_macro

.eqv	n		$t0
.eqv	m		$t1
.eqv	o		$t2
.eqv	i		$t3
.eqv	j1		$t4
.eqv	cond1	$s1
.eqv	cond2	$s2

main:			addi	m,	$zero, 0
				addi	o,	$zero, 1
				addi	i,	$zero, 0
				addi	j1,	$zero, 0
				
				read_int(n)
while1:			branch_gte(m, n, next1)				#	if m > n, end loop, else continue
				multiply(o, 2)						#	 o = o * 2
				#print_char('A')					#	loop counter
				branch_gt(o, n, next1)				#	if o > n, end loop
				add		m,	$zero,	o				#	m = o
				addi	j1,	j1,	1					#	j = j +1
				j		while1

next1:			sub 	n,	n,	 m					#	n = n - 1
				bne		n,	$zero,	print1			#	if  n!=0, branch to print 1
				bne		m,	$zero,	print1
				#add		cond1, $zero, n	
				#add		cond2, $zero, m
				#beq		cond1,	cond2, 	print0
print0:			print_int(0)
				j	for
print1:			print_int(1)

for:				sub		i,	j1,	1 
condfor:		blez	i,	exit
				addi	j1,	$zero,	0
				addi	m,	$zero,	0
				addi	o,	$zero,	1
while2:			branch_gte(m,	n,	next2)				#	if m > n, end loop
				multiply(o, 2)						#	 o = o*2
				#print_char('A')					#	loop counter
				branch_gt(o, n, next2)				#	if o > n, end loop
				add		m,	$zero,	o				#	m = o
				addi	j1,	j1,	1					#	j = j +1
				j		while2
				
next2:			sub 	n,	n,	 m
while3:			branch_lt(i, j1, next3)
				print_int(0)
				sub		i,	i,	1
				j	while3
				
next3:			bne		i,	$zero,	else_outer
				bne		n,	$zero,	else_inner
				print_int(0)
				j	for_inc
				
else_inner:		print_int(1)	
				j	for_inc	
						
else_outer:		print_int(1)

for_inc:			sub		i,	 i,	 1
				j	condfor

exit:			exit