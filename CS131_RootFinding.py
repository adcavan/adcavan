import math

# NEWTON'S METHOD (iterative) of ROOT FINDING
# x_k+1 = x_k - f(x_k)/f'(x_k) similarly,
# in this case, we just need to keep track of three things:
#	1. x_k (previous x)
#	2. f(x_k)
#	3. f'(x_k)
def newtons_method (value):
	#for our initial guess of x, we use the user input x in [0,1]
	x = value
	k = 0

	# we first initialize the value of f(X) as we will use this as a condition to stop the loop
	# (proof of f(x) and f'(x) is in documentation)
	fx = math.radians(math.sin(value)) - value
	f_prime = (math.cos(math.radians(x)))
	while (1): 
		fx = (math.sin(math.radians(x))) - value 
		f_prime = (math.cos(math.radians(x)))

		# Newton's Method application
		x_next = x - (fx / f_prime)

		# another condition to stop the loop:
		# the series have converged if the previous x is equal to the current x
		if (x == x_next):
			break

		#counting the number of iterations
		k = k + 1
		# # print current x for validation
		# print(k," | ",x_next)

		# we will now use the obtained x_k+1 as x in the next iteration 
		x = x_next

	print("\n[ Statistics ]")
	print("1. Iterations:		\t", k)
	print("2. F(x) last value: \t", fx)
	print("3. F'(x) last value:\t", f_prime)
	return x 


#For running

#change this for checking
value = 0.9
approx = newtons_method(value);
print("\narcsin(",value,") is approximately: ", approx)