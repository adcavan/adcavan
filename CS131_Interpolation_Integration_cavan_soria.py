# Cavan, Antonio (2015-65395)
# Soria, Spencer C. (2015-03835)
# CS 131 - THY

# We hereby declare that we have created this work completely on our own, no unauthorized assistance has been received or given in the completion of this work, and we have marked any citations accordingly.
# December 2020 - January 2021

import math
import numpy as np
from scipy.integrate import quad
import sympy 
from sympy import expand, symbols 


# For item 1,
## Cubic Spline Interpolation Algorithm ##
#READ comment block before each function for code explanation

#DEV SWITCH: True of False to toggle print lines
in_developer_mode = True


## MAIN ALGORITHM ##
	#Input: xData, yData
		#1: precalculate required values
		#2: construct and solve tridiagonal matrix
		#3: generate interpolants in each interval
		#4: extract coefficients of interpolants
	#Ouput: M (Matrix of coefficients)
def cubic_spline_interpolation(xData, yData):
	if in_developer_mode:
		print("[ CUBIC SPLINE INTERPOLATION ]")
		print("x: ", xData)
		print("y: ", yData)
	
	calculations = precalculations(xData, yData)
	z = tridiagonal_matrix(xData, yData, calculations)
	s_of_x = get_interpolants(xData, yData, calculations, z)
	M = get_coefficients(s_of_x)
	
	if in_developer_mode:
		print("\nCoefficient Matrix:")
	
	for i in range(len(xData)-1):
		for j in range(len(xData)-1):
			M[i][j] = round(M[i][j], 4)
		if in_developer_mode:
			print(M[i])
	
	if in_developer_mode:
		print("[END]")
	return M


#	step 1: do precalculations
	# Input: xData, yData
		#	1a: compute for h_array
		#	1b:	compute for b_array
		#	1c: compute for v_array
		#	1d: compute for u_array
		#	1e: z0 = zn = 0
	# Output: h[], b[], v[], u[]
def precalculations (xData, yData):
	n = len(xData)
	h = []
	h = [(xData[i+1] - xData[i])			for i in range(len(xData)-1)]
	b = [(1/h[i])*(yData[i+1] - yData[i])	for i in range(len(yData)-1)]
	v = [(2 * (h[i-1]+h[i]))				for i in range(1, len(h))]
	u = [(6 * (b[i] - b[i-1]))				for i in range(1, len(b))]
	z0 = 0
	zn = 0

	if in_developer_mode:
		print("-- start [ precalculations ] --")
		print("h: ",h,"\nb: ",b,"\nv: ", v, "\nu: ", u,"\nz0: ", z0, "\nzn: ", zn)
		print("-- end [ precalculations ] --\n")

	calculations = [h,v,u,z0,zn]
	return calculations


#	step 2: tridiagonal system
	#Input: xData, yData, h[], b[], v[], u[]
		#	2a: construct tridiagonal matrix
		#	2b: solve for tridiagonal matrix
	#Output: z[]
def tridiagonal_matrix (x, y, calculations):
	if in_developer_mode:
		print("-- start [ tridiagonal matrix ] --")

	h = calculations[0]
	v = calculations[1]
	u = calculations[2]
	z0 = calculations[3]
	zn = calculations[4]
	n = len(x) 

	t_matrix = [[0 for x in range(n-2)] for y in range(n-2)]
	for row in range(n-2):
		for col in range(n-2):
			if 		(col == row): 	t_matrix[row][col] = v[row]
			elif 	(col == row+1):	t_matrix[row][col] = h[row]
			elif	(col == row-1):	t_matrix[row][col] = h[row-1]
		if in_developer_mode:
			print(t_matrix[row],"[z",row+1,"] = [",u[row],"]")

	z = np.linalg.solve(t_matrix, u)
	z_list = z.tolist()
	z_list.insert(0,z0)
	z_list.append(zn)

	if in_developer_mode:
		print("z: ", z_list)
		print("-- end [ tridiagonal matrix ] -- \n")

	return z_list


