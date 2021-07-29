//Antonio Cavan
//2015-65395

#include<stdio.h>
#include<stdlib.h>

#define n 100

typedef char StackElemType;
typedef struct stacknode StackNode;
struct stacknode{
	StackElemType INFO;
	StackNode *LINK;
};

typedef struct stack Stack;
struct stack{
	StackNode *top;
};


void initStack(Stack *S){
	S->top = NULL;
}
void StackOverflow(void){
	printf("Stack Overflow Detected.\n");
	exit(1);
}

void StackUnderflow(void){
	printf("Stack Underflow Detected.\n");
	exit(1);
}
int IsEmptyStack(Stack *S){
	return (S->top == NULL);
}

void PUSH(Stack *S, StackElemType x){
	StackNode *alpha;
	alpha = (StackNode *) malloc(sizeof(StackNode));
	if (alpha == NULL)
		StackOverflow();
	else{
		alpha -> INFO = x;
		alpha -> LINK = S->top;
		S->top = alpha;
	}
}

void POP(Stack *S, StackElemType *x){
	StackNode *alpha;
	if (S->top == NULL)
		StackUnderflow();
	else{
		alpha = S->top;
		*x = S->top->INFO;
		S->top = S->top->LINK;
		free(alpha);
	}
}

int EOS(char *);
int isOperand(char c);
int ICP(char c);
int ISP(char c);
int RANK(char c);
void POLISH(char *, char *, int r);

int main(){
	char choice;
	char infix[n];
	char postfix[n];
	int r = 0;
	while(n){
		printf("What would you like to do?\n");
		printf("1. Convert an expression from infix to postfix\n");
		printf("2. Evaluate a postfix expression\n");
		printf("3. Evaluate an infix expression\n");
		printf("0. Quit\n\n");
		scanf(" %c", &choice);
										printf("choice: %c\n", choice);
	
			switch(choice){
				case '1': 
					printf("Enter infix expression: ");
					scanf(" %s", infix);
					printf("Expression:%s\n", infix);
					POLISH(infix, postfix, r);
					break;
				case '2':
					printf("Enter infix expression: ");
					scanf("%s", infix);
					printf("Expression:%s\n", infix);
					break;
				case '3':
					printf("Enter infix expression: ");
					scanf("%s", infix);
					printf("Expression:%s\n", infix);
					break;
				case '0':
					printf("Goodbye!\n");
					exit(1);
				default:
				printf("Enter a number between 0-3\n");
			}
	}
}
int EOS(char *string){
	return (*string == '\0');
}

int isOperand(char c){
	return ((c >= 48 && c <= 57) || (c >= 65 && c <= 90));
}

int ICP(char c){
	if (c == '+' || c == '-')
		return 1;
	else if (c == '*' || c == '/' || c == '%')
		return 3;
	else if (c == '^')
		return 6;
}
int ISP(char c){
	if (c == '+' || c == '-')
		return 2;
	else if (c == '*' || c == '/' || c == '%')
		return 4;
	else if (c == '^')
		return 5;
	else if (c == '(')
		return 0;
}
int RANK(char c){
	if (c >= 48 && c <= 57)
		return (1);
	else if (c >= 37 && c <= 47)
		return (-1);
}

int FINDRANK(char *postfix, char r){
	char x;
	r = 0;
	while(!EOS(postfix)){
		x = *postfix;
		r = r + RANK(x);
	}
	return r;
}

void POLISH(char *infix, char *postfix, int r){
	Stack S; StackNode *P; int i = 0; int k = 0; char xtop; 
	initStack(&S);

	printf("Incoming symbol\tStackElements\t\tOutput\t\t\t\n");
		char x;
		while(!EOS(infix)){
		x = *infix;
		//P = S.top;
		
		//Incoming Symbol
	printf("%c\t\t", x);

		//case 1: Token Operand
		if(isOperand(x) == 1){
			postfix[i] = x;
			//printf("%c\n\n", postfix[i]); 
			i = i+1;	
		} 
		
		//case 2: Token '('
		else if(x == '('){
			PUSH(&S, x);
		}

		//case 3: Token ')'
		else if (x == ')'){
			while(!IsEmptyStack(&S)){
				POP(&S, &xtop);
				if (xtop != '(' || S.top != NULL){
					postfix[i] = xtop;
					i = i + 1;
				} //else if(IsEmptyStack(&S) == 1)
					//FINDRANK(postfix, r);
			}
		}

		//case 4: Token Operator
		else {
			//printf("in coming: %c\n\n", x);
			//printf("is stack empty? %d\n\n", IsEmptyStack(&S));
			
			while(!IsEmptyStack(&S)){
				POP(&S, &xtop);
				if (ICP(x) < ISP(xtop)){
					//printf("GOT IN HERE\n\n");
					postfix[i] = xtop;
					i = i + 1;
				} else {
					PUSH(&S, xtop);
					PUSH(&S, x);
				}
			}
		}
		//Stack Elements
		for(k = 0; P != NULL; k++){
			printf("%c", P->INFO);
			P = P->LINK;
		}
		//Output
		printf("%s\t\t\n", postfix);
			infix = infix + 1;
		}
	r = 0;
	/*
	while(!IsEmptyStack(&S)){
		POP(&S, &x);
		printf("Stack content: %c\n\n", x);
	}*/
	//printf("postfix expression: %s\n\n", postfix);
}