#	step 3:	Generate interpolants
	#Input: xData, yData, h[], v[]
		#	3a: compute of s_array: apply formula per s_i
		#	3b: expand each interpolant
		#	3c: extract each coefficient
	#Ouput: cubic_spline_coefficients[]
def get_interpolants(xData, yData, calculations, z):
	if in_developer_mode:
		print("-- start [ get interpolants ] --")

	h = calculations[0]
	x = symbols('x')
	n = len(xData)

	s = []
	for i in range(n-1):
		first_term = sympy.expand((z[i]/6*h[i])*(xData[i+1] - x)**3)
		second_term = sympy.expand((z[i+1]/6*h[i])*(x - xData[i])**3)
		third_term = sympy.expand((yData[i+1]/h[i] - (h[i]*z[i+1])/6)*(x - xData[i]))
		fourth_term = sympy.expand((yData[i]/h[i] - (h[i]*z[i])/6)*(xData[i+1] - x))
		s.append(first_term + second_term + third_term + fourth_term)
		if in_developer_mode:
			print("s[",i,"] = ", s[i])
	
	if in_developer_mode:
		print("-- end [ get interpolants ] --")
	return s


#	step 4: Generate coefficient matrix
	#Input: s (list of polynomials)
		#4a: convert s_i to type polynomial (sympy.Poly)
		#4b: extract coefficients (sypmy.allcoeffs())
		#4c: reverse list (to comply with a0, a1, a2, a3 format)
		#4d: store list in array
	#Output: M (matrix of coefficients)
def get_coefficients(s):
	if in_developer_mode:
		print("\n-- start [ get coefficients ] --")
	
	M1 = []
	x = symbols("x")
	for i in range(len(s)):
		expr = sympy.Poly(s[i])
		coeffs = expr.all_coeffs()
		coeffs.reverse()
		if in_developer_mode:
			print(coeffs)
		M1.append(coeffs)
	M = np.asarray(M1)
	
	if in_developer_mode:
		print("-- end [ get coefficients ] --")
	
	return M


# For item 2,

# Given input x
# Solve for f(x) = x^2 * ln(x), the function to be integrated
# Return output
def f (x):
	return (x**2) * np.log(x)

# Given input a and b as the bounds of the integral and h as the step size
# Approximate the integral using Composite Trapezoid method
# Return output
def comp_trap (a, b, h):
    trap = (f(a) + f(b))/2
    cur = a + h
    while (cur < b):
        trap = trap + f(cur)
        cur = cur + h
    return trap * h
    
# Given input a and b as the bounds of the integral and h as the step size
# Approximate the integral using Composite Simpson's method
# Return output
def comp_simp (a, b, h):
    simp = f(a) + f(b)
    odd = 1
    cur = a + h
    while (cur < b):
        if (odd == 1):
            simp = simp + 4 * f(cur)
            odd = 0
        else:
            simp = simp + 2 * f(cur)
            odd = 1
        cur = cur + h
    return simp * h / 3


## "Main Method" ##

print("ITEM 1")

# For item 1,
# Cubic Spline DRIVER CODE : modify xData and yData to test algorithm
#Test Case 1
xData = [-1, 0, 1]
yData = [1, 2, -1]

#Test Case 2
# xData = [-1, 0, 1, 2]
# yData = [1.937, 1.3490, -0.995, 0]

#Test Case 3
# xData = [-1, 0, 1]
# yData = [1, 0, 1]

# #Test Case 4 (not successful)
# xData = [0.9, 1.3, 1.9, 2.1]
# yData = [1.3, 1.5, 1.85, 2.1]

cubic_spline_interpolation(xData, yData)


# For item 2,

b = 1.5
a = 1
#n = 5 # try as test value
n = float(input("\n\nITEM 2\nEnter n: "))
h = (b - a) / n
trap = comp_trap(a, b, h)
simp = comp_simp(a, b, h)
print("Composite Trapezoid: ", trap)
print("Composite Simpson's: ", simp)
ans, err = quad(f, a, b)
print("True value: ", ans)
print("Trapezoid Error: ", abs(ans-trap))
print("Simpson's Error: ", abs(ans-simp))